package pl.edu.agh.dfs.controllers;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.agh.dfs.utils.SecurityHelper;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class ApplicationNavigationController implements Serializable {

	@RequestMapping("/*")
	public ModelAndView demo() {
		ModelAndView mav;

		if (SecurityHelper.hasUserRole("ROLE_ADMIN")) {
			mav = new ModelAndView("index");
		} else {
			mav = new ModelAndView("index");
		}

		mav.addObject("username", SecurityHelper.getUsername());

		return mav;
	}
}