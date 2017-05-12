package cards;

import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

// TODO: Auto-generated Javadoc
/**
 * The Class Deck.
 */
public class Deck {								
	
	/** The cards. */
	private LinkedList<Card> cards = new LinkedList<Card>();   //Linked list that will contain the shuffled deck
	
	/**
	 * Instantiates a new deck.
	 */
	//Insert card by card in a random position among the other cards
	public Deck(){
		int total = 0;
		Random random = new Random();
		int index; //Position variable to initialize deck;
		
		for(int s=1; s<=4; s++){ 		//Defining a cards suit: 1=H, 2=D, 3=C, 4=S .
			for(int r=1; r<14 ;r++){   //Defining the value of the card: 2-9, 10=T, 11=J, 12=Q, 13=K, 1=A
				Card card = new Card(r, s);
				
				total = cards.size();	//Verification of the total size of the deck
				index = random.nextInt(total+1); //Generation of a random position in the deck to place the card
				
				cards.add(index,card);	//Adding cards to the deck 		
			}
		}
	}
	
	/**
	 * Instantiates a new deck.
	 *
	 * @param card_file the card file
	 */
	//Constructor for debugging mode
	public Deck(String card_file){
		BufferedReader br = null;
		FileReader fr = null;
		
		try{
			fr = new FileReader(card_file);
			br = new BufferedReader(fr);

			String line;
			
			line = br.readLine(); //file has only one line
			br.close();

			int i = 0;
			while(i < line.length()){
				if(line.charAt(i) == ' '){
					i++;
					continue;
				}
				
				int rank = Card.stringToRank(line.charAt(i));
				i++;
				int suit = Card.stringToSuit(line.charAt(i));
				i++;
				Card card = new Card(rank, suit);
				this.cards.addLast(card);
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Draw.
	 *
	 * @return the card
	 */
	public Card draw(){
		try{
			return this.cards.pop();
		}catch(Exception e){
			System.out.println("Deck is empty");
			System.exit(4);
			return this.cards.pop(); //dummy
		}
	}
}