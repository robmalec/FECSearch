package left.rising.FECSearchDraft.controllers;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import left.rising.FECSearchDraft.entities.DonorValue;

@Controller
public class HomeController {
	private RestTemplate rt = new RestTemplate();
	@Value("${fec.key}")
	String fecKey;

	@RequestMapping("/")
	public ModelAndView homePage() {
		return new ModelAndView("index");
	}

	/*
	 * @RequestMapping("/find-contributor") public ModelAndView
	 * findContributor(String contributor_name, String contributor_state) {
	 * HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.USER_AGENT,
	 * "testing"); // Accept headers define how we want to get data back
	 * headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
	 * headers.add("api_key", fecKey); headers.add("location", "Detroit+MI");
	 * 
	 * // Add header parameters to the request to the API HttpEntity<String> httpEnt
	 * = new HttpEntity<>("parameters", headers); ResponseEntity<DonorValue>
	 * respEnt; String url =
	 * "https://api.followthemoney.org/?d-et=2&d-ad-cty=DETROIT&d-ad-st=" +
	 * contributor_state + "&APIKey=" + ftmKey + "&mode=json"; try { respEnt = rt
	 * .exchange(url, HttpMethod.GET, httpEnt, DonorValue.class); return new
	 * ModelAndView("result-page", "result", respEnt.getBody().getResults()); }
	 * catch (RestClientException | IndexOutOfBoundsException e) { ModelAndView mv =
	 * new ModelAndView("index"); mv.addObject("badSearch", "No results"); return
	 * mv; }
	 * 
	 * }
	 */

	@RequestMapping("/find-contributor")
	public ModelAndView findContributor(String contributor_name, String contributor_state) {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.USER_AGENT, "testing");
		// Accept headers define how we want to get data back
		headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.add("api_key", fecKey);
		headers.add("location", "Detroit+MI");

		// Add header parameters to the request to the API
		HttpEntity<String> httpEnt = new HttpEntity<>("parameters", headers);
		ResponseEntity<DonorValue> respEnt;
		try {
			respEnt = rt.exchange(
					"http://api.open.fec.gov/v1/schedules/schedule_a/?api_key=" + fecKey + "&contributor_name="
							+ contributor_name + "&contributor_state=" + contributor_state,
					HttpMethod.GET, httpEnt, DonorValue.class);
			return new ModelAndView("result-page", "result", respEnt.getBody().getResults());
		} catch (RestClientException | IndexOutOfBoundsException e) {
			ModelAndView mv = new ModelAndView("index");
			mv.addObject("badSearch", "No results");
			return mv;
		}

	}

}
