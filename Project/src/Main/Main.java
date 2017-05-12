package Main;

import doublebonus10_7.DoubleBonus10_7;

/**
 * The Class Main.
 */
public class Main {

	public static void usage(){
		System.out.println("Usage for different execution modes:");
		System.out.println("---INTERACTIVE MODE: java -jar <<YOUR-JAR-NAME>>.jar -i credit");
		System.out.println("---DEBUG       MODE: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file");
		System.out.println("---SIMULATION  MODE: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
		System.exit(1);
	}
	
	public static void main(String[] args) {

		if(args.length < 2){
			usage();
		}
		
		DoubleBonus10_7 game = new DoubleBonus10_7();
		/*******************************INTERACTIVE MODE**********************************/
		if(args[0].equals("-i")){
			if(args.length != 2)
				usage();
			int initialCredit = Integer.parseInt(args[1]);
			game.interactiveMode(initialCredit);
			
		}//END INTERACTIVE MODE
		
		/**********************************DEBUG MODE*************************************/
		else if(args[0].equals("-d")){
			if(args.length != 4)
				usage();
			
			int initialCredit = Integer.parseInt(args[1]);
			game.debugMode(initialCredit, args[2], args[3]);
		}//END DEBUG MODE
		
		/*******************************SIMULATION MODE***********************************/
		else if(args[0].equals("-s")){
			if(args.length != 4)
				usage();
			
			int initialCredit = Integer.parseInt(args[1]);
			
			int bet = Integer.parseInt(args[2]);

			int nbdeals = Integer.parseInt(args[3]);
			
			game.simulationMode(initialCredit, bet, nbdeals);
			
		}//END SIMULATION MODE
		else{
			usage();
		}
	}
}