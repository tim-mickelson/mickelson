package se.evry.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class handles files and folder to pass the single files of extension html, htm or txt for processing.
 * Each file after processing will save the result in a DocumentValidation object instance.
 * @author Tim Mickelson
 *
 */
public class FileManager {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * Process a file from filename and inject word points and frequency in DocumentProcessor bean.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #processFile(File)
	 * @param fileName
	 * @throws IOException
	 */
	public DocumentProcessor processFile(String fileName) throws IOException{
		logger.info("fileName: "+fileName);
		File file = new File(fileName);
		DocumentProcessor processor = processFile(file);
		return processor;
	}  // end function processFile
	
	/**
	 * Process a file and inject word points and frequency in DocumentProcessor bean.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param fileName
	 * @throws IOException
	 */
	public DocumentProcessor processFile(File file) throws IOException{
		logger.info("fileName: "+file.getName());
		String extension = extension(file.getName());
		logger.info("extension: "+extension);
		BufferedReader reader = null;
		// Assuming that extension is txt, or this is an html file
		if(extension.equals("txt")){
			InputStream in = new FileInputStream(file);
	        reader = new BufferedReader(new InputStreamReader(in));
		}else{	
	        String html = Jsoup.parse(file, "UTF-8").text();
	        reader = new BufferedReader(new StringReader(html));
		}
        DocumentProcessor processor = new DocumentProcessor();
        processor.validateWords(reader, file.getName());
        
        reader.close();	
        return processor;
	}
	
	/**
	 * Process a folder, filter out .html, .htm and .txt files, validate the content and inject into DocumentProcessor beans.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param folderName
	 * @return
	 */
	public List<DocumentProcessor> processFolder(String folderName){
		List<DocumentProcessor> processors = new ArrayList<DocumentProcessor>();
		logger.info("folderName: "+folderName);
		File folder = new File(folderName);
		File[] htmlFiles = folder.listFiles(new FilenameFilter() {	
			@Override
			public boolean accept(File file, String fileName) {
				return fileName.endsWith(".html")||fileName.endsWith(".htm");
			}
		});
		File[] txtFiles = folder.listFiles(new FilenameFilter() {	
			@Override
			public boolean accept(File file, String fileName) {
				return fileName.endsWith(".txt");
			}
		});

		List<DocumentProcessor> lisHtmlFiles = processFiles(htmlFiles);
		List<DocumentProcessor> lisTextFiles = processFiles(txtFiles);
		
		processors.addAll(lisHtmlFiles);
		processors.addAll(lisTextFiles);
		return processors;
	}  // end processFolder
	
	/**
	 * Process the files one by one from the list of files.
	 * @param files
	 */
	private List<DocumentProcessor> processFiles(File[] files){
		List<DocumentProcessor> processors = new ArrayList<DocumentProcessor>();
		if(files!=null){
			for(File file : files){
				String extension = extension(file.getName());
				logger.info("fileName: "+file.getName());
				logger.info("extension: "+extension);
				try {
					DocumentProcessor processor = processFile(file);
					processors.add(processor);
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage());
				}
			} // end loop files
		}  // end files not null	
		return processors;
	} // end private function processFiles
	
	/**
	 * Utility function to get extension of a filename
	 * @param fileName Name of file with extension.
	 * @return Extension or empty String if not defined.
	 */
	private static String extension(String fileName){
	    String extension = "";
	    if(fileName!=null&&fileName.length()>0){
		    int dot = fileName.lastIndexOf(".");
		    if(dot>1)
		    	extension = fileName.substring(dot + 1);
	    }
	    return extension;
	}  // end function extension
	
}  // end public class FileUtil