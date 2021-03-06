package cards;

import java.util.Comparator;


/**
 * Class that implements Comparator, to compare objects of type Card and to be able to sort them.
 */

public class ComparatorByRank implements Comparator<Card> {
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Card c1, Card c2){
		if(c1.rank > c2.rank)
			return 1;
		else if(c1.rank == c2.rank)
			return 0;
		else
			return -1;
	}
}
