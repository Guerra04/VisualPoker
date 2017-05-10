package doublebonus10_7;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

import cards.Card;
import cards.Deck;
import player.Hand;
import player.Player;
import videoPoker.VideoPoker;

public class DoubleBonus10_7 extends VideoPoker{

	public static final int nWinningHands = 11;
	public static final int nBets = 5;
	
	//WinningH Hands
	public static final int ROYAL_FLUSH = 1;
	public static final int FOUR_ACES = 2;
	public static final int FOUR_2_4 = 3;
	public static final int FOUR_5_K = 4;
	public static final int STRAIGHT_FLUSH = 5;
	public static final int FULL_HOUSE = 6;
	public static final int FLUSH = 7;
	public static final int STRAIGHT = 8;
	public static final int THREE_OF_A_KIND = 9;
	public static final int TWO_PAIR = 10;
	public static final int JACKS_OR_BETTER = 11;
	public static final int OTHER = 0;
	
	public DoubleBonus10_7(){
		this.rewardTable = fillRewardTable();
	}
	
	private int[][] fillRewardTable(){
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
	
	public void statistics(Player player){
		System.out.printf("%-20s %-5s\n", "Hand", "Nb");
		System.out.printf("---------------------------\n");
		System.out.printf("%-20s %-5s\n", "Jacks or Better", String.valueOf(player.statistics[JACKS_OR_BETTER]));
		System.out.printf("%-20s %-5s\n", "Two Pair", String.valueOf(player.statistics[TWO_PAIR]));
		System.out.printf("%-20s %-5s\n", "Three of a Kind", String.valueOf(player.statistics[THREE_OF_A_KIND]));
		System.out.printf("%-20s %-5s\n", "Straight", String.valueOf(player.statistics[STRAIGHT]));
		System.out.printf("%-20s %-5s\n", "Flush", String.valueOf(player.statistics[FLUSH]));
		System.out.printf("%-20s %-5s\n", "Full house", String.valueOf(player.statistics[FULL_HOUSE]));
		System.out.printf("%-20s %-5s\n", "Four of a Kind", String.valueOf(player.statistics[FOUR_ACES] 
					+ player.statistics[FOUR_2_4] + player.statistics[FOUR_5_K]));
		System.out.printf("%-20s %-5s\n", "Straight Flush", String.valueOf(player.statistics[STRAIGHT_FLUSH]));
		System.out.printf("%-20s %-5s\n", "Royal Flush", String.valueOf(player.statistics[ROYAL_FLUSH]));
		System.out.printf("%-20s %-5s\n", "Other", String.valueOf(player.statistics[OTHER]));
		System.out.printf("---------------------------\n");
		System.out.printf("%-20s %-5s\n", "Total", String.valueOf(player.handsPlayed));
		System.out.printf("---------------------------\n");
		System.out.printf("%-15s %s (%s)\n", "Credit", String.valueOf(player.getCredit()), 
				String.valueOf(((double)player.getCredit())/player.getInitialCredit()*100));
	}
	
	public String advise(Player player){
 		String adviseString = "player should hold cards";
 		int[] cardsToHold = AdviseDoubleBonus10_7.cardsToHold(player, this);
 		if(cardsToHold[0] == -1){
 			return "player should discard everything";
 		}
 		for(int i = 0; i < 5; i++){
 			if(cardsToHold[i] == 1)
 				adviseString = adviseString + " " + (i+1); 
 		}

 		return adviseString;
 	}
	
	public void interactiveMode(int initialCredit){
		Player player = new Player(initialCredit, nWinningHands);
		deck = new Deck();
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
				if(line.length() > 1)
					bet = Character.getNumericValue(line.charAt(2));
				else
					bet = player.getLastBet();
				this.bet(player, bet);
				break;
			
			case '$':
				this.credit(player);
				break;
			
			case 'd':
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
		FileReader fr = null;
		
		try{
			fr = new FileReader(card_file);
			br = new BufferedReader(fr);

			String line;

			br = new BufferedReader(new FileReader(cmd_file));
			
			line = br.readLine(); //file has only one line
			this.state = INITIATING;
			int index = 0;
			while(index < line.length()){
				if(Character.isLetter(line.charAt(index)) || line.charAt(index) == '$'){
					char command = line.charAt(index);
					switch(command){
					case 'b':
						int bet;
						if(Character.isDigit(line.charAt(index+2))){
							int i = 2;
							//bet value can have more than one digit
							String betString = "";
							while(Character.isDigit(line.charAt(index+i)) && index+1 < line.length()){
								betString += line.charAt(index+2);
								i ++;
							}
							bet = Integer.parseInt(betString);
							index += i;
						}else{
							bet = player.getLastBet();
							index += 2;
						}
						this.bet(player, bet);
						break;
					
					case '$':
						this.credit(player);
						index += 2;
						break;
					
					case 'd':
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
							i += 2;
						}
						
						this.holdAndResults(player, indexes, deck);
						index += 2*(n+1);
						break;
						
					case 'a':
						this.getAdvise(player);
						index += 2;
						break;
						
					case 's':
						this.getStatistics(player);
						index += 2;
						break;
					
					case 'q':
						this.quit(player);
						break;
					
					default:
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
		Player player = new Player(initialCredit, nWinningHands);
		player.setLastBet(bet);
		
		for(int deal = 0; deal < nbdeals && player.getCredit() - bet >= 0; deal++){
			player.setCredit(player.getCredit() - bet);
			deck = new Deck();
			player.hand = new Hand(deck);
			int[] cardsToHold = AdviseDoubleBonus10_7.cardsToHold(player, this);
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
	
	public int handScore(Player player){		
		Card[] sortedhand = player.hand.handSort();
		
		int[] suit;
		
		/*Checking if the hand is a straight*/
		int straight=1;
		
		for (int i=0; i<4; i++){
			if(sortedhand[i].rank == sortedhand[i+1].rank - 1)
				straight++;
			if(straight==4){
				if(sortedhand[0].rank == 1 && sortedhand[1].rank==10)
					straight++;
			}
		}
		
		/*Checking if hand is flush*/
		int flush=0;
		
		suit = player.hand.isSameSuit();
		if (suit[0] == 5)
			flush=1;
		
		/*Is straight */
	
		if(straight==5){
			if(flush==1)
				if(sortedhand[1].rank==10 && sortedhand[0].rank==1)	//Has a Royal Flush in hand
					return ROYAL_FLUSH;		
				else			//Has straight flush in hand
					return STRAIGHT_FLUSH;	
			else				
				return STRAIGHT;		//Has a straight in hand
		}
		/*Checking pairs, three and four of a kind*/
		
		int[] ranks = player.hand.isSameRank();
		
		int four=0, three=0, pair=0;
		int rank_pos=0;	//Variable that will save the position of the possible four or three of a kind, so the rank can be determined
		
		for(int i=0; i<5; i++) {
			if(ranks[i]==4){
				four++;
				rank_pos=i;
			}
			if(ranks[i]==3){
				three++;
			}
			if(ranks[i]==2){
				pair++;
				rank_pos = i;
			}
		}
			
		pair=pair/2; //number of pairs in hand
		
		if(four!=0){//Has a four a a kind
			if(player.hand.hand[rank_pos].rank == 1)
				return FOUR_ACES;				//Aces
			if(player.hand.hand[rank_pos].rank <= 4 && 2 <= player.hand.hand[rank_pos].rank)
				return FOUR_2_4; 				//Has a four ranked between 2-4
			if(player.hand.hand[rank_pos].rank <= 13 && 5 <= player.hand.hand[rank_pos].rank)
				return FOUR_5_K;				//Has a four ranked between 5-K
		}
		if(three!= 0 && pair!=0){
			return FULL_HOUSE;		//Has a full house in hand
		}
		if(three != 0)
			return THREE_OF_A_KIND;		//Has a three of a kind	in hand
		if(pair>1)
			return TWO_PAIR;		//Has a two pair in hand
		
		if(pair == 1){
			if(player.hand.hand[rank_pos].rank > 10 || player.hand.hand[rank_pos].rank == 1)
				return JACKS_OR_BETTER;	//Has a pair of jacks or higher in hand
		}
		
		if(flush==1)
			return FLUSH;		//Has a flush in hand
		
		return OTHER;
	}
	
	void bet(Player player, int bet){
		if(state == INITIATING || state == BETTING){
			if(bet <= 5 && player.getCredit() - bet >= 0){
				player.setLastBet(bet);
				System.out.println("player is betting " + player.getLastBet());
				state = BETTING;
			}else
				System.out.println("b: illegal amount");
		}else
			this.illegalCommand('b');
		return;
	}
	
	void credit(Player player){
		System.out.println("player's credit is " + player.getCredit());
		return;
	}
	
	void deal(Player player){
		if(state == BETTING){
			player.setCredit(player.getCredit()-player.getLastBet());
			player.hand = new Hand(deck);
			System.out.println(player.hand);
			state = DEALING;
		}else{
			this.illegalCommand('d');
		}
		return;
	}
	
	void holdAndResults(Player player, int[] indexes, Deck deck){
		if(state == DEALING){
			player.hand.hold(indexes, deck);
			System.out.println(player.hand);
			
			int score = this.handScore(player);
			player.setCredit(player.getCredit() + reward(score, player.getLastBet()));
			if(score == 0)
				System.out.println("Player lost and his credit is " + player.getCredit());
			else
				System.out.println("Player wins with a " + this.scoreToString(score) 
					+ " and his credit is " + player.getCredit());
			
			player.incStatistics(score);
			player.incHandsPlayed();
			state = BETTING;
		}else
			this.illegalCommand('h');
		
		if(player.getCredit() == 0){
			System.out.println("GAME OVER");
			System.exit(3);
		}
		return;
	}
	
	void getAdvise(Player player){
		if(state == DEALING){
			System.out.println(this.advise(player));
		}else
			this.illegalCommand('a');
		return;
	}
	
	void getStatistics(Player player){
		this.statistics(player);
		return;
	}
	
	void quit(Player player){
		if(state == INITIATING || state == BETTING){
			System.out.println("Exiting the game");
			this.statistics(player);
			state = QUITTING;
		}else
			this.illegalCommand('q');
		return;
	}
	
	void illegalCommand(char command){
		System.out.println(command + ": illegal command");
		return;
	}
	
	public String scoreToString(int score){
 		switch(score){
 		case(ROYAL_FLUSH):
 			return "ROYAL FLUSH";
 		case(FOUR_ACES):
 			return "FOUR ACES";
 		case(FOUR_2_4):
 			return "FOUR 2-4";
 		case(FOUR_5_K):
 			return "FOUR 5-K";
 		case(STRAIGHT_FLUSH):
 			return "STRAIGHT FLUSH";
 		case(FULL_HOUSE):
 			return "FULL HOUSE";
 		case(FLUSH):
 			return "FLUSH";
 		case(STRAIGHT):
 			return "STRAIGHT";
 		case(THREE_OF_A_KIND):
 			return "THREE OF A KIND";
 		case(TWO_PAIR):
 			return "TWO PAIR";
 		case(JACKS_OR_BETTER):
 			return "JACKS OR BETTER";
 		default:
 			return "INVALID SCORE";
 		}
 	}
}

