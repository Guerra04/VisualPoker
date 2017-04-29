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
	
	public int handScore(Hand hand){
		
		int score=0; //Has nothing in hand
		
		Card[] sortedhand = hand.hand;
		
		java.util.Arrays.sort(sortedhand, new  ComparatorByRank());

		/*************/
		
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
		
		/*Is straight */
	
		if(straight==5){
			if(flush==1)
				if(sortedhand[1].rank==10)	//Has a Royal Flush in hand
					score=1;		
				else			//Has straight flush in hand
					score=5;	
			else				
				score=8;		//Has a straight in hand
		}
		/*Checking pairs, three and four of a kind*/
		
		int[] ranks= isSameRank(hand);
		
		/**************/
		for(int i = 0; i < 5; i++)
			System.out.println(ranks[i]);
		/**************/
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
			if(sortedhand[rank_pos].rank == 1)
				score = 2;				//Aces
			if(sortedhand[rank_pos].rank <= 4 && 2 <= sortedhand[rank_pos].rank)
				score = 3; 				//Has a four ranked between 2-4
			if(sortedhand[rank_pos].rank <= 13 && 5 <= sortedhand[rank_pos].rank)
				score = 4;				//Has a four ranked between 5-K
		}
		if(three!= 0 && pair!=0){
			score = 6;		//Has a full house in hand
		}
		if(three != 0)
			score = 9;		//Has a three of a kind	in hand
		if(pair>1)
			score = 10;		//Has a two pair in hand
		
		if(pair == 1){
			if(sortedhand[rank_pos].rank > 10 || sortedhand[rank_pos].rank == 1)
				score = 11;	//Has a pair of jacks or higher in hand
		}
		
		if(flush==1 && score<7)
			score=8;		//Has a flush in hand
		
		return score;
	}
	
 	private String BestMove(Hand hand){
		String advise = null;	
		return advise;
	}
}
