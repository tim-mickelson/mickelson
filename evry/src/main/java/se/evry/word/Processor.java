package se.evry.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for processing text Input Stream and value it.
 * @author Tim Mickelson
 * @since 05/04/2014
 */
public class Processor {
	Logger logger = LoggerFactory.getLogger(getClass());
	/**
	 * Read words and validate, line by line from text input.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param reader
	 * @throws IOException 
	 */
	public void extractWords(Reader reader) throws IOException{
		String line;
        while ((line = ((BufferedReader)reader).readLine()) != null) {
            logger.info(line);
        }
	} // end function extractWords
	
	/**
	 * 
	 * @param line
	 */
	private void evaluateLine(String line){
		
	}  // end function evaluateLine
	
}  // end class Processor