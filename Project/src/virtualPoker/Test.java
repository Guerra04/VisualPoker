package virtualPoker;

import cards.Deck;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		System.out.println(deck.cards);
		
		Deck deck2 = new Deck();
		System.out.println(deck2.cards);
		
		System.out.println(deck2.draw(deck2).toString());
		System.out.println(deck2.cards);
	}

}
