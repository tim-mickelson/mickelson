package se.evry.test.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.jsoup.Jsoup;
import org.junit.Test;

import se.evry.word.FileManager;
import se.evry.word.DocumentProcessor;

/**
 * Good links:
 * http://howtodoinjava.com/2013/10/06/how-to-read-data-from-inputstream-into-string-in-java/
 * 
 * @author Tim Mickelson
 *
 */
public class TestFileProcessing {

	String fileName = "C:/temp/lor.txt";
	String folderName = "c:/temp";
	
	@Test
	public void testProcessFolder() throws IOException{
		FileManager fileUtil = new FileManager();
		fileUtil.processFolder(folderName);
	}
	
	@Test
	public void testProcessTextFile() throws IOException{
		FileManager fileUtil = new FileManager();
		fileUtil.processFile(fileName);
	}
	
	@Test
	public void testTextFile() throws IOException{
		InputStream in = new FileInputStream(new File("C:/temp/test.txt"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        DocumentProcessor processor = new DocumentProcessor();
        processor.validateWords(reader);
        
        reader.close();
	}

	@Test
	public void testHtmlFile() throws IOException{
		//File file = new File("C:/temp/test.html");
		File file = new File("C:/temp/two_towers.html");
		//InputStream in = new FileInputStream(file);
        //BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        //String textOnly = Jsoup.parse(sb.toString()).text();
        String html = Jsoup.parse(file, "UTF-8").text();
        BufferedReader reader = new BufferedReader(new StringReader(html));
        
        DocumentProcessor processor = new DocumentProcessor();
        processor.validateWords(reader);
        
        reader.close();
	}

}  // end public class TestFileProcessing