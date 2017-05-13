package videoPoker;

import cards.Deck;
import player.Hand;
import player.Player;

/**
 * Defines the needed methods to run the game and implements the common methods
 * to all the game modes.
 */
public abstract class VideoPoker {
	
	/** The reward table with the different winning hands in different rows and
	 * the possible bets in each column
	 */
	protected int[][] rewardTable;
	
	/** Deck being used. */
	protected Deck deck;
	
	/** State INITIATING. */
	protected final static int INITIATING = 0;
	
	/** State BETTING. */
	protected final static int BETTING = 1;
	
	/** State DEALING. */
	protected final static int DEALING = 2;
	
	/** State QUITTING. */
	protected final static int QUITTING = 3;
	
	/** Current state. */
	protected int state = INITIATING ;

	/**
	 * Returns the number credits won by the player in the current turn.
	 *
	 * @param score Score of the player's hand (from handScore).
	 * @param bet Bet used in the current turn of the game.
	 * @return Reward according to the score of the hand and the used bet.
	 */
	protected int reward(int score, int bet){
		return rewardTable[score][bet];
	}
	
	/**
	 * Constructs reward table.
	 *
	 * @return Reward table.
	 */
	protected abstract int[][] fillRewardTable();
	
	/**
	 * Prints the statistics of the player.
	 *
	 * @param player Player.
	 */
	protected abstract void statistics(Player player);
	
	/**
	 * Returns the score of the player's hand.
	 *
	 * @param player Player.
	 * @return Score of the player's hand.
	 */
	protected abstract int handScore(Player player);
	
	/**
	 * Translates the score represented by an integer to a string.
	 *
	 * @param score Score of the player's hand.
	 * @return String corresponding to the score.
	 */
	protected abstract String scoreToString(int score);
	
	/**
	 * Computes the cards that the player should hold given his hand.
	 *
	 * @param player Player
	 * @return Array representing what cards the player should hold. In each position
	 * a 1 means the card in that position should be held and a 0 means the opposite.
	 */
	protected abstract int[] cardsToHold(Player player);
	
	/**
	 * Method to play the game in interactive mode.
	 *
	 * @param initialCredit Player's initial credit.
	 */
	public abstract void interactiveMode(int initialCredit);
	
	/**
	 * Method to play the game in debug mode.
	 *
	 * @param initialCredit Player's initial credit.
	 * @param cmd_file File with sequence of commands.
	 * @param card_file File with sequence of cards representing the deck.
	 */
	public abstract void debugMode(int initialCredit, String cmd_file, String card_file);
	
	/**
	 * Method to play the game in simulation mode.
	 *
	 * @param initialCredit Player's initial credit.
	 * @param bet Bet to be used in all the turns of the game.
	 * @param nbdeals Number of turns to be played.
	 */
	public abstract void simulationMode(int initialCredit, int bet, int nbdeals);

	/**
	 * Gets the deck.
	 *
	 * @return Deck
	 */
	public Deck getDeck() {
		return deck;
	}
	
	/**
	 * Gets the state.
	 *
	 * @return Current state. 
	 */
	public int getState() {
		return state;
	}
	
	/**
	 * Sets the state.
	 *
	 * @param state New state.
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Checks if the bet command can be used in the current state and
	 * if it is possible sets the player's bet value to the parameter value.
	 *
	 * @param player Player.
	 * @param bet New bet value.
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
	 * Shuffles the deck by creating a new one
	 */
	public void shuffle(){
		this.deck = new Deck();
	}
	
	/**
	 * Checks if the deal command can be used in the current state and 
	 * if it is possible gives the player a new hand
	 * by drawing five cards from the deck.
	 *
	 * @param player Player.
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
	 * Checks if the hold command can be used in the current state and
	 * if it possible draws new cards for the positions the player
	 * didn't want to hold.
	 *
	 * @param player Player.
	 * @param indexes Array with the indexes to be hold, inputted by the player.
	 * @param deck Deck.
	 */
	protected void holdAndResults(Player player, int[] indexes, Deck deck){
		if(state == DEALING){
			player.hand.hold(indexes, deck); //Holds the pretended cards
			System.out.println(player.hand);
			
			int score = this.handScore(player); //checks the score
			//Increments the credits.
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
		
		//Check if the player reached 0 credits
		if(player.getCredit() == 0){
			System.out.println("GAME OVER");
			System.exit(3);
		}
		return;
	}
	
	/**
	 * Prints the player's current credit.
	 *
	 * @param player Player.
	 */
	protected void credit(Player player){
		System.out.println("player's credit is " + player.getCredit());
		return;
	}
	
	/**
	 * Checks if the advise command can be used in the current state
	 * and gets the advise according to the player's hand.
	 *
	 * @param player Player.
	 */
	protected void getAdvise(Player player){
		if(state == DEALING){
			System.out.println(this.advise(player));
		}else
			this.illegalCommand('a');
		return;
	}
	
	/**
	 * Prints the advise to be given to the player according
	 * to the player's hand.
	 *
	 * @param player Player
	 * @return Advise string.
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
	 * @param player Player.
	 */
	protected void getStatistics(Player player){
		this.statistics(player);
		return;
	}
	
	/**
	 * Checks if the quit command can be used in the current state,
	 * prints the player's statistic for the current game and terminates
	 * the program.
	 *
	 * @param player Player
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
	 * Prints the input command followed by ": illegal command".
	 *
	 * @param command Input Command.
	 */
	protected void illegalCommand(char command){
		System.out.println(command + ": illegal command");
		return;
	}
}
