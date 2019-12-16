package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.util.concurrent.RateLimiter;

import left.rising.FECSearchDraft.dbrepos.CanCommitteeRepo;
import left.rising.FECSearchDraft.dbrepos.CandidateCommitteeId;
import left.rising.FECSearchDraft.dbrepos.CandidateData;
import left.rising.FECSearchDraft.dbrepos.CandidateDataRepo;
import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.dbrepos.PerStateCandDataRepo;
import left.rising.FECSearchDraft.dbrepos.State;
import left.rising.FECSearchDraft.dbrepos.StateRepo;
import left.rising.FECSearchDraft.entities.CandFundsPerState;
import left.rising.FECSearchDraft.entities.DBDonationResult;
import left.rising.FECSearchDraft.entities.PoliticalParty;
import left.rising.FECSearchDraft.entities.StateScheduleAResults;

@Controller
public class RobController {

	@Autowired
	ElResultRepo resRepo;

	@Autowired
	CandidateDataRepo cRepo;

	@Autowired
	CanCommitteeRepo canComRepo;

	@Autowired
	StateRepo sRepo;
	
	@Autowired
	PerStateCandDataRepo pscdRepo;

	@Value("${gmaps.key}")
	String gMapsKey;

	@Value("${fec.key}")
	String fecKey;

	List<State> states;

	RestTemplate rt = new RestTemplate();

	final RateLimiter rateLimiter = RateLimiter.create(2.0);

