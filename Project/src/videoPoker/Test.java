package videoPoker;

import cards.Deck;
import player.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		//System.out.println(deck.cards);
		
		Deck deck2 = new Deck();
		//System.out.println(deck2.cards);
		
		System.out.println(deck2.draw().toString());
		//System.out.println(deck2.cards);
		Hand hand = new Hand(deck);
		System.out.println(hand.toString());
		
		//Test hold method
		int index[] = new int[5];
		index[0] = 1;
		index[1] = 3;
		index[2] = 0;
		index[3] = 0;
		index[4] = 0;
		hand.hold(index, deck2);
		System.out.println(hand.toString());
		
		//test player statistics
		/*Player dani = new Player(1000);
		dani.statistics(1000);*/
		
	}

}
