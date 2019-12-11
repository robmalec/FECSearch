package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import left.rising.FECSearchDraft.dbrepos.CanCommitteeRepo;
import left.rising.FECSearchDraft.dbrepos.CandidateCommitteeId;
import left.rising.FECSearchDraft.dbrepos.CandidateData;
import left.rising.FECSearchDraft.dbrepos.CandidateDataRepo;
import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.dbrepos.State;
import left.rising.FECSearchDraft.dbrepos.StateRepo;
import left.rising.FECSearchDraft.entities.PoliticalParty;

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
	
	List<State> states;
	
	RestTemplate rt = new RestTemplate();

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

					resRepo.save(new ElResult(winningParty,rCandId, dCandId,electionYear));
				}
				
				br.close();
				//Loading committee IDs
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
		String mapAPIKey = "AIzaSyBNBZxsM5lncOL5puCd0OboEiOruXMJrok";
		ModelAndView view = new ModelAndView("test-map","apiKey",mapAPIKey);
		
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
	public ModelAndView loadStateStatsPage(String stateCode){
		ArrayList<String> citySearchQueries = new ArrayList<>();	

		
		return new ModelAndView("redirect:/nina-state-stats-page");
		
	}
}
