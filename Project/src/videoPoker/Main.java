package videoPoker;

import java.util.Scanner;

import player.*;
import cards.Deck;

public class Main {

	public static void usage(){
		System.out.println("Usage for different execution modes:");
		System.out.println("---INTERACTIVE MODE: java -jar <<YOUR-JAR-NAME>>.jar -i credit");
		System.out.println("---DEBUG       MODE: java -jar <<YOUR-JAR-NAME>>.jar -d credit cmd-file card-file");
		System.out.println("---SIMULATION  MODE: java -jar <<YOUR-JAR-NAME>>.jar -s credit bet nbdeals");
		System.exit(1);
	}
	
	public static void main(String[] args) {
		
		/*if(args.length == 2 || args.length == 4){
			Player player = new Player(Integer.parseInt(args[1]));
		}else
			usage();*/
		
		/*******************************INTERACTIVE MODE**********************************/
		if(args[0].equals("-i")){
			if(args.length != 2)
				usage();
			
			int initialCredit = Integer.parseInt(args[1]);
			Player player = new Player(initialCredit);
			
			Scanner scan = null;
			Scanner scan2 = null;
			
			boolean quit = false;
			while(!quit){
				scan = new Scanner(System.in);
				String line = scan.next().trim();
				
				char command = line.charAt(0);
				switch(command){
				case 'b':
					if(line.length() > 1){
						player.setLastBet(Character.getNumericValue(line.charAt(1)));
					}
					break;
				
				case '$':
					System.out.println("player's credit is " + player.getCredit());
					break;
				
				case 'd':
					Deck deck = new Deck();
					player.hand = new Hand(deck);
					System.out.println(player.hand);
					
					boolean end = false; 
					while(!end){
						scan2 = new Scanner(System.in);
						String line2 = scan2.nextLine();
						
						char command2 = line2.charAt(0);
						
						switch(command2){
						case '$':
							System.out.println("player's credit is " + player.getCredit());
							break;
							
						case 'h':
							int[] indexes= new int[5];
							
							for(int i = 2; i < line2.length(); i += 2)
								indexes[i/2 - 1] = Character.getNumericValue(line2.charAt(i));
							
							player.hand.hold(indexes, deck);
							System.out.println(player.hand);
							
							//--------------------------------ver se ganhou ou perdeu----------------------------------
							
							System.out.println(player.hand.handScore(player.hand));
							
							end = true;
							break;
							
						case 'a':
							//--------------------------------------fazer advise---------------------------------------
						case 's':
							player.statistics(initialCredit);
							break;
						
						default:
							System.out.println(command2 + ": illegal command");
							break;
						}
					}
					break;
					
				case 's':
					player.statistics(initialCredit);
					break;
				
				case 'q':
					System.out.println("Exiting the game");
					player.statistics(initialCredit);
					quit = true;
					break;
				
				default:
					System.out.println(command + ": illegal command");
					break;
				}
			}
			scan.close();
			scan2.close();
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
