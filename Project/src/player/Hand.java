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
	
	public int handScore(){
		
		int score=0; //Has nothing in hand
		
		Card[] sortedhand = this.hand;
		
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
		
		suit = isSameSuit();
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
		
		int[] ranks= isSameRank();
		
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
	
	//returns -1 if does not exist
	private int indexOfRank(int rank){
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
	private int indexOfCard(int rank, int suit){
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
 		int maxSuit = 0;
 		for(int i = 0; i < 5; i++){
 			if(suits[i] > maxSameSuit){
 				maxSameSuit = suits[i];
 				maxSuit = this.hand[i].suit;
 			}
 		}
 		
 		int[] ranks = this.isSameRank(); 
 		int maxSameRank = 0;
 		int maxRank = 0; //Rank that appears the most time
 		for(int i = 0; i < 5; i++){
 			if(ranks[i] > maxSameRank){
 				maxSameRank = ranks[i];
 				maxRank = this.hand[i].rank;
 			}
 		}
 		
 		Card[] sortedhand = this.hand;
		java.util.Arrays.sort(sortedhand, new  ComparatorByRank());
 		
		/*****Variables*****/
		int first = sortedhand[0].rank;
 		int nJumps;
 		int jumpRank;
 		int jumpSuit;
 		int nHighCards;
 		int cardsToRoyal;
 		int lastCard;
 		int nGaps;
		/*******************/
		
 		/*****Play (1) - Straight flush, four of a kind, royal flush**********/
 		if(score <= 5){ //royal flush, four of a kind or straight flush
 			indexes[0] = 1;
 			indexes[1] = 1;
 			indexes[2] = 1;
 			indexes[3] = 1;
 			indexes[4] = 1;
 			return indexes;
 		}
 		
 		/*****Play (2) - 4 to a royal flush***********************************/
 		if(maxSameSuit >= 4 && maxSameRank < 3){
 			cardsToRoyal = 0;
 			int[] highCards = new int[5]; //stores the position of each card of a royal flush.
 			
 			highCards[0] = indexOfCard(10, maxSuit);
 			if(highCards[0] != -1)
 				cardsToRoyal++;
 			
 			highCards[1] = indexOfCard(11, maxSuit);
 			if(highCards[1] != -1)
 				cardsToRoyal++;
 			
 			highCards[2] = indexOfCard(12, maxSuit);
 			if(highCards[2] != -1)
 				cardsToRoyal++;
 			
 			highCards[3] = indexOfCard(13, maxSuit);
 			if(highCards[3] != -1)
 				cardsToRoyal++;
 			
 			highCards[4] = indexOfCard(1, maxSuit);
 			if(highCards[4] != -1)
 				cardsToRoyal++;
 			
 			if(cardsToRoyal == 4){
 				for(int i = 0; i < 5; i++){
 					if(highCards[i] != -1)
 						indexes[highCards[i]] = 1;
 				}
 				return indexes;
 			}
 		}
 		
 		/*****Play(3) - Three aces********************************************/
 		if(maxSameRank == 3 && maxRank == 1){
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
 		if(maxSameSuit == 4){
 			//first card belongs to the straight
 			nJumps = 0;
 			jumpRank = 0;
 			jumpSuit = 0;
 			first = sortedhand[0].rank;
 			for(int i = 1; i < 5; i++){
 				if(nJumps > 1)
 					break;
 				else{
 					if(sortedhand[i].rank != first+i){
 						nJumps++;
 						jumpRank = sortedhand[i].rank;
 						jumpSuit = sortedhand[i].suit;
 					}
 				}
 					
 			}
 			if(nJumps < 1){
 				for(int i = 0; i < 5; i++)
 						indexes[i] = 1;
 				
 				indexes[indexOfCard(jumpRank, jumpSuit)] = 0;
 				return indexes;
 			}else{ //check if the first card doesnt belong to the possible straight, but the other 4 do
 				nJumps = 0;
 				first = sortedhand[1].rank;
 				for(int i = 2; i < 5; i++){
 					if(nJumps > 0)
 						break;
 					else{
 						if(sortedhand[i].rank != (first+i-1))
 							nJumps++;
 					}
 				}
 				if(nJumps == 0){
 					for(int i = 0; i < 5; i++)
 						indexes[i] = 1;
 					
 					indexes[indexOfRank(sortedhand[0].rank)] = 0;
 					return indexes;
 				}
 			}
 		}
 		
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
 		if(maxSameSuit == 4){
 			for(int i = 0; i < 5; i++){
 				if(suits[i] == 4)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(10) - 3 to a royal flush***********************************/
 		if(maxSameSuit >= 3 && maxSameRank < 4){
 			cardsToRoyal = 0;
 			int[] highCards = new int[5]; //stores the position of each card of a royal flush.
 			
 			highCards[0] = indexOfCard(10, maxSuit);
 			if(highCards[0] != -1)
 				cardsToRoyal++;
 			
 			highCards[1] = indexOfCard(11, maxSuit);
 			if(highCards[1] != -1)
 				cardsToRoyal++;
 			
 			highCards[2] = indexOfCard(12, maxSuit);
 			if(highCards[2] != -1)
 				cardsToRoyal++;
 			
 			highCards[3] = indexOfCard(13, maxSuit);
 			if(highCards[3] != -1)
 				cardsToRoyal++;
 			
 			highCards[4] = indexOfCard(1, maxSuit);
 			if(highCards[4] != -1)
 				cardsToRoyal++;
 			
 			if(cardsToRoyal == 3){
 				for(int i = 0; i < 5; i++){
 					if(highCards[i] != -1)
 						indexes[highCards[i]] = 1;
 				}
 				return indexes;
 			}
 		}
 		
 		/*****Play(11) - 4 to an outside straight*****************************/
 		if(maxSameRank < 3){
 			//in sorted hand, either the first card or the last card doesnt belong to straight 			
 			nJumps = 0;
 			first = sortedhand[0].rank;
 			for(int i = 1; i < 5; i++){
 				if(nJumps == 0){
 					if(sortedhand[i].rank != first+i)
 						nJumps = 1;
 				}else
 					break;
 			}
 			if(nJumps == 0){
 				for(int i = 0; i < 5; i++)
 					indexes[i] = 1;
 				
 				indexes[indexOfRank(sortedhand[4].rank)] = 0;
 				return indexes;
 			}else{
 				first = sortedhand[1].rank;
 				nJumps = 0;
 				for(int i = 2; i < 5; i++){
 					if(nJumps == 0){
 						if(sortedhand[i].rank != first+i-1)
 	 						nJumps = 1;
 					}else
 						break;
 				}
 				if(nJumps == 0){
 					for(int i = 0; i < 5; i++)
 	 					indexes[i] = 1;
 					
 					indexes[indexOfRank(sortedhand[0].rank)] = 0;
 					return indexes;
 				}
 			}
 		}
    
 		/*****Play(12) - Low pair ********************************************/
 		if(maxSameRank == 2 && maxRank < 11){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(13) - AKJQ unsuited ***************************************/
 		if(indexOfRank(1) != -1 || indexOfRank(11) != -1 || indexOfRank(12) != -1 || indexOfRank(13) != -1){
 			indexes[indexOfRank(1)]=1;
 			indexes[indexOfRank(11)]=1;
 			indexes[indexOfRank(12)]=1;
 			indexes[indexOfRank(13)]=1;
 			
 			return indexes;
 		}		

 		/*****Play(14) - 3 to a straight flush (type 1)***********************/
 		if(maxSameSuit >= 3){
 			//first card of the straight can be in the first 3 cards of the sorted hand
 			lastCard = 0; //accounts for the number of card between 2 cards that belong to the straight
 			nGaps = 0;
 			nJumps = 0;
 			nHighCards = 0;
 			int[] jumpRank14 = {0,0};
 			if(sortedhand[0].suit == maxSuit){ //first card of the straight = first card of sortedhand
 				first = sortedhand[0].rank;
 				for(int i = 1; i < 5; i++){
 					if(nJumps > 2)
 						break;
 					else{
 						if(sortedhand[i].rank != first + i){
 							jumpRank14[nJumps] = sortedhand[i].rank;
 							nJumps++;
 							lastCard++;
 						}else{
 							if(lastCard > 0){
 								nGaps++;
 								lastCard = 0;
 							}
 							if(sortedhand[i].rank > 10 || sortedhand[i].rank == 1)
 								nHighCards++;
 						}
 					}
 				}
 				if(nJumps < 3 && nHighCards > nGaps && first != 1 && !(first == 2 && nGaps == 0)){
 					for(int i = 0; i < 5; i++)
 						indexes[i] = 1;
 					
 					for(int i = 0; i < 2; i++)
 						indexes[indexOfRank(jumpRank14[i])] = 0;
 					
 					return indexes;
 				}
 			}
 			
 			lastCard = 0;
 			nGaps = 0;
 			nJumps = 0;
 			nHighCards = 0;
 			jumpRank14[0] = 0; jumpRank14[1] = 0;
 			if(sortedhand[1].suit == maxSuit){ //first card of the straight = second card of sortedhand
 				first = sortedhand[1].rank;
 				for(int i = 2; i < 5; i++){
 					if(nJumps > 2)
 						break;
 					else{
 						if(sortedhand[i].rank != first + i - 1){
 							jumpRank14[nJumps] = sortedhand[i].rank;
 							nJumps++;
 							lastCard++;
 						}else{
 							if(lastCard > 0){
 								nGaps++;
 								lastCard = 0;
 							}
 							if(sortedhand[i].rank > 10 || sortedhand[i].rank == 1)
 								nHighCards++;
 						}
 					}
 				}
 				if(nJumps < 3 && nHighCards > nGaps && first != 1 && !(first == 2 && nGaps == 0)){
 					for(int i = 0; i < 5; i++)
 						indexes[i] = 1;
 					
 					for(int i = 0; i < 2; i++)
 						indexes[indexOfRank(jumpRank14[i])] = 0;
 					
 					return indexes;
 				}
 			}
 			
 			lastCard = 0;
 			nGaps = 0;
 			nJumps = 0;
 			nHighCards = 0;
 			jumpRank14[0] = 0; jumpRank14[1] = 0;
 			if(sortedhand[2].suit == maxSuit){ //first card of the straight = third card of sortedhand
 				first = sortedhand[2].rank;
 				for(int i = 2; i < 5; i++){
 					if(nJumps > 2)
 						break;
 					else{
 						if(sortedhand[i].rank != first + i - 2){
 							jumpRank14[nJumps] = sortedhand[i].rank;
 							nJumps++;
 							lastCard++;
 						}else{
 							if(lastCard > 0){
 								nGaps++;
 								lastCard = 0;
 							}
 							if(sortedhand[i].rank > 10 || sortedhand[i].rank == 1)
 								nHighCards++;
 						}
 					}
 				}
 				if(nJumps < 3 && nHighCards > nGaps && first != 1 && !(first == 2 && nGaps == 0)){
 					for(int i = 0; i < 5; i++)
 						indexes[i] = 1;
 					
 					for(int i = 0; i < 2; i++)
 						indexes[indexOfRank(jumpRank14[i])] = 0;
 					
 					return indexes;
 				}
 			}
 		}
 		
 		/*****Play(15) - 4 to an inside straight with 3 high cards************/
 		//card 2, 3 or 4 dont belong to the straight
 		first = sortedhand[0].rank;
 		nJumps = 0;
 		jumpRank = 0;
 		nHighCards = 0;
 		for(int i = 1; i < 5; i++){
 			if(nJumps > 1)
 				break;
 			else{
 				if(sortedhand[i].rank != first+i){
 					nJumps++;
 					jumpRank = sortedhand[i].rank;
 				}else{
 					if(sortedhand[i].rank > 10 || sortedhand[i].rank == 1)
 						nHighCards++;
 				}
 			}
 		}
 		
 		if(nJumps <= 1 && nHighCards == 3){
 			for(int i = 0; i < 5; i++)
 				indexes[i] = 1;
 			
 			indexes[indexOfRank(jumpRank)] = 0;
 			return indexes;
 		}
 		
 		/*****Play(16) - QJ suited********************************************/
 		//can only have one J and one Q, because if there are more than one, the advise would be to keep the pair
 		if(indexOfRank(11) != -1 && indexOfRank(12) != -1){
	 		if(this.hand[indexOfRank(11)].suit == this.hand[indexOfRank(12)].suit){
	 			indexes[indexOfRank(11)] = 1;
	 			indexes[indexOfRank(12)] = 1;
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(17) - 3 to a flush with 2 high cards***********************/
 		if(maxSameSuit == 3){
 			nHighCards = 0;
 			int[] indexHighCard17 = new int[2];
 			
 			for(int i = 0; i < 5; i++){
 				if(this.hand[i].suit == maxSuit){
 					if(this.hand[i].rank > 10 || this.hand[i].rank == 1){
 						indexHighCard17[nHighCards] = i;
 						nHighCards++;
 					}
 				}
 			}
 			if(nHighCards == 2){
 				indexes[indexHighCard17[0]] = 1;
 				indexes[indexHighCard17[1]] = 1;
 				return indexes;
 			}
 		}
 		
 		/*****Play(18) - 2 suited high cards**********************************/
 		for(int i = 0; i < 5; i++){
 			if((this.hand[i].rank > 10 || this.hand[i].rank == 1) && suits[i] >= 2){
 				for(int j = i+1; j < 5; j++){
 					if((this.hand[j].rank > 10 || this.hand[j].rank == 1) && this.hand[i].suit == this.hand[j].suit){
 						indexes[i] = 1;
 						indexes[j] = 1;
 						return indexes;
 					}
 				}
 			}
 		}
 		
 		/*****Play(19) - 4 to an inside straight with 2 high cards************/
 		//card 2, 3 or 4 dont belong to the straight
 		first = sortedhand[0].rank;
 		nJumps = 0;
 		jumpRank = 0;
 		nHighCards = 0;
 		for(int i = 1; i < 5; i++){
 			if(nJumps > 1)
 				break;
 			else{
 				if(sortedhand[i].rank != first+i){
 					nJumps++;
 					jumpRank = sortedhand[i].rank;
 				}else{
 					if(sortedhand[i].rank > 10 || sortedhand[i].rank == 1)
 						nHighCards++;
 				}
 			}
 		}
 		
 		if(nJumps <= 1 && nHighCards == 2){
 			for(int i = 0; i < 5; i++)
 				indexes[i] = 1;
 			
 			indexes[indexOfRank(jumpRank)] = 0;
 			return indexes;
 		}
 		
 		/*****Play(20) - 3 to a straight flush (type 2)***********************/
 		
 		/*****Play(21) - 4 to an inside straight with 1 high card*************/
 		
 		/*****Play(22) - KQJ unsuited*****************************************/
 		
 		/*****Play(23) - JT suited********************************************/
 		
 		/*****Play(24) - QJ unsuited******************************************/
 		
 		/*****Play(25) - 3 to a flush with 1 high card************************/
 		
 		/*****Play(26) - QT suited********************************************/
 		
 		/*****Play(27) - 3 to a straight flush (type 3)***********************/
 		
 		/*****Play(28) - KQ, KJ unsuited**************************************/
 		
 		/*****Play(29) - Ace**************************************************/
 		
 		/*****Play(30) - KT suited********************************************/
 		
 		/*****Play(31) - Jack, Queen or King**********************************/
 		
 		/*****Play(32) - 4 to an inside straight with no high cards***********/
 		
 		/*****Play(33) - 3 to a flush with no high cards**********************/
 		
 		/*****Play(34) - Discard everything***********************************/
 		return indexes;
 		
 	}//end cardsToHold
}
