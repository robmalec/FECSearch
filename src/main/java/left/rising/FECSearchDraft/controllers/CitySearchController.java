package left.rising.FECSearchDraft.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.dbrepos.SearchResultRepo;
import left.rising.FECSearchDraft.entities.DBDonation;
import left.rising.FECSearchDraft.entities.LocationSearchResult;
import left.rising.FECSearchDraft.entities.PoliticalParty;
import left.rising.FECSearchDraft.entities.ScheduleAResults;
import left.rising.FECSearchDraft.entities.loserCommitteeIds;
import left.rising.FECSearchDraft.entities.winnerCommitteeIds;

@Controller
public class CitySearchController {

	@Autowired
	ElResultRepo elr;

	@Autowired
	CandidateDataRepo cdr;

	@Autowired
	CanCommitteeRepo ccr;

	@Autowired
	SearchResultRepo lsrr;

	private RestTemplate rt = new RestTemplate();

	@Value("${fec.key}")
	String fecKey;

	@Value("${fec.key2}")
	String fecKey2;

	@RequestMapping("/test-search")
	public ModelAndView testSearch() {
		return new ModelAndView("test-search");
	}

	@RequestMapping("/confirm-page")
	public ModelAndView confirm() {
		return new ModelAndView("confirm-page");
	}

	@RequestMapping("/test-search-historical")
	public ModelAndView testSearchHistorical() {
		return new ModelAndView("test-search-historical");
	}

