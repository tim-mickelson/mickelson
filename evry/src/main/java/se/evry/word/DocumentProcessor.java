package se.evry.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class for processing text Input Stream and value it. One instance of this class per document since it holds
 * all counters as attributes to the class.
 * 
 * @author Tim Mickelson
 * @since 05/04/2014
 */
public class DocumentProcessor {
	Logger logger = LoggerFactory.getLogger(getClass());
	long normalaOrd;
	long storaOrd;
	// Words with double letters
	long doubleLetter;
	// All words between 3 and 20 characters
	long allaOrd;
	// All words even short dumped words
	long wordsCount;
	
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
			wordsCount++;
			// Ignore all words 3 chars or less
			if(word.length()>3&&word.length()<21){
				allaOrd++;
				WordType wordType = evaluateWord(word);
				if(wordType.doubleLetter)
					doubleLetter++;
				if(word.length()<11)
					normalaOrd++;
				else if(wordType.hyphen)
					storaOrd++;
			}
		}
	}  // end function evaluateLine
	
	private WordType evaluateWord(String word){
		WordType wordType = new WordType();
		wordType.doubleLetter = doubleLetter(word);
		
		String hyphen = "-";
		Pattern pattern = Pattern.compile(hyphen);
		Matcher matcher = pattern.matcher(word);
		wordType.hyphen = matcher.find();
		return wordType;
	}  // end private function evaluateWord
	
	/**
	 * Find out if a word contains a double letter.
	 * 
	 * @author Tim Mickelson
	 * @param word The single word to examen
	 * @return true if double letter is found.
	 */
	public static boolean doubleLetter(String word){
		// Match character loaded in \1 with next character
		String doubleLetter = "([a-zA-Z])\\1";
		Pattern pattern = Pattern.compile(doubleLetter);
		Matcher matcher = pattern.matcher(word);
		return matcher.find();
	}
	
	private class WordType{
		boolean doubleLetter = false;
		boolean hyphen = false;
	}
	
}  // end class Processor