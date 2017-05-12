package videoPoker;

import player.Player;

/**
 * The Interface Advise.
 */
public interface Advise {
	
	abstract int[] cardsToHold(Player player, VideoPoker game);
}
