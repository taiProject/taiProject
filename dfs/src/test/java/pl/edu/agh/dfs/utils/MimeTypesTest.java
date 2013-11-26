package pl.edu.agh.dfs.utils;

import org.junit.Assert;
import org.junit.Test;

public class MimeTypesTest {

	@Test
	public void getCssMimeType() {
		String fileName = "test.css";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/css");
	}

	@Test
	public void getCsvMimeType() {
		String fileName = "test.csv";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/csv");
	}

	@Test
	public void getGifMimeType() {
		String fileName = "test.gif";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "image/gif");
	}

	@Test
	public void getHtmMimeType() {
		String fileName = "test.htm";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/html");
	}

	@Test
	public void getHtmlMimeType() {
		String fileName = "test.html";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/html");
	}

	@Test
	public void getJpegMimeType() {
		String fileName = "test.jpeg";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "image/jpeg");
	}

	@Test
	public void getMp3MimeType() {
		String fileName = "test.mp3";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "audio/mpeg");
	}

	@Test
	public void getMp4MimeType() {
		String fileName = "test.mp4";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "video/mp4");
	}

	@Test
	public void getMpegMimeType() {
		String fileName = "test.mpeg";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "audio/mpeg");
	}

	@Test
	public void getPdfMimeType() {
		String fileName = "test.pdf";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "application/pdf");
	}

	@Test
	public void getPlainTextMimeType() {
		String fileName = "test.txt";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/plain");
	}

	@Test
	public void getPngMimeType() {
		String fileName = "test.png";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "image/png");
	}

	@Test
	public void getXmlMimeType() {
		String fileName = "test.xml";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "text/xml");
	}

	@Test
	public void getZipMimeType() {
		String fileName = "test.zip";
		Assert.assertEquals(MimeTypes.getMimeType(fileName), "application/zip");
	}
}
