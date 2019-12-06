package left.rising.FECSearchDraft.controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import left.rising.FECSearchDraft.dbrepos.ElResult;
import left.rising.FECSearchDraft.dbrepos.ElResultRepo;
import left.rising.FECSearchDraft.entities.PoliticalParty;

@Controller
public class RobController {

	@Autowired
	ElResultRepo resRepo;

	static List<ElResult> tempResultStorage;

	@RequestMapping("/load-el-data")
	public ModelAndView loadElDataFromCSV() {
		tempResultStorage = resRepo.findAll();
		if (resRepo.findAll().size() == 0) {
			BufferedReader br = null;
			try {
				br = new BufferedReader(new FileReader("us-presidential-election-results.csv"));
				String line = "";
				line = br.readLine();
				while ((line = br.readLine()) != null) {
					System.out.println("Hello");
					resRepo.save(new ElResult(line));
				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return new ModelAndView("index");
	}
}
