package pl.edu.agh.dfs.utils;

import org.apache.commons.lang.StringUtils;

public abstract class MimeTypes {

	private static String CSS = "text/css";
	private static String CSV = "text/csv";
	private static String GIF = "image/gif";
	private static String HTM = "text/html";
	private static String HTML = "text/html";
	private static String JPEG = "image/jpeg";
	private static String MP3 = "audio/mpeg";
	private static String MP4 = "video/mp4";
	private static String MPEG = "audio/mpeg";
	private static String PDF = "application/pdf";
	private static String PLAIN_TEXT = "text/plain";
	private static String PNG = "image/png";
	private static String XML = "text/xml";
	private static String ZIP = "application/zip";

	public static String getMimeType(String filename) {
		if (StringUtils.isEmpty(filename)) {
			return null;
		}
		filename = filename.toUpperCase();
		if (filename.endsWith(".CSS")) {
			return CSS;
		}
		if (filename.endsWith(".CSV")) {
			return CSV;
		}
		if (filename.endsWith(".GIF")) {
			return GIF;
		}
		if (filename.endsWith(".HTM")) {
			return HTM;
		}
		if (filename.endsWith(".HTML")) {
			return HTML;
		}
		if (filename.endsWith(".JPEG")) {
			return JPEG;
		}
		if (filename.endsWith(".MP3")) {
			return MP3;
		}
		if (filename.endsWith(".MP4")) {
			return MP4;
		}
		if (filename.endsWith(".MPEG")) {
			return MPEG;
		}
		if (filename.endsWith(".PDF")) {
			return PDF;
		}
		if (filename.endsWith(".TXT")) {
			return PLAIN_TEXT;
		}
		if (filename.endsWith(".PNG")) {
			return PNG;
		}
		if (filename.endsWith(".XML")) {
			return XML;
		}
		if (filename.endsWith(".ZIP")) {
			return ZIP;
		}
		return null;
	}
}
