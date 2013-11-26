package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import pl.edu.agh.dfs.utils.MimeTypes;

import com.google.api.client.http.ByteArrayContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public class DriveManager {

	private GoogleDrive googleDrive;

	/**
	 * Retrieve a list of File resources.
	 * 
	 * @return List of File resources.
	 */
	public List<File> getAllFiles() throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		return googleDrive.retrieveAllFiles(drive);
	}

	/**
	 * Insert new file.
	 * 
	 * @param filename
	 *            Filename of the file to insert.
	 * @return Inserted file metadata if successful, {@code null} otherwise.
	 */
	public File insertFile(String filename) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		String mimeType = MimeTypes.getMimeType(filename);
		return googleDrive.insertFile(drive, filename, filename, null, mimeType, filename);
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
	public File insertFile(String filename, String driveTitle, String description) throws GeneralSecurityException,
			IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		String mimeType = MimeTypes.getMimeType(filename);
		return googleDrive.insertFile(drive, driveTitle, description, null, mimeType, filename);
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
	public File insertFile(File file, ByteArrayContent content) throws GeneralSecurityException, IOException,
			URISyntaxException {
		Drive drive = googleDrive.getDriveService();
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
	public InputStream downloadFile(File file) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		return googleDrive.downloadFile(drive, file);
	}

	/**
	 * Permanently delete a file, skipping the trash.
	 * 
	 * @param fileId
	 *            ID of the file to delete.
	 */
	public void deleteFile(String fileId) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		googleDrive.deleteFile(drive, fileId);
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
	 * @return Updated file metadata if successful, {@code null} otherwise.
	 */
	public File updateFile(String fileId, String title, String description, String mimeType, String fileName)
			throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		return googleDrive.updateFile(drive, fileId, title, description, mimeType, fileName, true);
	}

	/**
	 * Update an existing file's metadata and content.
	 * 
	 * @param file
	 *            File data to be updated
	 * @return Updated file metadata if successful, {@code null} otherwise.
	 */
	public File updateFile(File file) throws GeneralSecurityException, IOException, URISyntaxException {
		Drive drive = googleDrive.getDriveService();
		return drive.files().update(file.getId(), file).execute();
	}

	public void setDrive(GoogleDrive googleDrive) {
		this.googleDrive = googleDrive;
	}

}
