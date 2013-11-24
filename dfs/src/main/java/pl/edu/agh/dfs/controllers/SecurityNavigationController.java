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

    /**
     *
     * @return Model and view of login page.
     */
	@RequestMapping("/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");

        return mav;
	}

    /**
     *
     * @return Model and view after login failure.
     */
	@RequestMapping("/loginfailed")
	public ModelAndView loginFailed() {
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("message", "bad credentials");

		return mav;
	}

    /**
     *
     * @return Model and view after succesfully logout
     */
	@RequestMapping("/logout")
	public ModelAndView logout() {
		ModelAndView mav = new ModelAndView("login");

		mav.addObject("message", "logout success");

		return mav;
	}
}
