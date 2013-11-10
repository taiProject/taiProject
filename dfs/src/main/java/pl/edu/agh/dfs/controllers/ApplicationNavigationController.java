package pl.edu.agh.dfs.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.agh.dfs.googledrive.DriveManager;
import pl.edu.agh.dfs.utils.SecurityHelper;

import com.google.api.client.http.ByteArrayContent;
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
	public ModelAndView fileList() {
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

	@RequestMapping(value = "/delete/{fileId}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("fileId") String fileId) {
		try {
			DriveManager.deleteFile(fileId);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/fileList");
	}

	@RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
	public ModelAndView delete() {
		try {
			for (File file : files) {
				DriveManager.deleteFile(file.getId());
			}
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/fileList");
	}

	@RequestMapping("/newFile")
	public ModelAndView newFile() {
		ModelAndView mav = new ModelAndView("file");

		mav.addObject("newFile", true);

		addCommonValues(mav, "fileList");
		return mav;
	}

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName,
			@RequestParam("description") String description) {
		ModelAndView mav = new ModelAndView("redirect:/fileList");

		if (file == null) {
			return mav;
		}

		File uploadFile = new File();
		if (StringUtils.isEmpty(fileName)) {
			uploadFile.setTitle(file.getOriginalFilename());
		} else {
			uploadFile.setTitle(fileName);
		}

		if (StringUtils.isEmpty(description)) {
			uploadFile.setDescription(file.getOriginalFilename());
		} else {
			uploadFile.setDescription(description);
		}

		uploadFile.setMimeType(file.getContentType());

		try {
			ByteArrayContent fileContent = new ByteArrayContent(file.getContentType(), file.getBytes());
			DriveManager.getDriveService().files().insert(uploadFile, fileContent).execute();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return mav;
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