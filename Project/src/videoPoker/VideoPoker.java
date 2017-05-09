package videoPoker;

import java.io.IOException;
import java.util.Scanner;

import cards.Deck;
import player.Hand;
import player.Player;

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
	
	public abstract void statistics(Player player);
	public abstract void interactiveMode(int initialCredit);
	//public abstract void debugMode(int initialCredit);
	public abstract void simulationMode(int initialCredit, int bet, int nbdeals);
}
