package doubleBonus;

import cards.Card;
import player.Player;
import videoPoker.VideoPoker;

// TODO: Auto-generated Javadoc
/**Class DoubleBonus. Class that is to be extended by all DoubleBonus game modes */
public abstract class DoubleBonus extends VideoPoker{

	/** Total number of hands that are scored/that are winning hands*/
	public static final int nWinningHands = 11;
	
	/** Number of possible values that can be bet(1 2 3 4 5)*/
	public static final int nBets = 5;
	
	/** Defining the value of the winning hands*/
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
	
	/**
	 * .
	 */
	public DoubleBonus(){
		this.rewardTable = fillRewardTable();
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#statistics(player.Player)
	 */
	public void statistics(Player player){
		double percentage = ((double)player.getCredit())/player.getInitialCredit()*100;
		String percentageStr = String.valueOf(percentage);
		int maxLength = 5;
		if(percentageStr.length() < 5)
			maxLength = percentageStr.length();
		
		percentageStr = percentageStr.substring(0, maxLength);
		
		System.out.printf("\n%-20s %-5s\n", "Hand", "Nb");
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
		System.out.printf("%-15s %s (%s)\n\n", "Credit", String.valueOf(player.getCredit()), percentageStr);
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#scoreToString(int)
	 */
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
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#handScore(player.Player)
	 */
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
		
		/**
		 * Checking if it's a straight or a play that derivates from a  straight  
		 * */
	
		if(straight==5){
			if(flush==1)
				if(sortedhand[1].rank==10 && sortedhand[0].rank==1)	//Has a Royal Flush in hand
					return ROYAL_FLUSH;		
				else			//Has straight flush in hand
					return STRAIGHT_FLUSH;	
			else				
				return STRAIGHT;		//Has a straight in hand
		}
		/**
		 * Checking pairs three and four of a kind
		 * */
		
		int[] ranks = player.hand.isSameRank();
		
		int four=0, three=0, pair=0;
		int rank_pos=0;	//Variable that will save the position of the possible four or three of a kind
					   //so the rank can be determined
		
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
	
	/**
	 * Prints the command.
	 *
	 * @param cmd string of the inserted command
	 */
	protected void printCommand(String cmd){
		System.out.println("-cmd " + cmd);
	}
}