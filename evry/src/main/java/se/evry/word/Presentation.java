package se.evry.word;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class prepares data for presentation on console.
 * @author Tim Mickelson
 *
 */
public class Presentation {
	Logger logger = LoggerFactory.getLogger(getClass());
	// List of all documents
	List<DocumentProcessor> processors;
	
	public void print(){
		System.out.println("");
		System.out.println("Summary");
		System.out.println("------------------------------------------");
		int i = 1;
		Collections.sort(processors, new Comparator<DocumentProcessor>(){
			public int compare(DocumentProcessor p1, DocumentProcessor p2){
				return ((Comparable<Integer>)p2.points()).compareTo(p1.points());
			}
		});
		
		for(DocumentProcessor processor : processors){
			System.out.println(i+" - "+processor.getFileName()+" ("+processor.points()+")");
			i++;
		}
		
		System.out.println("");
		System.out.println("Words");
		System.out.println("------------------------------------------");
		Map<String, Integer> order = orderWords(DocumentProcessor.getAllWords());
		//logger.debug(order.toString());
		int size = 10;
		int w = 0;
		for(Entry<String, Integer> entry : order.entrySet()){
			w++;
			System.out.println(w+". "+entry.getKey()+" ("+entry.getValue()+")");
			if(w>size)
				break;
		}
	}
	 
	/**
	 * Order words by points to ordered Map.
	 */
	private Map<String, Integer> orderWords(Map<String, Integer> unsortMap){
		List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(unsortMap.entrySet());
		 
		// sort list based on comparator
		Collections.sort(list, new Comparator<Entry<String, Integer>>() {
			public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
				return ((Comparable<Integer>)o2.getValue()).compareTo(o1.getValue());
			}
		});
 
		// put sorted list into map again
                //LinkedHashMap make sure order in which keys were inserted
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	} // end private function orderWords
	
	public void setProcessors(List<DocumentProcessor> processors){
		this.processors = processors;
	}
	
}  // end class Presentation