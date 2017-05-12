package doubleBonusTenSeven;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import cards.Deck;
import doubleBonus.DoubleBonus;
import player.Hand;
import player.Player;

public class DoubleBonusTenSeven extends DoubleBonus{

	public void interactiveMode(int initialCredit){
		Player player = new Player(initialCredit, nWinningHands);
		Scanner scan = null;

		this.state = INITIATING;
		
		while(state != QUITTING){
			
			scan = new Scanner(System.in);
			String line = scan.nextLine();
			char command;
			try{
				command = line.charAt(0);
			}catch(StringIndexOutOfBoundsException e){
				command = '\0';
			}
			
			switch(command){
			case 'b':
				int bet;
				if(line.length() > 1){
					String betString = "";
					for(int i = 2; i < line.length(); i++){
						betString += line.charAt(i);
					}
					try{
						bet = Integer.parseInt(betString);
						if((bet <= 5) && (player.getCredit() - bet >= 0) && (bet > 0)){
							this.bet(player, bet);
							break;
						}else{
							System.out.println("b: illegal amount");
							break;
						}
					}catch(NumberFormatException e){
						System.out.println("b: illegal amount");
						break;
					}
				}else
					bet = player.getLastBet();
				this.bet(player, bet);
				break;
			
			case '$':
				this.credit(player);
				break;
			
			case 'd':
				this.shuffle();
				this.deal(player);
				break;
			
			case 'h':
				int[] indexes= new int[5];
				for(int i = 2; i < line.length(); i += 2)
					indexes[i/2 - 1] = Character.getNumericValue(line.charAt(i));
				
				this.holdAndResults(player, indexes, deck);
				break;
				
			case 'a':
				this.getAdvise(player);
				break;
				
			case 's':
				this.getStatistics(player);
				break;
			
			case 'q':
				this.quit(player);
				break;
			
			default:
				System.out.println(command + ": illegal command");
				break;
			}
		}
		scan.close();
		return;
	}
	
	public void debugMode(int initialCredit, String cmd_file, String card_file){
		Player player = new Player(initialCredit, nWinningHands);
		deck = new Deck(card_file);
		
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader(cmd_file));

			String line;
			
			line = br.readLine(); //file has only one line
			br.close();
			this.state = INITIATING;
			int index = 0;
			while(index < line.length()){
				if(Character.isLetter(line.charAt(index)) || line.charAt(index) == '$'){
					char command = line.charAt(index);
					String cmd = "";
					cmd += command;
					switch(command){
					case 'b':
						int bet;
						if(index+2 < line.length() && Character.isDigit(line.charAt(index+2))){
							int i = 2;
							//bet value can have more than one digit
							String betString = "";
							while(Character.isDigit(line.charAt(index+i)) && index+1 < line.length()){
								betString += line.charAt(index+2);
								i ++;
							}
							bet = Integer.parseInt(betString);
							index += i;
							cmd += (" " + bet);
						}else{
							bet = player.getLastBet();
							index += 2;
						}

						this.printCommand(cmd);
						this.bet(player, bet);
						break;
					
					case '$':
						this.printCommand(cmd);
						this.credit(player);
						index += 2;
						break;
					
					case 'd':
						this.printCommand(cmd);
						this.deal(player);
						index += 2;
						break;
					
					case 'h':
						int[] indexes= new int[5];
						int n = 0;
						int i = 2;
						while(index+i < line.length() && Character.isDigit(line.charAt(index + i))){
							indexes[n] = Character.getNumericValue(line.charAt(index + i));
							n++;
							cmd += (" " + line.charAt(index + i));
							i += 2;
						}
						this.printCommand(cmd);
						this.holdAndResults(player, indexes, deck);
						index += 2*(n+1);
						break;
						
					case 'a':
						this.printCommand(cmd);
						this.getAdvise(player);
						index += 2;
						break;
						
					case 's':
						this.printCommand(cmd);
						this.getStatistics(player);
						index += 2;
						break;
					
					case 'q':
						this.printCommand(cmd);
						this.quit(player);
						break;
					
					default:
						this.printCommand(cmd);
						System.out.println(command + ": illegal command");
						index++;
						break;
					}
				}else
					index++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return;
	}
	
	public void simulationMode(int initialCredit, int bet, int nbdeals){
		if(bet > 5){
			System.out.println("b: illegal amount");
			System.exit(2);
		}
		
		Player player = new Player(initialCredit, nWinningHands);
		player.setLastBet(bet);
		
		for(int deal = 0; deal < nbdeals && player.getCredit() - bet >= 0; deal++){
			player.setCredit(player.getCredit() - bet);
			this.shuffle();
			player.hand = new Hand(deck);
			int[] cardsToHold = AdviseDoubleBonusTenSeven.cardsToHold(player, this);
			int[] indexes = new int[5];
			int nCardsToHold = 0;
			for(int i = 0; i < 5; i++){
				if(cardsToHold[i] == 1){
					indexes[nCardsToHold] = i+1;
					nCardsToHold++;
				}
			}
			player.hand.hold(indexes, deck);
			int score = this.handScore(player);
			player.setCredit(player.getCredit() + reward(score, bet));
			player.incStatistics(score);
			player.incHandsPlayed();
		}
		this.statistics(player);
	}
	
	protected int[][] fillRewardTable(){
		int[][] rewardTable = new int[nWinningHands+1][nBets+1];
		for(int bet = 1; bet <= nBets; bet++){
			rewardTable[OTHER][bet] = 0;
			rewardTable[ROYAL_FLUSH][bet] = 250*bet;
			rewardTable[FOUR_ACES][bet] = 160*bet;
			rewardTable[FOUR_2_4][bet] = 80*bet;
			rewardTable[FOUR_5_K][bet] = 50*bet;
			rewardTable[STRAIGHT_FLUSH][bet] = 50*bet;
			rewardTable[FULL_HOUSE][bet] = 10*bet;
			rewardTable[FLUSH][bet] = 7*bet;
			rewardTable[STRAIGHT][bet] = 5*bet;
			rewardTable[THREE_OF_A_KIND][bet] = 3*bet;
			rewardTable[TWO_PAIR][bet] = bet;
			rewardTable[JACKS_OR_BETTER][bet] = bet;
		}
		rewardTable[ROYAL_FLUSH][5] = 4000;
		return rewardTable;
	}
	
	public String advise(Player player){
 		String adviseString = "player should hold cards";
 		int[] cardsToHold = AdviseDoubleBonusTenSeven.cardsToHold(player, this);
 		if(cardsToHold[0] == -1){
 			return "player should discard everything\n";
 		}
 		for(int i = 0; i < 5; i++){
 			if(cardsToHold[i] == 1)
 				adviseString = adviseString + " " + (i+1); 
 		}

 		return adviseString;
 	}
}
