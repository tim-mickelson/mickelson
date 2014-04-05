package se.evry.word;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

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
	 * Utility function to get extension of a filename
	 * @param fileName Name of file with extension.
	 * @return Extension or empty String if not defined.
	 */
	public static String extension(String fileName){
	    String extension = "";
	    if(fileName!=null&&fileName.length()>0){
		    int dot = fileName.lastIndexOf(".");
		    if(dot>1)
		    	extension = fileName.substring(dot + 1);
	    }
	    return extension;
	}  // end function extension
	
	public void processFile(String fileName) throws IOException{
		logger.info("fileName: "+fileName);
		File file = new File(fileName);
		processFile(file);
	}  // end function processFile
	
	public void processFile(File file) throws IOException{
		logger.info("fileName: "+file.getName());
		String extension = extension(file.getName());
		logger.info("extension: "+extension);
		
		InputStream in = new FileInputStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        
        DocumentProcessor processor = new DocumentProcessor();
        processor.validateWords(reader);
        
        reader.close();		
	}
	
	public void processFolder(String folderName){
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

		processFiles(htmlFiles);
		processFiles(txtFiles);
	}  // end processFolder
	
	/**
	 * Process the files one by one from the list of files.
	 * @param files
	 */
	private void processFiles(File[] files){
		if(files!=null){
			for(File file : files){
				String extension = extension(file.getName());
				logger.info("fileName: "+file.getName());
				logger.info("extension: "+extension);
				try {
					processFile(file);
				} catch (IOException e) {
					logger.error(e.getLocalizedMessage());
				}
			} // end loop files
		}  // end files not null		
	} // end private function processFiles
	
}  // end public class FileUtil