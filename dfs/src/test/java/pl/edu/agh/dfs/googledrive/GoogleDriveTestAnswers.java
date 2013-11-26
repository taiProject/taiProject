package pl.edu.agh.dfs.googledrive;

import java.util.List;

import com.google.api.services.drive.model.File;

public interface GoogleDriveTestAnswers {

	public abstract List<File> retrieveAllFiles();

	public abstract File insertFile();

	public abstract boolean fileExists(String FileId);

	public abstract File getFile(String id);

	public abstract File getDefaultFile();
}
