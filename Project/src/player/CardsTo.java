package player;

public abstract class CardsTo {
	int[] indexes; //1 belongs; 0 dont belong
	int nCardsTo;
	
	public CardsTo(){
		indexes = new int[5];
		nCardsTo = 0;
	}
	abstract void compute(int maxSameSuit, int mostRepSuit, Hand hand);
}
