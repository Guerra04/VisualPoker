package player;

public class CardsToFlush extends CardsTo{
	public int nHighCards;
	
	public CardsToFlush(){
		super();
		nHighCards = 0;
	}
	
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