	@RequestMapping("/load-el-data")
	public ModelAndView loadElDataFromCSV() {
		if (resRepo.findAll().size() == 0) {
			BufferedReader br = null;
			try {
				// Loading presidential election result data
				br = new BufferedReader(new FileReader("us-presidential-election-results.csv"));
				String line = "";
				line = br.readLine();
				String[] fields;
				while ((line = br.readLine()) != null) {
					fields = line.split(",");

					int electionYear = Integer.valueOf(fields[0]);

					// Getting winning political party

					PoliticalParty winningParty;
					switch (fields[1].toLowerCase().charAt(0)) {
					case 'd':
						winningParty = PoliticalParty.DEMOCRAT;
						break;
					case 'r':
						winningParty = PoliticalParty.REPUBLICAN;
						break;
					default:
						throw new Exception("Invalid party input");
					}

					// Adding candidate names to candidates table if they aren't already there,
					// getting relevant candidate IDs
					String dCandName = fields[2];
					String rCandName = fields[3];

					if (cRepo.getCandidateIdFromName(rCandName).size() == 0) {
						cRepo.save(new CandidateData(rCandName, PoliticalParty.REPUBLICAN));
					}
					int rCandId = cRepo.getCandidateIdFromName(rCandName).get(0);
					if (cRepo.getCandidateIdFromName(dCandName).size() == 0) {
						cRepo.save(new CandidateData(dCandName, PoliticalParty.DEMOCRAT));
					}
					int dCandId = cRepo.getCandidateIdFromName(dCandName).get(0);

					resRepo.save(new ElResult(winningParty, rCandId, dCandId, electionYear));
				}

				br.close();
				// Loading committee IDs
				br = new BufferedReader(new FileReader("committee_ids.csv"));
				line = br.readLine();
				while ((line = br.readLine()) != null) {
					fields = line.split(",");

					System.out.println(fields[0]);

					int year = Integer.valueOf(fields[0]);
					CandidateData candidate = cRepo.getCandidateDataFromName(fields[1]).get(0);

					String committeeId = fields[2];

					System.out.println(line);

					canComRepo.save(new CandidateCommitteeId(year, candidate, committeeId));
				}
				br.close();

			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
		return new ModelAndView("index");
	}

	@RequestMapping("load-test-map")
	public ModelAndView loadMap() {
		ModelAndView view = new ModelAndView("test-map", "apiKey", gMapsKey);

		view.addObject("opacity", 0.9);

		states = sRepo.findAll();

		String idString = "";
		String stateCodeString = "";

		for (State s : states) {
			idString += s.getOSMStateId() + " ";
			stateCodeString += s.getStateCode() + " ";
		}

		view.addObject("idString", idString);
		view.addObject("stateCodes", stateCodeString);

		return view;
	}

	@RequestMapping("load-state-stats-page")
	public ModelAndView loadStateStatsPage(String stateCode) {
		ModelAndView mv = new ModelAndView("state-stats-page");
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<StateScheduleAResults> respEnt;
		ArrayList<StateScheduleAResults> resultList = new ArrayList<>();
		ArrayList<CandFundsPerState> candFunds = new ArrayList<>();
		
		/*
		 * NOTE: I had to rewrite this as a list of object arrays. 
		 * Also had to rewrite CandFundsPerState to include an integer reference to the row in the CandidateData table that contains the candidate we're looking at,
		 * rather than having a reference to the CandidateData class that was initially there, because saving the state data to MySQL was getting very messy and JpaRepository
		 * was not liking the fact that we had a table referencing a table that references yet another table.  So I changed it to an integer reference in order to get the 
		 * code to compile.  Changed the former candFunds to an ArrayList of object arrays because changing this CandFundsPerState reference to an integer meant I could no longer
		 * get the candidate name in the same way I was doing before, and I formerly learned that working with JpaRepos inside of classes was just about impossible, so I did this 
		 * to work around having to do that.  Please slack me if you have any questions.
		 */
		
		ArrayList<Object[]> candFundsArr = new ArrayList<>();

		List<CandidateCommitteeId> comIDList = canComRepo.findAll();
		List<ElResult> elResultList = resRepo.findAll();

		Map<CandidateData, Double> fundingMap = new HashMap<>();

		double totalWinCandFundsDonated = 0.0;
		double totalLoseCandFundsDonated = 0.0;
		double totalFundsDonated = 0.0;

		CandFundsPerState bmw = new CandFundsPerState(-1,0.0);

		CandFundsPerState bml = new CandFundsPerState(-1,0.0);

		CandFundsPerState smw = new CandFundsPerState(-1, Double.MAX_VALUE);

		CandFundsPerState sml = new CandFundsPerState(-1, Double.MAX_VALUE);

		String url = "";
		
		String stateName = sRepo.findByStateCode(stateCode).get(0).getStateName();
		mv.addObject("stateName",stateName);

		for (CandidateCommitteeId c : comIDList) {
			url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + fecKey + "&committee_id="
					+ c.getCommittee_id() + "&state=" + stateCode + "&per_page=100";
			System.out.println(url);
			respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, StateScheduleAResults.class);

			rateLimiter.acquire();
			StateScheduleAResults results = rt.getForObject(url, StateScheduleAResults.class);

			resultList.add(results);

			CandidateData thisCand = c.getCandidate_assigned();

			for (DBDonationResult r : results.getResults()) {
				if ((r.getCycle() % 4 == 0) && r.getCycle() < 2020 && r.getCycle() >= 1980) {

					ElResult thisResult = resRepo.findByElectionYear(r.getCycle()).get(0);

					double fundsDonated = r.getTotal();

					totalFundsDonated += fundsDonated;
					
					CandFundsPerState thisCandFunds = new CandFundsPerState(thisCand.getId(), fundsDonated);
					
					if ((candFunds.isEmpty()) || (fundsDonated <= candFunds.get(0).getFunds())) {
						candFundsArr.add(0,getCandidateInfo(thisCand.getId(), fundsDonated));
					}
					else if (fundsDonated >= candFunds.get(candFunds.size() - 1).getFunds()) {
						candFundsArr.add(getCandidateInfo(thisCand.getId(), fundsDonated));
					}
					else {
						/*candFunds.add(thisCandFunds);						
						candFunds.sort(new Comparator<CandFundsPerState>() {
							@Override
							public int compare(CandFundsPerState lhs, CandFundsPerState rhs) {
								if (lhs.getFunds() <= rhs.getFunds()) {
									return 1;
								}
								else{
									return -1;
								}
							}							
						});*/
						candFundsArr.add(getCandidateInfo(thisCand.getId(), fundsDonated));
						candFundsArr.sort(new Comparator<Object[]>() {
							public int compare(Object[] lhs, Object[] rhs) {
								if ((double)lhs[1] <= (double)rhs[1]) {
									return 1;
								}
								else {
									return -1;
								}
							}
						});

					}

					if (thisCand.getAfiliatedParty() == thisResult.getWinningParty()) {
						// Code for if committee c donated to a winning candidate this cycle
						totalWinCandFundsDonated += fundsDonated;
						if (fundsDonated > bmw.getFunds()) {
							bmw = thisCandFunds;
						} else if (fundsDonated < smw.getFunds()) {
							smw = thisCandFunds;
						}
					} else {
						// Code for if committee c donated to a losing candidate this cycle
						totalLoseCandFundsDonated += fundsDonated;
						if (fundsDonated > bml.getFunds()) {
							bml = thisCandFunds;
						}
						else if (fundsDonated < sml.getFunds()) {
							sml = thisCandFunds;
						}
					}
				}
			}
		}
		System.out.println(candFunds);

		mv.addObject("candFundsList", candFundsArr);
		mv.addObject("totalWinningFunds", totalWinCandFundsDonated);
		mv.addObject("totalLosingFunds", totalLoseCandFundsDonated);
		mv.addObject("totalFunds", totalFundsDonated);
		
		mv.addObject("bmw", getCandidateData(bmw.getCandId()));
		mv.addObject("bmwBudget", bmw.getFunds());

		mv.addObject("bml", getCandidateData(bml.getCandId()));
		mv.addObject("bmlBudget", bml.getFunds());
		
		mv.addObject("smw", getCandidateData(smw.getCandId()));
		mv.addObject("smwBudget", smw.getFunds());
		
		mv.addObject("sml", getCandidateData(sml.getCandId()));
		mv.addObject("smlBudget", sml.getFunds());

		return mv;
	}

	public Object[] getCandidateInfo(int candId, double budget) {
		return new Object[] {getCandidateData(candId).getName(),budget};
	}
	public CandidateData getCandidateData(int candId) {
		return cRepo.findById(candId).orElse(null);
	}
	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "testing");
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("api_key", fecKey);
		headers.add("location", "Detroit+MI");
		return headers;
	}
}
