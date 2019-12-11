package left.rising.FECSearchDraft.controllers;

import java.util.ArrayList;
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
import left.rising.FECSearchDraft.entities.ScheduleAResults;
import left.rising.FECSearchDraft.entities.loserCommitteeIds;
import left.rising.FECSearchDraft.entities.winnerCommitteeIds;

@Controller
public class AdamController {

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

	@RequestMapping("/test-search-historical")
	public ModelAndView testSearchHistorical() {
		return new ModelAndView("test-search-historical");
	}

	public LocationSearchResult getLocationSearchResult(String city, String state, Integer electionYear) {
		// System.out.println(electionYear);
		ElResult result = elr.findByElectionYear(electionYear).get(0);

		Integer winner_id = result.getWinnerId();
		Integer loser_id = result.getLoserId();

		CandidateData winner = cdr.getCandidateDataFromID(winner_id).get(0);
		CandidateData loser = cdr.getCandidateDataFromID(loser_id).get(0);

		String winner_name = cdr.getCandidateNameFromId(winner_id).get(0);
		String loser_name = cdr.getCandidateNameFromId(loser_id).get(0);

		List<DBDonation> winnerDonations = new ArrayList<>();
		List<DBDonation> loserDonations = new ArrayList<>();

		List<winnerCommitteeIds> winner_committee_ids = new ArrayList<>();
		// System.out.println(winner.getName());
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(winner)) {
			// System.out.println(c.getCandidate_assigned().getName());
			// System.out.println(c.getElection_year() + " " + electionYear);
			// System.out.println(c.getElection_year().equals(electionYear));
			if (c.getElection_year().equals(electionYear)) {
				winnerDonations.addAll(getCandidateDonations(city, state, c.getCommittee_id(), electionYear));
			}
			// winner_committee_ids.add(new winnerCommitteeIds(c.getCommittee_id()));
		}
		List<loserCommitteeIds> loser_committee_ids = new ArrayList<>();
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(loser)) {
			// System.out.println(c.getCandidate_assigned().getName());
			if (c.getElection_year().equals(electionYear)) {
				loserDonations.addAll(getCandidateDonations(city, state, c.getCommittee_id(), electionYear));
			}
			// loser_committee_ids.add(new loserCommitteeIds(c.getCommittee_id()));
		}
		/*
		 * for (winnerCommitteeIds w : winner_committee_ids) {
		 * winnerDonations.addAll(getCandidateDonations(city, state,
		 * w.getCommitteeId())); //System.out.println(winnerDonations.size()); }
		 * 
		 * System.out.println(loserDonations.size()); for (loserCommitteeIds l :
		 * loser_committee_ids) { //System.out.println(l.getCommitteeId());
		 * loserDonations.addAll(getCandidateDonations(city, state,
		 * l.getCommitteeId())); //System.out.println(loserDonations.size()); }
		 */
		int total_winners = 0;
		int total_losers = 0;
		if (winnerDonations.size() > loserDonations.size()) {
			total_winners += 1;
		} else {
			total_losers += 1;
		}
		double winner_total_donations = 0;
		double loser_total_donations = 0;
		double largest_winning_donation = 0.0;

		try {
			largest_winning_donation = winnerDonations.get(0).getContributionReceiptAmount();
		} catch (IndexOutOfBoundsException e) {

		}

		double largest_losing_donation = 0.0;
		try {
			largest_losing_donation = loserDonations.get(0).getContributionReceiptAmount();
		} catch (IndexOutOfBoundsException e) {
		}

		for (DBDonation d : winnerDonations) {
			winner_total_donations += d.getContributionReceiptAmount();
			if (d.getContributionReceiptAmount() > largest_winning_donation) {
				largest_winning_donation = d.getContributionReceiptAmount();
			}
		}

		for (DBDonation d : loserDonations) {
			loser_total_donations += d.getContributionReceiptAmount();
			if (d.getContributionReceiptAmount() > largest_losing_donation) {
				largest_losing_donation = d.getContributionReceiptAmount();
			}
		}
		double avg_winning_donation = winner_total_donations / winnerDonations.size();
		double avg_losing_donation = loser_total_donations / loserDonations.size();
		LocationSearchResult lsr = new LocationSearchResult(winner_name, loser_name, winner_committee_ids,
				loser_committee_ids, winnerDonations, loserDonations, total_winners, total_losers,
				winner_total_donations, loser_total_donations, largest_winning_donation, largest_losing_donation,
				avg_winning_donation, avg_losing_donation, city, state, electionYear);
		/*
		 * for (loserCommitteeIds l : loser_committee_ids) {
		 * l.setLocationSearchResultAssigned(lsr); } for (winnerCommitteeIds w :
		 * winner_committee_ids) { w.setLocationSearchResultAssigned(lsr); }
		 */
		return lsr;
	}

	@RequestMapping("compare-location-search-results")
	public ModelAndView compareLocationSearchResults(String city1, String state1, String city2, String state2,
			Integer electionYear) {
		LocationSearchResult lsr1 = new LocationSearchResult();
		LocationSearchResult lsr2 = new LocationSearchResult();
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

		String location1 = city1 + ", " + state1;
		String location2 = city2 + ", " + state2;

		ModelAndView mv = new ModelAndView("compare-location-search-results");
		mv.addObject("location1", location1);
		mv.addObject("location2", location2);
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

	@RequestMapping("location-search-results")
	public ModelAndView locationSearchResults(String city, String state, int electionYear) {
		LocationSearchResult lsr = new LocationSearchResult();
		if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear) == null) {
			lsr = getLocationSearchResult(city, state, electionYear);
			lsrr.save(lsr);
		} else {
			lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, electionYear);
		}
		String location = city + ", " + state;

		ModelAndView mv = new ModelAndView("location-search-results");
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

	final RateLimiter rateLimiter = RateLimiter.create(2.0);

	public List<DBDonation> getCandidateDonations(String city, String state, String committee_id,
			Integer electionYear) {
		int electionYearInt = electionYear;
		List<DBDonation> donations = new ArrayList<>();
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		rateLimiter.acquire();
		respEnt = rt.exchange(
				"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&committee_id=" + committee_id
						+ "&contributor_city=" + city + "&contributor_state=" + state + "&per_page=100",
				HttpMethod.GET, httpEnt, ScheduleAResults.class);
		System.out
				.println("Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages() / 2) + " seconds");
		rateLimiter.acquire();
		try {
			List<DBDonation> toAdd = respEnt.getBody().getResults();
			for (DBDonation d : toAdd) {
				try {
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
					} catch (NullPointerException e) {

					}
				} catch (NumberFormatException e) {

				}
			}
		} catch (HttpClientErrorException e) {

		}

		try {
			while (respEnt.getBody().getPagination().getLast_indexes().getLast_index() != null) {
				// System.out.println(respEnt.getBody().getPagination().getLast_indexes().getLast_index());
				rateLimiter.acquire();
				String url = "http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&committee_id="
						+ committee_id + "&contributor_city=" + city + "&contributor_state=" + state
						+ "&per_page=100&last_index="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
						+ "&last_contribution_receipt_date="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_contribution_receipt_date();
				// System.out.println(url);
				rateLimiter.acquire();
				respEnt = rt.exchange(url, HttpMethod.GET, httpEnt, ScheduleAResults.class);

				List<DBDonation> toAdd = respEnt.getBody().getResults();
				for (DBDonation d : toAdd) {
					try {
						try {
							if (d.getContributionReceiptDate().contains("" + electionYearInt)) {
								donations.add(d);
							} else if (((d.getReportYear() <= electionYearInt)
									&& (d.getReportYear() > (electionYearInt - 4)))
									&& !d.getEntityType().equals("ORG")) {
								donations.add(d);
								if (d.getContributionReceiptAmount() > 10000) {
									System.out.println("BigDonation - ReportYear: " + d.getReportYear()
											+ " ElectionYearInt:" + electionYearInt + " "
											+ d.getContributionReceiptDate() + " " + d.getContributionReceiptAmount());
								}
							}
						} catch (NullPointerException e) {

						}
					} catch (NumberFormatException e) {

					}
				}
			}
		} catch (NullPointerException | HttpClientErrorException e) {
		}
		if (donations.size() == 0) {
			DBDonation db = new DBDonation();
			db.setContributionReceiptAmount(0.0);
			donations.add(db);
			return donations;
		}
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

		// For each election year ...
		for (Integer i : electionYears) {

			// If the search result database does not already contain a result for the given
			// city, state, and election year ...
			if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i) == null) {
				// And the election year is not one of the following ...
				if (i != 2000 && i != 1976 && i != 1972) {
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
				if (i != 2000 && i != 1976 && i != 1972) {
					lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i);
					results.add(lsr);
				}
			}
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
			// totalData += String.format("%.2f", l.getWinnerTotalDonations()) + ", ";
			if(cdr.getCandidateDataFromName(l.getWinnerName()).get(0).getAfiliatedParty().toString().equals("DEMOCRAT")) {
				avgData += Math.round(l.getAvgWinningDonation()) + "," + Math.round(l.getAvgLosingDonation()) + ",";
				totalData += Math.round(l.getWinnerTotalDonations()) + "," + Math.round(l.getLoserTotalDonations()) + ",";
				electionYearsTbl += "'" + l.getWinnerName() + " (" + l.getElectionYear() + ")', '" + l.getLoserName() + " (" + l.getElectionYear() + ")', ";
			} else {
				avgData += Math.round(l.getAvgLosingDonation()) + "," + Math.round(l.getAvgWinningDonation()) + ",";
				totalData += Math.round(l.getLoserTotalDonations()) + "," + Math.round(l.getWinnerTotalDonations()) + ",";
				electionYearsTbl += "'" + l.getLoserName() + " (" + l.getElectionYear() + ")', '" + l.getWinnerName() + " (" + l.getElectionYear() + ")', ";
			}
			

		}
		System.out.println(electionYearsTbl);
		// Calculate average winning and losing donations for all election years
		averageWinningDonation = averageWinningDonation / 9;
		averageLosingDonation = averageLosingDonation / 9;

		// Add objects to the ModelAndView
		// results.get(0).getWin
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
