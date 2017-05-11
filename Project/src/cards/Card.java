package cards;

/*-----------------------------------------------------------------------------
 *Ranks:
 *	11 = J
 *	12 = Q
 *	13 = K
 *	1 = A
 *
 *Suits:
 *	1 = H
 *	2 = D
 *	3 = C
 *	4 = S
 ------------------------------------------------------------------------------*/

/**
 * The Class Card.
 */
public class Card {
	public int rank;
	public int suit;
	
	//Ranks
	private final static int T = 10;
	private final static int J = 11;
	private final static int Q = 12;
	private final static int K = 13;
	private final static int A = 1;
	
	//Suits
	private final static int H = 1;
	private final static int D = 2;
	private final static int C = 3;
	private final static int S = 4;
	
	public Card(int rank, int suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	//Translates rank integer to string
	private String rankToString(int rank){
		if(rank == T)
			return "T";
		if(rank == J)
			return "J";
		if(rank == Q)
			return "Q";
		if(rank == K)
			return "K";
		if(rank == A)
			return "A";
		else
			return String.valueOf(rank);
	}
	
	//Translates string to rank integer
	public static int stringToRank(char rank){
		if(rank == 'T')
			return T;
		if(rank == 'J')
			return J;
		if(rank == 'Q')
			return Q;
		if(rank == 'K')
			return K;
		if(rank == 'A')
			return A;
		else
			return Character.getNumericValue(rank);
	}
	
	//Translates suit integer to string
	private String suitToString(int suit){
		if(suit == H)
			return "H";
		if(suit == D)
			return "D";
		if(suit == C)
			return "C";
		if(suit == S)
			return "S";
		
		return String.valueOf(suit);
	}
	
	//Translates string to rank integer
	public static int stringToSuit(char suit){
		if(suit == 'H')
			return H;
		if(suit == 'D')
			return D;
		if(suit == 'C')
			return C;
		if(suit == 'S')
			return S;
		
		return Character.getNumericValue(suit);
	}

	//toString method for card object
	@Override
	public String toString() {
		String rank = rankToString(this.rank);
		String suit = suitToString(this.suit);
		return rank + suit;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
	
	//Check if a card is a high card
	public boolean isHighCard(){
		if(this.rank > T || this.rank == A)
			return true;
		else
			return false;
	}
}
