package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/config/spring-test-config.xml")
public class DriveManagerTest {

	@Test
	public void getDriveService() throws GeneralSecurityException, IOException, URISyntaxException {
		Drive service = DriveManager.getDriveService();
		Assert.assertNotNull(service);
		Assert.assertNotNull(service.getBaseUrl());
		Assert.assertNotEquals(service.getBaseUrl(), "");
	}

	@Test
	public void getAllFiles() throws GeneralSecurityException, IOException, URISyntaxException {
		List<File> files = DriveManager.getAllFiles(DriveManager.getDriveService());
		Assert.assertNotNull(files);

		// TODO Dokonczyc jak bedzie wiecej operacji
	}
}
