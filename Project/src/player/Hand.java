package player;

import java.util.*;
import cards.*;
import videoPoker.*;


public class Hand {
	public Card[] hand = new Card[5];
	
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
	
	private int[] isSameRank(){		//returns the number of times the rank of a card repeats itself in a hand
		int[] rankcount={1,1,1,1,1};
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(this.hand[i].rank == this.hand[j].rank){
					rankcount[i] ++;
					rankcount[j]++;
				}
			}
		}
		return rankcount;
	}
	
	private int[] isSameSuit(){		//returns the number of times there is the same suit
		int[] samesuit = {1,1,1,1,1};
			
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(this.hand[i].suit == this.hand[j].suit){
					samesuit[i]++;
					samesuit[j]++;
				}
			}
		}
		return samesuit;
	}
	
	protected Card[] handSort(){
		Card[] sortedhand = new Card[5];
		
		for(int i = 0; i < 5; i++){
			sortedhand[i] = new Card(this.hand[i].rank, this.hand[i].suit);
		}
		java.util.Arrays.sort(sortedhand, new ComparatorByRank());
		return sortedhand;
	}
	
	public int handScore(){		
		Card[] sortedhand = handSort();
		
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
		
		suit = isSameSuit();
		if (suit[0] == 5)
			flush=1;
		
		/*Is straight */
	
		if(straight==5){
			if(flush==1)
				if(sortedhand[1].rank==10 && sortedhand[0].rank==1)	//Has a Royal Flush in hand
					return 1;		
				else			//Has straight flush in hand
					return 5;	
			else				
				return 8;		//Has a straight in hand
		}
		/*Checking pairs, three and four of a kind*/
		
		int[] ranks = isSameRank();
		
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
			if(hand[rank_pos].rank == 1)
				return 2;				//Aces
			if(hand[rank_pos].rank <= 4 && 2 <= hand[rank_pos].rank)
				return 3; 				//Has a four ranked between 2-4
			if(hand[rank_pos].rank <= 13 && 5 <= hand[rank_pos].rank)
				return 4;				//Has a four ranked between 5-K
		}
		if(three!= 0 && pair!=0){
			return 6;		//Has a full house in hand
		}
		if(three != 0)
			return 9;		//Has a three of a kind	in hand
		if(pair>1)
			return 10;		//Has a two pair in hand
		
		if(pair == 1){
			if(hand[rank_pos].rank > 10 || hand[rank_pos].rank == 1)
				return 11;	//Has a pair of jacks or higher in hand
		}
		
		if(flush==1)
			return 7;		//Has a flush in hand
		
		return 0;
	}
	
	//returns -1 if does not exist
	protected int indexOfRank(int rank){
		int index = -1;
		for(int i = 0; i < 5; i++){
			if(this.hand[i].rank == rank){
				index = i;
				break;
			}
		}
		return index;
	}
	
	//returns -1 if does not exist
	protected int indexOfCard(int rank, int suit){
		int index = -1;
		Card card = new Card(rank, suit);
		for(int i = 0; i < 5; i++){
			if(this.hand[i].equals(card)){
				index = i;
				break;
			}
		}
		return index;
	}
	
	//return array with 5 positions correponding to each card in the hand
	//if position = 1 it should be kept
	//if position = 0 is should be discarded
 	private int[] cardsToHold(){
 		
 		int[] indexes = {0,0,0,0,0};	//vector of cards to hold
 		
 		int score = this.handScore();	//Verifying the score of the hand
 		
 		int[] suits = this.isSameSuit(); 
 		int maxSameSuit = 0;
 		int mostRepSuit = 0;
 		for(int i = 0; i < 5; i++){
 			if(suits[i] > maxSameSuit){
 				maxSameSuit = suits[i];
 				mostRepSuit = this.hand[i].suit;
 			}
 		}
 		
 		int[] ranks = this.isSameRank(); 
 		int maxSameRank = 0;
 		int mostRepRank = 0; //Rank that appears the most time
 		for(int i = 0; i < 5; i++){
 			if(ranks[i] > maxSameRank){
 				maxSameRank = ranks[i];
 				mostRepRank = this.hand[i].rank;
 			}
 		}

 		/*****Play (1) - Straight flush, four of a kind, royal flush**********/
 		if(score > 0 && score <= 5){ //royal flush, four of a kind or straight flush
 			indexes[0] = 1;
 			indexes[1] = 1;
 			indexes[2] = 1;
 			indexes[3] = 1;
 			indexes[4] = 1;
 			return indexes;
 		}
 		
 		/*****Play (2) - 4 to a royal flush***********************************/
 		CardsToHighCards highCards = new CardsToHighCards();
 		highCards.compute(maxSameSuit, mostRepSuit, this);
 		
 		if(highCards.royal && highCards.nCardsTo == 4)
 			return highCards.indexes;
 		
 		/*****Play(3) - Three aces********************************************/
 		if(maxSameRank == 3 && mostRepRank == 1){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 3)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(4) - Straight, flush, full house***************************/
 		if(score >= 6 && score <= 8){
 			indexes[0] = 1;
 			indexes[1] = 1;
 			indexes[2] = 1;
 			indexes[3] = 1;
 			indexes[4] = 1;
 			return indexes;
 		}
 		
 		/*****Play(5) - Three of a kind (except aces)*************************/
 		if(maxSameRank == 3){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 3)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(6) - 4 to a straight flush*********************************/
 		CardsToStraightFlush straightFlush = new CardsToStraightFlush();
 		straightFlush.compute(maxSameSuit, mostRepSuit, this);
 		
 		System.out.println(straightFlush.type);
 		if(straightFlush.nCardsTo == 4)
 			return straightFlush.indexes;
 		
 		/*****Play(7) - Two pair**********************************************/
 		if(score == 10){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(8) - High pair*********************************************/
 		if(score == 11){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(9) - 4 to a flush******************************************/
 		CardsToFlush flush = new CardsToFlush();
 		flush.compute(maxSameSuit, mostRepSuit, this);
 		
 		if(flush.nCardsTo == 4)
 			return flush.indexes;
 			
 		/*****Play(10) - 3 to a royal flush***********************************/
 		if(highCards.royal && highCards.nCardsTo == 3)
 			return highCards.indexes;
 		
 		/*****Play(11) - 4 to an outside straight*****************************/
 		CardsToStraight straight = new CardsToStraight();
 		straight.compute(maxSameSuit, mostRepSuit, this);
 		
 		if(straight.outside)
 			return straight.indexes;
    
 		/*****Play(12) - Low pair ********************************************/
 		if(maxSameRank == 2 && mostRepRank < 11){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(13) - AKJQ unsuited ***************************************/
 		if(highCards.AKQJunsuited)
 			return highCards.indexes;

 		/*****Play(14) - 3 to a straight flush (type 1)***********************/
 		if(straightFlush.type == 1)
 			return straightFlush.indexes;
 		
 		/*****Play(15) - 4 to an inside straight with 3 high cards************/
 		if(!straight.outside && straight.nHighCards == 3)
 			return straight.indexes;
 			
 		
 		/*****Play(16) - QJ suited********************************************/
 		if(highCards.QJsuited)
 			return highCards.indexes;
 		
 		/*****Play(17) - 3 to a flush with 2 high cards***********************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 2)
 			return flush.indexes;
 		
 		/*****Play(18) - 2 suited high cards**********************************/
 		for(int i = 0; i < 5; i++){
 			if(this.hand[i].isHighCard() && suits[i] >= 2){
 				for(int j = i+1; j < 5; j++){
 					if(this.hand[j].isHighCard() && this.hand[i].suit == this.hand[j].suit){
 						indexes[i] = 1;
 						indexes[j] = 1;
 						return indexes;
 					}
 				}
 			}
 		}
 		
 		/*****Play(19) - 4 to an inside straight with 2 high cards************/
 		if(!straight.outside && straight.nHighCards == 2)
 			return straight.indexes;
 		
 		/*****Play(20) - 3 to a straight flush (type 2)***********************/
 		if(straightFlush.type == 2)
 			return straightFlush.indexes;
 		
 		/*****Play(21) - 4 to an inside straight with 1 high card*************/
 		if(!straight.outside && straight.nHighCards == 1)
 			return straight.indexes;
 		 		
 		/*****Play(22) - KQJ unsuited*****************************************/
 		if(highCards.KQJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(23) - JT suited********************************************/
 		if(highCards.JTsuited)
 			return highCards.indexes;
 		
 		/*****Play(24) - QJ unsuited******************************************/
 		if(highCards.QJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(25) - 3 to a flush with 1 high card************************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 1)
 			return flush.indexes;
 		
 		/*****Play(26) - QT suited********************************************/
 		if(highCards.QTsuited)
 			return flush.indexes;
 		
 		/*****Play(27) - 3 to a straight flush (type 3)***********************/
 		if(straightFlush.type == 3)
 			return straightFlush.indexes;
 		
 		/*****Play(28) - KQ, KJ unsuited**************************************/
 		if(highCards.KQ_KJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(29) - Ace**************************************************/
 		if(highCards.Ace)
 			return highCards.indexes;
 		
 		/*****Play(30) - KT suited********************************************/
 		if(highCards.KTsuited)
 			return highCards.indexes;
 		
 		/*****Play(31) - Jack, Queen or King**********************************/
 		if(highCards.K_Q_J)
 			return highCards.indexes;
 		
 		/*****Play(32) - 4 to an inside straight with no high cards***********/
		if(!straight.outside && straight.nHighCards == 0)
			return straight.indexes;
 		
 		/*****Play(33) - 3 to a flush with no high cards**********************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 0)
 			return flush.indexes;
 		
 		/*****Play(34) - Discard everything***********************************/
 		indexes[0] = -1; //flag to discard everything
 		return indexes;
 		
 	}//end cardsToHold
 	
 	public String advise(){
 		String advise = "player should hold";
 		int[] cardsToHold = this.cardsToHold();
 		if(cardsToHold[0] == -1){
 			return "player should discard everything";
 		}
 		for(int i = 0; i < 5; i++){
 			if(cardsToHold[i] == 1)
 				advise = advise + " " + (i+1); 
 		}
 		return advise;
 	}
}
