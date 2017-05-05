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
 		
 		Card[] sortedhand = this.handSort();
 		
		/*****Variables*****/
		int first = sortedhand[0].rank;
 		int nJumps;
 		int jumpRank;
 		int jumpSuit;
 		int nHighCards = 0;
 		int nHighCardsAux;
 		int cardsToRoyal;
 		int lastCard;
 		int nGaps;
 		int cardsToStraight;
 		int[] straightIndexes = new int[0];
		/*******************/
 		int[] highCards = new int[5]; //stores the position of each card of a royal flush.
 		highCards[0] = indexOfRank(10);
 		highCards[1] = indexOfRank(11);
 		highCards[2] = indexOfRank(12);
 		highCards[3] = indexOfRank(13);
 		highCards[4] = indexOfRank(1);
 		
 		for(int i = 1; i < 5; i++){
 			if(highCards[i] != -1)
 				nHighCards++;
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
 		if(maxSameSuit >= 4 && maxSameRank < 3 && nHighCards >= 3){
 			cardsToRoyal = 0;
 			int[] royalCards2 = {-1, -1, -1, -1};
 			
 			for(int i = 0; i < 5; i++){
 				if(highCards[i] != -1 && this.hand[highCards[i]].suit == maxSuit){
 					royalCards2[cardsToRoyal] = highCards[i];
 					cardsToRoyal++; 					
 				}
 			}
 			
 			if(cardsToRoyal == 4){
 				for(int i = 0; i < 4; i++){
 					if(royalCards2[i] != -1)
 						indexes[royalCards2[i]] = 1;
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
 			//straight can begin in the first or second card of the sortedhand
 			cardsToStraight = 0;
 			for(int i = 0; i < 2; i++){
 				if(cardsToStraight != 4){
	 				cardsToStraight = 0;
	 				straightIndexes = new int[4];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfCard(sortedhand[i].rank + j, maxSuit) != -1){
	 						straightIndexes[cardsToStraight] = indexOfCard(sortedhand[i].rank + j, maxSuit);
	 						cardsToStraight++;
	 					}
	 				}
 				}
 			}
 			
 			if(cardsToStraight == 4){
 				for(int i = 0; i < 4; i++)
 					indexes[straightIndexes[i]] = 1;

 				return indexes;
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
 		if(maxSameSuit >= 3 && maxSameRank < 4 && nHighCards >= 2){
 			cardsToRoyal = 0;
 			int[] royalCards10 = {-1, -1, -1};
 			
 			for(int i = 0; i < 5; i++){
 				if(highCards[i] != -1 && this.hand[highCards[i]].suit == maxSuit){
 					royalCards10[cardsToRoyal] = highCards[i];
 					cardsToRoyal++; 					
 				}
 			}
 			
 			if(cardsToRoyal == 3){
 				for(int i = 0; i < 3; i++){
 					if(royalCards10[i] != -1)
 						indexes[royalCards10[i]] = 1;
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
 			if(nJumps == 0 && first != 1){ //A234 is an inside straight
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
 				if(nJumps == 0 && first != 1){
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
 		if(nHighCards == 4){
 			indexes[highCards[1]]=1;
 			indexes[highCards[2]]=1;
 			indexes[highCards[3]]=1;
 			indexes[highCards[4]]=1;
 			
 			return indexes;
 		}		

 		/*****Play(14) - 3 to a straight flush (type 1)***********************/
 		if(maxSameSuit >= 3){
 			//straight can begin in the first, second or third card of the sortedhand
 			cardsToStraight = 0;
 			nGaps = 0;
 			nHighCardsAux = 0;
 			
 			for(first = 0; first < 3; first++){
 				if(cardsToStraight != 3){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				nGaps = 0;
	 				straightIndexes = new int[3];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfCard(sortedhand[first].rank + j, maxSuit) != -1){
	 						straightIndexes[cardsToStraight] = indexOfCard(sortedhand[first].rank + j, maxSuit);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						
	 						cardsToStraight++;
	 					}else
	 						nGaps++;
	 				}
 				}else
 					break;
 			}
 			
 			if(cardsToStraight == 3 && nHighCardsAux >= nGaps && sortedhand[first-1].rank != 1 && sortedhand[first-1].rank != 2){
 				for(int i = 0; i < 3; i++)
 					indexes[straightIndexes[i]] = 1;
 				
 				return indexes;
 			}
 		}
 		
 		/*****Play(15) - 4 to an inside straight with 3 high cards************/
 		if(nHighCards >= 3){
 			cardsToStraight = 0;
 			nHighCardsAux = 0;
 			
	 		for(int i = 0; i < 2; i++){
				if(cardsToStraight != 4){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				straightIndexes = new int[4];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfRank(sortedhand[i].rank + j) != -1){
	 						straightIndexes[cardsToStraight] = indexOfRank(sortedhand[i].rank + j);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						cardsToStraight++;
	 					}
	 				}
				}
			}
	 		
	 		if(cardsToStraight == 4 && nHighCardsAux ==3){
	 			for(int i = 0; i < 4; i++){
	 				indexes[straightIndexes[i]] = 1;
	 			}
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(16) - QJ suited********************************************/
 		//can only have one J and one Q, because if there are more than one, the advise would be to keep the pair
 		if(highCards[1] != -1 && highCards[2] != -1){
	 		if(this.hand[highCards[1]].suit == this.hand[highCards[2]].suit){
	 			indexes[highCards[1]] = 1;
	 			indexes[highCards[2]] = 1;
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(17) - 3 to a flush with 2 high cards***********************/
 		if(maxSameSuit == 3 && nHighCards >= 2){
 			nHighCardsAux = 0;
 			
 			for(int i = 0; i < 5; i++){
 				if(this.hand[i].suit == maxSuit){
 					if(this.hand[i].isHighCard()){
 						nHighCardsAux++;
 					}
 				}
 			}
 			if(nHighCardsAux == 2){
 				for(int i = 0; i < 5; i++){
 					if(this.hand[i].suit == maxSuit)
 						indexes[i] = 1;
 				}
 				return indexes;
 			}
 		}
 		
 		/*****Play(18) - 2 suited high cards**********************************/
 		if(nHighCards >= 2){
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
 		}
 		
 		/*****Play(19) - 4 to an inside straight with 2 high cards************/
 		if(nHighCards >= 2){
 			cardsToStraight = 0;
 			nHighCardsAux = 0;
 			
	 		for(int i = 0; i < 2; i++){
				if(cardsToStraight != 4){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				straightIndexes = new int[4];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfRank(sortedhand[i].rank + j) != -1){
	 						straightIndexes[cardsToStraight] = indexOfRank(sortedhand[i].rank + j);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						cardsToStraight++;
	 					}
	 				}
				}
			}
	 		
	 		if(cardsToStraight == 4 && nHighCardsAux == 2){
	 			for(int i = 0; i < 4; i++){
	 				indexes[straightIndexes[i]] = 1;
	 			}
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(20) - 3 to a straight flush (type 2)***********************/
 		if(maxSameSuit >= 3){
 			//straight can begin in the first, second or third card of the sortedhand
 			cardsToStraight = 0;
 			nGaps = 0;
 			nHighCardsAux = 0;
 			
 			for(first = 0; first < 3; first++){
 				if(cardsToStraight != 3){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				nGaps = 0;
	 				straightIndexes = new int[3];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfCard(sortedhand[first].rank + j, maxSuit) != -1){
	 						straightIndexes[cardsToStraight] = indexOfCard(sortedhand[first].rank + j, maxSuit);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						
	 						cardsToStraight++;
	 					}else
	 						nGaps++;
	 				}
 				}else
 					break;
 			}
 			
 			if(cardsToStraight == 3 && (nGaps == 1 || (nGaps == 2 && nHighCardsAux == 1) || (nGaps == 0 && (sortedhand[first-1].rank == 1 || sortedhand[first-1].rank == 2)))){
 				for(int i = 0; i < 3; i++)
 					indexes[straightIndexes[i]] = 1;
 				
 				return indexes;
 			}
 		}
 		
 		/*****Play(21) - 4 to an inside straight with 1 high card*************/
 		if(nHighCards >= 1){
 			cardsToStraight = 0;
 			nHighCardsAux = 0;
 			
	 		for(int i = 0; i < 2; i++){
				if(cardsToStraight != 4){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				straightIndexes = new int[4];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfRank(sortedhand[i].rank + j) != -1){
	 						straightIndexes[cardsToStraight] = indexOfRank(sortedhand[i].rank + j);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						cardsToStraight++;
	 					}
	 				}
				}
			}
	 		
	 		if(cardsToStraight == 4 && nHighCardsAux == 1){
	 			for(int i = 0; i < 4; i++){
	 				indexes[straightIndexes[i]] = 1;
	 			}
	 			return indexes;
	 		}
 		}
 		 		
 		/*****Play(22) - KQJ unsuited*****************************************/
 		if(highCards[1] != -1 && highCards[2] != -1 && highCards[3] != -1){
 			indexes[highCards[1]]=1;
 			indexes[highCards[2]]=1;
 			indexes[highCards[3]]=1;
 			
 			return indexes;
 		}	
 		
 		/*****Play(23) - JT suited********************************************/
 		if(highCards[1] != -1 && highCards[0] != -1){
	 		if(this.hand[highCards[1]].suit == this.hand[highCards[0]].suit){
	 			indexes[highCards[1]] = 1;
	 			indexes[highCards[0]] = 1;
	 			
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(24) - QJ unsuited******************************************/
 		if(highCards[1] != -1 && highCards[2] != -1){
 			indexes[highCards[1]]=1;
 			indexes[highCards[2]]=1;
 			
 			return indexes;
 		}	
 		
 		/*****Play(25) - 3 to a flush with 1 high card************************/
 		if(maxSameSuit == 3 && nHighCards >= 1){
 			nHighCardsAux = 0;
 			
 			for(int i = 0; i < 5; i++){
 				if(this.hand[i].suit == maxSuit){
 					if(this.hand[i].isHighCard()){
 						nHighCardsAux++;
 					}
 				}
 			}
 			if(nHighCardsAux == 1){
 				for(int i = 0; i < 5; i++){
 					if(this.hand[i].suit == maxSuit)
 						indexes[i] = 1;
 				}
 				return indexes;
 			}
 		}
 		
 		/*****Play(26) - QT suited********************************************/
 		if(highCards[2] != -1 && highCards[0] != -1){
	 		if(this.hand[highCards[2]].suit == this.hand[highCards[0]].suit){
	 			indexes[highCards[2]] = 1;
	 			indexes[highCards[0]] = 1;
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(27) - 3 to a straight flush (type 3)***********************/
 		if(maxSameSuit >= 3){
 			//straight can begin in the first, second or third card of the sortedhand
 			cardsToStraight = 0;
 			nGaps = 0;
 			nHighCardsAux = 0;
 			
 			for(first = 0; first < 3; first++){
 				if(cardsToStraight != 3){
	 				cardsToStraight = 0;
	 				nHighCardsAux = 0;
	 				nGaps = 0;
	 				straightIndexes = new int[3];
	 				for(int j = 0; j < 5; j++){
	 					if(indexOfCard(sortedhand[first].rank + j, maxSuit) != -1){
	 						straightIndexes[cardsToStraight] = indexOfCard(sortedhand[first].rank + j, maxSuit);
	 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
	 							nHighCardsAux++;
	 						
	 						cardsToStraight++;
	 					}else
	 						nGaps++;
	 				}
 				}else
 					break;
 			}
 			
 			if(cardsToStraight == 3 && nGaps == 2 && nHighCardsAux == 0){
 				for(int i = 0; i < 3; i++)
 					indexes[straightIndexes[i]] = 1;
 				
 				return indexes;
 			}
 		}
 		
 		/*****Play(28) - KQ, KJ unsuited**************************************/
 		if(highCards[3] != -1){
 			if(highCards[2] != -1){
 				indexes[highCards[3]] = 1;
 				indexes[highCards[2]] = 1;
 				return indexes;
 			}
 			if(highCards[1] != -1){
 				indexes[highCards[3]] = 1;
 				indexes[highCards[1]] = 1;
 				return indexes;
 			}
 		}
 		
 		/*****Play(29) - Ace**************************************************/
 		if(highCards[4] != -1){
 			indexes[highCards[4]] = 1;
 			
 			return indexes;
 		}
 		
 		/*****Play(30) - KT suited********************************************/
 		if(highCards[0] != -1 && highCards[3] != -1){
	 		if(this.hand[highCards[0]].suit == this.hand[highCards[3]].suit){
	 			indexes[highCards[0]] = 1;
	 			indexes[highCards[3]] = 1;
	 			return indexes;
	 		}
 		}
 		
 		/*****Play(31) - Jack, Queen or King**********************************/
 		if(highCards[3] != -1){
 			indexes[highCards[3]] = 1;
 			
 			return indexes;
 		}
 		
 		if(highCards[2] != -1){
 			indexes[highCards[2]] = 1;
 			
 			return indexes;
 		}
 		
 		if(highCards[1] != -1){
 			indexes[highCards[1]] = 1;
 			
 			return indexes;
 		}
 		
 		/*****Play(32) - 4 to an inside straight with no high cards***********/
		cardsToStraight = 0;
		nHighCardsAux = 0;
		
 		for(int i = 0; i < 2; i++){
			if(cardsToStraight != 4){
 				cardsToStraight = 0;
 				nHighCardsAux = 0;
 				straightIndexes = new int[4];
 				for(int j = 0; j < 5; j++){
 					if(indexOfCard(sortedhand[i].rank + j, maxSuit) != -1){
 						straightIndexes[cardsToStraight] = indexOfCard(sortedhand[i].rank + j, maxSuit);
 						if(hand[straightIndexes[cardsToStraight]].isHighCard())
 							nHighCardsAux++;
 						cardsToStraight++;
 					}
 				}
			}
		}
 		
 		if(cardsToStraight == 4 && nHighCardsAux == 0){
 			for(int i = 0; i < 4; i++){
 				indexes[straightIndexes[i]] = 1;
 			}
 		}
 		
 		/*****Play(33) - 3 to a flush with no high cards**********************/
 		if(maxSameSuit == 3){
 			for(int i = 0; i < 5; i++){
 				if(this.hand[i].suit == maxSuit){
 					indexes[i] = 1;
 				}
 			}
 			return indexes;
 		}
 		
 		/*****Play(34) - Discard everything***********************************/
 		return indexes;
 		
 	}//end cardsToHold
 	
 	public String advise(){
 		String advise = "player should hold";
 		int[] cardsToHold = this.cardsToHold();
 		for(int i = 0; i < 5; i++){
 			if(cardsToHold[i] == 1)
 				advise = advise + " " + (i+1); 
 		}
 		return advise;
 	}
}
