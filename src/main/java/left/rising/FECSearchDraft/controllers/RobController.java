package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import left.rising.FECSearchDraft.dbrepos.CanFundsPerStateRepo;
import left.rising.FECSearchDraft.dbrepos.CandidateCommitteeId;
import left.rising.FECSearchDraft.dbrepos.CandidateData;
import left.rising.FECSearchDraft.dbrepos.CandidateDataRepo;
import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
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
	CanFundsPerStateRepo cfpsRepo;

	@Autowired
	StateRepo sRepo;

	@Value("${gmaps.key}")
	String gMapsKey;

	@Value("${fec.key}")
	String fecKey;

	@Value("${fec.key2}")
	String fecKey2;
	
	@Value("${fec.key3}")
	String fecKey3;
	
	@Value("${fec.key4}")
	String fecKey4;
	
	ArrayList<String> fecKeys;
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
		fecKeys = new ArrayList<>();
		fecKeys.add(fecKey);
		fecKeys.add(fecKey2);
		fecKeys.add(fecKey3);
		fecKeys.add(fecKey4);

		ModelAndView mv = new ModelAndView("state-stats-page");
		List<CandFundsPerState> fundsFromThisState = cfpsRepo.findByStateCode(stateCode);

		// Testing: printing committee IDs and their associated candidates
		for (CandidateCommitteeId c : canComRepo.findAll()) {
			System.out.println("Committee ID: " + c.getId() + " Associated candidate: "
					+ c.getCandidate_assigned().getName() + " in year: " + c.getElection_year());
		}

		if (fundsFromThisState.size() == 0) {
			// Running this code if there isn't any per-state search data saved in our DB
			HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
			ResponseEntity<StateScheduleAResults> respEnt;

			/*
			 * NOTE: I had to rewrite this as a list of object arrays. Also had to rewrite
			 * CandFundsPerState to include an integer reference to the row in the
			 * CandidateData table that contains the candidate we're looking at, rather than
			 * having a reference to the CandidateData class that was initially there,
			 * because saving the state data to MySQL was getting very messy and
			 * JpaRepository was not liking the fact that we had a table referencing a table
			 * that references yet another table. So I changed it to an integer reference in
			 * order to get the code to compile. Changed the former candFunds to an
			 * ArrayList of object arrays because changing this CandFundsPerState reference
			 * to an integer meant I no longer get the candidate name in the same way I was
			 * doing before, and I formerly learned that working with JpaRepos inside of
			 * classes was just about impossible, so I did this to work around having to do
			 * that. Please slack me if you have any questions.
			 */

			List<CandidateCommitteeId> comIDList = canComRepo.findAll();
			List<ElResult> elResultList = resRepo.findAll();

			Map<CandidateData, Double> fundingMap = new HashMap<>();

			String url = "";

			String stateName = sRepo.findByStateCode(stateCode).get(0).getStateName();
			mv.addObject("stateName", stateName);
			
			StateScheduleAResults results = null;

			// going through our list of candidate committee IDs
			for (CandidateCommitteeId c : comIDList) {
				
				for (String key : fecKeys) {
					try {
					url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + key + "&committee_id="
							+ c.getCommittee_id() + "&state=" + stateCode + "&per_page=100";
					results = rt.getForObject(url, StateScheduleAResults.class);
					break;
					}
					catch (Exception e) {
						
					}
				}
				

				String candName = c.getCandidate_assigned().getName();

				CandidateData thisCand = c.getCandidate_assigned();

				// Going through each result
				for (DBDonationResult r : results.getResults()) {

					

					Boolean resultIsValidElectionYear = (((r.getCycle() % 4) == 0) && (r.getCycle() >= 1980)
							&& (r.getCycle() < 2020));

					if (resultIsValidElectionYear) {
						ElResult thisResult = getResultOfYear(r.getCycle());
						
						
						
						Boolean candidateIsWinnerOrLoser = ((thisCand.getId() == thisResult.getWinnerId())
								|| (thisCand.getId() == thisResult.getLoserId()));
						if (candidateIsWinnerOrLoser) {
							System.out.println("Year: " + r.getCycle());
							System.out.println("Candidate name: " + getCandidateData(thisCand.getId()).getName());


							int posOfCandInList = listContainsCandidateInYear(fundsFromThisState, thisCand.getId(),
									r.getCycle());
							if (posOfCandInList == -1) {
								CandFundsPerState newCFPS = new CandFundsPerState(stateCode, thisCand.getId(),
										r.getCycle(), r.getTotal());
								fundsFromThisState.add(newCFPS);
								
							} else {
								fundsFromThisState.get(posOfCandInList).addFunds(r.getTotal());
							}
						}
					}
				}
			}
			
			// Finally saving everything to the DB
			for (CandFundsPerState c : fundsFromThisState) {
				cfpsRepo.save(c);
			}
		}

		// Calculating biggest and smallest money winners and losers from list

		CandFundsPerState bmw = new CandFundsPerState(stateCode, -1, -1, 0.0);
		CandFundsPerState bml = new CandFundsPerState(stateCode, -1, -1, 0.0);
		CandFundsPerState smw = new CandFundsPerState(stateCode, -1, -1, Double.MAX_VALUE);
		CandFundsPerState sml = new CandFundsPerState(stateCode, -1, -1, Double.MAX_VALUE);

		Double totalWinningFunds = 0.0;
		Double totalLosingFunds = 0.0;
		Double totalFunds = 0.0;

		Double thisCandFunds = 0.0;
		for (CandFundsPerState c : fundsFromThisState) {
			thisCandFunds = c.getFunds();
			totalFunds += thisCandFunds;
			String thisCandName = getCandidateData(c.getCandId()).getName();

			ElResult result = getResultOfYear(c.getYear());

			// Determine if candidate is a winner or loser
			if (c.getCandId() == result.getWinnerId()) {
				totalWinningFunds += thisCandFunds;
				if (thisCandFunds > bmw.getFunds()) {
					bmw = c;
				} else if (thisCandFunds < smw.getFunds()) {
					smw = c;
				}
			} else if (c.getCandId() == result.getLoserId()) {
				totalLosingFunds += thisCandFunds;
				if (thisCandFunds > bml.getFunds()) {
					bml = c;
				} else if (thisCandFunds < sml.getFunds()) {
					sml = c;
				}
			}
		}

		fundsFromThisState.sort(new Comparator<CandFundsPerState>() {
			public int compare(CandFundsPerState lhs, CandFundsPerState rhs) {
				if (lhs.getFunds() <= rhs.getFunds()) {
					return 1;
				} else {
					return -1;
				}
			}
		});

		ArrayList<Object[]> candFundsArr = new ArrayList<>();
		for (CandFundsPerState c : fundsFromThisState) {
			candFundsArr.add(getCandidateInfo(c.getCandId(), c.getFunds(), c.getYear()));
		}

		// Sorting array of per-candidate state fundraising numbers

		mv.addObject("candFundsList", candFundsArr);
		mv.addObject("totalWinningFunds", removeScientificNotation(totalWinningFunds));
		mv.addObject("totalLosingFunds", removeScientificNotation(totalLosingFunds));
		mv.addObject("totalFunds", removeScientificNotation(totalFunds));

		mv.addObject("bmw", getCandidateData(bmw.getCandId()));
		mv.addObject("bmwBudget", removeScientificNotation(bmw.getFunds()));

		mv.addObject("bml", getCandidateData(bml.getCandId()));
		mv.addObject("bmlBudget", removeScientificNotation(bml.getFunds()));

		mv.addObject("smw", getCandidateData(smw.getCandId()));
		mv.addObject("smwBudget", removeScientificNotation(smw.getFunds()));

		mv.addObject("sml", getCandidateData(sml.getCandId()));
		mv.addObject("smlBudget", removeScientificNotation(sml.getFunds()));

		return mv;
	}

	public int listContainsCandidateInYear(List<CandFundsPerState> list, int candId, int year) {
		int i = 0;
		for (CandFundsPerState c : list) {
			if ((c.getCandId() == candId) && (c.getYear() == year)) {
				return i;
			}
			i++;
		}
		return -1;
	}

	ElResult getResultOfYear(int year) {
		try {
			return resRepo.findByElectionYear(year).get(0);
		} catch (Exception e) {
			System.out.println("No results for year " + year);
			return null;
		}
	}

	public String removeScientificNotation(double input) {
		String output = String.format("%20.0f", input);
		return output;
	}

	public Object[] getCandidateInfo(int candId, double budget, int year) {
		return new Object[] { getCandidateData(candId).getName(), removeScientificNotation(budget), year };
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
