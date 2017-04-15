package VirtualPoker;

public class Card {
	
	int v;
	int n;	
	
	String Value;
	String Naipe;
	
	Card(){
		/*Passing the codified card to a symbol*/
		if(v==10)
			Value = "J";
		if(v==11)
			Value = "Q";
		if(v==12)
			Value = "K";
		if(v==13)
			Value = "A";
		
		/*Decoding the naipe from numbers to string*/
		
		if(n==1)
			Naipe="H";
		if(n==2)
			Naipe="D";
		if(n==3)
			Naipe="C";
		if(n==4)
			Naipe="S";
	}
}
