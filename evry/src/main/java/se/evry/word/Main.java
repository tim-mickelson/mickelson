package se.evry.word;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class to launch from command line.
 * <br><br>
 * <i>Usage:</i><br>
 * <code>
 * $ java -jar evry.jar option option-param
 * <br><br>
 * Possible options with option-param are:
 * <br>
 * -f file <i>Absolute path to single file with txt or html postfix.</i>
 * <br>
 * -d directory <i>Absolute path to folder containing html and/or txt files.</i>
 * </code>
 * @author Tim Mickelson
 *
 */
public class Main {
	Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * The main entry point for this program package.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param args Arguments for files/folders
	 */
	public static void main(String[] args){
		List<DocumentProcessor> processors = null;
		if(args!=null&&args.length>1&&args[0]!=null&&args[0].length()>0&&args[1]!=null&&args[1].length()>0){
			if(args[0].equals("-f")){
				processors = new ArrayList<DocumentProcessor>();
				DocumentProcessor processor = processFile(args[1]);
				processors.add(processor);
			} else if(args[0].equals("-d")){
				FileManager fileManager = new FileManager();
				processors = fileManager.processFolder(args[1]);
			}else
				usage();
		} else // end check args definied
			usage();
		
		if(processors!=null){
	        Presentation presentation = new Presentation();
	        presentation.setProcessors(processors);
	        presentation.print();			
		} // end processors not null
	}  // end public function main
	
	/**
	 * Inject the points and words into a DocumentProcessor bean.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 * @param fileName
	 * @return <code>null</code> on error
	 */
	private static DocumentProcessor processFile(String fileName){
		DocumentProcessor processor = null;
		FileManager fileManager = new FileManager();
		try {
			processor = fileManager.processFile(fileName);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
		return processor;
	}  // end private function processFile
	
	/**
	 * Utility function to print usage syntax.
	 * @author Tim Mickelson
	 * @since 05/04/2014
	 */
	private static void usage(){
		System.out.println("\nUsage:");
		System.out.println("$ java -jar evry.jar [option] [file]");
		System.out.println("");
		System.out.println("[file] is file or directory depending on the [option] that is -d (directory) or -f (file)");
		System.out.println("");
		System.out.println("Only files with extension .txt, .htm or .html are considered.");
	}  // end public function usage
} // end class Main