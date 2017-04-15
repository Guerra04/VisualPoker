package cards;
import java.util.LinkedList;
import java.util.Random;

public class Deck {								
	private LinkedList<Card> cards = new LinkedList<Card>();   //Linked list that will contain the shuffled deck
	
	Deck(){
		int total;
		Random random = new Random();
		int index; //Position variable to initialize deck;
		
		for(int s=1; s<=4; s++){ 		//Defining a cards naipe: 1=H, 2=D, 3=C, 4=S .
			for(int r=1; r<13 ;r++){   //Defining the value of the card: 2-10, 10=J, 11=Q, 12=K, 1=A
				Card card = new Card(r, s);
				
				total = cards.size();	//Verification of the total size of the deck
				index = random.nextInt(total); //Generation of a random position in the deck to place the card
				
				cards.add(index,card);	//Adding cards to the deck 		
			}
		}
	}
}