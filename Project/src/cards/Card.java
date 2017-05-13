
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
 * Class to represent each card.
 */
public class Card {
	
	/** Rank of the card. */
	public int rank;
	
	/** Suit of the card. */
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
	
	/**
	 * Constructor.
	 *
	 * @param rank rank of the card
	 * @param suit suit of the card
	 */
	public Card(int rank, int suit){
		this.rank = rank;
		this.suit = suit;
	}
	
	/**
	 * Rank to string.
	 *
	 * @param rank rank of the card
	 * @return string
	 */
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
	
	/**
	 * String to rank.
	 *
	 * @param rank rank of the card
	 * @return int
	 */
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
	
	/**
	 * Suit to string.
	 *
	 * @param suit suit of the card
	 * @return string
	 */
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
	
	/**
	 * String to suit.
	 *
	 * @param suit suit of the card
	 * @return int
	 */
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	//toString method for card object
	@Override
	public String toString() {
		String rank = rankToString(this.rank);
		String suit = suitToString(this.suit);
		return rank + suit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
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
	
	/**
	 * Checks if is high card.
	 *
	 * @return true, if is high card
	 */
	//Check if a card is a high card
	public boolean isHighCard(){
		if(this.rank > T || this.rank == A)
			return true;
		else
			return false;
	}
}
