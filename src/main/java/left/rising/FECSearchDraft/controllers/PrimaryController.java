package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

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
import left.rising.FECSearchDraft.dbrepos.PrimaryCandidateLocationSearchInfoRepo;
import left.rising.FECSearchDraft.dbrepos.PrimaryLocationSearchResultRepo;
import left.rising.FECSearchDraft.dbrepos.PrimarySearchRepo;
import left.rising.FECSearchDraft.dbrepos.PrimaryStateSearch;
import left.rising.FECSearchDraft.dbrepos.SearchResultRepo;
import left.rising.FECSearchDraft.entities.DBDonation;
import left.rising.FECSearchDraft.entities.DBDonationResult;
import left.rising.FECSearchDraft.entities.PoliticalParty;
import left.rising.FECSearchDraft.entities.PrimaryCandidateLocationSearchInfo;
import left.rising.FECSearchDraft.entities.StateScheduleAResults;

@Controller
public class PrimaryController {

	@Autowired
	PrimaryLocationSearchResultRepo plsrr;

	@Autowired
	PrimaryCandidateLocationSearchInfoRepo pclsir;

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

	public PrimaryStateSearch getStateSearchResult(CandidateData c, String stateCode) {
		String url = "";
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		System.out.println(c.getName());
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
		for (int i = 19; i <= 52; i++) {
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

	public PrimaryCandidateLocationSearchInfo getPrimaryLocationSearchResult(String city, String state,
			CandidateData candidate) {
		if (pclsir.findByCandidateNameAndCityAndState(candidate.getName(), city, state) != null) {
			return pclsir.findByCandidateNameAndCityAndState(candidate.getName(), city, state);
		}
		System.out.println(candidate.getName());
		double totalSumDonations = 0;

		double totalNumDonations = 0;

		double avgDonation = 0;

		double largestDonation = 0;

		double percentDonationsForState = 0;

		double percentTotalDonations = 0;
		String donationScatterData = "";
		int index;
		ArrayList<Integer> rand = new ArrayList<>();
		List<DBDonation> donations = csc.getCandidateDonations(city, state,
				ccr.findByCandidateAssigned(candidate).get(0).getCommittee_id(), 2020);
		totalNumDonations = donations.size();
		System.out.println(totalNumDonations);
		for (index = 0; index < donations.size() && donationScatterData.length() <= 5500; index++) {

			totalSumDonations += donations.get(index).getContributionReceiptAmount();
			if (donations.get(index).getContributionReceiptAmount() > largestDonation) {
				largestDonation = donations.get(index).getContributionReceiptAmount();
			}
			int randomInteger = (int) (((double) donations.size() - 1.0) * Math.random());

			// If the index location has already been generated, then generate a new random
			// index
			while (rand.contains(randomInteger) && rand.size() < donations.size() - 1) {
				randomInteger = (int) (((double) donations.size() - 1.0) * Math.random());
			}
			// If not, save it to the winRand list
			rand.add(randomInteger);
			if (donations.get(randomInteger).getContributionReceiptAmount() != null
					&& donations.get(randomInteger).getContributionReceiptDate() != null
					&& donations.get(randomInteger).getContributionReceiptAmount() > 0.0
					&& donationScatterData.length() <= 5500) {
				donationScatterData += "{x:" + donations.get(randomInteger).getContributionReceiptDate().substring(0, 4)
						+ "." + donations.get(randomInteger).getContributionReceiptDate().substring(5, 7)
						+ donations.get(randomInteger).getContributionReceiptDate().substring(8, 10) + ", y:"
						+ donations.get(randomInteger).getContributionReceiptAmount() + "},";
			}
		}/*
		for (int i = index; i < donations.size(); i++) {
			totalSumDonations += donations.get(index).getContributionReceiptAmount();
			if (donations.get(index).getContributionReceiptAmount() > largestDonation) {
				largestDonation = donations.get(index).getContributionReceiptAmount();
			}
		}*/
		avgDonation = totalSumDonations / totalNumDonations;
		System.out.println("TotalLocationDonations:" + new BigDecimal(totalSumDonations));
		try {
			percentDonationsForState = (new BigDecimal(totalSumDonations)
					.divide(new BigDecimal(psr.findByIdAndState(candidate, state).getFunds()), 4, RoundingMode.HALF_UP))
							.doubleValue()
					* 100;
		} catch (ArithmeticException e) {
			percentDonationsForState = 0.0;
		}
		double totalDonations = 0;
		for (String s : stateCodes) {
			if (s.equals("RI")) {
			}
			totalDonations += psr.findByIdAndState(candidate, s).getFunds();
		}
		System.out.println(totalDonations);
		try {
			percentTotalDonations = (new BigDecimal(totalSumDonations).divide(new BigDecimal(totalDonations), 4,
					RoundingMode.HALF_UP)).doubleValue() * 100;
		} catch (ArithmeticException e) {
			percentTotalDonations = 0;
		}
		PrimaryCandidateLocationSearchInfo pclsi = new PrimaryCandidateLocationSearchInfo(candidate.getName(), city,
				state, totalSumDonations, totalNumDonations, avgDonation, largestDonation, percentDonationsForState,
				percentTotalDonations, donationScatterData);
		pclsir.save(pclsi);
		return pclsi;
	}

	@RequestMapping("primary-location-search")
	public ModelAndView primaryLocationSearch(String city, String state) {
		ArrayList<CandidateData> candidates = new ArrayList<>();
		List<PrimaryCandidateLocationSearchInfo> searches = new ArrayList<>();
		double avgDonTot = 0;
		double avgDon = 0;
		double totDon = 0;
		PrimaryCandidateLocationSearchInfo winner = new PrimaryCandidateLocationSearchInfo();
		for (int i = 19; i <= 52; i++) {
			candidates.add(cdr.getCandidateDataFromID(i).get(0));
		}
		// System.out.println(candidates.get(0).getName());
		for (CandidateData d : candidates) {
			PrimaryCandidateLocationSearchInfo stateSearch = getPrimaryLocationSearchResult(city, state, d);
			searches.add(stateSearch);
		}
		HashMap<String, String> urls = new HashMap<>();
		HashMap<String, String> colors = new HashMap<>();
		HashMap<String, String> parties = new HashMap<>();
		for (PrimaryCandidateLocationSearchInfo p : searches) {
			avgDonTot += (int) p.getAvgDonation();
			totDon += p.getTotalSumDonations();
			if ((int) p.getTotalNumDonations() > winner.getTotalNumDonations()) {
				winner = p;
			}
			String url = "";
			for (int i = 0; i < p.getCandidateName().split(" ").length; i++) {
				url += p.getCandidateName().split(" ")[i];
			}
			urls.put(p.getCandidateName(), "Candidate_Photos/" + url + ".jpg");
			switch (cdr.getCandidateDataFromName(p.getCandidateName()).get(0).getAfiliatedParty()) {
			case REPUBLICAN:
				colors.put(p.getCandidateName(), "#de0000");
				parties.put(p.getCandidateName(), "Republican");
				break;
			case DEMOCRAT:
				colors.put(p.getCandidateName(), "#0071cd");
				parties.put(p.getCandidateName(), "Democrat");
				break;
			case LIBERTARIAN:
				colors.put(p.getCandidateName(), "#f9d334");
				parties.put(p.getCandidateName(), "Libertarian");
				break;
			case GREEN:
				colors.put(p.getCandidateName(), "#17aa5c");
				parties.put(p.getCandidateName(), "Green Party");
				break;
			case INDEPENDENT:
				colors.put(p.getCandidateName(), "#ff10c8");
				parties.put(p.getCandidateName(), "Independent");
				break;
			}
		}
		System.out.println(winner.getPercentDonationsForState());
		avgDon = avgDonTot / searches.size();
		ModelAndView mv = new ModelAndView("primary-location-search-results");
		mv.addObject("parties", parties);
		mv.addObject("colors", colors);
		mv.addObject("totDon", String.format("%,.2f", totDon));
		mv.addObject("totWinDon", String.format("%,.2f", winner.getTotalSumDonations()));
		mv.addObject("bigWinDon", String.format("%,.2f", winner.getLargestDonation()));
		mv.addObject("avgDon", String.format("%,.2f", avgDon));
		mv.addObject("avgWinDon", String.format("%,.2f", winner.getAvgDonation()));
		mv.addObject("majname", winner.getCandidateName());
		mv.addObject("result", searches);
		mv.addObject("location", searches.get(0).getCity() + ", " + searches.get(0).getState());
		mv.addObject("urls", urls);
		mv.addObject("scatDat", winner.getDonationScatterData());
		mv.addObject("winner", winner);
		return mv;
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

}
