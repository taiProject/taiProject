package pl.edu.agh.dfs.googledrive;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.security.SecureRandom;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class GoogleDriveTestImpl implements GoogleDrive, GoogleDriveTestAnswers {

	private List<File> files = new LinkedList<File>();
	private File file = new File();
	private SecureRandom random = new SecureRandom();

	@PostConstruct
	private void initialize() {
		file.setId("DefaultId");
		file.setTitle("TestFile");
		file.setDescription("TestFile");
		file.setMimeType("MimeType");

		files.add(file);
	}

	@Override
	public Drive getDriveService() throws GeneralSecurityException, IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		return new Drive.Builder(httpTransport, jsonFactory, null).build();
	}

	@Override
	public List<File> retrieveAllFiles(Drive service) throws IOException {
		if (service == null) {
			return null;
		}
		return files;
	}

	@Override
	public File insertFile(Drive service, String title, String description, String parentId, String mimeType,
			String filename) {
		if (service == null) {
			return null;
		}

		String id = new BigInteger(130, random).toString(32);

		File file = new File();
		file.setId(id);
		file.setTitle(title);
		file.setDescription(description);
		file.setMimeType(mimeType);
		files.add(file);

		return file;
	}

	@Override
	public InputStream downloadFile(Drive service, File file) {
		if (service == null || file == null) {
			return null;
		} else {
			try {
				return new FileInputStream(file.getTitle());
			} catch (FileNotFoundException e) {
				return null;
			}
		}
	}

	@Override
	public void deleteFile(Drive service, String fileId) {
		if (service == null) {
			throw new RuntimeException();
		}
		if (fileId == null) {
			throw new RuntimeException();
		}
	}

	@Override
	public File updateFile(Drive service, String fileId, String newTitle, String newDescription, String newMimeType,
			String newFilename, boolean newRevision) {
		if (service == null || StringUtils.isEmpty(fileId)) {
			throw new RuntimeException();
		}
		File found = null;
		for (File f : files) {
			if (f.getId().equals(fileId)) {
				found = f;
			}
		}
		if (found == null) {
			return null;
		}

		found.setTitle(newTitle);
		found.setDescription(newDescription);
		found.setMimeType(newMimeType);

		return found;
	}

	@Override
	public List<File> retrieveAllFiles() {
		return files;
	}

	@Override
	public File insertFile() {
		if (files.size() == 0) {
			return null;
		}
		return files.get(files.size() - 1);
	}

	@Override
	public boolean fileExists(String fileId) {
		for (File f : files) {
			if (f.getId().equals(fileId)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public File getFile(String id) {
		for (File f : files) {
			if (f.getId().equals(id)) {
				return f;
			}
		}
		return null;
	}

	@Override
	public File getDefaultFile() {
		return file;
	}

}
