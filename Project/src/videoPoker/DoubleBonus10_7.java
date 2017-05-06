package videoPoker;

public class DoubleBonus10_7 {

	public static final int nWinningHands = 11;
	public static final int nBets = 5;
	
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
		int[][] rewardTable = new int[nWinningHands+1][nBets+1];
		for(int bet = 1; bet <= nBets; bet++){
			rewardTable[1][bet] = 250*bet;
			rewardTable[2][bet] = 160*bet;
			rewardTable[3][bet] = 80*bet;
			rewardTable[4][bet] = 50*bet;
			rewardTable[5][bet] = 50*bet;
			rewardTable[6][bet] = 10*bet;
			rewardTable[7][bet] = 7*bet;
			rewardTable[8][bet] = 5*bet;
			rewardTable[9][bet] = 3*bet;
			rewardTable[10][bet] = bet;
			rewardTable[11][bet] = bet;
		}
		rewardTable[1][5] = 4000;
		VideoPoker game = new VideoPoker(rewardTable);
		/*******************************INTERACTIVE MODE**********************************/
		if(args[0].equals("-i")){
			if(args.length != 2)
				usage();
			int initialCredit = Integer.parseInt(args[1]);
			game.interactiveMode(initialCredit);
			
		}//END INTERACTIVE MODE
		
		/**********************************DEBUG MODE*************************************/
		else if(args[0].equals("-d")){
			
		}//END DEBUG MODE
		
		/*******************************SIMULATION MODE***********************************/
		else if(args[0].equals("-s")){
			
		}//END SIMULATION MODE
		else{
			usage();
		}
	}

}
