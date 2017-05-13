package player;

import cards.*;

/**
 * Player's hand.
 */
public class Hand {
	
	/** Array of cards that represent the player's hand. */
	public Card[] hand = new Card[5];
	
	/**
	 * Constructor.
	 *
	 * @param deck Deck from which the cards are drawn.
	 */
	public Hand(Deck deck){
		for(int i = 0; i < 5; i++){
			hand[i] = deck.draw();
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String string = new String();
		for(int i = 0; i < 5; i++){
			string = string + hand[i].toString() + " ";
		}
		return ("player's hand is " + string);
	}
	
	/**
	 * Draws cards to the positions that the player don't want to hold.
	 * Receives postions starting in 1 instead of 0.
	 *
	 * @param index Array with the indexes inputed by the player.
	 * @param deck Deck from which the cards are drawn.
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
	 * Check if the integer exists in an array
	 *
	 * @param index Array
	 * @param toFind Integer to find
	 * @return true, if toFind exists in index.
	 */
	private boolean contains(int index[], int toFind){
		for(int i = 0; i < index.length; i++){
			if(index[i] == toFind)
				return true;
		}
		return false;
	}
	
	/**
	 * Computes the number of cards with the same rank as the one in each
	 * position
	 *
	 * @return Array that in each position has the number of cards with 
	 * the same rank as the card in that position
	 */
	public int[] isSameRank(){
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
	 * Computes the number of cards with the same suit as the one in each
	 * position
	 *
	 * @return Array that in each position has the number of cards with 
	 * the same suit as the card in that position
	 */
	public int[] isSameSuit(){
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
	 * Copys the player's hand to a new hand and sorts and returns the new one
	 *
	 * @return Sorted hand
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
	 * Returns the index of the first card in the hand with the same rank as the
	 * integer argument.
	 *
	 * @param rank Rank of the card to find
	 * @return Index of the card. -1 if it doesn't exist.
	 */
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
	 * Returns the index of the first card in the hand with the same rank and
	 *  suit as the arguments.
	 *
	 * @param rank Rank of the card to find
	 * @param suit Suit of the card to find
	 * @return Index of the card. -1 if it doesn't exist.
	 */
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
