package player;

import cards.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Hand.
 */
public class Hand {
	
	/** The hand. */
	public Card[] hand = new Card[5];
	
	/**
	 * Instantiates a new hand.
	 *
	 * @param deck the deck
	 */
	//Constructor
	public Hand(Deck deck){
		for(int i = 0; i < 5; i++){
			hand[i] = deck.draw();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	//toString
	@Override
	public String toString() {
		String string = new String();
		for(int i = 0; i < 5; i++){
			string = string + hand[i].toString() + " ";
		}
		return ("player's hand is " + string);
	}
	
	/**
	 * Hold.
	 *
	 * @param index the index
	 * @param deck the deck
	 */
	public void hold(int index[], Deck deck){
		for(int i = 0; i < 5; i++){
			if(!contains(index, i+1)){
				hand[i] = deck.draw();
			}
		}
		return;
	}
	
	/**
	 * Contains.
	 *
	 * @param index the index
	 * @param toFind the to find
	 * @return true, if successful
	 */
	private boolean contains(int index[], int toFind){
		for(int i = 0; i < index.length; i++){
			if(index[i] == toFind)
				return true;
		}
		return false;
	}
	
	/**
	 * Checks if is same rank.
	 *
	 * @return the int[]
	 */
	public int[] isSameRank(){		//returns the number of times the rank of a card repeats itself in a hand
		int[] rankcount={1,1,1,1,1};
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(this.hand[i].rank == this.hand[j].rank){
					rankcount[i] ++;
					rankcount[j]++;
				}
			}
		}
		return rankcount;
	}
	
	/**
	 * Checks if is same suit.
	 *
	 * @return the int[]
	 */
	public int[] isSameSuit(){		//returns the number of times there is the same suit
		int[] samesuit = {1,1,1,1,1};
			
		for(int i=0; i<4; i++){
			for(int j=i+1; j<5; j++){
				if(this.hand[i].suit == this.hand[j].suit){
					samesuit[i]++;
					samesuit[j]++;
				}
			}
		}
		return samesuit;
	}
	
	/**
	 * Hand sort.
	 *
	 * @return the card[]
	 */
	public Card[] handSort(){
		Card[] sortedhand = new Card[5];
		
		for(int i = 0; i < 5; i++){
			sortedhand[i] = new Card(this.hand[i].rank, this.hand[i].suit);
		}
		java.util.Arrays.sort(sortedhand, new ComparatorByRank());
		return sortedhand;
	}
	
	/**
	 * Index of rank.
	 *
	 * @param rank the rank
	 * @return the int
	 */
	//returns -1 if does not exist
	protected int indexOfRank(int rank){
		int index = -1;
		for(int i = 0; i < 5; i++){
			if(this.hand[i].rank == rank){
				index = i;
				break;
			}
		}
		return index;
	}
	
	/**
	 * Index of card.
	 *
	 * @param rank the rank
	 * @param suit the suit
	 * @return the int
	 */
	//returns -1 if does not exist
	protected int indexOfCard(int rank, int suit){
		int index = -1;
		Card card = new Card(rank, suit);
		for(int i = 0; i < 5; i++){
			if(this.hand[i].equals(card)){
				index = i;
				break;
			}
		}
		return index;
	}
}
