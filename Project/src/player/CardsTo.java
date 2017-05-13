package player;

/**
 * Abstract Class to be extended by classes that compute the number
 * of cards to a specific hand.
 */
public abstract class CardsTo {
	
	/** Indicator of the cards that belong to the specific hand. */
	public int[] indexes; //1 belongs; 0 dont belong
	
	/** Number of cards to the specific hand. */
	public int nCardsTo;
	
	/**
	 * Constructor
	 */
	public CardsTo(){
		indexes = new int[5];
		nCardsTo = 0;
	}
	
	/**
	 * Computes the number of cards to the specific hand.
	 *
	 * @param maxSameSuit Array with 5 positions. Each position has the number
	 * of cards with the same suit as the card in that position.
	 * @param mostRepSuit Most represented suit.
	 * @param hand Player's hand.
	 */
	abstract public void compute(int maxSameSuit, int mostRepSuit, Hand hand);
}
