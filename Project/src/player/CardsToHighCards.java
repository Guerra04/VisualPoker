package player;

/**
 * Stores the number of cards to a royal flush or if there is a
 * a combination of high cards and the indexes of those cards
 */
public class CardsToHighCards extends CardsTo{
	
	/** Rotal Flush. */
	public boolean royal;
	
	/** AKQJ unsuited. */
	public boolean AKQJunsuited;
	
	/** QJ suited. */
	public boolean QJsuited;
	
	/** KQJ unsuited. */
	public boolean KQJunsuited;
	
	/** JT suited. */
	public boolean JTsuited;
	
	/** QJ unsuited. */
	public boolean QJunsuited;
	
	/** QT suited. */
	public boolean QTsuited;
	
	/** KQ or KJ unsuited. */
	public boolean KQ_KJunsuited;
	
	/** Ace. */
	public boolean Ace;
	
	/** KT suited. */
	public boolean KTsuited;
	
	/** K, Q or J. */
	public boolean K_Q_J;
	
	/**
	 * Constructor.
	 */
	public CardsToHighCards(){
		royal = false;
		AKQJunsuited = false;
		QJsuited = false;
		KQJunsuited = false;
		JTsuited = false;
		QJunsuited = false;
		QTsuited = false;
		KQ_KJunsuited = false;
		Ace = false;
		KTsuited = false;
		K_Q_J = false;
	}
	
	/* (non-Javadoc)
	 * @see player.CardsTo#compute(int, int, player.Hand)
	 */
	public void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		int[] highCards = new int[5]; //stores the position of each card of a royal flush.
		int nHighCards = 0;
		
		//Indexes of the high cards
 		highCards[0] = hand.indexOfRank(10); //Ten
 		highCards[1] = hand.indexOfRank(11); //Jack
 		highCards[2] = hand.indexOfRank(12); //Queen
 		highCards[3] = hand.indexOfRank(13); //King
 		highCards[4] = hand.indexOfRank(1);  //Ace
 		
 		for(int i = 1; i < 5; i++){
 			if(highCards[i] != -1)
 				nHighCards++;
 		}
 		
 		//Check candidates to royal flush
		int[] royalCards = {-1, -1, -1, -1};
		for(int i = 0; i < 5; i++){
			if(highCards[i] != -1 && hand.hand[highCards[i]].suit == mostRepSuit){
				royalCards[this.nCardsTo] = highCards[i];
				this.nCardsTo++; 					
			}
		}
		
		//If there are more than 3 cards to a royal flush
		if(nCardsTo >= 3){
			for(int i = 0; i < nCardsTo; i++){
				if(royalCards[i] != -1){
					this.indexes[royalCards[i]] = 1;
					this.royal = true;
				}
			}
			return;
		}

		//Check combination of high cards
 		if(nHighCards >= 4){
 			if(highCards[1] != -1 && highCards[2] != -1 && highCards[3] != -1 && highCards[4] != -1){
 				this.AKQJunsuited = true;
 				this.indexes[highCards[1]] = 1;
 				this.indexes[highCards[2]] = 1;
 				this.indexes[highCards[3]] = 1;
 				this.indexes[highCards[4]] = 1;
 				return;
 			}
 		}
 		
 		if(nHighCards >= 2){
 			if(highCards[1] != -1 && highCards[2] != -1){
 				if(hand.hand[highCards[1]].suit == hand.hand[highCards[2]].suit){
 					this.indexes[highCards[1]] = 1;
 					this.indexes[highCards[2]] = 1;
 					this.QJsuited = true;
 					return;
 				}else
 					this.QJunsuited = true;
 			}
 		}
 		
 		if(nHighCards >= 3){
 			if(highCards[1] != -1 && highCards[2] != -1 && highCards[3] != -1){
 				this.indexes[highCards[1]] = 1;
 				this.indexes[highCards[2]] = 1;
 				this.indexes[highCards[3]] = 1;
 				this.KQJunsuited = true;
 				return;
 			}
 		}
 		
 		if(nHighCards >= 1){
 			if(highCards[0] != -1 && highCards[1] != -1){
 				if(hand.hand[highCards[0]].suit == hand.hand[highCards[1]].suit){
 					this.indexes[highCards[0]] = 1;
 	 				this.indexes[highCards[1]] = 1;
 					this.JTsuited = true;
 					return;
 				}
 			}
 			if(this.QJunsuited){
 				this.indexes[highCards[1]] = 1;
				this.indexes[highCards[2]] = 1;
				return;
 			}
 			if(highCards[0] != -1 && highCards[2] != -1){
 				if(hand.hand[highCards[0]].suit == hand.hand[highCards[2]].suit){
 					this.indexes[highCards[0]] = 1;
 	 				this.indexes[highCards[2]] = 1;
 					this.QTsuited = true;
 					return;
 				}
 			}
 			if(highCards[3] != -1){
 				if(highCards[2] != -1){
 					this.indexes[highCards[2]] = 1;
 	 				this.indexes[highCards[3]] = 1;
 	 				this.KQ_KJunsuited = true;
 	 				return;
 				}
 				if(highCards[1] != -1){
 					this.indexes[highCards[1]] = 1;
 	 				this.indexes[highCards[3]] = 1;
 	 				this.KQ_KJunsuited = true;
 	 				return;
 				}
 			}
 			if(highCards[0] != -1 && highCards[3] != -1){
 				if(hand.hand[highCards[0]].suit == hand.hand[highCards[3]].suit){
 					this.KTsuited = true;
 				}
 			}
 		}
 		
 		if(nHighCards >= 1){
 			if(highCards[4] != -1){
 				this.indexes[highCards[4]] = 1;
 				this.Ace = true;
 				return;
 			}
 		}
 		
 		if(this.KTsuited){
 			this.indexes[highCards[0]] = 1;
			this.indexes[highCards[3]] = 1;
			return;
 		}
 		
 		if(nHighCards >= 1){
 			if(highCards[1] != -1){
 				this.indexes[highCards[1]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 			if(highCards[2] != -1){
 				this.indexes[highCards[2]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 			if(highCards[3] != -1){
 				this.indexes[highCards[3]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 		}
		return;
	}
}
