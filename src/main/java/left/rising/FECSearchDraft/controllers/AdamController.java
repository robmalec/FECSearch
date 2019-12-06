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

import left.rising.FECSearchDraft.dbrepos.CandidateDataRepo;
import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.entities.Donation;
import left.rising.FECSearchDraft.entities.ScheduleAResults;

@Controller
public class AdamController {

	@Autowired
	ElResultRepo elr;
	
	@Autowired
	CandidateDataRepo cdr;
	
	private RestTemplate rt = new RestTemplate();
	
	@Value("${fec.key}")
	String fecKey;

	@RequestMapping("location-search-results")
	public ModelAndView locationSearchResults() {

		String city = "Detroit";
		String state = "MI";
		String winner_committee_id = "C00580100";
		String loser_committee_id = "C00575795";
		List<Donation> winnerDonations = getCandidateDonations(city, state, winner_committee_id);
		List<Donation> loserDonations = getCandidateDonations(city, state, loser_committee_id);
		int total_winners = 0;
		int total_losers = 0;
		if (winnerDonations.size() > loserDonations.size()) {
			total_winners += 1;
		} else {
			total_losers += 1;
		}
		double largest_winner_total = 0;
		double largest_loser_total = 0;
		double largest_winning_donation = winnerDonations.get(0).getContribution_receipt_amount();
		double largest_losing_donation = loserDonations.get(0).getContribution_receipt_amount();
		for (Donation d : winnerDonations) {
			largest_winner_total += d.getContribution_receipt_amount();
			if (d.getContribution_receipt_amount() > largest_winning_donation) {
				largest_winning_donation=d.getContribution_receipt_amount();
			}
		}
		
		for (Donation d : loserDonations) {
			largest_loser_total += d.getContribution_receipt_amount();
			if (d.getContribution_receipt_amount() > largest_losing_donation) {
				largest_losing_donation=d.getContribution_receipt_amount();
			}
		}
		double avg_winning_donation = largest_winner_total/winnerDonations.size();
		double avg_losing_donation = largest_loser_total/loserDonations.size();

		String location = city + ", " + state;

		ModelAndView mv = new ModelAndView("location-search-results");
		mv.addObject("location", location);
		mv.addObject("total_winners", total_winners);
		mv.addObject("total_losers", total_losers);
		mv.addObject("avg_winning_donation", String.format("%.2f", avg_winning_donation));
		mv.addObject("avg_losing_donation", String.format("%.2f", avg_losing_donation));
		mv.addObject("largest_winning_donation", String.format("%.2f", largest_winning_donation));
		mv.addObject("largest_losing_donation", String.format("%.2f", largest_losing_donation));
		mv.addObject("largest_winner_recipient", "Donald Trump");
		mv.addObject("largest_loser_recipient", "Hillary Clinton");
		mv.addObject("largest_winner_total", String.format("%.2f", largest_winner_total));
		mv.addObject("largest_loser_total", String.format("%.2f", largest_loser_total));
		mv.addObject("largest_total_winner_recipient", "Donald Trump");
		mv.addObject("largest_total_loser_recipient", "Hillary Clinton");
		return mv;
	}

	public HttpHeaders getHeaders () {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "testing");
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("api_key", fecKey);
		headers.add("location", "Detroit+MI");
		return headers;
	}
	
	public List<Donation> getCandidateDonations(String city, String state, String committee_id) {
		List<Donation> donations = new ArrayList<>();
		
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		respEnt = rt.exchange(
				"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
						+ "&committee_id=C00575795&contributor_city=Detroit&contributor_state=MI&per_page=100",
				HttpMethod.GET, httpEnt, ScheduleAResults.class);

		donations.addAll(respEnt.getBody().getResults());
		try {
			while (respEnt.getBody().getPagination().getLast_indexes().getLast_index() != null) {
				System.out.println(respEnt.getBody().getPagination().getLast_indexes().getLast_index());
				respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
						+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state + "&per_page=100&last_index="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
						+ "&last_contribution_receipt_date="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_contribution_receipt_date(),
						HttpMethod.GET, httpEnt, ScheduleAResults.class);
				donations.addAll(respEnt.getBody().getResults());
			}
		} catch (NullPointerException e) {
		}
		return donations;
	}
	
	public List<Donation> getHistoricalCandidateDonations(String city, String state, String committee_id) {
		List<ElResult> elResults = elr.findAll();
		
		List<Donation> donations = new ArrayList<>();
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", getHeaders());
		ResponseEntity<ScheduleAResults> respEnt;
		respEnt = rt.exchange(
				"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
						+ "&committee_id=C00575795&contributor_city=Detroit&contributor_state=MI&per_page=100",
				HttpMethod.GET, httpEnt, ScheduleAResults.class);

		donations.addAll(respEnt.getBody().getResults());
		try {
			while (respEnt.getBody().getPagination().getLast_indexes().getLast_index() != null) {
				System.out.println(respEnt.getBody().getPagination().getLast_indexes().getLast_index());
				respEnt = rt.exchange("http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey
						+ "&committee_id=" + committee_id + "&contributor_city=" + city + "&contributor_state=" + state + "&per_page=100&last_index="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_index()
						+ "&last_contribution_receipt_date="
						+ respEnt.getBody().getPagination().getLast_indexes().getLast_contribution_receipt_date(),
						HttpMethod.GET, httpEnt, ScheduleAResults.class);
				donations.addAll(respEnt.getBody().getResults());
			}
		} catch (NullPointerException e) {
		}
		return donations;
	}
}
