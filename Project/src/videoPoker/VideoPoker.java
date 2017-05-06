package videoPoker;

import java.util.Scanner;

import cards.Deck;
import player.Hand;
import player.Player;

public class VideoPoker {

	int[][] rewardTable;
	
	public VideoPoker(int[][] rewardTable){
		this.rewardTable = rewardTable;
	}
	
	public int reward(int score, int bet){
		return rewardTable[score][bet];
	}
	
	void interactiveMode(int initialCredit){
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
						
						int score = player.hand.handScore();
						if(score == 0){
							player.setCredit(player.getCredit() - player.getLastBet());
							System.out.println("Player lost and his credit is " + player.getCredit());
						}else{
							player.setCredit(player.getCredit() + reward(score, player.getLastBet()));
							System.out.println("Player wins with a " + player.hand.scoreToString(score) 
								+ " and his credit is " + player.getCredit());
						}
						player.incStatistics(score);
						player.incHandsPlayed();
						
						end = true;
						break;
						
					case 'a':
						System.out.println(player.hand.advise());
						break;
						
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
	}
}
