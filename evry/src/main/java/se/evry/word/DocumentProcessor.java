package se.evry.word;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
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
	private static Map<String, Integer> allWords = new HashMap<String, Integer>();
	// All words grouped by word with frequency as Integer
	Map<String, Integer> words = new HashMap<String, Integer>();
	// All words even short dumped words
	long wordsCount;
	// Number of words that are between 3 and 20 characters if longer the 20 then it contains a hyphen
	int validWordsCount;
	Integer documentPoints = null;
	boolean copied = false;
	
	public static Map<String, Integer> getAllWords(){
		return allWords;
	}  // end public getAllWords
	
	/**
	 * Read words and validate, line by line from text input.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param reader
	 * @throws IOException 
	 */
	public void validateWords(Reader reader) throws IOException{
		String line;
        while ((line = ((BufferedReader)reader).readLine()) != null) {
            evaluateLine(line);
        }
        // Make sure to elaborate everything
        points();
	} // end function extractWords
	
	/**
	 * Calculate points from map of words
	 * @return
	 */
	public int points(){
		// This will only be calculated once
		if(documentPoints!=null)
			return documentPoints.intValue();
		else
			return calculatePoints();
	}  // end function points
	
	/**
	 * Use this function if the internal counter needs to be reset.
	 * @return
	 */
	public int calculatePoints(){
		int points = 0;
		// Loop map of words, substitue frequency with points and sum up the points.
		for(Entry<String, Integer> wordEntry: words.entrySet()){
			String word = wordEntry.getKey();
			Integer frequency = wordEntry.getValue();
			// the points this word is worth
			int p = 0;
			if(validWordsCount<101){
				// Only care about short words else keep p 0
				if(word.length()>2&&word.length()<21)
					p=1;
			}else{
				// Ok it's more then 100 words in the document so short words 2 points and long words also get a point
				p = word.length()<21?2:1;
				// Double letter adds one point to the word
				if(validWordsCount>999&&doubleLetter(word))
					p = p+1;
			}  // end valid words more then 100	
			
			if(p==0)
				words.remove(word);
			else
				words.put(word, p*frequency);
			// Add this words points to the sum of points in the document
			points += p*frequency;
		}  // end loop words
		// Copy map to global map of all words from all documents
		copyDocumentMap();
		
		// Save the result in bean
		documentPoints = points;
		return points;
	}
	
	/**
	 * Copy the result of the internal map to the static map. This is a irreversible function.
	 */
	public void copyDocumentMap(){
		if(copied) return;
		copied = true;
		for(Entry<String, Integer> entry : words.entrySet()){
			Integer i = allWords.get(entry.getKey());
			Integer j = entry.getValue();
			i = i==null?0:i;
			j = j==null?0:j;
			i = i+j;
			allWords.put(entry.getKey(), i);
		}
	}  // /end public void copyDocumentMap
	
	/**
	 * 
	 * @param line
	 */
	private void evaluateLine(String line){
		String[] words = line.split(" ");
		for(String word : words){
			wordsCount++;
			if(word.length()>2&&word.length()<21){
				// Add word and so increase frequency. Only add short word or long with hyphen
				if(word.length()<11||hyphen(word)){
					validWordsCount++;
					addWord(word);
				}
			}  // end word of interest			
		}
	}  // end function evaluateLine
	
	/**
	 * Add word of interest and calculate frequency. Word is key and frequency value on a Map.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 */
	private void addWord(String word){
		// i is the frequency of the word
		Integer i = words.get(word);
		i = i==null?1:++i;
		words.put(word, i);
	} // end public void addWord
	
	/**
	 * 
	 * @param word
	 * @return
	 */
	public static boolean hyphen(String word){
		String hyphen = "-";
		Pattern pattern = Pattern.compile(hyphen);
		Matcher matcher = pattern.matcher(word);
		return matcher.find();
	}
	
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
	}  // end function doubleLetter
	
}  // end class Processor
