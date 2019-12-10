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
		ElResult result = elr.findByElectionYear(electionYear).get(0);

		Integer winner_id = result.getWinnerId();
		Integer loser_id = result.getLoserId();

		CandidateData winner = cdr.getCandidateDataFromID(winner_id).get(0);
		CandidateData loser = cdr.getCandidateDataFromID(loser_id).get(0);

		String winner_name = cdr.getCandidateNameFromId(winner_id).get(0);
		String loser_name = cdr.getCandidateNameFromId(loser_id).get(0);

		List<winnerCommitteeIds> winner_committee_ids = new ArrayList<>();
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(winner)) {
			winner_committee_ids.add(new winnerCommitteeIds(c.getCommittee_id()));
		}
		List<loserCommitteeIds> loser_committee_ids = new ArrayList<>();
		for (CandidateCommitteeId c : ccr.findByCandidateAssigned(loser)) {
			loser_committee_ids.add(new loserCommitteeIds(c.getCommittee_id()));
		}

		List<DBDonation> winnerDonations = new ArrayList<>();
		for (winnerCommitteeIds w : winner_committee_ids) {
			winnerDonations.addAll(getCandidateDonations(city, state, w.getCommitteeId()));
			//System.out.println(winnerDonations.size());
		}
		List<DBDonation> loserDonations = new ArrayList<>();
		System.out.println(loserDonations.size());
		for (loserCommitteeIds l : loser_committee_ids) {
			//System.out.println(l.getCommitteeId());
			loserDonations.addAll(getCandidateDonations(city, state, l.getCommitteeId()));
			//System.out.println(loserDonations.size());
		}
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
		for (loserCommitteeIds l : loser_committee_ids) {
			l.setLocationSearchResultAssigned(lsr);
		}
		for (winnerCommitteeIds w : winner_committee_ids) {
			w.setLocationSearchResultAssigned(lsr);
		}
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

	public List<DBDonation> getCandidateDonations(String city, String state, String committee_id) {
		List<DBDonation> donations = new ArrayList<>();
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		//System.out.println(rateLimiter.acquire());
		rateLimiter.acquire();
		respEnt = rt.exchange(
				"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&committee_id=" + committee_id
						+ "&contributor_city=" + city + "&contributor_state=" + state + "&per_page=100",
				HttpMethod.GET, httpEnt, ScheduleAResults.class);
		System.out.println("Estimated Time remaining:" + (respEnt.getBody().getPagination().getPages()/2) + " seconds");
		rateLimiter.acquire();
		donations.addAll(respEnt.getBody().getResults());
		try {
			while (respEnt.getBody().getPagination().getLast_indexes().getLast_index() != null) {
				// System.out.println(respEnt.getBody().getPagination().getLast_indexes().getLast_index());
				rateLimiter.acquire();
				respEnt = rt.exchange(
						"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&committee_id="
								+ committee_id + "&contributor_city=" + city + "&contributor_state=" + state
								+ "&per_page=100&last_index="
								+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
								+ "&last_contribution_receipt_date="
								+ respEnt.getBody().getPagination().getLast_indexes()
										.getLast_contribution_receipt_date(),
						HttpMethod.GET, httpEnt, ScheduleAResults.class);
				donations.addAll(respEnt.getBody().getResults());
			}
		} catch (NullPointerException e) {
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
		LocationSearchResult lsr = new LocationSearchResult();
		List<LocationSearchResult> results = new ArrayList<>();
		List<Integer> electionYears = new ArrayList<>();

		for (ElResult e : elr.findAll()) {
			if (!electionYears.contains(e.getElectionYear())) {
				electionYears.add(e.getElectionYear());
			}
		}
		for (Integer i : electionYears) {
			//System.out.println(i);
			rateLimiter.acquire();
			if (lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i) == null) {
				if (i != 2000 && i != 1976 && i != 1972) {
					rateLimiter.acquire();
					lsr = getLocationSearchResult(city, state, i);
					rateLimiter.acquire();
					if (lsr != null) {
						//System.out.println(lsr.toString());
						lsrr.save(lsr);
					}
					rateLimiter.acquire();
					results.add(lsr);
					rateLimiter.acquire();
				}
			} else {
				if (i != 2000 && i != 1976 && i != 1972) {
					lsr = lsrr.getSearchResultsFromCityStateAndElectionYear(city, state, i);
					results.add(lsr);
				}
			}
		}
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
		for (LocationSearchResult l : results) {
			if (l.getLargestLosingDonation() > largestLosingDonation) {
				largestLosingDonation = l.getLargestLosingDonation();
				biggestLoserRecipient = l.getLoserName();
			}
			totalWinners += l.getTotalWinners();
			totalLosers += l.getTotalLosers();
			if (l.getLoserTotalDonations() > largestTotalLoserDonations) {
				largestTotalLoserDonations = l.getLoserTotalDonations();
				largestTotalLoserName = l.getLoserName();
			}
			if (l.getWinnerTotalDonations() > largestTotalWinnerDonations) {
				largestTotalWinnerDonations = l.getWinnerTotalDonations();
				largestTotalWinnerName = l.getWinnerName();
			}
			if (l.getLargestWinningDonation() > largestWinningDonation) {
				largestWinningDonation = l.getLargestWinningDonation();
				biggestWinnerRecipient = l.getWinnerName();
			}
			averageWinningDonation += l.getAvgWinningDonation();
			averageLosingDonation += l.getAvgLosingDonation();
		}
		//System.out.println(totalDonations);
		//System.out.println(totalWinningDonations + " " + totalNumWinningDonations);
		averageWinningDonation = averageWinningDonation / results.size();
		averageLosingDonation = averageLosingDonation / results.size();

		String location = city + ", " + state;

		ModelAndView mv = new ModelAndView("location-search-results");
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
