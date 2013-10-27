package pl.edu.agh.dfs.controllers;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class DemoController implements Serializable {

	private final List<String> msgs = new LinkedList<String>();

	@RequestMapping("/")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");

		msgs.add("Sample");
		msgs.add("Freemarker");
		msgs.add("Usage");

		mav.addObject("msgs", msgs);

		return mav;
	}
}