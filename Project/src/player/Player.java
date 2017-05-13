package player;

// TODO: Auto-generated Javadoc
/**
 * The Class Player.
 */
public class Player {
	
	/** The credit. */
	private int credit;
	
	/** The initial credit. */
	private final int initialCredit;
	
	/** The last bet. */
	private int lastBet = 5;
	
	/** The hand. */
	public Hand hand;
	
	/** The statistics. */
	public int statistics[];
	
	/** The hands played. */
	public int handsPlayed;
	
	/**
	 * Instantiates a new player.
	 *
	 * @param credit the credit
	 * @param nWinningHands the n winning hands
	 */
	//Contructor
	public Player(int credit, int nWinningHands){
		this.credit = credit;
		this.initialCredit = credit;
		this.statistics = new int[nWinningHands+1];
	}
	
	/**
	 * Sets the last bet.
	 *
	 * @param bet the new last bet
	 */
	//Setters
	public void setLastBet(int bet){
		this.lastBet = bet;
		return;
	}

	/**
	 * Sets the credit.
	 *
	 * @param credit the new credit
	 */
	public void setCredit(int credit){
		this.credit = credit;
		return;
	}
	
	/**
	 * Inc statistics.
	 *
	 * @param score the score
	 */
	public void incStatistics(int score){
		this.statistics[score]++;
		return;
	}
	
	/**
	 * Inc hands played.
	 */
	public void incHandsPlayed(){
		this.handsPlayed += 1;
	}
	
	
	/**
	 * Gets the last bet.
	 *
	 * @return the last bet
	 */
	//Getters
	public int getLastBet(){
		return this.lastBet;
	}
	
	/**
	 * Gets the credit.
	 *
	 * @return the credit
	 */
	public int getCredit(){
		return this.credit;
	}
	
	/**
	 * Gets the initial credit.
	 *
	 * @return the initial credit
	 */
	public int getInitialCredit(){
		return this.initialCredit;
	}
}
