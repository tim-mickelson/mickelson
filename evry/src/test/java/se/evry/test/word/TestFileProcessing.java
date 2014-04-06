package se.evry.test.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.evry.word.FileManager;
import se.evry.word.DocumentProcessor;
import se.evry.word.Presentation;

/**
 * Good links:
 * http://howtodoinjava.com/2013/10/06/how-to-read-data-from-inputstream-into-string-in-java/
 * 
 * @author Tim Mickelson
 *
 */
public class TestFileProcessing {
	Logger logger = LoggerFactory.getLogger(getClass());
	String fileName = "C:/temp/lor.txt";
	String folderName = "c:/temp";
	
	@Test
	public void testProcessFolder() throws IOException{
		FileManager fileUtil = new FileManager();
		List<DocumentProcessor> processors = fileUtil.processFolder(folderName);
		Assert.assertNotNull(processors);
			
        Presentation presentation = new Presentation();
        presentation.setProcessors(processors);
        presentation.print();
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
        processor.validateWords(reader, "test.txt");
        
        int points = processor.points();
        logger.info("points: "+points);
        List<DocumentProcessor> list = new ArrayList<DocumentProcessor>();
        list.add(processor);
        
        Presentation p = new Presentation();
        p.setProcessors(list);
        
        p.print();
        
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
        processor.validateWords(reader, file.getName());
        
        reader.close();
	}

}  // end public class TestFileProcessing
