package pl.edu.agh.dfs.controllers;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class DemoController implements Serializable {

	@RequestMapping("/*")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		mav.addObject("username", authentication.getName());

		return mav;
	}
}