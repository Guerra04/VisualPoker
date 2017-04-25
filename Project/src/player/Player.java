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
		this.lastBet = 5;
		return;
	}
	
	public void setCredit(int credit){
		this.credit = credit;
		return;
	}
	
	public void incStatistics(int index){
		this.statistics[index] += 1;
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
		System.out.printf("%-15s %s (%s)\n", "Credit", String.valueOf(this.credit), String.valueOf(this.credit/initialCredit*100));
		
	}
}
