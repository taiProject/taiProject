package pl.edu.agh.dfs.controllers;

import java.io.Serializable;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class SecurityNavigationController implements Serializable {

	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");

        return mav;
	}

	@RequestMapping("/loginfailed")
	public ModelAndView loginFailed() {
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("message", "bad credentials");

		return mav;
	}

	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("message", "logout success");

		return mav;
	}
}
