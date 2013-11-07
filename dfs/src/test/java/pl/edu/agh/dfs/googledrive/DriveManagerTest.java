package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.google.api.services.drive.model.File;

public class DriveManagerTest {

	// @Test
	// public void getDriveService() throws GeneralSecurityException,
	// IOException, URISyntaxException {
	// Drive service = DriveManager.getDriveService();
	// Assert.assertNotNull(service);
	// Assert.assertNotNull(service.getBaseUrl());
	// Assert.assertNotEquals(service.getBaseUrl(), "");
	// }

	@Test
	public void getAllFiles() throws GeneralSecurityException, IOException, URISyntaxException {
		List<File> files = DriveManager.getAllFiles();

		for (File f : files) {
			System.out.println(f.getId() + ". " + f.getTitle());
		}
		Assert.assertNotNull(files);

		// TODO Dokonczyc jak bedzie wiecej operacji
	}

}
