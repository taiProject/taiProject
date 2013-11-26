package pl.edu.agh.dfs.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import pl.edu.agh.dfs.daos.UserDao;
import pl.edu.agh.dfs.googledrive.DriveManager;
import pl.edu.agh.dfs.models.User;
import pl.edu.agh.dfs.utils.SecurityHelper;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.model.File;

/**
 * Main controller of application. Navigates through the pages and maintain
 * needed operations.
 */

@Controller
@Scope("session")
@SuppressWarnings("serial")
public class ApplicationNavigationController implements Serializable {

	private final int BUFFER_SIZE = 4096;

	private List<File> files = null;

	@Autowired
	private DriveManager manager;

	@Autowired
	private UserDao userDao;

	@RequestMapping("/*")
	public ModelAndView demo() {
		ModelAndView mav = new ModelAndView("index");

		addCommonValues(mav, "home");
		return mav;
	}

	/**
	 * 
	 * 
	 * @return Model and view of files list available on Google Drive
	 */
	@RequestMapping("/fileList")
	public ModelAndView fileList() {
		ModelAndView mav = new ModelAndView("fileList");

		downloadFiles();
		mav.addObject("files", files);

		addCommonValues(mav, "fileList");
		return mav;
	}

	/**
	 * 
	 * @return Model and view containing information about currently logged
	 *         user.
	 */
	@RequestMapping("/userInfo")
	public ModelAndView userInfo() {
		ModelAndView mav = new ModelAndView("userInfo");

		String login = SecurityHelper.getUsername();

		if (SecurityHelper.getAuthorities().size() == 1) {
			mav.addObject("admin", false);
		} else {
			mav.addObject("admin", true);
		}
		mav.addObject("login", login);

		addCommonValues(mav, "userInfo");
		return mav;
	}

	/**
	 * 
	 * @return Model and view containing list of users to manage.
	 */
	@RequestMapping("/userManagement")
	public ModelAndView userManagement() {
		ModelAndView mav = new ModelAndView("userManagement");

		List<User> users = userDao.selectAll();
		mav.addObject("users", users);

		addCommonValues(mav, "userManagement");
		return mav;
	}

	/**
	 * 
	 * @return Model and view for adding new user.
	 */
	@RequestMapping("/addUser")
	public ModelAndView addUser() {
		ModelAndView mav = new ModelAndView("user");

		mav.addObject("newUser", true);
		mav.addObject("admin", true);

		addCommonValues(mav, "user");
		return mav;
	}

	/**
	 * @param fileNr
	 *            number of the file on Google Drive to download
	 * 
	 */
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
			InputStream inputStream = manager.downloadFile(file);
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

