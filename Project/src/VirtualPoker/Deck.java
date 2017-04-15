package VirtualPoker;
import java.util.LinkedList;
import java.util.Random;


public class Deck {
	
	int index = 0; 								//Position variable to initialize deck;
	Random random = new Random();
	int total;
	
	
	LinkedList<Card> deck = new LinkedList();   //Linked list that will contain the shuffled deck
	
	Card card;
	
	Deck(){
		for(int n=1; n<=4; n++){ 		//Defining a cards naipe: 1=H, 2=D, 3=C, 4=S .
			
			for(int v=2; v<=13 ;v++){   //Defining the value of the card: 2-10, 10=J, 11=Q, 12=K, 13=A
				card.v = v;
				card.n = n;
				
				total = deck.size();	//Verification of the total size of the deck
				index = random.nextInt(total);//generation of a random position in the deck to place the card
				
				deck.add(index,card);	//Adding cards to the deck 		
			}
		}
	}
}