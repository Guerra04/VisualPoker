package player;

import cards.Card;

/**
 * Stores the number of cards to a straight flush, their indexes, the type of 
 * straight flush draw and the number of high cards.
 */
public class CardsToStraightFlush extends CardsTo{
	
	/** Type of straight flush draw. */
	public int type;
	
	/** Number of high cards. */
	public int nHighCards;
	
	/**
	 * Constructor.
	 */
	public CardsToStraightFlush(){
		super();
		type = 4; //to facilitate conditions
	}
	
	/* (non-Javadoc)
	 * @see player.CardsTo#compute(int, int, player.Hand)
	 */
	public void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		//in sorted hand, three first cards can start the candidate straight flush	
		Card[] sortedHand = hand.handSort();
		int typeAux;
		int nHighCardsAux;
		int nCardsToAux;
		int nGaps;
		int sinceLast; /*Number of cards missing since the last card that belongs
					   to the possible straight draw*/
		int[] straightIndexes; //Stores the cards to a straightFlush
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
					/*Searches for the next cards to a straight beginning in the
					rank of the selected card*/
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
					//clean indexes in case there are 2 candidates to straight
					this.indexes = new int[5]; 
					for(int j = 0; j < nCardsToAux; j++)
						this.indexes[straightIndexes[j]] = 1;
					
				}else if(nCardsToAux == 3){
					//Check the type of straight flush draw
					if(nHighCardsAux >= nGaps && first != 1 && first != 2)
						typeAux = 1;
					else if(nGaps == 1 || (nGaps == 2 && nHighCardsAux == 1) 
							|| (nGaps == 0 && (first == 1 || first == 2)))
						typeAux = 2;
					else
						typeAux = 3;

					/*Save the cards to the straight flush if is better
					then the one already saved*/
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
