package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.util.concurrent.RateLimiter;

import left.rising.FECSearchDraft.dbrepos.CanCommitteeRepo;
import left.rising.FECSearchDraft.dbrepos.CandidateCommitteeId;
import left.rising.FECSearchDraft.dbrepos.CandidateData;
import left.rising.FECSearchDraft.dbrepos.CandidateDataRepo;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.dbrepos.PrimaryLocationSearchResultRepo;
import left.rising.FECSearchDraft.dbrepos.PrimarySearchRepo;
import left.rising.FECSearchDraft.dbrepos.PrimaryStateSearch;
import left.rising.FECSearchDraft.dbrepos.SearchResultRepo;
import left.rising.FECSearchDraft.entities.DBDonation;
import left.rising.FECSearchDraft.entities.DBDonationResult;
import left.rising.FECSearchDraft.entities.LocationSearchResult;
import left.rising.FECSearchDraft.entities.PoliticalParty;
import left.rising.FECSearchDraft.entities.PrimaryLocationSearchResult;
import left.rising.FECSearchDraft.entities.StateScheduleAResults;

@Controller
public class PrimaryController {
	
	@Autowired
	PrimaryLocationSearchResultRepo plsrr;
	
	@Autowired
	ElResultRepo elr;

	@Autowired
	CandidateDataRepo cdr;

	@Autowired
	CanCommitteeRepo ccr;

	@Autowired
	SearchResultRepo lsrr;

	@Autowired
	PrimarySearchRepo psr;

	@Autowired
	CitySearchController csc = new CitySearchController();

	private RestTemplate rt = new RestTemplate();

	@Value("${fec.key}")
	String fecKey;

	@Value("${fec.key2}")
	String fecKey2;

	final RateLimiter rateLimiter = RateLimiter.create(2.0);

	private String[] stateCodes = { "AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC", "FL", "GA", "HI", "ID", "IL",
			"IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM",
			"NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI",
			"WY", "GU", "AA", "AS", "AE", "AP", "ZZ", "MP", "PR", "VI" };
	private String[] stateNames = { "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut",
			"Delaware", "Washington, D.C.", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa",
			"Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachussetts", "Michigan", "Minnesota",
			"Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico",
			"New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "Rhode Island",
			"South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
			"West Virginia", "Wisconsin", "Wyoming", "Guam", "Armed Forces Americas", "American Samoa",
			"Armed Forces Europe", "Armed Forces Pacific", "Foreign Countries", "Northern Mariana Islands",
			"Puerto Rico", "Virgin Islands" };
	
	@RequestMapping("test1")
	public ModelAndView test1() {
//		Map<String, Double> candidateTotalSumDonations = new HashMap<>();
//		candidateTotalSumDonations.put("test1", 0.0);
//		PrimaryLocationSearchResult plsr = new PrimaryLocationSearchResult("primaryCandidateName", candidateTotalSumDonations,
//				new HashMap<String, Integer>(), new HashMap<String, Double>(),
//				new HashMap<String, Double>(), new HashMap<String, Double>(),
//				new HashMap<String, Double>(), new HashMap<String, String>(),
//				new BigDecimal(0), "topFundraiser", "worstFundraiser", "city", "state");
//			plsrr.save(plsr);
			System.out.println(plsrr.findAll().get(0).getCandidateTotalSumDonations().toString());
		return null;
	}

	public PrimaryStateSearch getStateSearchResult(CandidateData c, String stateCode) {
		String url = "";
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());

