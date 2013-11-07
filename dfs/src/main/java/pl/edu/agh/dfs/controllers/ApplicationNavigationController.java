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

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

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

	@RequestMapping("/filesList")
	public ModelAndView filesList() {
		ModelAndView mav = new ModelAndView("filesList");

		try {
			Drive service = DriveManager.getDriveService();
            //File file = DriveManager.insertFile(service, "test.txt", "this is first insert", null, "text/plain", "G:\\STUDIA\\javaeetutorial5\\README.txt");
            List<File> files = DriveManager.getAllFiles(service);
			for (File f : files) {
				System.out.println(f.getTitle());
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		mav.addObject("username", SecurityHelper.getUsername());

		return mav;
	}
}