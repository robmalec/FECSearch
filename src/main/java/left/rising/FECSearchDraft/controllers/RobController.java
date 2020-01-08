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
import left.rising.FECSearchDraft.dbrepos.CustomStateProperty;
import left.rising.FECSearchDraft.dbrepos.CustomStatePropertyRepo;
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
	CustomStatePropertyRepo cspRepo;

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

	static ArrayList<String> fecKeys;
	List<State> states;

	RestTemplate rt = new RestTemplate();

	final RateLimiter rateLimiter = RateLimiter.create(2.0);

	ArrayList<ArrayList<String>> dataMap = new ArrayList<>();

	Map<String, Integer> catIndexMap = new HashMap<>();

	int scRow = -1;
	int scCol = -1;

	Boolean verticalTable = null;

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
				
				//Reading in each line in the text file
				while ((line = br.readLine()) != null) {
					fields = line.split(",");

					int electionYear = Integer.valueOf(fields[0]);

					// Getting winning political party of this election
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

					//Saving all individual candidates to our database, if they aren't already there
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
				//Reading each line from committee IDs CSV
				while ((line = br.readLine()) != null) {
					fields = line.split(",");

					System.out.println(fields[0]);

					int year = Integer.valueOf(fields[0]);
					CandidateData candidate = cRepo.getCandidateDataFromName(fields[1]).get(0);

					String committeeId = fields[2];

					System.out.println(line);
					//Saving all candidate committes to our database
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

	@RequestMapping("load-state-stats-page")
	public ModelAndView loadStateStatsPage(String stateCode, int beginYear, int endYear) {

		// Loading FEC keys
		if ((fecKeys == null) || (fecKeys.isEmpty())) {
			fecKeys = new ArrayList<>();
			fecKeys.add(fecKey);
			fecKeys.add(fecKey2);
			fecKeys.add(fecKey3);
			fecKeys.add(fecKey4);
		}

		ModelAndView mv = new ModelAndView("state-stats-page");
		
		//Getting candidate data on this state from SQL repository
		List<CandFundsPerState> fundsFromThisState = cfpsRepo.findByStateCode(stateCode);

		// Testing: printing committee IDs and their associated candidates
		for (CandidateCommitteeId c : canComRepo.findAll()) {
			System.out.println("Committee ID: " + c.getId() + " Associated candidate: "
					+ c.getCandidate_assigned().getName() + " in year: " + c.getElection_year());
		}

		//Adding time range data to our ModelAndView
		mv.addObject("beginYear", beginYear);

		mv.addObject("endYear", endYear);

		String stateName = sRepo.findByStateCode(stateCode).get(0).getStateName();
		mv.addObject("stateName", stateName);

		//If we don't already have candidate data from this state, calling the APIs and downloading it
		if (fundsFromThisState.size() == 0) {
			HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
			ResponseEntity<StateScheduleAResults> respEnt;
			
			List<CandidateCommitteeId> comIDList = canComRepo.findAll();
			List<ElResult> elResultList = resRepo.findAll();
			
			Map<CandidateData, Double> fundingMap = new HashMap<>();

			String url = "";

			StateScheduleAResults results = null;

			// going through our list of candidate committee IDs
			for (CandidateCommitteeId c : comIDList) {

				for (String key : fecKeys) {
					try {
						url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + key
								+ "&committee_id=" + c.getCommittee_id() + "&state=" + stateCode + "&per_page=100";
						results = rt.getForObject(url, StateScheduleAResults.class);
						break;
					} catch (Exception e) {

					}
				}

				String candName = c.getCandidate_assigned().getName();

				CandidateData thisCand = c.getCandidate_assigned();

				// Going through each result
				for (DBDonationResult r : results.getResults()) {

					Boolean resultIsValidElectionYear = (((r.getCycle() % 4) == 0) && (r.getCycle() >= beginYear)
							&& (r.getCycle() < endYear));

					if (resultIsValidElectionYear) {
						ElResult thisResult = getResultOfYear(r.getCycle());

						Boolean candidateIsWinnerOrLoser = ((thisCand.getId() == thisResult.getWinnerId())
								|| (thisCand.getId() == thisResult.getLoserId()));
						if (candidateIsWinnerOrLoser) {
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
		ArrayList<Object[]> candFundsArr = new ArrayList<>();
		for (CandFundsPerState c : fundsFromThisState) {
			if (yearIsWithinRange(c.getYear(),beginYear,endYear)) {
				candFundsArr.add(getCandidateInfo(c.getCandId(),c.getFunds(),c.getYear()));
				thisCandFunds = c.getFunds();
				totalFunds += thisCandFunds;
				String thisCandName = getCandidateData(c.getCandId()).getName();

				ElResult result = getResultOfYear(c.getYear());

				// Determine if candidate is a winner or loser
				if (c.getCandId() == result.getWinnerId()) {
					totalWinningFunds += thisCandFunds;
					if (thisCandFunds > bmw.getFunds()) {
						bmw = c;
					}
					if (thisCandFunds < smw.getFunds()) {
						smw = c;
					}
				} else if (c.getCandId() == result.getLoserId()) {
					totalLosingFunds += thisCandFunds;
					if (thisCandFunds > bml.getFunds()) {
						bml = c;
					}
					if (thisCandFunds < sml.getFunds()) {
						sml = c;
					}
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

		
		// Sorting array of per-candidate state fundraising numbers

		mv.addObject("candFundsList", candFundsArr);
		mv.addObject("totalWinningFunds", formatDollarAmount(totalWinningFunds));
		mv.addObject("totalLosingFunds", formatDollarAmount(totalLosingFunds));
		mv.addObject("totalFunds", formatDollarAmount(totalFunds));

		double predictionScore = 100.0 * (totalWinningFunds / totalFunds);
		mv.addObject("predictionScore", String.format("%2.2f", predictionScore));

		mv.addObject("bmw", getCandidateData(bmw.getCandId()));
		mv.addObject("bmwBudget", formatDollarAmount(bmw.getFunds()));

		mv.addObject("bml", getCandidateData(bml.getCandId()));
		mv.addObject("bmlBudget", formatDollarAmount(bml.getFunds()));

		mv.addObject("smw", getCandidateData(smw.getCandId()));
		mv.addObject("smwBudget", formatDollarAmount(smw.getFunds()));

		mv.addObject("sml", getCandidateData(sml.getCandId()));
		mv.addObject("smlBudget", formatDollarAmount(sml.getFunds()));

		return mv;
	}

	@RequestMapping("show-load-custom-data-page")
	public ModelAndView showLoadCustomDataPage() {
		return new ModelAndView("load-custom-data");
	}

	@RequestMapping("load-custom-data")
	public ModelAndView loadCustomData(String filePath, int beginYear, int endYear) {
		// Clearing out data from previous load

		dataMap = new ArrayList<>();
		catIndexMap = new HashMap<>();
		verticalTable = null;
		scRow = -1;
		scCol = -1;

		BufferedReader br = null;
		try {
			// Loading presidential election result data
			System.out.println(filePath);

			br = new BufferedReader(new FileReader(filePath));
			String line = "";
			ArrayList<String> thisLineList;
			while ((line = br.readLine()) != null) {
				thisLineList = new ArrayList<>();
				for (String s : line.split(",")) {
					thisLineList.add(s);
				}
				dataMap.add(thisLineList);
			}
		} catch (Exception e) {

		}

		// Looking for newly imported table until it finds a cell that contains a state
		// name
		for (int rowNum = 0; rowNum < dataMap.size(); rowNum++) {
			for (int colNum = 0; colNum < dataMap.get(0).size(); colNum++) {
				if (getIsStateName(dataMap.get(rowNum).get(colNum))) {
					scRow = rowNum;
					scCol = colNum;
					break;
				}
			}
			if (scRow != -1) {
				break;
			}
		}

		// Checking cells around found cell to see if state names are in a row or a
		// column

		if (scRow != -1) {

			Boolean cellBelow = false;
			Boolean cellAbove = false;
			Boolean cellToRight = false;
			Boolean cellToLeft = false;

			int stateLabelIndex = -1;

			if (scRow != 0) {
				cellAbove = getIsStateName(dataMap.get(scRow - 1).get(scCol));
			}
			if (scRow != dataMap.size() - 1) {
				cellBelow = getIsStateName(dataMap.get(scRow + 1).get(scCol));
			}
			if (scCol != 0) {
				cellToLeft = getIsStateName(dataMap.get(scRow).get(scCol - 1));
			}
			if (scCol != dataMap.size() - 1) {
				cellToRight = getIsStateName(dataMap.get(scRow).get(scCol + 1));
			}

			if ((cellBelow) || (cellAbove)) {
				verticalTable = true;
				stateLabelIndex = scCol;
			} else if ((cellToRight || cellToLeft)) {
				verticalTable = false;
				stateLabelIndex = scRow;
			}
		}

		// If no state names have been found in the entire table (or a single one was
		// found by accident),
		// send user to an error message page
		if (verticalTable == null) {
			return new ModelAndView("data-import-error");
		}

		// Getting arrayList of found data categories
		ArrayList<String> categories;
		if (verticalTable) {
			categories = dataMap.get(0);
		} else {
			categories = new ArrayList<>();
			for (ArrayList<String> list : dataMap) {
				categories.add(list.get(0));
			}
		}

		// Saving indexes of these by name for easy retrieval after user has selected
		// name of category they're saving
		for (int i = 0; i < categories.size(); i++) {
			catIndexMap.put(categories.get(i), i);
		}

		ModelAndView mv = new ModelAndView("set-custom-data-settings");
		mv.addObject("categories", categories);
		mv.addObject("beginYear", beginYear);
		mv.addObject("endYear", endYear);

		return mv;
	}

	@RequestMapping("calculate-prediction-rate-and-compare")
	public ModelAndView calcPredictionRate(Integer beginYear, Integer endYear, String compCat) {
		double totalMoneyDonated = 0.0;
		double totalWinningMoneyDonated = 0.0;

		List<State> states = sRepo.findAll();
		ArrayList<Double> predictionRates = new ArrayList<>();
		ElResult thisYearResult = null;

		// Iterating through the states
		String scatterData = "";

		Boolean saveStateOpacity = ((cspRepo.findByCategory("predictionScore").size() == 0));

		for (State s : states) {
			totalMoneyDonated = 0.0;
			totalWinningMoneyDonated = 0.0;
			List<CandFundsPerState> candFundsList = cfpsRepo.findByStateCode(s.getStateCode());
			// Iterating through each presidential candidate for this state
			for (CandFundsPerState c : candFundsList) {

				// Adding money donated to total

				int year = c.getYear();

				// Checking if this candidate ran in a year that qualifies for this query given
				// bounds
				if ((year >= beginYear) && (year <= endYear)) {
					double thisFunds = c.getFunds();

					totalMoneyDonated += thisFunds;

					// Determining if this was a winning candidate and adding to totalWinningMoney
					// if so
					thisYearResult = resRepo.findByElectionYear(c.getYear()).get(0);
					if (c.getCandId() == thisYearResult.getWinnerId()) {
						totalWinningMoneyDonated += thisFunds;
					}

				}

			}

			// Divide this by total amount
			double pct = (totalWinningMoneyDonated / totalMoneyDonated);
			scatterData += "{x:" + pct + ",y:"
					+ cspRepo.getPropertyFromState(compCat, s.getStateCode()).get(0).getValue() + "}";

			// Code for calculating values for states' overall election prediction abilities
			if (saveStateOpacity) {
				cspRepo.save(new CustomStateProperty(s.getStateCode(), "predictionScore", pct));
			}
			if (s.getStateCode() != "PR") {
				scatterData += ",";
			}

		}
		ModelAndView mv = new ModelAndView("show-custom-data-chart");

		mv.addObject("category", compCat);
		mv.addObject("beginYear", beginYear);
		mv.addObject("endYear", endYear);
		mv.addObject("states", states);
		mv.addObject("scatterData", scatterData);

		return mv;
	}

	@RequestMapping("download-state-funds-per-candidate-data")
	public ModelAndView getCandFundsPerStateFromAPI() {
		int beginYear = 1980;
		int endYear = 2016;

		// Loading FEC keys
		if ((fecKeys == null) || (fecKeys.isEmpty())) {
			fecKeys = new ArrayList<>();
			fecKeys.add(fecKey);
			fecKeys.add(fecKey2);
			fecKeys.add(fecKey3);
			fecKeys.add(fecKey4);
		}

		for (State s : sRepo.findAll()) {

			if (cfpsRepo.findByStateCode(s.getStateCode()).size() == 0) {
				String stateCode = s.getStateCode();
				ArrayList<CandFundsPerState> fundsFromThisState = new ArrayList<>();

				// Running this code if there isn't any per-state search data saved in our DB
				HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
				ResponseEntity<StateScheduleAResults> respEnt;

				List<CandidateCommitteeId> comIDList = canComRepo.findAll();
				List<ElResult> elResultList = resRepo.findAll();

				String url = "";

				StateScheduleAResults results = null;

				// going through our list of candidate committee IDs
				for (CandidateCommitteeId c : comIDList) {
					//Iterating through our list of API keys until one of them works and we get the data back
					for (String key : fecKeys) {
						try {
							url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + key
									+ "&committee_id=" + c.getCommittee_id() + "&state=" + stateCode + "&per_page=100";
							results = rt.getForObject(url, StateScheduleAResults.class);
							break;
						} catch (Exception e) {
							System.out.println("Key " + key + " didn't work ");
						}
					}

					String candName = c.getCandidate_assigned().getName();

					CandidateData thisCand = c.getCandidate_assigned();

					System.out.println(results);
					// Going through each result returned by our API call
					for (DBDonationResult r : results.getResults()) {

						//Checking to see if this result pertains to a presidential election and falls within our time range
						Boolean resultIsValidElectionYear = (((r.getCycle() % 4) == 0) && (r.getCycle() >= beginYear)
								&& (r.getCycle() <= endYear));

						if (resultIsValidElectionYear) {
							ElResult thisResult = getResultOfYear(r.getCycle());

							//Making sure the API didn't send us faulty data
							Boolean candidateIsWinnerOrLoser = ((thisCand.getId() == thisResult.getWinnerId())
									|| (thisCand.getId() == thisResult.getLoserId()));
							
							
							if (candidateIsWinnerOrLoser) {
								/*For candidates with multiple fundraising committees: Checking to see if we already have an entry for that candidate
								in our fundraising database, if not, adding them to the database, and if so, adding the funds for this committee to the
								//existing candidate database entry*/
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
		}

		return new ModelAndView("redirect:/");

	}

	@RequestMapping("save-data-to-db")
	public ModelAndView saveDataToDb(String categories, int beginYear, int endYear) {

		String stateName = "";
		String data = "";

		//Getting the spreadsheet row/column for the dataset category requested by the user
		int catIndex = catIndexMap.get(categories);

		//TODO: Finish code that allows users to select and display multiple categories of data
		for (String s : categories.split("&&")) {

			if (cspRepo.findByCategory(s).size() == 0) {
				//Seeing if input table is vertically or horizontally oriented
				if (verticalTable) {
					for (int i = scRow; i < dataMap.size(); i++) {
						stateName = dataMap.get(i).get(scCol);

						//Getting the stateCode for data where the states' full names are listed rather than the 2-character code
						if (stateName.length() != 2) {
							System.out.println(stateName);
							stateName = sRepo.findByStateName(stateName).get(0).getStateCode();
						}

						data = dataMap.get(i).get(catIndex);
						//Saving custom data properties to our repository
						cspRepo.save(new CustomStateProperty(stateName, s, Double.valueOf(data)));
					}
				} else {
					for (int i = scCol; i < dataMap.get(0).size(); i++) {
						stateName = dataMap.get(scRow).get(i);

						if (stateName.length() != 2) {
							stateName = sRepo.findByStateName(stateName).get(0).getStateCode();
						}

						data = dataMap.get(catIndex).get(i);
						cspRepo.save(new CustomStateProperty(stateName, s, Double.valueOf(data)));
					}
				}
			}
		}
		ModelAndView mv = new ModelAndView("redirect:/calculate-prediction-rate-and-compare?beginYear=" + beginYear
				+ "&endYear=2016&compCat=" + categories);
		return mv;
	}

	/*Returns true if year falls within the inclusive range of [beginYear, endYear]*/
	Boolean yearIsWithinRange(int year, int beginYear, int endYear) {
		return ((year >= beginYear) && (year <= endYear));
	}

	/*
	 * Returns true if input is either a state name or stateCode
	 */
	Boolean getIsStateName(String input) {
		String[] stateArr = (input.length() > 2) ? PrimaryController.stateNames : PrimaryController.stateCodes;
		for (String s : stateArr) {
			if (input.equalsIgnoreCase(s)) {
				return true;
			}
		}
		return false;
	}

	//Returns the position of a specific candidate's presidential run within a list, and comes back as -1 if it fails to find anything
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

	//Gets the result of a given year
	ElResult getResultOfYear(int year) {
		try {
			return resRepo.findByElectionYear(year).get(0);
		} catch (Exception e) {
			System.out.println("No results for year " + year);
			return null;
		}
	}

	/*
	 * Removes scientific notation and adds commas to make monetary amounts more
	 * readable
	 */
	public String formatDollarAmount(double input) {
		return String.format("%,20.0f", input);
	}

	public Object[] getCandidateInfo(int candId, double budget, int year) {
		return new Object[] { getCandidateData(candId).getName(), formatDollarAmount(budget), year };
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
