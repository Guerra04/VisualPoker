package doubleBonusTenSeven;

import doubleBonus.DoubleBonus;
import player.CardsToFlush;
import player.CardsToHighCards;
import player.CardsToStraight;
import player.CardsToStraightFlush;
import player.Player;

/**
 * The Class AdviseDoubleBonus10_7.
 */
public class AdviseDoubleBonusTenSeven {

	//return array with 5 positions corresponding to each card in the hand
	//if position = 1 it should be kept
	//if position = 0 is should be discarded
 	static int[] cardsToHold(Player player, DoubleBonus game){
 		
 		int[] indexes = {0,0,0,0,0};	//vector of cards to hold
 		
 		int score = game.handScore(player);	//Verifying the score of the hand
 		
 		int[] suits = player.hand.isSameSuit(); 
 		int maxSameSuit = 0;
 		int mostRepSuit = 0;
 		for(int i = 0; i < 5; i++){
 			if(suits[i] > maxSameSuit){
 				maxSameSuit = suits[i];
 				mostRepSuit = player.hand.hand[i].suit;
 			}
 		}
 		
 		int[] ranks = player.hand.isSameRank(); 
 		int maxSameRank = 0;
 		int mostRepRank = 0; //Rank that appears the most time
 		for(int i = 0; i < 5; i++){
 			if(ranks[i] > maxSameRank){
 				maxSameRank = ranks[i];
 				mostRepRank = player.hand.hand[i].rank;
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
 		highCards.compute(maxSameSuit, mostRepSuit, player.hand);
 		
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
 		straightFlush.compute(maxSameSuit, mostRepSuit, player.hand);
 		
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
 		flush.compute(maxSameSuit, mostRepSuit, player.hand);
 		
 		if(flush.nCardsTo == 4)
 			return flush.indexes;
 			
 		/*****Play(10) - 3 to a royal flush***********************************/
 		if(highCards.royal && highCards.nCardsTo == 3)
 			return highCards.indexes;
 		
 		/*****Play(11) - 4 to an outside straight*****************************/
 		CardsToStraight straight = new CardsToStraight();
 		straight.compute(maxSameSuit, mostRepSuit, player.hand);
 		
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
 			if(player.hand.hand[i].isHighCard() && suits[i] >= 2){
 				for(int j = i+1; j < 5; j++){
 					if(player.hand.hand[j].isHighCard() && player.hand.hand[i].suit == player.hand.hand[j].suit){
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
 			return highCards.indexes;
 		
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
}
