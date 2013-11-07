package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.ParentReference;

public class DriveManager {

	/** Email of the Service Account */
	private static final String SERVICE_ACCOUNT_EMAIL = "1037047798881-90rmqar39s6uqi78bdk3dnmhbqbvqc1r@developer.gserviceaccount.com";

	/** Path to the Service Account's Private Key file */
	private static final String SERVICE_ACCOUNT_PKCS12_FILE_PATH = "googleAuthentication/4656349a2c2ec6511a6790dba4deee6fc115eaa1-privatekey.p12";

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
				.build();
		return service;
	}

	public static List<File> getAllFiles(Drive service) throws IOException {
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

    public static File insertFile(Drive service, String title, String description, String parentId, String mimeType, String filename){
        File body = new File();
        body.setTitle(title);
        body.setDescription(description);
        body.setMimeType(mimeType);

        if (parentId != null && parentId.length() > 0) {
            body.setParents(
                    Arrays.asList(new ParentReference().setId(parentId)));
        }

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
}
