package player;

/**
 * The Class CardsTo.
 */
public abstract class CardsTo {
	public int[] indexes; //1 belongs; 0 dont belong
	public int nCardsTo;
	
	public CardsTo(){
		indexes = new int[5];
		nCardsTo = 0;
	}
	abstract public void compute(int maxSameSuit, int mostRepSuit, Hand hand);
}
