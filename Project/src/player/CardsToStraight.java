package player;

import cards.Card;

public class CardsToStraight extends CardsTo{
	int nHighCards;
	boolean outside;
	
	CardsToStraight(){
		super();
		nHighCards = 0;
		outside = false;
	}
	
	void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		//in sorted hand, three first cards can start the candidate straight		
		Card[] sortedHand = hand.handSort();
		boolean outsideAux;
		int nHighCardsAux;
		int nCardsToAux;
		int[] straightIndexes;
		int first;
		
		if(nHighCards < 3){ //only 4 to straights matter so cant be any three of a kind in the hand
			for(int i = 0; i < 3; i++){
				outsideAux = true;
				nHighCardsAux = 0;
				nCardsToAux = 0;
				straightIndexes = new int[4];
				
				first = sortedHand[i].rank;
				for(int j = 0; j < 5; j++){
					if(hand.indexOfRank(first + j) != -1){
						straightIndexes[nCardsToAux] = hand.indexOfRank(first + j);
						if(hand.hand[straightIndexes[nCardsToAux]].isHighCard())
							nHighCardsAux++;
						nCardsToAux++;
					}else{
						if(j != 4 && first != 10 && first != 11)
							outsideAux = false;
					}
					if(first == 10 || first == 11){ //sequences starting in 10 or J jave to take into account if exists an Ace
						if(hand.indexOfRank(1) != -1){
							straightIndexes[nCardsToAux] = hand.indexOfRank(1);
							nHighCardsAux++;
							if(first == 10)
								outsideAux = false;
						}else{
							outsideAux = false;
						}
					}
				}
				if(nCardsToAux == 4){ //straight just matter if there are 4 cards
					if(outsideAux){
						if(!this.outside || (this.outside && nHighCardsAux > this.nHighCards))
						this.nCardsTo = nCardsToAux;
						this.nHighCards = nHighCardsAux;
						for(int j = 0; j < nCardsToAux; j++)
							this.indexes[straightIndexes[j]] = 1;
						
					}else{
						if(!this.outside && nHighCardsAux > this.nHighCards){
							this.nCardsTo = nCardsToAux;
							this.nHighCards = nHighCardsAux;
							for(int j = 0; j < nCardsToAux; j++)
								this.indexes[straightIndexes[j]] = 1;
						}
					}
				}
			}
		}
		return;
	}
}
