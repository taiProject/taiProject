package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import pl.edu.agh.dfs.utils.MimeTypes;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.ByteArrayContent;
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

public abstract class DriveManager {

	/** Email of the Service Account */
	private static final String SERVICE_ACCOUNT_EMAIL = "1037047798881-90rmqar39s6uqi78bdk3dnmhbqbvqc1r@developer.gserviceaccount.com";

	/** Path to the Service Account's Private Key file */
	private static final String SERVICE_ACCOUNT_PKCS12_FILE_PATH = "googleAuthentication/4656349a2c2ec6511a6790dba4deee6fc115eaa1-privatekey.p12";

	/** Application name */
	private static final String APPLICATION_NAME = "GoogleDriveFileSharing";

	/**
	 * Build and returns a Drive service object authorized with the service
	 * accounts.
	 * 
	 * @return Drive service object that is ready to make requests.
	 */
	public static Drive getDriveService() throws GeneralSecurityException, IOException, URISyntaxException {
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

	/**
	 * Retrieve a list of File resources.
	 * 
	 * @return List of File resources.
	 */
	public static List<File> getAllFiles() throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		return retrieveAllFiles(drive);
	}

	/**
	 * Insert new file.
	 * 
	 * @param filename
	 *            Filename of the file to insert.
	 * @return Inserted file metadata if successful, {@code null} otherwise.
	 */
	public static File insertFile(String filename) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		String mimeType = MimeTypes.getMimeType(filename);
		return insertFile(drive, filename, filename, null, mimeType, filename);
	}

	/**
	 * Insert new file.
	 * 
	 * @param filename
	 *            Filename of the file to insert.
	 * @param driveTitle
	 *            Title of the file to insert, including the extension.
	 * @param description
	 *            Description of the file to insert.
	 * @return Inserted file metadata if successful, {@code null} otherwise.
	 */
	public static File insertFile(String filename, String driveTitle, String description)
			throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		String mimeType = MimeTypes.getMimeType(filename);
		return insertFile(drive, driveTitle, description, null, mimeType, filename);
	}

	/**
	 * Insert new file.
	 * 
	 * @param file
	 *            Inserted file
	 * @param content
	 *            File content
	 * @return Inserted file metadata if successful, {@code null} otherwise.
	 */
	public static File insertFile(File file, ByteArrayContent content) throws GeneralSecurityException, IOException,
			URISyntaxException {
		Drive drive = getDriveService();
		return drive.files().insert(file, content).execute();
	}

	/**
	 * Download a file's content.
	 * 
	 * @param file
	 *            Drive File instance.
	 * @return InputStream containing the file's content if successful,
	 *         {@code null} otherwise.
	 */
	public static InputStream downloadFile(File file) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		return downloadFile(drive, file);
	}

	/**
	 * Permanently delete a file, skipping the trash.
	 * 
	 * @param fileId
	 *            ID of the file to delete.
	 */
	public static void deleteFile(String fileId) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		deleteFile(drive, fileId);
	}

	public static File updateFile(String fileId, String title, String description, String mimeType, String fileName)
			throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		return updateFile(drive, fileId, title, description, mimeType, fileName, true);
	}

	public static File updateFile(File file) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = getDriveService();
		return drive.files().update(file.getId(), file).execute();
	}

	/**
	 * Retrieve a list of File resources.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @return List of File resources.
	 */
	private static List<File> retrieveAllFiles(Drive service) throws IOException {
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

	/**
	 * Insert new file.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param title
	 *            Title of the file to insert, including the extension.
	 * @param description
	 *            Description of the file to insert.
	 * @param parentId
	 *            Optional parent folder's ID.
	 * @param mimeType
	 *            MIME type of the file to insert.
	 * @param filename
	 *            Filename of the file to insert.
	 * @return Inserted file metadata if successful, {@code null} otherwise.
	 */
	private static File insertFile(Drive service, String title, String description, String parentId, String mimeType,
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

	/**
	 * Download a file's content.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param file
	 *            Drive File instance.
	 * @return InputStream containing the file's content if successful,
	 *         {@code null} otherwise.
	 */
	private static InputStream downloadFile(Drive service, File file) {
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

	/**
	 * Permanently delete a file, skipping the trash.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to delete.
	 */
	private static void deleteFile(Drive service, String fileId) {
		try {
			service.files().delete(fileId).execute();
		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

	/**
	 * Update an existing file's metadata and content.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to update.
	 * @param newTitle
	 *            New title for the file.
	 * @param newDescription
	 *            New description for the file.
	 * @param newMimeType
	 *            New MIME type for the file.
	 * @param newFilename
	 *            Filename of the new content to upload.
	 * @param newRevision
	 *            Whether or not to create a new revision for this file.
	 * @return Updated file metadata if successful, {@code null} otherwise.
	 */
	private static File updateFile(Drive service, String fileId, String newTitle, String newDescription,
			String newMimeType, String newFilename, boolean newRevision) {
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
