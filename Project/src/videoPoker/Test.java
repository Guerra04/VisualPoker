package videoPoker;

import cards.*;
import player.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Deck deck = new Deck();
		//System.out.println(deck.cards);
		
		//Deck deck2 = new Deck();
		//System.out.println(deck2.cards);
		
		//System.out.println(deck2.draw().toString());
		//System.out.println(deck2.cards);
		//Hand hand = new Hand(deck);
		//System.out.println(hand.toString());
		
		//Test hold method
		//int index[] = new int[5];
		//index[0] = 1;
		//index[1] = 3;
		//index[2] = 0;
		//index[3] = 0;
		//index[4] = 0;
		//hand.hold(index, deck2);
		//System.out.println(hand.toString());
		
		//test player statistics
		/*Player dani = new Player(1000);
		dani.statistics(1000);*/
		
		//43
		Hand hand = new Hand(deck);
		Card card1 = new Card(1, 1);
		hand.hand[0] = card1;
		Card card2 = new Card(2, 2);
		hand.hand[1] = card2; 
		Card card3 = new Card(4, 2);
		hand.hand[2] = card3; 
		Card card4 = new Card(5, 2);
		hand.hand[3] = card4; 
		Card card5 = new Card(7, 3);
		hand.hand[4] = card5; 
		
		System.out.println(hand.toString());
		System.out.println(hand.handScore());
		System.out.println(hand.advise());
		
		
	}

}