	public LocationSearchResult getLocationSearchResult(String city, String state, Integer electionYear) {
		// Declare variables
		ElResult result = elr.findByElectionYear(electionYear).get(0);
		Integer winner_id = result.getWinnerId();
		Integer loser_id = result.getLoserId();
		CandidateData winner = cdr.getCandidateDataFromID(winner_id).get(0);
		CandidateData loser = cdr.getCandidateDataFromID(loser_id).get(0);
		String winner_name = cdr.getCandidateNameFromId(winner_id).get(0);
		String loser_name = cdr.getCandidateNameFromId(loser_id).get(0);
		String winnerDonationScatterData = "";
		String loserDonationScatterData = "";
		List<DBDonation> winnerDonations = new ArrayList<>();
		List<DBDonation> loserDonations = new ArrayList<>();
		List<winnerCommitteeIds> winner_committee_ids = new ArrayList<>();
		List<loserCommitteeIds> loser_committee_ids = new ArrayList<>();
		int total_winners = 0;
		int total_losers = 0;
		double winner_total_donations = 0;
		double loser_total_donations = 0;
		double largest_winning_donation = 0.0;
		double largest_losing_donation = 0.0;
		int winnerIndex = 0;
		int loserIndex = 0;
		int numWinnerDonations = 0;
		int numLoserDonations = 0;
		ArrayList<Integer> winRand = new ArrayList<>();
		ArrayList<Integer> loseRand = new ArrayList<>();

		// Populate the winnerDonations and loserDonations arrays with all donations to
		// the winning and losing candidates from the selected location
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(winner)) {
			if (c.getElection_year().equals(electionYear)) {
				winnerDonations.addAll(getCandidateDonations(city, state, c.getCommittee_id(), electionYear));
			}
		}
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(loser)) {
			if (c.getElection_year().equals(electionYear)) {
				loserDonations.addAll(getCandidateDonations(city, state, c.getCommittee_id(), electionYear));
			}
		}

		// Gather the total number of winner and loser donations
		numWinnerDonations = winnerDonations.size();
		numLoserDonations = loserDonations.size();

		// Record whether the majority of donations from the location went to the
		// winning or losing candidate
		if (numWinnerDonations > numLoserDonations) {
			total_winners += 1;
		} else {
			total_losers += 1;
		}

		// Set an initial value for the largest winning and losing donations
		try {
			largest_winning_donation = winnerDonations.get(0).getContributionReceiptAmount();
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			largest_losing_donation = loserDonations.get(0).getContributionReceiptAmount();
		} catch (IndexOutOfBoundsException e) {
		}

		// Begin traversing through the array of winnerDonations to record information
		// about the donations. If winnerDonationScatterData exceeds 5500 characters,
		// break the loop.
		for (winnerIndex = 0; winnerIndex < winnerDonations.size()
				&& winnerDonationScatterData.length() <= 5500; winnerIndex++) {

			// Add donation amount to winner_total_donations
			winner_total_donations += winnerDonations.get(winnerIndex).getContributionReceiptAmount();

			// If the donation is larger than the previously recorded largest individual
			// donation, save it to largest_winning_donation
			if (winnerDonations.get(winnerIndex).getContributionReceiptAmount() > largest_winning_donation) {
				largest_winning_donation = winnerDonations.get(winnerIndex).getContributionReceiptAmount();
			}

			// Generate a random array index from winnerDonations
			int randomInteger = (int) (((double) winnerDonations.size() - 1.0) * Math.random());

			// If the index location has already been generated, then generate a new random
			// index
			while (winRand.contains(randomInteger) && winRand.size() < winnerDonations.size() - 1) {
				randomInteger = (int) (((double) winnerDonations.size() - 1.0) * Math.random());
			}
			// If not, save it to the winRand list
			winRand.add(randomInteger);
			// Create a string using the receipt date and amount of the randomly selected
			// donation and add it to the winnerDonationScatterData string. This string will
			// ultimately be used to populate a scatter chart displaying donations to the
			// winning candidate (it corresponds to the data object used by chartjs).
			if (winnerDonations.get(randomInteger).getContributionReceiptAmount() != null
					&& winnerDonations.get(randomInteger).getContributionReceiptDate() != null
					&& winnerDonations.get(randomInteger).getContributionReceiptAmount() > 0.0
					&& winnerDonationScatterData.length() <= 5800) {
				winnerDonationScatterData += "{x:"
						+ winnerDonations.get(randomInteger).getContributionReceiptDate().substring(0, 4) + "."
						+ winnerDonations.get(randomInteger).getContributionReceiptDate().substring(5, 7)
						+ winnerDonations.get(randomInteger).getContributionReceiptDate().substring(8, 10) + ", y:"
						+ winnerDonations.get(randomInteger).getContributionReceiptAmount() + "},";
			}
		}

		// If the previous loop was broken before reaching the end of the
		// winnerDonations
		// array, then continue traversing through the array to record donation data.
		// However, the data will no longer be added to the scatter chart displayed in
		// the view.
		for (int i = winnerIndex; i < winnerDonations.size(); i++) {
			winner_total_donations += winnerDonations.get(i).getContributionReceiptAmount();
			if (winnerDonations.get(i).getContributionReceiptAmount() > largest_winning_donation) {
				largest_winning_donation = winnerDonations.get(i).getContributionReceiptAmount();
			}
		}

		// Perform the same tasks for the loser donations
		for (loserIndex = 0; loserIndex < loserDonations.size()
				&& loserDonationScatterData.length() <= 5500; loserIndex++) {
			loser_total_donations += loserDonations.get(loserIndex).getContributionReceiptAmount();
			if (loserDonations.get(loserIndex).getContributionReceiptAmount() > largest_losing_donation) {
				largest_losing_donation = loserDonations.get(loserIndex).getContributionReceiptAmount();
			}
			int randomInteger2 = (int) (((double) loserDonations.size() - 1.0) * Math.random());
			while (loseRand.contains(randomInteger2) && loseRand.size() < loserDonations.size() - 1) {
				randomInteger2 = (int) (((double) loserDonations.size() - 1.0) * Math.random());
			}

			if (loserDonations.get(loserIndex).getContributionReceiptAmount() != null
					&& loserDonations.get(loserIndex).getContributionReceiptDate() != null
					&& loserDonations.get(loserIndex).getContributionReceiptAmount() > 0.0) {
				loserDonationScatterData += "{x:"
						+ loserDonations.get(loserIndex).getContributionReceiptDate().substring(0, 4) + "."
						+ loserDonations.get(loserIndex).getContributionReceiptDate().substring(5, 7)
						+ loserDonations.get(loserIndex).getContributionReceiptDate().substring(8, 10) + ", y:"
						+ loserDonations.get(loserIndex).getContributionReceiptAmount() + "},";
			}
			loseRand.add(randomInteger2);

		}

		for (int i = loserIndex; i < loserDonations.size(); i++) {
			loser_total_donations += loserDonations.get(i).getContributionReceiptAmount();
			if (loserDonations.get(i).getContributionReceiptAmount() > largest_losing_donation) {
				largest_losing_donation = loserDonations.get(i).getContributionReceiptAmount();
			}
		}

		// Remove a comma from the end of the winner and loser scatter data strings.
		// Catches index out of bound in the event the string was empty.
		try {
			winnerDonationScatterData = winnerDonationScatterData.substring(0, winnerDonationScatterData.length() - 1);
		} catch (Exception e) {
		}
		try {
			loserDonationScatterData = loserDonationScatterData.substring(0, loserDonationScatterData.length() - 1);
		} catch (Exception e) {
		}

		// Calculate average winning and losing donations
		double avg_winning_donation = winner_total_donations / winnerDonations.size();
		double avg_losing_donation = loser_total_donations / loserDonations.size();
		// Construct a LocationSearchResult object using the data gathered and return it
		LocationSearchResult lsr = new LocationSearchResult(winner_name, loser_name, winner_committee_ids,
				loser_committee_ids, winnerDonations, loserDonations, total_winners, total_losers,
				winner_total_donations, loser_total_donations, largest_winning_donation, largest_losing_donation,
				avg_winning_donation, avg_losing_donation, city, state, electionYear, winnerDonationScatterData,
				loserDonationScatterData, numLoserDonations, numWinnerDonations);

		return lsr;
	}

	@RequestMapping("compare-location-search-results")
	public ModelAndView compareLocationSearchResults(String city1, String state1, String city2, String state2,
			Integer electionYear) {
		LocationSearchResult lsr1 = new LocationSearchResult();
		LocationSearchResult lsr2 = new LocationSearchResult();
		String location1 = city1 + ", " + state1;
		String location2 = city2 + ", " + state2;
		String lsr1MostDon = "";
		String lsr2MostDon = "";

		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city1, state1, electionYear) == null) {
			lsr1 = getLocationSearchResult(city1, state1, electionYear);
			lsrr.save(lsr1);
		} else {
			lsr1 = lsrr.getSearchResultsFromCityStateAndElectionYear(city1, state1, electionYear);
		}
		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city2, state2, electionYear) == null) {
			lsr2 = getLocationSearchResult(city2, state2, electionYear);
			lsrr.save(lsr2);
		} else {
			lsr2 = lsrr.getSearchResultsFromCityStateAndElectionYear(city2, state2, electionYear);
		}

		if (lsr1.getNumWinnerDonations() > lsr1.getNumLoserDonations()) {
			lsr1MostDon = lsr1.getWinnerName();
		} else {
			lsr1MostDon = lsr1.getLoserName();
		}
		if (lsr2.getNumWinnerDonations() > lsr2.getNumLoserDonations()) {
			lsr2MostDon = lsr2.getWinnerName();
		} else {
			lsr2MostDon = lsr2.getLoserName();
		}

		ModelAndView mv = new ModelAndView("compare-location-search-results");
		if (elr.findByElectionYear(electionYear).get(0).getWinningParty().equals(PoliticalParty.DEMOCRAT)) {
			mv.addObject("winnerColor1", "#0071cd");
			mv.addObject("winnerColor2", "#add8e6");
			mv.addObject("loserColor1", "#de0000");
			mv.addObject("loserColor2", "#ffc0cb");
		} else {
			mv.addObject("winnerColor1", "#de0000");
			mv.addObject("winnerColor2", "#ffc0cb");
			mv.addObject("loserColor1", "#0071cd");
			mv.addObject("loserColor2", "#add8e6");
		}
		mv.addObject("location1result", lsr1);
		mv.addObject("location2result", lsr2);
		mv.addObject("location1", location1);
		mv.addObject("location2", location2);
		mv.addObject("location1MostDon", lsr1MostDon);
		mv.addObject("location2MostDon", lsr2MostDon);
		mv.addObject("location1_winners", lsr1.getTotalWinners());
		mv.addObject("location2_winners", lsr2.getTotalWinners());
		mv.addObject("avg_winning_donation_location1", String.format("%.2f", lsr1.getAvgWinningDonation()));
		mv.addObject("avg_winning_donation_location2", String.format("%.2f", lsr2.getAvgWinningDonation()));
		mv.addObject("largest_winning_donation_location1", String.format("%.2f", lsr1.getLargestWinningDonation()));
		mv.addObject("largest_winning_donation_location2", String.format("%.2f", lsr2.getLargestWinningDonation()));
		mv.addObject("largest_winner_recipient_location1", lsr1.getWinnerName());
		mv.addObject("largest_winner_recipient_location2", lsr2.getWinnerName());
		mv.addObject("largest_winner_total_location1", String.format("%.2f", lsr1.getWinnerTotalDonations()));
		mv.addObject("largest_winner_total_location2", String.format("%.2f", lsr2.getWinnerTotalDonations()));
		mv.addObject("largest_total_winner_recipient_location1", lsr1.getWinnerName());
		mv.addObject("largest_total_winner_recipient_location1", lsr2.getWinnerName());
		return mv;
	}

	public int checkPages(String city, String state, int electionYear, String committee_id) {
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		rateLimiter.acquire();
		try {

			respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
					+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state
					+ "&per_page=100", HttpMethod.GET, httpEnt, ScheduleAResults.class);
			System.out.println(
					"Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages() / 5) + " seconds");
		} catch (HttpClientErrorException e) {
			// try {
			respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey2
					+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state
					+ "&per_page=100", HttpMethod.GET, httpEnt, ScheduleAResults.class);
			System.out.println(
					"Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages() / 5) + " seconds");
		}
		int pages = respEnt.getBody().getPagination().getPages();
		return pages;
	}

	@RequestMapping("location-search-results")
	public ModelAndView locationSearchResults(String city, String state, int electionYear) {
		LocationSearchResult lsr = new LocationSearchResult();
		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear) == null) {
			int pages = checkPages(
					city, state, electionYear, ccr
					.findByCandidateAssigned(
							cdr.getCandidateDataFromID(
									elr.findByElectionYear(electionYear).get(0).getWinnerId()).get(
											0))
					.get(0).getCommittee_id());
			if (pages >50) {
				ModelAndView confirm = new ModelAndView("confirm-page");
				confirm.addObject("city", city);
				confirm.addObject("state", state);
				confirm.addObject("electionYear", electionYear);
				confirm.addObject("estimate", pages);
				return confirm;
			}
			int pages2 = checkPages(city, state, electionYear, ccr.findByCandidateAssigned(
					cdr.getCandidateDataFromID(elr.findByElectionYear(electionYear).get(0).getLoserId()).get(0))
					.get(0).getCommittee_id());
			if (pages2 > 50) {
				ModelAndView confirm = new ModelAndView("confirm-page");
				confirm.addObject("city", city);
				confirm.addObject("state", state);
				confirm.addObject("electionYear", electionYear);
				confirm.addObject("estimate", pages + pages2);
				return confirm;
			}
				
			lsr = getLocationSearchResult(city, state, electionYear);
			lsrr.save(lsr);
		} else {
			lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear);
		}
		String location = city + ", " + state;
		String majorityDonationName = "";
		if (lsr.getNumWinnerDonations() > lsr.getNumLoserDonations()) {
			majorityDonationName = lsr.getWinnerName();
		} else {
			majorityDonationName = lsr.getLoserName();
		}

		System.out.println(
				"Winnersize: " + lsr.getWinnerDonations().size() + " LoserSize: " + lsr.getLoserDonations().size());
		ModelAndView mv = new ModelAndView("location-search-results");
		if (elr.findByElectionYear(electionYear).get(0).getWinningParty().equals(PoliticalParty.DEMOCRAT)) {
			mv.addObject("winnerColor", "#0071cd");
			mv.addObject("loserColor", "#de0000");
		} else {
			mv.addObject("winnerColor", "#de0000");
			mv.addObject("loserColor", "#0071cd");
		}
		mv.addObject("majname", majorityDonationName);
		mv.addObject("results", lsr);
		mv.addObject("loserDonationData", lsr.getLoserDonationScatterData());
		mv.addObject("winnerDonationData", lsr.getWinnerDonationScatterData());
		mv.addObject("location", location);
		mv.addObject("total_winners", lsr.getTotalWinners());
		mv.addObject("total_losers", lsr.getTotalLosers());
		mv.addObject("avg_winning_donation", String.format("%.2f", lsr.getAvgWinningDonation()));
		mv.addObject("avg_losing_donation", String.format("%.2f", lsr.getAvgLosingDonation()));
		mv.addObject("largest_winning_donation", String.format("%.2f", lsr.getLargestWinningDonation()));
		mv.addObject("largest_losing_donation", String.format("%.2f", lsr.getLargestLosingDonation()));
		mv.addObject("largest_winner_recipient", lsr.getWinnerName());
		mv.addObject("largest_loser_recipient", lsr.getLoserName());
		mv.addObject("largest_winner_total", String.format("%.2f", lsr.getWinnerTotalDonations()));
		mv.addObject("largest_loser_total", String.format("%.2f", lsr.getLoserTotalDonations()));
		mv.addObject("largest_total_winner_recipient", lsr.getWinnerName());
		mv.addObject("largest_total_loser_recipient", lsr.getLoserName());
		return mv;
	}
	
	@RequestMapping("confirmed-location-search-results")
	public ModelAndView confirmedLocationSearchResults(String city, String state, int electionYear, boolean b) {
		LocationSearchResult lsr = new LocationSearchResult();
		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear) == null) {			
			lsr = getLocationSearchResult(city, state, electionYear);
			lsrr.save(lsr);
		} else {
			lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear);
		}
		String location = city + ", " + state;
		String majorityDonationName = "";
		if (lsr.getNumWinnerDonations() > lsr.getNumLoserDonations()) {
			majorityDonationName = lsr.getWinnerName();
		} else {
			majorityDonationName = lsr.getLoserName();
		}

		System.out.println(
				"Winnersize: " + lsr.getWinnerDonations().size() + " LoserSize: " + lsr.getLoserDonations().size());
		ModelAndView mv = new ModelAndView("location-search-results");
		if (elr.findByElectionYear(electionYear).get(0).getWinningParty().equals(PoliticalParty.DEMOCRAT)) {
			mv.addObject("winnerColor", "#0071cd");
			mv.addObject("loserColor", "#de0000");
		} else {
			mv.addObject("winnerColor", "#de0000");
			mv.addObject("loserColor", "#0071cd");
		}
		mv.addObject("majname", majorityDonationName);
		mv.addObject("results", lsr);
		mv.addObject("loserDonationData", lsr.getLoserDonationScatterData());
		mv.addObject("winnerDonationData", lsr.getWinnerDonationScatterData());
		mv.addObject("location", location);
		mv.addObject("total_winners", lsr.getTotalWinners());
		mv.addObject("total_losers", lsr.getTotalLosers());
		mv.addObject("avg_winning_donation", String.format("%.2f", lsr.getAvgWinningDonation()));
		mv.addObject("avg_losing_donation", String.format("%.2f", lsr.getAvgLosingDonation()));
		mv.addObject("largest_winning_donation", String.format("%.2f", lsr.getLargestWinningDonation()));
		mv.addObject("largest_losing_donation", String.format("%.2f", lsr.getLargestLosingDonation()));
		mv.addObject("largest_winner_recipient", lsr.getWinnerName());
		mv.addObject("largest_loser_recipient", lsr.getLoserName());
		mv.addObject("largest_winner_total", String.format("%.2f", lsr.getWinnerTotalDonations()));
		mv.addObject("largest_loser_total", String.format("%.2f", lsr.getLoserTotalDonations()));
		mv.addObject("largest_total_winner_recipient", lsr.getWinnerName());
		mv.addObject("largest_total_loser_recipient", lsr.getLoserName());
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

	final RateLimiter rateLimiter = RateLimiter.create(5.0);
	final RateLimiter rateLimiter2 = RateLimiter.create(3.0);

	public List<DBDonation> getCandidateDonations(String city, String state, String committee_id,
			Integer electionYear) {
		System.out.println("Started getting donations at: " + new Date());
		int electionYearInt = electionYear;
		List<DBDonation> donations = new ArrayList<>();
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		rateLimiter.acquire();
		try {

			respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
					+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state
					+ "&per_page=100", HttpMethod.GET, httpEnt, ScheduleAResults.class);
			System.out.println(
					"Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages() / 5) + " seconds");
		} catch (HttpClientErrorException e) {
			// try {
			respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey2
					+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state
					+ "&per_page=100", HttpMethod.GET, httpEnt, ScheduleAResults.class);
			System.out.println(
					"Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages() / 5) + " seconds");
		}
		int pages = respEnt.getBody().getPagination().getPages();
		List<DBDonation> toAdd = respEnt.getBody().getResults();
		try {
			for (DBDonation d : toAdd) {
				try {
					try {
						if (d.getContributionReceiptDate().contains("" + electionYearInt)) {
							donations.add(d);
						} else if (((d.getReportYear() <= electionYearInt)
								&& (d.getReportYear() > (electionYearInt - 4))) && !d.getEntityType().equals("ORG")) {
							donations.add(d);
						}
					} catch (NullPointerException e) {

					}
				} catch (NumberFormatException e) {

				}
			}
		} catch (HttpClientErrorException e) {
		}
		String url = "";
		try {
			while (respEnt.getBody().getPagination().getLast_indexes().getLast_index() != null) {

				rateLimiter.acquire();
				try {

					url = "http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&committee_id="
							+ committee_id + "&contributor_city=" + city + "&contributor_state=" + state
							+ "&per_page=100&last_index="
							+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
							+ "&last_contribution_receipt_date="
							+ respEnt.getBody().getPagination().getLast_indexes().getLast_contribution_receipt_date();
					respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, ScheduleAResults.class);
					System.out.println("No error on first key");
				} catch (HttpClientErrorException e) {
					System.out.println("Got an error on first key");
					try {
						rateLimiter2.acquire();
						url = "http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey2 + "&committee_id="
								+ committee_id + "&contributor_city=" + city + "&contributor_state=" + state
								+ "&per_page=100&last_index="
								+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
								+ "&last_contribution_receipt_date=" + respEnt.getBody().getPagination()
										.getLast_indexes().getLast_contribution_receipt_date();
						respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, ScheduleAResults.class);
					} catch (HttpClientErrorException e1) {
						try {
							System.out.println("second error");
							url = "http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
									+ "&committee_id=" + committee_id + "&contributor_city=" + city
									+ "&contributor_state=" + state + "&per_page=100&last_index="
									+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
									+ "&last_contribution_receipt_date=" + respEnt.getBody().getPagination()
											.getLast_indexes().getLast_contribution_receipt_date();
							respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, ScheduleAResults.class);
						} catch (HttpClientErrorException e2) {
							System.out.println("3rd error");
							url = "http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey2
									+ "&committee_id=" + committee_id + "&contributor_city=" + city
									+ "&contributor_state=" + state + "&per_page=100&last_index="
									+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
									+ "&last_contribution_receipt_date=" + respEnt.getBody().getPagination()
											.getLast_indexes().getLast_contribution_receipt_date();
							respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, ScheduleAResults.class);
						}
					}
				}
				System.out.println("Pages remaining: " + pages);
				pages--;
				toAdd = respEnt.getBody().getResults();

				for (DBDonation d : toAdd) {
					try {
						if (d.getContributionReceiptDate().contains("" + electionYearInt)) {
							donations.add(d);
						} else if (((d.getReportYear() <= electionYearInt)
								&& (d.getReportYear() > (electionYearInt - 4))) && !d.getEntityType().equals("ORG")) {
							donations.add(d);
							if (d.getContributionReceiptAmount() > 10000) {
								System.out.println("BigDonation - ReportYear: " + d.getReportYear()
										+ " ElectionYearInt:" + electionYearInt + " " + d.getContributionReceiptDate()
										+ " " + d.getContributionReceiptAmount());
							}
						}
					} catch (NumberFormatException e) {
					}
				}
			}
		} catch (NullPointerException | HttpClientErrorException e) {
			if (e.getClass() == NullPointerException.class) {
			} else {
			}

		}
		if (donations.size() == 0) {
			DBDonation db = new DBDonation();
			db.setContributionReceiptAmount(0.0);
			donations.add(db);
			return donations;
		}
		System.out.println("Finished getting donations at: " + new Date());
		return donations;
	}

	@RequestMapping("historical-search-results")
	public ModelAndView historicalSearchResults(String city, String state) {
		// Declare variables
		LocationSearchResult lsr = new LocationSearchResult();
		List<LocationSearchResult> results = new ArrayList<>();
		List<Integer> electionYears = new ArrayList<>();
		double largestWinningDonation = 0.0;
		double largestLosingDonation = 0.0;
		int totalWinners = 0;
		int totalLosers = 0;
		double largestTotalWinnerDonations = 0.0;
		String largestTotalWinnerName = "";
		double largestTotalLoserDonations = 0.0;
		String largestTotalLoserName = "";
		String biggestLoserRecipient = "";
		String biggestWinnerRecipient = "";
		double averageWinningDonation = 0.0;
		double averageLosingDonation = 0.0;
		ArrayList<String> winnerNames = new ArrayList<>();
		ArrayList<String> loserNames = new ArrayList<>();
		ArrayList<String> ties = new ArrayList<>();
		String location = city + ", " + state;
		ModelAndView mv = new ModelAndView("historical-location-search-results");

		// Save election years in the election result repo to an array
		for (ElResult e : elr.findAll()) {
			if (!electionYears.contains(e.getElectionYear())) {
				electionYears.add(e.getElectionYear());
			}
		}
		System.out.println(electionYears.toString());
		// For each election year ...
		try {
			for (Integer i : electionYears) {

				// If the search result database does not already contain a result for the given
				// city, state, and election year ...
				if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i) == null) {
					// And the election year is not one of the following ...
					if (i != 2000 && i != 1976 && i != 1972 && i != 2020) {
						// Then perform a new search for the city, state, and election year.
						lsr = getLocationSearchResult(city, state, i);
						// If the result is not null ...
						if (lsr != null) {
							// Then save the result to the database ...
							lsrr.save(lsr);
						}
						// And add the search result to the results array.
						results.add(lsr);
					}
				}
				// Otherwise, pull the existing search result from the database and add it to
				// the results array
				else {
					if (i != 2000 && i != 1976 && i != 1972 && i != 2020) {
						lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i);
						results.add(lsr);
					}
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		String totalData = "";
		String electionYearsTbl = "";
		String avgData = "";
		// Calculate historical data using all search results for each location
		for (LocationSearchResult l : results) {

			// Check for largest losing donation and largest total donations to the loser
			if (l.getLargestLosingDonation() > largestLosingDonation) {
				largestLosingDonation = l.getLargestLosingDonation();
				biggestLoserRecipient = l.getLoserName();
			}
			if (l.getLoserTotalDonations() > largestTotalLoserDonations) {
				largestTotalLoserDonations = l.getLoserTotalDonations();
				largestTotalLoserName = l.getLoserName();
			}

			// Add average losing donation to be used in calculation after the loop
			// concludes
			averageLosingDonation += l.getAvgLosingDonation();

			// Check for largest winning donation and largest total donations to the winner
			if (l.getWinnerTotalDonations() > largestTotalWinnerDonations) {
				largestTotalWinnerDonations = l.getWinnerTotalDonations();
				largestTotalWinnerName = l.getWinnerName();
			}
			if (l.getLargestWinningDonation() > largestWinningDonation) {
				largestWinningDonation = l.getLargestWinningDonation();
				biggestWinnerRecipient = l.getWinnerName();
			}
			// Add average winning donation to be used in calculation after the loop
			// concludes
			averageWinningDonation += l.getAvgWinningDonation();

			// Find total number of winners, losers, and ties supported at the location
			if (l.getWinnerTotalDonations() > l.getLoserTotalDonations()) {
				totalWinners += 1;
				winnerNames.add(l.getWinnerName() + " (" + l.getElectionYear() + ")");
			} else if (l.getWinnerTotalDonations() < l.getLoserTotalDonations()) {
				totalLosers += 1;
				loserNames.add(l.getLoserName() + " (" + l.getElectionYear() + ")");
			} else {
				ties.add(l.getWinnerName() + " vs. " + l.getLoserName() + " (" + l.getElectionYear() + ")");
			}
			if (cdr.getCandidateDataFromName(l.getWinnerName()).get(0).getAfiliatedParty().toString()
					.equals("DEMOCRAT")) {
				avgData += Math.round(l.getAvgWinningDonation()) + "," + Math.round(l.getAvgLosingDonation()) + ",";
				totalData += Math.round(l.getWinnerTotalDonations()) + "," + Math.round(l.getLoserTotalDonations())
						+ ",";
				electionYearsTbl += "'" + l.getWinnerName() + " (" + l.getElectionYear() + ")', '" + l.getLoserName()
						+ " (" + l.getElectionYear() + ")', ";
			} else {
				avgData += Math.round(l.getAvgLosingDonation()) + "," + Math.round(l.getAvgWinningDonation()) + ",";
				totalData += Math.round(l.getLoserTotalDonations()) + "," + Math.round(l.getWinnerTotalDonations())
						+ ",";
				electionYearsTbl += "'" + l.getLoserName() + " (" + l.getElectionYear() + ")', '" + l.getWinnerName()
						+ " (" + l.getElectionYear() + ")', ";
			}

		}
		// Calculate average winning and losing donations for all election years
		averageWinningDonation = averageWinningDonation / 9;
		averageLosingDonation = averageLosingDonation / 9;

		// Add objects to the ModelAndView
		mv.addObject("avgData", avgData);
		mv.addObject("electionYears", electionYearsTbl);
		mv.addObject("totalData", totalData);
		mv.addObject("results", results);
		mv.addObject("ties", ties);
		mv.addObject("winnerNames", winnerNames);
		mv.addObject("loserNames", loserNames);
		mv.addObject("location", location);
		mv.addObject("total_winners", totalWinners);
		mv.addObject("total_losers", totalLosers);
		mv.addObject("avg_winning_donation", String.format("%.2f", averageWinningDonation));
		mv.addObject("avg_losing_donation", String.format("%.2f", averageLosingDonation));
		mv.addObject("largest_winning_donation", String.format("%.2f", largestWinningDonation));
		mv.addObject("largest_losing_donation", String.format("%.2f", largestLosingDonation));
		mv.addObject("largest_winner_recipient", biggestWinnerRecipient);
		mv.addObject("largest_loser_recipient", biggestLoserRecipient);
		mv.addObject("largest_winner_total", String.format("%.2f", largestTotalWinnerDonations));
		mv.addObject("largest_loser_total", String.format("%.2f", largestTotalLoserDonations));
		mv.addObject("largest_total_winner_recipient", largestTotalWinnerName);
		mv.addObject("largest_total_loser_recipient", largestTotalLoserName);
		return mv;
	}
}
