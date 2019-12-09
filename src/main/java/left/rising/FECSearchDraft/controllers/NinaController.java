package left.rising.FECSearchDraft.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NinaController {

	@RequestMapping("test")
	public ModelAndView homePage2(String test) {
		return new ModelAndView("index", "testing", test);
	}
	
}
