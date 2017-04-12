package VirtualPoker;

public class Deck {
	
	int p; 								//Position variable to initialize deck;   								
	
	Card[] deck = new Card[51] ; 
	
	Deck(){
		for(int n=1; n<=4; n++){ 		//Defining a cards naipe: 1=H, 2=D, 3=C, 4=S .
			
			for(int v=1; v<13;v++){     //Defining the value of the card: 2-10, 11=J, 12=Q, 13=K, 14=A
				int p = ((n*v)-1);
				
				deck[p].n = n;
				deck[p].v = v+1;
				
			}
		}
	
	}
	
}