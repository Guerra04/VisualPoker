package player;

/**
 * The Class Player.
 */
public class Player {
	private int credit;
	private final int initialCredit;
	private int lastBet = 5;
	public Hand hand;
	public int statistics[];
	public int handsPlayed;
	
	//Contructor
	public Player(int credit, int nWinningHands){
		this.credit = credit;
		this.initialCredit = credit;
		this.statistics = new int[nWinningHands+1];
	}
	
	//Setters
	public void setLastBet(int bet){
		this.lastBet = bet;
		return;
	}

	public void setCredit(int credit){
		this.credit = credit;
		return;
	}
	
	public void incStatistics(int score){
		this.statistics[score]++;
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
	
	public int getInitialCredit(){
		return this.initialCredit;
	}
}
