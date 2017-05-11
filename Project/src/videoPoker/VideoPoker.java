package videoPoker;

import cards.Deck;
import player.Hand;
import player.Player;

/**
 * The Class VideoPoker.
 */
public abstract class VideoPoker {
	
	protected int[][] rewardTable;
	
	protected Deck deck;
	
	//States
	protected final static int INITIATING = 0;
	protected final static int BETTING = 1;
	protected final static int DEALING = 2;
	protected final static int QUITTING = 3;
	protected int state;
	
	protected int reward(int score, int bet){
		return rewardTable[score][bet];
	}
	
	protected abstract int[][] fillRewardTable();
	protected abstract void statistics(Player player);
	protected abstract int handScore(Player player);
	protected abstract String scoreToString(int score);
	protected abstract String advise(Player player);
	
	public abstract void interactiveMode(int initialCredit);
	public abstract void debugMode(int initialCredit, String cmd_file, String card_file);
	public abstract void simulationMode(int initialCredit, int bet, int nbdeals);
	
	protected void bet(Player player, int bet){
		if(state == INITIATING || state == BETTING){
			if(bet <= 5 && player.getCredit() - bet >= 0){
				player.setLastBet(bet);
				System.out.println("player is betting " + player.getLastBet());
				state = BETTING;
			}else
				System.out.println("b: illegal amount");
		}else
			this.illegalCommand('b');
		return;
	}
	
	protected void shuffle(){
		this.deck = new Deck();
	}
	
	protected void deal(Player player){
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
	
	protected void credit(Player player){
		System.out.println("player's credit is " + player.getCredit());
		return;
	}
	
	protected void getAdvise(Player player){
		if(state == DEALING){
			System.out.println(this.advise(player));
		}else
			this.illegalCommand('a');
		return;
	}
	
	protected void getStatistics(Player player){
		this.statistics(player);
		return;
	}
	
	protected void quit(Player player){
		if(state == INITIATING || state == BETTING){
			System.out.println("Exiting the game");
			this.statistics(player);
			state = QUITTING;
		}else
			this.illegalCommand('q');
		return;
	}
	
	protected void illegalCommand(char command){
		System.out.println(command + ": illegal command");
		return;
	}
	
}
