package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

public class GoogleDriveImpl implements GoogleDrive {

	/** Email of the Service Account */
	private static final String SERVICE_ACCOUNT_EMAIL = "1037047798881-90rmqar39s6uqi78bdk3dnmhbqbvqc1r@developer.gserviceaccount.com";

	/** Path to the Service Account's Private Key file */
	private static final String SERVICE_ACCOUNT_PKCS12_FILE_PATH = "googleAuthentication/4656349a2c2ec6511a6790dba4deee6fc115eaa1-privatekey.p12";

	/** Application name */
	private static final String APPLICATION_NAME = "GoogleDriveFileSharing";

	/*
	 * (non-Javadoc)
	 * @see pl.edu.agh.dfs.googledrive.GoogleDrive#getDriveService()
	 */
	@Override
	public Drive getDriveService() throws GeneralSecurityException, IOException, URISyntaxException {
		HttpTransport httpTransport = new NetHttpTransport();
		JacksonFactory jsonFactory = new JacksonFactory();
		List<String> scopes = new ArrayList<String>();
		scopes.add(DriveScopes.DRIVE);

		String filename = Thread.currentThread().getContextClassLoader().getResource(SERVICE_ACCOUNT_PKCS12_FILE_PATH)
				.getFile();

		GoogleCredential credential = new GoogleCredential.Builder().setTransport(httpTransport)
				.setJsonFactory(jsonFactory).setServiceAccountId(SERVICE_ACCOUNT_EMAIL).setServiceAccountScopes(scopes)
				.setServiceAccountPrivateKeyFromP12File(new java.io.File(filename)).build();
		Drive service = new Drive.Builder(httpTransport, jsonFactory, null).setHttpRequestInitializer(credential)
				.setApplicationName(APPLICATION_NAME).build();
		return service;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * pl.edu.agh.dfs.googledrive.GoogleDrive#retrieveAllFiles(com.google.api
	 * .services.drive.Drive)
	 */
	@Override
	public List<File> retrieveAllFiles(Drive service) throws IOException {
		List<File> result = new ArrayList<File>();
		Files.List request = service.files().list();

		do {
			try {
				FileList files = request.execute();

				result.addAll(files.getItems());
				request.setPageToken(files.getNextPageToken());
			} catch (IOException e) {
				System.out.println("An error occurred: " + e);
				request.setPageToken(null);
			}
		} while (request.getPageToken() != null && request.getPageToken().length() > 0);

		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * pl.edu.agh.dfs.googledrive.GoogleDrive#insertFile(com.google.api.services
	 * .drive.Drive, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public File insertFile(Drive service, String title, String description, String parentId, String mimeType,
			String filename) {
		// File's metadata.
		File body = new File();
		body.setTitle(title);
		body.setDescription(description);
		body.setMimeType(mimeType);

		// Set the parent folder.
		if (parentId != null && parentId.length() > 0) {
			body.setParents(Arrays.asList(new ParentReference().setId(parentId)));
		}

		// File's content.
		java.io.File fileContent = new java.io.File(filename);
		FileContent mediaContent = new FileContent(mimeType, fileContent);
		try {
			File file = service.files().insert(body, mediaContent).execute();

			// Uncomment the following line to print the File ID.
			// System.out.println("File ID: %s" + file.getId());

			return file;
		} catch (IOException e) {
			System.out.println("An error occured: " + e);
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * pl.edu.agh.dfs.googledrive.GoogleDrive#downloadFile(com.google.api.services
	 * .drive.Drive, com.google.api.services.drive.model.File)
	 */
	@Override
	public InputStream downloadFile(Drive service, File file) {
		if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
			try {
				HttpResponse resp = service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
						.execute();
				return resp.getContent();
			} catch (IOException e) {
				// An error occurred.
				e.printStackTrace();
				return null;
			}
		} else {
			// The file doesn't have any content stored on Drive.
			return null;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * pl.edu.agh.dfs.googledrive.GoogleDrive#deleteFile(com.google.api.services
	 * .drive.Drive, java.lang.String)
	 */
	@Override
	public void deleteFile(Drive service, String fileId) {
		try {
			service.files().delete(fileId).execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see
	 * pl.edu.agh.dfs.googledrive.GoogleDrive#updateFile(com.google.api.services
	 * .drive.Drive, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public File updateFile(Drive service, String fileId, String newTitle, String newDescription, String newMimeType,
			String newFilename, boolean newRevision) {
		try {
			// First retrieve the file from the API.
			File file = service.files().get(fileId).execute();

			// File's new metadata.
			file.setTitle(newTitle);
			file.setDescription(newDescription);
			file.setMimeType(newMimeType);

			// File's new content.
			java.io.File fileContent = new java.io.File(newFilename);
			FileContent mediaContent = new FileContent(newMimeType, fileContent);

			// Send the request to the API.
			File updatedFile = service.files().update(fileId, file, mediaContent).execute();

			return updatedFile;
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
			return null;
		}
	}
}
