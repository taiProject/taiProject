package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.api.services.drive.model.File;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "/config/spring-test-config.xml")
public class DriveManagerTest {

	@Autowired
	private DriveManager manager;

	@Autowired
	GoogleDriveTestAnswers answers;

	@Test
	public void testManagerNotNull() {
		Assert.assertTrue(manager != null);
	}

	@Test
	public void testAnswersNotNull() {
		Assert.assertTrue(answers != null);
	}

	@Test
	public void getAll() throws GeneralSecurityException, IOException, URISyntaxException {
		List<File> files = manager.getAllFiles();
		List<File> tartgetList = answers.retrieveAllFiles();

		Assert.assertTrue(files.size() == tartgetList.size());
	}

	@Test
	public void insertFile() throws GeneralSecurityException, IOException, URISyntaxException {
		String filename = "TestFile.txt";

		File f = manager.insertFile(filename);

		Assert.assertTrue(answers.fileExists(f.getId()));
	}

	@Test
	public void insertFileDescriptionAndDriveTitle() throws GeneralSecurityException, IOException, URISyntaxException {
		String fileName = "TestFile.txt";
		String driveTitle = "DriveTitle";
		String description = "desc";

		File f = manager.insertFile(fileName, driveTitle, description);

		Assert.assertTrue(answers.fileExists(f.getId()));
		Assert.assertTrue(answers.getFile(f.getId()).getDescription().equals(description));
	}

	@Test
	public void deleteFile() throws GeneralSecurityException, IOException, URISyntaxException {
		File f = manager.insertFile("deleteFileTestFile.txt");

		Assert.assertTrue(answers.fileExists(f.getId()));

		manager.deleteFile(f.getId());

	}

	@Test
	public void updateFileNameTitleDesc() throws GeneralSecurityException, IOException, URISyntaxException {
		File f = manager.insertFile("deleteFileTestFile.txt");

		Assert.assertTrue(answers.fileExists(f.getId()));

		String newDesc = "newDesc";
		String newId = "newFileId";
		String newTitle = "newFileTitle";
		String newMime = "newMime";
		String newFileName = "newFileName";

		manager.updateFile(newId, newTitle, newDesc, newMime, newFileName);
		List<File> files = answers.retrieveAllFiles();

		for (File file : files) {
			if (file.getId().equals(newId)) {
				Assert.assertTrue(file.getDescription().equals(newDesc));
				Assert.assertTrue(file.getMimeType().equals(newMime));
				break;
			}
		}
	}
}
