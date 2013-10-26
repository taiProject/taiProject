package pl.edu.agh.dfs.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class DemoController {

	private List<String> msgs = new LinkedList<String>();
	
	@RequestMapping("/")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");
		
		msgs.add("Sample");
		msgs.add("Freemarker");
		msgs.add("Usage");
		
		mav.addObject("msgs", msgs);
		mav.addObject("msg", "Demo application");

		return mav;
	}
}