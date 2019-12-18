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
	
	@RequestMapping("about")
	public ModelAndView aboutPage() {

		return new ModelAndView("about", "p", "");
	}
	
	@RequestMapping("contact")
	public ModelAndView contactPage() {
		return new ModelAndView("contact");
	}
	
//	@RequestMapping("about") 
//	public ModelAndView() {
//		return new ModelAndView();
//	}
}
