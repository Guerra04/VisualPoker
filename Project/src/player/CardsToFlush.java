package player;

// TODO: Auto-generated Javadoc
/**
 * CardsToFlush.
 */
public class CardsToFlush extends CardsTo{
	
	/** The n high cards. */
	public int nHighCards;
	
	/**
	 * Instantiates a new cards to flush.
	 */
	public CardsToFlush(){
		super();
		nHighCards = 0;
	}
	
	/* (non-Javadoc)
	 * @see player.CardsTo#compute(int, int, player.Hand)
	 */
	public void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		if(maxSameSuit == 4){
			for(int i = 0; i < 5; i++){
				if(hand.hand[i].suit == mostRepSuit)
					this.indexes[i] = 1;
			}
			this.nCardsTo = 4;
			return;
		}
		if(maxSameSuit == 3){
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
