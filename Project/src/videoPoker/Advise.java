package videoPoker;

import player.Player;

public interface Advise {
	
	abstract int[] cardsToHold(Player player, VideoPoker game);
}
