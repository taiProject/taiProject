package pl.edu.agh.dfs.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.agh.dfs.googledrive.DriveManager;
import pl.edu.agh.dfs.utils.SecurityHelper;

import com.google.api.services.drive.model.File;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class ApplicationNavigationController implements Serializable {

	@RequestMapping("/*")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");

		addCommonValues(mav, "home");
		return mav;
	}

	@RequestMapping("/fileList")
	public ModelAndView filesList() {
		ModelAndView mav = new ModelAndView("fileList");

		List<File> files = null;

		try {
			files = DriveManager.getAllFiles();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		mav.addObject("files", files);

		addCommonValues(mav, "fileList");
		return mav;
	}

	@RequestMapping("/userInfo")
	public ModelAndView userInfo() {
		ModelAndView mav = new ModelAndView("userInfo");

		addCommonValues(mav, "userInfo");
		return mav;
	}

	@RequestMapping("/userManagement")
	public ModelAndView userManagement() {
		ModelAndView mav = new ModelAndView("userManagement");

		addCommonValues(mav, "userManagement");
		return mav;
	}

	private void addCommonValues(ModelAndView mav, String name) {
		mav.addObject("isAdmin", SecurityHelper.hasUserRole("ROLE_ADMIN"));
		mav.addObject("username", SecurityHelper.getUsername());
		mav.addObject("viewName", name);
	}
}