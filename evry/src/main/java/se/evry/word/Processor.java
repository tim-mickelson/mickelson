package se.evry.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for processing text Input Stream and value it.
 * @author Tim Mickelson
 * @since 05/04/2014
 */
public class Processor {
	Logger logger = LoggerFactory.getLogger(getClass());
	long normalaOrd;
	long storaOrd;
	// We assume that the words shorter then 3 and longer then 20 are not counted
	long allaOrd;
	
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
            evaluateLine(line);
        }
        
        logger.info("normalaOrd: "+normalaOrd);
        logger.info("storaOrd: "+storaOrd);
        logger.info("allaOrd: "+allaOrd);
	} // end function extractWords
	
	/**
	 * 
	 * @param line
	 */
	private void evaluateLine(String line){
		String[] words = line.split(" ");
		for(String word : words){
			// Ignore all words 3 chars or less
			if(word.length()>3&&word.length()<21){
				allaOrd++;
				WordType wordType = evaluateWord(word);
				if(word.length()<11)
					normalaOrd++;
				else if(wordType.hyphen)
					storaOrd++;
			}
		}
	}  // end function evaluateLine
	
	private WordType evaluateWord(String word){
		WordType wordType = new WordType();
		// Match character loaded in \1 with next character
		String doubleLetter = "([a-zA-Z])\\1";
		Pattern pattern = Pattern.compile(doubleLetter);
		Matcher matcher = pattern.matcher(word);
		wordType.doubleLetter = matcher.find();
		
		String hyphen = "-";
		pattern = Pattern.compile(hyphen);
		matcher = pattern.matcher(word);
		wordType.hyphen = matcher.find();
		return wordType;
	}
	
	private class WordType{
		boolean doubleLetter = false;
		boolean hyphen = false;
	}
	
}  // end class Processor