package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

public interface GoogleDrive {

	/**
	 * Build and returns a Drive service object authorized with the service
	 * accounts.
	 * 
	 * @return Drive service object that is ready to make requests.
	 */
	public abstract Drive getDriveService() throws GeneralSecurityException, IOException, URISyntaxException;

	/**
	 * Retrieve a list of File resources.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @return List of File resources.
	 */
	public abstract List<File> retrieveAllFiles(Drive service) throws IOException;

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
	public abstract File insertFile(Drive service, String title, String description, String parentId, String mimeType,
			String filename);

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
	public abstract InputStream downloadFile(Drive service, File file);

	/**
	 * Permanently delete a file, skipping the trash.
	 * 
	 * @param service
	 *            Drive API service instance.
	 * @param fileId
	 *            ID of the file to delete.
	 */
	public abstract void deleteFile(Drive service, String fileId);

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
	public abstract File updateFile(Drive service, String fileId, String newTitle, String newDescription,
			String newMimeType, String newFilename, boolean newRevision);

}