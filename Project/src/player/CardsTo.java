package player;

// TODO: Auto-generated Javadoc
/**
 * The Class CardsTo.
 */
public abstract class CardsTo {
	
	/** The indexes. */
	public int[] indexes; //1 belongs; 0 dont belong
	
	/** The n cards to. */
	public int nCardsTo;
	
	/**
	 * Instantiates a new cards to.
	 */
	public CardsTo(){
		indexes = new int[5];
		nCardsTo = 0;
	}
	
	/**
	 * Compute.
	 *
	 * @param maxSameSuit the max same suit
	 * @param mostRepSuit the most rep suit
	 * @param hand the hand
	 */
	abstract public void compute(int maxSameSuit, int mostRepSuit, Hand hand);
}
