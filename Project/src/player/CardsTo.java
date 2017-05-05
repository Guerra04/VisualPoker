package player;

public abstract class CardsTo {
	int[] indexes; //1 belongs; 0 dont belong
	int nCardsTo;
	
	public CardsTo(){
		indexes = new int[4];
		nCardsTo = 0;
	}
	abstract void compute(int maxSameRank, int mostRepRank, int maxSameSuit, int mostRepSuit, int nHighCards, Hand hand);
}
