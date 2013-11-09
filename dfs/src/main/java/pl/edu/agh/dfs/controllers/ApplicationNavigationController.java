package pl.edu.agh.dfs.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.agh.dfs.googledrive.DriveManager;
import pl.edu.agh.dfs.utils.SecurityHelper;

import com.google.api.services.drive.model.File;

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class ApplicationNavigationController implements Serializable {

	private List<File> files = null;

	private final int BUFFER_SIZE = 4096;

	@RequestMapping("/*")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");

		addCommonValues(mav, "home");
		return mav;
	}

	@RequestMapping("/fileList")
	public ModelAndView filesList() {
		ModelAndView mav = new ModelAndView("fileList");

		downloadFiles();
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

	@RequestMapping(value = "/file/{fileNr}", method = RequestMethod.GET)
	@ResponseBody
	public void getFile(@PathVariable("fileNr") int fileNr, HttpServletResponse response) {
		if (files == null) {
			downloadFiles();
		}

		File file = files.get(fileNr);
		long fileSize = file.getFileSize();

		response.setContentType(file.getMimeType());
		response.setContentLength((int) fileSize);

		try {
			InputStream inputStream = DriveManager.downloadFile(file);
			OutputStream outStream = response.getOutputStream();

			byte[] buffer = new byte[BUFFER_SIZE];
			int bytesRead = -1;

			// write bytes read from the input stream into the output stream
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outStream.write(buffer, 0, bytesRead);
			}

			inputStream.close();
			outStream.close();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	private void addCommonValues(ModelAndView mav, String name) {
		mav.addObject("isAdmin", SecurityHelper.hasUserRole("ROLE_ADMIN"));
		mav.addObject("username", SecurityHelper.getUsername());
		mav.addObject("viewName", name);
	}

	private void downloadFiles() {
		try {
			files = DriveManager.getAllFiles();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}