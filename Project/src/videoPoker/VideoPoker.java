package videoPoker;

import cards.Deck;
import player.Hand;
import player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class VideoPoker.
 */
public abstract class VideoPoker {
	
	/** The reward table. */
	protected int[][] rewardTable;
	
	/** The deck. */
	protected Deck deck;
	
	/** The Constant INITIATING. */
	//States
	protected final static int INITIATING = 0;
	
	/** The Constant BETTING. */
	protected final static int BETTING = 1;
	
	/** The Constant DEALING. */
	protected final static int DEALING = 2;
	
	/** The Constant QUITTING. */
	protected final static int QUITTING = 3;
	
	/** The state. */
	protected int state = INITIATING ;

	/**
	 * Reward.
	 *
	 * @param score the score
	 * @param bet the bet
	 * @return the int
	 */
	protected int reward(int score, int bet){
		return rewardTable[score][bet];
	}
	
	/**
	 * Fill reward table.
	 *
	 * @return the int[][]
	 */
	protected abstract int[][] fillRewardTable();
	
	/**
	 * Statistics.
	 *
	 * @param player the player
	 */
	protected abstract void statistics(Player player);
	
	/**
	 * Hand score.
	 *
	 * @param player the player
	 * @return the int
	 */
	protected abstract int handScore(Player player);
	
	/**
	 * Score to string.
	 *
	 * @param score the score
	 * @return the string
	 */
	protected abstract String scoreToString(int score);
	
	/**
	 * Cards to hold.
	 *
	 * @param player the player
	 * @return the int[]
	 */
	protected abstract int[] cardsToHold(Player player);
	
	/**
	 * Interactive mode.
	 *
	 * @param initialCredit the initial credit
	 */
	public abstract void interactiveMode(int initialCredit);
	
	/**
	 * Debug mode.
	 *
	 * @param initialCredit the initial credit
	 * @param cmd_file the cmd file
	 * @param card_file the card file
	 */
	public abstract void debugMode(int initialCredit, String cmd_file, String card_file);
	
	/**
	 * Simulation mode.
	 *
	 * @param initialCredit the initial credit
	 * @param bet the bet
	 * @param nbdeals the nbdeals
	 */
	public abstract void simulationMode(int initialCredit, int bet, int nbdeals);

	/**
	 * Gets the deck.
	 *
	 * @return the deck
	 */
	//Deck Getter
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	//State getter and setter
	public int getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state the new state
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Bet.
	 *
	 * @param player the player
	 * @param bet the bet
	 */
	public void bet(Player player, int bet){
		if(state == INITIATING || state == BETTING){
				player.setLastBet(bet);
				System.out.println("player is betting " + player.getLastBet());
				state = BETTING;
		}else
			this.illegalCommand('b');
		return;
	}
	
	/**
	 * Shuffle.
	 */
	public void shuffle(){
		this.deck = new Deck();
	}
	
	/**
	 * Deal.
	 *
	 * @param player the player
	 */
	public void deal(Player player){
		if(state == BETTING){
			player.setCredit(player.getCredit()-player.getLastBet());
			player.hand = new Hand(deck);
			System.out.println(player.hand);
			state = DEALING;
		}else{
			this.illegalCommand('d');
		}
		return;
	}
	
	/**
	 * Hold and results.
	 *
	 * @param player the player
	 * @param indexes the indexes
	 * @param deck the deck
	 */
	protected void holdAndResults(Player player, int[] indexes, Deck deck){
		if(state == DEALING){
			player.hand.hold(indexes, deck);
			System.out.println(player.hand);
			
			int score = this.handScore(player);
			player.setCredit(player.getCredit() + reward(score, player.getLastBet()));
			if(score == 0)
				System.out.println("Player lost and his credit is " + player.getCredit());
			else
				System.out.println("Player wins with a " + this.scoreToString(score) 
					+ " and his credit is " + player.getCredit());
			
			player.incStatistics(score);
			player.incHandsPlayed();
			state = BETTING;
		}else
			this.illegalCommand('h');
		
		if(player.getCredit() == 0){
			System.out.println("GAME OVER");
			System.exit(3);
		}
		return;
	}
	
	/**
	 * Credit.
	 *
	 * @param player the player
	 */
	protected void credit(Player player){
		System.out.println("player's credit is " + player.getCredit());
		return;
	}
	
	/**
	 * Gets the advise.
	 *
	 * @param player the player
	 * @return the advise
	 */
	protected void getAdvise(Player player){
		if(state == DEALING){
			System.out.println(this.advise(player));
		}else
			this.illegalCommand('a');
		return;
	}
	
	/**
	 * Advise.
	 *
	 * @param player the player
	 * @return the string
	 */
	public String advise(Player player){
 		String adviseString = "player should hold cards";
 		int[] cardsToHold = this.cardsToHold(player);
 		if(cardsToHold[0] == -1){
 			return "player should discard everything\n";
 		}
 		for(int i = 0; i < 5; i++){
 			if(cardsToHold[i] == 1)
 				adviseString = adviseString + " " + (i+1); 
 		}

 		return adviseString;
 	}
	
	/**
	 * Gets the statistics.
	 *
	 * @param player the player
	 * @return the statistics
	 */
	protected void getStatistics(Player player){
		this.statistics(player);
		return;
	}
	
	/**
	 * Quit.
	 *
	 * @param player the player
	 */
	protected void quit(Player player){
		if(state == INITIATING || state == BETTING){
			System.out.println("Exiting the game");
			this.statistics(player);
			state = QUITTING;
		}else
			this.illegalCommand('q');
		return;
	}
	
	/**
	 * Illegal command.
	 *
	 * @param command the command
	 */
	protected void illegalCommand(char command){
		System.out.println(command + ": illegal command");
		return;
	}
}
