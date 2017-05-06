package player;

public class CardsToHighCards extends CardsTo{
	boolean royal;
	boolean AKQJunsuited;
	boolean QJsuited;
	boolean KQJunsuited;
	boolean JTsuited;
	boolean QJunsuited;
	boolean QTsuited;
	boolean KQ_KJunsuited;
	boolean Ace;
	boolean KTsuited;
	boolean K_Q_J;
	
	CardsToHighCards(){
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
	
	void compute(int maxSameSuit, int mostRepSuit, Hand hand){
		int[] highCards = new int[5]; //stores the position of each card of a royal flush.
		int nHighCards = 0;
		
 		highCards[0] = hand.indexOfRank(10);
 		highCards[1] = hand.indexOfRank(11);
 		highCards[2] = hand.indexOfRank(12);
 		highCards[3] = hand.indexOfRank(13);
 		highCards[4] = hand.indexOfRank(1);
 		
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
		
		if(nCardsTo >= 3){
			for(int i = 0; i < nCardsTo; i++){
				if(royalCards[i] != -1){
					this.indexes[royalCards[i]] = 1;
					this.royal = true;
				}
			}
			return;
		}

		//Check high cards
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
 		
 		if(nHighCards >= 2){
 			if(highCards[0] != -1 && highCards[1] != -1){
 				if(hand.hand[highCards[1]].suit == hand.hand[highCards[2]].suit){
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
 			if(highCards[3] != -1){
 				this.indexes[highCards[3]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 			if(highCards[2] != -1){
 				this.indexes[highCards[2]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 			if(highCards[3] != -1){
 				this.indexes[highCards[1]] = 1;
 				this.K_Q_J = true;
 				return;
 			}
 		}
		return;
	}
}
