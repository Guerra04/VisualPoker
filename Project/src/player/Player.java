package player;

public class Player {
	private int credit;
	private int lastBet = 5;
	public Hand hand;
	private int statistics[] = new int[10];
	private int handsPlayed;
	
	//Contructor
	public Player(int credit){
		this.credit = credit;
	}
	
	//Setters
	public void setLastBet(int bet){
		if(bet < 5)
			this.lastBet = 5;
		else
			System.out.println("b: illegal amount");
		return;
	}
	
	public void setCredit(int credit){
		this.credit = credit;
		return;
	}
	
	public void incStatistics(int score){
		switch(score){
		case(1):
 			statistics[8]++;
			break;
 		case(2):
 			statistics[6]++;
 			break;
 		case(3):
 			statistics[6]++;
			break;
 		case(4):
 			statistics[6]++;
			break;
 		case(5):
 			statistics[7]++;
			break;
 		case(6):
 			statistics[5]++;
			break;
 		case(7):
 			statistics[4]++;
			break;
 		case(8):
 			statistics[3]++;
			break;
 		case(9):
 			statistics[2]++;
			break;
 		case(10):
 			statistics[1]++;
			break;
 		case(11):
 			statistics[0]++;
			break;
 		default:
 			statistics[9]++;
 			break;
 		}
		return;
	}
	
	public void incHandsPlayed(){
		this.handsPlayed += 1;
	}
	
	//Getters
	public int getLastBet(){
		return this.lastBet;
	}
	
	public int getCredit(){
		return this.credit;
	}
	
	//Statistics
	public void statistics(int initialCredit){
		System.out.printf("%-20s %-5s\n", "Hand", "Nb");
		System.out.printf("---------------------------\n");
		System.out.printf("%-20s %-5s\n", "Jacks or Better", String.valueOf(this.statistics[0]));
		System.out.printf("%-20s %-5s\n", "Two Pair", String.valueOf(this.statistics[1]));
		System.out.printf("%-20s %-5s\n", "Three of a Kind", String.valueOf(this.statistics[2]));
		System.out.printf("%-20s %-5s\n", "Straight", String.valueOf(this.statistics[3]));
		System.out.printf("%-20s %-5s\n", "Flush", String.valueOf(this.statistics[4]));
		System.out.printf("%-20s %-5s\n", "Full house", String.valueOf(this.statistics[5]));
		System.out.printf("%-20s %-5s\n", "Four of a Kind", String.valueOf(this.statistics[6]));
		System.out.printf("%-20s %-5s\n", "Straight Flush", String.valueOf(this.statistics[7]));
		System.out.printf("%-20s %-5s\n", "Royal Flush", String.valueOf(this.statistics[8]));
		System.out.printf("%-20s %-5s\n", "Other", String.valueOf(this.statistics[9]));
		System.out.printf("---------------------------\n");
		System.out.printf("%-20s %-5s\n", "Total", String.valueOf(this.handsPlayed));
		System.out.printf("---------------------------\n");
		System.out.printf("%-15s %s (%s)\n", "Credit", String.valueOf(this.credit), String.valueOf(((double)this.credit)/initialCredit*100));
		
	}
}
