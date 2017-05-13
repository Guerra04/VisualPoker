package player;

/**
 * Stores the number of cards to a flush, the indexes of those cards
 * and the number of high cards among those cards.
 */
public class CardsToFlush extends CardsTo{
	
	/** Number of high cards */
	public int nHighCards;
	
	/**
	 * Contructor
	 */
	public CardsToFlush(){
		super();
		nHighCards = 0;
	}
	
	/* (non-Javadoc)
	 * @see player.CardsTo#compute(int, int, player.Hand)
	 */
	public void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		if(maxSameSuit == 4){ //Check if there are 4 cards of the same suit
			for(int i = 0; i < 5; i++){
				if(hand.hand[i].suit == mostRepSuit)
					this.indexes[i] = 1;
			}
			this.nCardsTo = 4;
			return;
		}
		if(maxSameSuit == 3){ //Check if there are 3 cards of the same suit
			for(int i = 0; i < 5; i++){
				if(hand.hand[i].suit == mostRepSuit){
					this.indexes[i] = 1;
					if(hand.hand[i].isHighCard())
						this.nHighCards++;
				}
			}
			this.nCardsTo = 3;
			return;
		}
		return;
	}
}
