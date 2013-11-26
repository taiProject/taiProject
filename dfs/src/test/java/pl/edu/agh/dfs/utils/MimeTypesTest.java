package pl.edu.agh.dfs.utils;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class MimeTypesTest {

    @Test
    public void getCssMimeType(){
        String fileName = "test.css";
        assertEquals(MimeTypes.getMimeType(fileName), "text/css");
    }

    @Test
    public void getCsvMimeType(){
        String fileName = "test.csv";
        assertEquals(MimeTypes.getMimeType(fileName), "text/csv");
    }
    @Test
    public void getGifMimeType(){
        String fileName = "test.gif";
        assertEquals(MimeTypes.getMimeType(fileName), "image/gif");
    }

    @Test
    public void getHtmMimeType(){
        String fileName = "test.htm";
        assertEquals(MimeTypes.getMimeType(fileName), "text/html");
    }

    @Test
    public void getHtmlMimeType(){
        String fileName = "test.html";
        assertEquals(MimeTypes.getMimeType(fileName), "text/html");
    }

    @Test
    public void getJpegMimeType(){
        String fileName = "test.jpeg";
        assertEquals(MimeTypes.getMimeType(fileName), "image/jpeg");
    }

    @Test
    public void getMp3MimeType(){
        String fileName = "test.mp3";
        assertEquals(MimeTypes.getMimeType(fileName), "audio/mpeg");
    }

    @Test
    public void getMp4MimeType(){
        String fileName = "test.mp4";
        assertEquals(MimeTypes.getMimeType(fileName), "video/mp4");
    }

    @Test
    public void getMpegMimeType(){
        String fileName = "test.mpeg";
        assertEquals(MimeTypes.getMimeType(fileName), "audio/mpeg");
    }
    @Test
    public void getPdfMimeType(){
        String fileName = "test.pdf";
        assertEquals(MimeTypes.getMimeType(fileName), "application/pdf");
    }
    @Test
    public void getPlainTextMimeType(){
        String fileName = "test.txt";
        assertEquals(MimeTypes.getMimeType(fileName), "text/plain");
    }
    @Test
    public void getPngMimeType(){
        String fileName = "test.png";
        assertEquals(MimeTypes.getMimeType(fileName), "image/png");
    }
    @Test
    public void getXmlMimeType(){
        String fileName = "test.xml";
        assertEquals(MimeTypes.getMimeType(fileName), "text/xml");
    }
    @Test
    public void getZipMimeType(){
        String fileName = "test.zip";
        assertEquals(MimeTypes.getMimeType(fileName), "application/zip");
    }
}
