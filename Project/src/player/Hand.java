package player;

import java.util.*;
import cards.*;
import videoPoker.*;


public class Hand {
	private Card[] hand = new Card[5];
	
	//Constructor
	public Hand(Deck deck){
		for(int i = 0; i < 5; i++){
			hand[i] = deck.draw();
		}
	}
	
	//toString
	@Override
	public String toString() {
		String string = new String();
		for(int i = 0; i < 5; i++){
			string = string + hand[i].toString() + " ";
		}
		return ("player's hand is " + string);
	}
	
	public void hold(int index[], Deck deck){
		for(int i = 0; i < 5; i++){
			if(!contains(index, i+1)){
				hand[i] = deck.draw();
			}
		}
		return;
	}
	
	private boolean contains(int index[], int toFind){
		for(int i = 0; i < index.length; i++){
			if(index[i] == toFind)
				return true;
		}
		return false;
	}
	
	private int[] isSameRank(Hand hand){		//returns the number of times the rank of a card repeats itself in a hand
		int[] rankcount={1,1,1,1,1};
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(hand.hand[i].rank == hand.hand[j].rank){
					rankcount[i] ++;
					rankcount[j]++;
				}
			}
		}
		return rankcount;
	}
	
	private int[] isSameSuit(Hand hand){		//returns the number of times there is the same suit
		int[] samesuit = {1,1,1,1,1};
			
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(hand.hand[i].suit == hand.hand[j].suit){
					samesuit[i]++;
					samesuit[j]++;
				}
			}
		}
		return samesuit;
	}
	
	private int handScore(Hand hand){
		int score = 0;	
		Card[] sortedhand = hand.hand;
		
		java.util.Arrays.sort(sortedhand, new ComparatorByRank());
		
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
		
		suit = isSameSuit(hand);
		if (suit[0]==5)
			flush=1;
		
		/*Is straight flush? */
	
		if(straight==5){
			if(flush==1)
				if(sortedhand[1].rank==10)	//Nigga cagou-se todo!!!!!
					score=1;				
				else			//Nigga has a straight flush
					score=5;
			else				//Nigga has a straight
				score=8;
		}
		/**/
		return score;
	}
	
 	private String BestMove(Hand hand){
		String advise = null;	
		return advise;
	}
}
