package player;

import cards.Card;

/**
 * The Class CardsToStraightFlush.
 */
public class CardsToStraightFlush extends CardsTo{
	public int type;
	public int nHighCards;
	
	public CardsToStraightFlush(){
		super();
		type = 4; //to facilitate conditions
	}
	
	public void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		//in sorted hand, two first cards can start the candidate straight		
		Card[] sortedHand = hand.handSort();
		int typeAux;
		int nHighCardsAux;
		int nCardsToAux;
		int nGaps;
		int sinceLast;
		int[] straightIndexes;
		int first;
		
		for(int i = 0; i < 3; i++){
			nGaps = 0;
			nHighCardsAux = 0;
			nCardsToAux = 0;
			straightIndexes = new int[4];
			sinceLast = 0;
			if(sortedHand[i].suit == mostRepSuit){
				first = sortedHand[i].rank;
				for(int j = 0; j < 5; j++){
					if(hand.indexOfCard(first + j, mostRepSuit) != -1){
						straightIndexes[nCardsToAux] = hand.indexOfCard(first + j, mostRepSuit);
						if(hand.hand[straightIndexes[nCardsToAux]].isHighCard())
							nHighCardsAux++;
						nCardsToAux++;
						nGaps += sinceLast;
						sinceLast = 0;
					}else{
						sinceLast++;
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
