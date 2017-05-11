package cards;

import java.util.Comparator;

//Comparator to sort a hand by the rank of the cards
public class ComparatorByRank implements Comparator<Card> {
	public int compare(Card c1, Card c2){
		if(c1.rank > c2.rank)
			return 1;
		else if(c1.rank == c2.rank)
			return 0;
		else
			return -1;
	}
}
