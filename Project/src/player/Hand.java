package player;

import java.util.*;
import cards.*;
import virtualPoker.*;


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
		return string;
	}
	
	public void hold(int index[], Deck deck){
		for(int i = 0; i < 5; i++){
			if(!contains(index, i)){
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
	
	
 	private String BestMove(Hand hand){
		String advise = null;
		
		/*Verifying if it's a Straight Flush*/
		int[] suit;
		int ranking = 1;
		int discard = 0;
		
		suit = isSameSuit(hand);
		
		if(suit[0]==5){								
			for(int i=0; i<5;i++){
	
				if(hand.hand[i].rank == 1){
					ranking++;	
					continue;
				}
				
				if(hand.hand[i].rank % 10 == i){
					ranking ++;
				}
				else
					discard = i;
				
			}
			if(ranking==5)											//Royal Flush
				advise = "Keep Straight Flush!";
			
			if(ranking==4)											//Almost royal Flush
				advise = "Discard" + hand.hand[discard].toString();
		}
		
		return advise;
	}
}
