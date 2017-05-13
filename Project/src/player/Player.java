package player;

/**
 * Player 
 */
public class Player {
	
	/** Player's current credit. */
	private int credit;
	
	/** Player's initial credit. */
	private final int initialCredit;
	
	/** Player's last bet (default = 5). */
	private int lastBet = 5;
	
	/** Player's hand. */
	public Hand hand;
	
	/** Player's statistics. */
	public int statistics[];
	
	/** Number of hands played by the player. */
	public int handsPlayed;
	
	/**
	 * Contructor.
	 *
	 * @param credit Player's initial credit.
	 * @param nWinningHands Number of hands that return credits (may differ
	 * between game modes.
	 */
	public Player(int credit, int nWinningHands){
		this.credit = credit;
		this.initialCredit = credit;
		this.statistics = new int[nWinningHands+1];
	}
	
	/**
	 * Sets the player's last bet.
	 *
	 * @param bet New bet
	 */
	public void setLastBet(int bet){
		this.lastBet = bet;
		return;
	}

	/**
	 * Sets the plauer's credit.
	 *
	 * @param credit New credit
	 */
	public void setCredit(int credit){
		this.credit = credit;
		return;
	}
	
	/**
	 * Increments statistics.
	 *
	 * @param score Score of the hand of the player (from handScore)
	 */
	public void incStatistics(int score){
		this.statistics[score]++;
		return;
	}
	
	/**
	 * Increments hands played.
	 */
	public void incHandsPlayed(){
		this.handsPlayed += 1;
	}
	
	
	/**
	 * Gets the player's last bet.
	 *
	 * @return Player's last bet
	 */
	public int getLastBet(){
		return this.lastBet;
	}
	
	/**
	 * Gets the player's current credit.
	 *
	 * @return Player's current credit
	 */
	public int getCredit(){
		return this.credit;
	}
	
	/**
	 * Gets the player's initial credit.
	 *
	 * @return Player's initial credit
	 */
	public int getInitialCredit(){
		return this.initialCredit;
	}
}
