package se.evry.word;

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
	public static void main(String[] args){
		
		Logger logger = LoggerFactory.getLogger(Main.class);
		for(String arg : args){
			logger.info(arg);
		}
		
		if(args!=null&&args.length>1&&args[0]!=null&&args[0].length()>0&&args[1]!=null&&args[1].length()>0){
			if(args[0].equals("-f"))
				logger.info(args[1]);
		}  // end check args definied
		else
			usage();
	}  // end public function main
	
	private static void usage(){
		System.out.println("\nUsage:");
		System.out.println("$ java -jar evry.jar option option-param");
		System.out.println("\nPossible option, option-param are:");
		System.out.println("-f file");
		System.out.println("-d directory");
	}  // end public function usage
} // end class Main