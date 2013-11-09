package pl.edu.agh.dfs.googledrive;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.GeneralSecurityException;
import java.util.List;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.api.services.drive.model.File;

public class DriveManagerTest {

	private static String textFilePath;
	private static String jpgFilePath;
	private static String pngFilePath;

	@BeforeClass
	public static void initialize() {
		String path = DriveManagerTest.class.getResource("/testfiles/testfile.txt").toString();
		textFilePath = path.substring(path.indexOf('/') + 1, path.length());

		path = DriveManagerTest.class.getResource("/testfiles/sample.jpg").toString();
		jpgFilePath = path.substring(path.indexOf('/') + 1, path.length());

		path = DriveManagerTest.class.getResource("/testfiles/scroogeSample.png").toString();
		pngFilePath = path.substring(path.indexOf('/') + 1, path.length());
	}

	@Test
	public void insertPlainTextFile() throws GeneralSecurityException, IOException, URISyntaxException {
		File fl = DriveManager.insertFile(textFilePath);

		List<File> files = DriveManager.getAllFiles();

		for (File f : files) {
			System.out.println(f.getTitle());
		}

		// DriveManager.deleteFile(fl.getId());
		//
		// files = DriveManager.getAllFiles();
		//
		// for (File f : files) {
		// System.out.println(f.getTitle());
		// }
	}

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
