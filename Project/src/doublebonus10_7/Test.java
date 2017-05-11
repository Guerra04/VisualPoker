package doublebonus10_7;

import java.io.BufferedReader;
import java.io.FileReader;
import cards.*;
import player.*;
import doublebonus10_7.*;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BufferedReader br = null;
		try{
			br = new BufferedReader(new FileReader("TESTS/cardtestadvisor.txt"));
			DoubleBonus10_7 game = new DoubleBonus10_7();
			Player player = new Player(100000, 11);
			Deck deck = new Deck();
			player.hand = new Hand(deck);
			String line;
			for(int i = 0; i < 81; i++){
				line = br.readLine();
				player.hand.hand[0] = new Card(Card.stringToRank(line.charAt(0)), Card.stringToSuit(line.charAt(1)));
				player.hand.hand[1] = new Card(Card.stringToRank(line.charAt(3)), Card.stringToSuit(line.charAt(4)));
				player.hand.hand[2] = new Card(Card.stringToRank(line.charAt(6)), Card.stringToSuit(line.charAt(7)));
				player.hand.hand[3] = new Card(Card.stringToRank(line.charAt(9)), Card.stringToSuit(line.charAt(10)));
				player.hand.hand[4] = new Card(Card.stringToRank(line.charAt(12)), Card.stringToSuit(line.charAt(13)));
				System.out.println(player.hand);

				
				System.out.println(game.advise(player) + "\n");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