		if (ccr.findByCandidateAssigned(c).get(0).getElection_year() == 2020) {
			// System.out.println("Started finding for: " + c.getName() + ", " + stateCode);
			ResponseEntity<StateScheduleAResults> respEnt = null;
			try {
				url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + fecKey + "&committee_id="
						+ ccr.findByCandidateAssigned(c).get(0).getCommittee_id() + "&state=" + stateCode
						+ "&per_page=100";
				rateLimiter.acquire();
				respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, StateScheduleAResults.class);
			} catch (HttpClientErrorException e) {
				url = "http://api.open.fec.gov/v1/schedules/schedule_a/by_state/?api_key=" + fecKey2 + "&committee_id="
						+ ccr.findByCandidateAssigned(c).get(0).getCommittee_id() + "&state=" + stateCode
						+ "&per_page=100";
				rateLimiter.acquire();
				respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, StateScheduleAResults.class);
			} catch (NullPointerException e) {
			}
			List<DBDonationResult> stateResults = respEnt.getBody().getResults();
			double stateTot = 0;
			for (DBDonationResult d : stateResults) {
				stateTot += d.getTotal();
			}
			// System.out.println("Finished finding for: " + c.getName());
			return new PrimaryStateSearch(c, stateTot, stateCode);
		}
		return null;
	}

	@RequestMapping("primary-states")
	public ModelAndView primaryStateResults() {
		ArrayList<CandidateData> candidates = new ArrayList<>();
		// for (int i = 19; i <= 53; i++) {
		for (int i = 19; i <= 53; i++) {
			candidates.add(cdr.getCandidateDataFromID(i).get(0));
		}
		// System.out.println(candidates.get(0).getName());
		for (CandidateData d : candidates) {
			for (String s : stateCodes) {
				if (psr.findByIdAndState(d, s) == null) {
					PrimaryStateSearch stateSearch = getStateSearchResult(d, s);
					psr.save(stateSearch);
				}
			}
		}

		System.out.println(stateCodes.length + " " + stateNames.length);
		HashMap<String, PrimaryStateSearch> topRaisers = new HashMap<>();
		HashMap<String, Double> candFundTot = new HashMap<>();
		for (String s : stateCodes) {
			List<PrimaryStateSearch> search = psr.findByState(s);
			PrimaryStateSearch top = search.get(0);
			for (PrimaryStateSearch s1 : search) {
				if (s1.getFunds() > top.getFunds()) {
					top = s1;
				}
				if (!candFundTot.containsKey(s1.getCandidate().getName())) {
					candFundTot.put(s1.getCandidate().getName(), s1.getFunds());
				} else {
					candFundTot.replace(s1.getCandidate().getName(),
							(candFundTot.get(s1.getCandidate().getName()) + s1.getFunds()));
				}
			}
			topRaisers.put(s, top);
		}
		String stateInfo = "";
		int i = 0;
		for (String s : stateCodes) {
			double percent = (new BigDecimal(topRaisers.get(s).getFunds()).divide(
					new BigDecimal(candFundTot.get(topRaisers.get(s).getCandidate().getName())), 4,
					RoundingMode.HALF_UP)).doubleValue() * 100;

			String description = "\"<div class=\\\"text-left\\\" style=\\\"border-top: solid 2pt;\\\"><strong>Top Fundraiser:</strong> "
					+ topRaisers.get(s).getCandidate().getName()
					+ "</div><div class=\\\"text-left\\\"><strong>Funds Raised Here:</strong> $"
					+ String.format("%,.2f", topRaisers.get(s).getFunds())
					+ "</div><div class=\\\"text-left\\\"><strong>% of Candidate's Total Funds:</strong> "
					+ String.format("%.2f", percent) + "%</div>\"";

			stateInfo += s + ": {name:\"" + stateNames[i] + "\", description: " + description
					+ ",color:\"default\",hover_color:\"default\",url:\"default\", hide: 'no'},";

			i++;
		}
		stateInfo = stateInfo.substring(0, stateInfo.length() - 1);
		ModelAndView mv = new ModelAndView("primary-state-results", "results", stateInfo);
		mv.addObject("thing", "thing");

		return mv;
	}

	@RequestMapping("primary-location-search")
	public ModelAndView primaryLocationSearch(String city, String state, String candidateName) {
		String id = ccr.findByCandidateAssigned(cdr.getCandidateDataFromName(candidateName).get(0)).get(0)
				.getCommittee_id();
		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, 2020) == null) {

		}
		List<DBDonation> donations = csc.getCandidateDonations(city, state, id, 2020);

		return null;
	}

	public HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "testing");
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("api_key", fecKey);
		headers.add("location", "Detroit+MI");
		return headers;
	}

	public String[] getStateCodes() {
		return stateCodes;
	}

	@RequestMapping("/load-primary-data")
	public ModelAndView loadElDataFromCSV() {
		BufferedReader br = null;
		String line = "";
		String[] fields;
		// Loading committee IDs
		try {
			br = new BufferedReader(new FileReader("committee_ids.csv"));
			line = br.readLine();
			while ((line = br.readLine()) != null) {
				fields = line.split(",");

				System.out.println(fields[0]);

				int year = Integer.valueOf(fields[0]);
				CandidateData candidate;
				if (cdr.getCandidateDataFromName(fields[1]).size() == 0) {
					if (fields[1].equals("Howie Hawkins")) {
						candidate = new CandidateData(fields[1], PoliticalParty.GREEN);
						cdr.save(candidate);
					} else if (fields[1].equals("Vermin Supreme") || fields[1].equals("Samm Tittle")) {
						candidate = new CandidateData(fields[1], PoliticalParty.INDEPENDENT);
						cdr.save(candidate);
					} else if (fields[1].equals("Adam Kokesh")) {
						candidate = new CandidateData(fields[1], PoliticalParty.LIBERTARIAN);
						cdr.save(candidate);
					} else if (fields[1].equals("Rocky de la Fuente") || fields[1].equals("Joe Collins")
							|| fields[1].equals("Joe Walsh")) {
						candidate = new CandidateData(fields[1], PoliticalParty.REPUBLICAN);
						cdr.save(candidate);
					} else {
						candidate = new CandidateData(fields[1], PoliticalParty.DEMOCRAT);
						cdr.save(candidate);
					}
				} else {
					candidate = cdr.getCandidateDataFromName(fields[1]).get(0);
				}

				ccr.save(new CandidateCommitteeId(year, candidate, fields[2]));
			}
			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ModelAndView("index");
	}

	public LocationSearchResult getLocationSearchResult(String city, String state, Integer electionYear,
			String committeeId) {
		// Declare variables
		String donationScatterData = "";
		double numDonations = 0;
		double largestSingleDonation = 0.0;
		int index = 0;
		int totalSumDonations = 0;
		ArrayList<Integer> rand = new ArrayList<>();
		ArrayList<DBDonation> donations = new ArrayList<>();

		// Populate the donations array with all donations to the candidate from the
		// selected location
		// donations.addAll(ac.getCandidateDonations(city, state, committeeId,
		// electionYear));

		// Gather the total number of winner and loser donations
		numDonations = donations.size();

		// Set an initial value for the largest winning and losing donations
		try {
			largestSingleDonation = donations.get(0).getContributionReceiptAmount();
		} catch (IndexOutOfBoundsException e) {

		}

		// Begin traversing through the array of donations to record information
		// about the donations. If donationScatterData exceeds 5500 characters,
		// break the loop.
		for (index = 0; index < donations.size() && donationScatterData.length() <= 5500; index++) {

			// Add donation amount to winner_total_donations
			totalSumDonations += donations.get(index).getContributionReceiptAmount();

			// If the donation is larger than the previously recorded largest individual
			// donation, save it to largest_winning_donation
			if (donations.get(index).getContributionReceiptAmount() > largestSingleDonation) {
				largestSingleDonation = donations.get(index).getContributionReceiptAmount();
			}

			// Generate a random array index from winnerDonations
			int randomInteger = (int) (((double) donations.size() - 1.0) * Math.random());

			// If the index location has already been generated, then generate a new random
			// index
			while (rand.contains(randomInteger) && rand.size() < donations.size() - 1) {
				randomInteger = (int) (((double) donations.size() - 1.0) * Math.random());
			}
			// If not, save it to the winRand list
			rand.add(randomInteger);
			// Create a string using the receipt date and amount of the randomly selected
			// donation and add it to the winnerDonationScatterData string. This string will
			// ultimately be used to populate a scatter chart displaying donations to the
			// winning candidate (it corresponds to the data object used by chartjs).
			if (donations.get(randomInteger).getContributionReceiptAmount() != null
					&& donations.get(randomInteger).getContributionReceiptDate() != null
					&& donations.get(randomInteger).getContributionReceiptAmount() > 0.0
					&& donationScatterData.length() <= 5800) {
				donationScatterData += "{x:" + donations.get(randomInteger).getContributionReceiptDate().substring(0, 4)
						+ "." + donations.get(randomInteger).getContributionReceiptDate().substring(5, 7)
						+ donations.get(randomInteger).getContributionReceiptDate().substring(8, 10) + ", y:"
						+ donations.get(randomInteger).getContributionReceiptAmount() + "},";
			}
		}

		// If the previous loop was broken before reaching the end of the
		// winnerDonations
		// array, then continue traversing through the array to record donation data.
		// However, the data will no longer be added to the scatter chart displayed in
		// the view.
		for (int i = index; i < donations.size(); i++) {
			totalSumDonations += donations.get(i).getContributionReceiptAmount();
			if (donations.get(i).getContributionReceiptAmount() > largestSingleDonation) {
				largestSingleDonation = donations.get(i).getContributionReceiptAmount();
			}
		}

		// Remove a comma from the end of the winner and loser scatter data strings.
		// Catches index out of bound in the event the string was empty.
		try {
			donationScatterData = donationScatterData.substring(0, donationScatterData.length() - 1);
		} catch (Exception e) {
		}

		// Calculate average winning and losing donations
		double avg_winning_donation = totalSumDonations / donations.size();
		// Make new object
		// Construct a LocationSearchResult object using the data gathered and return it

		return null;
	}
}
