package se.evry.word;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class will contain all statistics of text document. It's porpouse is to calculate the value of the document.
 * 
 * @author Tim Mickelson
 *
 */
public class DocumentValidation {
	Logger logger = LoggerFactory.getLogger(getClass());
	// The singleton hashmap with all words and points
	static Map<String, Integer> words = new HashMap<String, Integer>();
	// The words connected to this document, except the double letter words
	private Map<String, Integer> documentWords = new HashMap<String, Integer>();
	// The words with double letter go in this map instead of documentWords
	private Map<String, Integer> doubleLetterWords = new HashMap<String, Integer>();
	// Words with len > 2 and < 11
	private long normalaOrd;
	// Words longer then 10 with the character '-'
	private long storaOrd;
	// Words with double letters
	private long doubleLetter;
	// All words between 3 and 20 characters
	private long allaOrd;
	// All words even short dumped words
	private long wordsCount;
	//  When the documentWords have been copied to the words Map set true and never repeat.
	boolean copied = false;
	
	/**
	 * Calculate the points following the rules.
	 * 
	 * <ol>
	 *   <li> Words with length between 3 and 10 give one point. 
	 *   <li> If the document has more then 100 words then 2 points for each word.
	 *   <li> If the document has more then 1000 words then the double letter words get one more point
	 * </ol>
	 * <br>
	 * 
	 * To sum upp: <br>
	 * <ul>
	 * <li>Words shorter then 3 and longer then 20 are never considered ever not even in the count of how many words the document has.
	 * <li>Words with length from 3 to 10 chars are 1 point.
	 * <li>If the document holds more then 100 words then the previous words get 2 points and the longer words 1 point.
	 * <li>If the document holds more then 1000 words the double letter words are worth one extra point each.
	 * </ul>
	 * 
	 * @return
	 */
	public int points(){
		long points = normalaOrd;
		
		if(allaOrd<101){
			// map words from documentWords to words Map, just copy the points
		}
		
		if(allaOrd>100){
			points += normalaOrd;
			points += storaOrd;
			// map words from documentWords to words Map, double the points
		}
		
		if(allaOrd>1000){
			points += doubleLetter;
			// for each word give another point if double letter use Processor.doubleLetter(word) to find out
		}
		return 0;
	}
	
	/**
	 * Add point giving word. When calculating points these words will be moved to global map, not here.
	 * Double letter words will be added to corresponding Map.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #addDoubleLetterWord(String)
	 */
	public void addWord(String word){
		// If this is a double letter word, then use that function instead
		if(DocumentProcessor.doubleLetter(word)){
			addDoubleLetterWord(word);
		}else{
			Integer i = documentWords.get(word);
			if(i==null)
				documentWords.put(word, 1);
			else{
				// TODO: Check so this increments in map
				i++;
			}			
		} // end word is not double letter
		logger.debug("word: "+word);
	} // end public void addWord
	
	/**
	 * Just blindly add to Map for double letters. If not double letter, log it but add.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #addWord(String)
	 * @param word
	 */
	public void addDoubleLetterWord(String word){
		if(!DocumentProcessor.doubleLetter(word))
			logger.error("Word added faulty to map of double letters: "+word);
		
		
	} // end function addDoubleLetterWord

	/**
	 * Get the number of <i>normal</i> words. Normal words are defined as words with length between 3 and 10.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #setNormalaOrd(long)
	 * @return
	 */
	public long getNormalaOrd() {
		return normalaOrd;
	}
	
	/**
	 * Set amount of normal words in this document.
	 * @see #getNormalaOrd()
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param normalaOrd 
	 */
	public void setNormalaOrd(long normalaOrd) {
		this.normalaOrd = normalaOrd;
	}
	
	/**
	 * Get the amount of <i>big</i> words definied as words between 11 and 20 characters containing the character <b>'-'</b>.
	 * 
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #setStoraOrd(long)
	 * @return
	 */
	public long getStoraOrd() {
		return storaOrd;
	}
	
	/**
	 * Set amount of big words in document.
	 * 
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #getStoraOrd()
	 * @param storaOrd
	 */
	public void setStoraOrd(long storaOrd) {
		this.storaOrd = storaOrd;
	}
	
	/**
	 * Get ammunt of words containing double letter in the document.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #setDoubleLetter(long)
	 * @return
	 */
	public long getDoubleLetter() {
		return doubleLetter;
	}
	
	/**
	 * Set amount of words containg double letter in document.
	 * 
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #getDoubleLetter()
	 * @param doubleLetter
	 */
	public void setDoubleLetter(long doubleLetter) {
		this.doubleLetter = doubleLetter;
	}
	
	/**
	 * Get amount of <i>valide</i> words in document.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #setAllaOrd(long)
	 * @return
	 */
	public long getAllaOrd() {
		return allaOrd;
	}
	
	/**
	 * Set amount of valid words in document.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #getAllaOrd()
	 * @param allaOrd
	 */
	public void setAllaOrd(long allaOrd) {
		this.allaOrd = allaOrd;
	}
	
	/**
	 * Get amount of words in document, also the ignored not valid ones.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #setWordsCount(long)
	 * @return
	 */
	public long getWordsCount() {
		return wordsCount;
	}
	
	/**
	 * Set amount of words in document, also not valid words.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @see #getWordsCount()
	 * @param wordsCount
	 */
	public void setWordsCount(long wordsCount) {
		this.wordsCount = wordsCount;
	}
		
}  // end class DocumentValidation