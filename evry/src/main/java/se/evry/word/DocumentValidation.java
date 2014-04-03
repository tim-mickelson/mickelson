package se.evry.word;

import java.util.HashMap;
import java.util.Map;

/**
 * This class will contain all statistics of text document. It's porpouse is to calculate the value of the document.
 * 
 * @author Tim Mickelson
 *
 */
public class DocumentValidation {
	// The singleton hashmap with all words and points
	static Map<String, Integer> words = new HashMap<String, Integer>();
	// The words connected to this document
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
	 * Add point giving word. When calculating points these words will be moved to global map.
	 */
	public void addWord(String word){
		Integer i = documentWords.get(word);
		if(i==null)
			documentWords.put(word, 1);
		else{
			// TODO: Check so this increments in map
			i++;
		}			
	} // end public void addWord

	
	public long getNormalaOrd() {
		return normalaOrd;
	}
	public void setNormalaOrd(long normalaOrd) {
		this.normalaOrd = normalaOrd;
	}
	public long getStoraOrd() {
		return storaOrd;
	}
	public void setStoraOrd(long storaOrd) {
		this.storaOrd = storaOrd;
	}
	public long getDoubleLetter() {
		return doubleLetter;
	}
	public void setDoubleLetter(long doubleLetter) {
		this.doubleLetter = doubleLetter;
	}
	public long getAllaOrd() {
		return allaOrd;
	}
	public void setAllaOrd(long allaOrd) {
		this.allaOrd = allaOrd;
	}
	public long getWordsCount() {
		return wordsCount;
	}
	public void setWordsCount(long wordsCount) {
		this.wordsCount = wordsCount;
	}
		
}  // end class DocumentValidation