	/**
	 * @param fileId
	 *            id of a file to delete.
	 * @return Model and view with deleted file.
	 */
	@RequestMapping(value = "/delete/{fileId}", method = RequestMethod.GET)
	public ModelAndView delete(@PathVariable("fileId") String fileId) {
		try {
			manager.deleteFile(fileId);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/fileList");
	}

	/**
	 * 
	 * @return Model and view with deleted all files.
	 */
	@RequestMapping(value = "/deleteAll", method = RequestMethod.GET)
	public ModelAndView delete() {
		try {
			for (File file : files) {
				manager.deleteFile(file.getId());
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

	/**
	 * 
	 * @return Model and view to add new file
	 */
	@RequestMapping("/newFile")
	public ModelAndView newFile() {
		ModelAndView mav = new ModelAndView("file");

		mav.addObject("newFile", true);

		addCommonValues(mav, "fileList");
		return mav;
	}

	/**
	 * @param login
	 *            login of newly added user
	 * @param password
	 *            password of newly added user
	 * @param roleName
	 *            type of role in system
	 * @return Model and view to create new user
	 */
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public ModelAndView newUser(@RequestParam("login") String login, @RequestParam("password") String password,
			@RequestParam("role") String roleName) {
		ModelAndView mav = new ModelAndView("redirect:/userManagement");

		userDao.create(login, password, roleName);

		return mav;
	}

	/**
	 * @param login
	 *            login of the user to be deleted
	 * @return Model and view after deleting user from database.
	 */
	@RequestMapping(value = "/deleteUser/{login}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable("login") String login) {
		userDao.delete(login);

		return new ModelAndView("redirect:/userManagement");
	}

	/**
	 * @param file
	 *            file to be uploaded
	 * @param fileName
	 *            name of the file on Google Drive
	 * @param description
	 *            description of the file
	 * @return Model and view to upload files.
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName,
			@RequestParam("description") String description) {
		ModelAndView mav = new ModelAndView("redirect:/fileList");

		if (file == null) {
			return mav;
		}

		uploadNewFile(file, fileName, description);

		return mav;
	}

	/**
	 * @param login
	 *            login of the user to be edited
	 * @return Model and view to edit user
	 */
	@RequestMapping(value = "/editUser/{login}", method = RequestMethod.GET)
	public ModelAndView editUser(@PathVariable("login") String login) {
		ModelAndView mav = new ModelAndView("user");

		mav.addObject("user", userDao.select(login));
		mav.addObject("newUser", false);
		mav.addObject("admin", true);
		addCommonValues(mav, "userManagement");
		return mav;
	}

	/**
	 * @param login
	 *            updated login
	 * @param password
	 *            updated password
	 * @param roleName
	 *            updated role
	 * @return Model and view after updating user in database
	 */
	@RequestMapping(value = "/editUser", method = RequestMethod.POST)
	public ModelAndView editUser(@RequestParam("login") String login, @RequestParam("password") String password,
			@RequestParam("role") String roleName) {
		ModelAndView mav = new ModelAndView("redirect:/userManagement");

		userDao.update(login, password, roleName);
		mav.addObject("newUser", false);

		return mav;
	}

	/**
	 * @param login
	 *            previous login of the user
	 * @param newLogin
	 *            new login of the user
	 * @param password
	 *            new password of the user
	 * @param oldPassword
	 *            previous password of the user
	 * @param passwordReWrite
	 *            new password for confirmation if user didn't make a mistake
	 * @param roleName
	 *            role of the user
	 * @return Model and view for logged user to edit login and password
	 */
	@RequestMapping(value = "/editUserInfo", method = RequestMethod.POST)
	public ModelAndView editUserInfo(@RequestParam("login") String login, @RequestParam("newLogin") String newLogin,
			@RequestParam("password") String password, @RequestParam("passwordReWrite") String passwordReWrite,
			@RequestParam("oldPassword") String oldPassword, @RequestParam("role") String roleName) {
		ModelAndView mav = new ModelAndView("/userInfo");

		mav.addObject("login", login);
		if (roleName.equals("ROLE_ADMIN")) {
			mav.addObject("admin", true);
		} else {
			mav.addObject("admin", false);
		}
		if (!oldPassword.equals("") && oldPassword.equals(userDao.select(login).getPassword())) {
			if (password.equals("")) {
				if (!login.equals(newLogin)) {
					userDao.delete(login);
					userDao.create(newLogin, oldPassword, roleName);
				}
				mav = new ModelAndView("redirect:/index");
			} else {
				if (password.equals(passwordReWrite)) {
					if (!login.equals(newLogin)) {
						userDao.delete(login);
						userDao.create(newLogin, password, roleName);
					} else {
						userDao.update(login, password, roleName);
					}
					mav = new ModelAndView("redirect:/index");
				} else {
					mav.addObject("message", "mismatch password");
				}
			}
		} else {
			mav.addObject("message", "wrong old");
		}

		addCommonValues(mav, "userInfo");

		return mav;
	}

	/**
	 * @param fileNr
	 *            number of the file to edit
	 * @return Model and view for editing file.
	 */
	@RequestMapping(value = "/edit/{fileNr}", method = RequestMethod.GET)
	public ModelAndView editFile(@PathVariable("fileNr") int fileNr) {
		ModelAndView mav = new ModelAndView("file");

		if (files == null) {
			return new ModelAndView("redirect:/fileList");
		}

		mav.addObject("file", files.get(fileNr));
		mav.addObject("fileNr", fileNr);
		mav.addObject("newFile", false);

		addCommonValues(mav, "fileList");
		return mav;
	}

	/**
	 * 
	 * @param fileName
	 *            name of the file
	 * @param description
	 *            description of the file
	 * @param fileNr
	 *            number of the file on the files list
	 * @return Model and view for updating file on Google Drive
	 */
	@RequestMapping(value = "/editFile", method = RequestMethod.POST)
	public ModelAndView uploadFile(@RequestParam("fileName") String fileName,
			@RequestParam("description") String description, @RequestParam("fileNr") int fileNr) {
		ModelAndView mav = new ModelAndView("redirect:/fileList");

		File updateFile = files.get(fileNr);
		if (StringUtils.isNotEmpty(fileName)) {
			updateFile.setTitle(fileName);
		}

		if (StringUtils.isNotEmpty(description)) {
			updateFile.setDescription(description);
		}

		try {
			manager.updateFile(updateFile);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return mav;
	}

	/**
	 * Uploads file from Quick Upload and sends it Google Drive
	 * 
	 * @param request
	 *            Multipart request with file
	 * @return redirection to model
	 */
	@RequestMapping(value = "/quickUploadFile", method = RequestMethod.POST)
	public ModelAndView quickFileUpload(MultipartHttpServletRequest request) {
		ModelAndView mav = new ModelAndView("/fileTable");

		Iterator<String> iter = request.getFileNames();

		while (iter.hasNext()) {
			MultipartFile file = request.getFile(iter.next());
			uploadNewFile(file, file.getOriginalFilename(), file.getOriginalFilename());
		}

		downloadFiles();
		mav.addObject("files", files);

		addCommonValues(mav, "fileList");

		return mav;
	}

	private void addCommonValues(ModelAndView mav, String name) {
		mav.addObject("isAdmin", SecurityHelper.hasUserRole("ROLE_ADMIN"));
		mav.addObject("username", SecurityHelper.getUsername());
		mav.addObject("viewName", name);
	}

	private void downloadFiles() {
		try {
			files = manager.getAllFiles();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void uploadNewFile(MultipartFile file, String fileName, String description) {

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
			manager.insertFile(uploadFile, fileContent);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}