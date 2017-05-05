package player;

import cards.Card;

public class CardsToStraightFlush extends CardsTo{
	int type;
	int nHighCards;
	
	CardsToStraightFlush(){
		super();
		type = 4; //to facilitate conditions
	}
	
	void compute(int maxSameRank, int mostRepRank, int maxSameSuit, int mostRepSuit, int nHighCards, Hand hand){
		//in sorted hand, two first cards can start the candidate straight		
		Card[] sortedHand = hand.handSort();
		int typeAux;
		int nHighCardsAux;
		int nCardsToAux;
		int nGaps;
		int[] straightIndexes;
		int first;
		
		if(maxSameRank >= 3){
			for(int i = 0; i < 3; i++){
				nGaps = 0;
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
						nGaps++;
					}
				}
				
				if(nCardsToAux == 4){
					if(this.type != 0 || nHighCardsAux > this.nHighCards)
					this.type = 0;
					this.nCardsTo = nCardsToAux;
					this.nHighCards = nHighCardsAux;
					for(int j = 0; j < nCardsToAux; j++)
						this.indexes[straightIndexes[j]] = 1;
					
				}else if(nCardsToAux == 3){
					if(nHighCardsAux >= nGaps && first != 1 && first != 2)
						typeAux = 1;
					else if(nGaps == 1 || (nGaps == 2 && nHighCardsAux == 1) || (nGaps == 0 && (first == 1 || first == 2)))
						typeAux = 2;
					else
						typeAux = 3;
					
					if(typeAux < this.type){
						this.type = typeAux;
						this.nCardsTo = nCardsToAux;
						for(int j = 0; j < nCardsToAux; j++)
							this.indexes[straightIndexes[j]] = 1;
					}
				}
			}
		}
		return;
	}
}
