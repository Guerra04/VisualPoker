package doubleBonusTenSeven;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;

import cards.Deck;
import doubleBonus.DoubleBonus;
import player.CardsToFlush;
import player.CardsToHighCards;
import player.CardsToStraight;
import player.CardsToStraightFlush;
import player.Hand;
import player.Player;

// TODO: Auto-generated Javadoc
/**
 * The Class DoubleBonusTenSeven.
 */
public class DoubleBonusTenSeven extends DoubleBonus{

	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#interactiveMode(int)
	 */
	public void interactiveMode(int initialCredit){
		Player player = new Player(initialCredit, nWinningHands);
		Scanner scan = null;

		this.state = INITIATING;
		
		while(state != QUITTING){
			
			scan = new Scanner(System.in);
			String line = scan.nextLine();
			char command;
			try{
				command = line.charAt(0);
			}catch(StringIndexOutOfBoundsException e){
				command = '\0';
			}
			
			switch(command){
			case 'b':
				int bet;
				if(line.length() > 1){
					String betString = "";
					for(int i = 2; i < line.length(); i++){
						betString += line.charAt(i);
					}
					try{
						bet = Integer.parseInt(betString);
						if((bet <= 5) && (player.getCredit() - bet >= 0) && (bet > 0)){
							this.bet(player, bet);
							break;
						}else{
							System.out.println("b: illegal amount");
							break;
						}
					}catch(NumberFormatException e){
						System.out.println("b: illegal amount");
						break;
					}
				}else
					bet = player.getLastBet();
				this.bet(player, bet);
				break;
			
			case '$':
				this.credit(player);
				break;
			
			case 'd':
				this.shuffle();
				this.deal(player);
				break;
			
			case 'h':
				int[] indexes= new int[5];
				for(int i = 2; i < line.length(); i += 2)
					indexes[i/2 - 1] = Character.getNumericValue(line.charAt(i));
				
				this.holdAndResults(player, indexes, deck);
				break;
				
			case 'a':
				this.getAdvise(player);
				break;
				
			case 's':
				this.getStatistics(player);
				break;
			
			case 'q':
				this.quit(player);
				break;
			
			default:
				System.out.println(command + ": illegal command");
				break;
			}
		}
		scan.close();
		return;
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#debugMode(int, java.lang.String, java.lang.String)
	 */
	public void debugMode(int initialCredit, String cmd_file, String card_file){
		Player player = new Player(initialCredit, nWinningHands);
		deck = new Deck(card_file);
		
		BufferedReader br = null;
		
		try{
			br = new BufferedReader(new FileReader(cmd_file));

			String line;
			
			line = br.readLine(); //file has only one line
			br.close();
			this.state = INITIATING;
			int index = 0;
			while(index < line.length()){
				if(Character.isLetter(line.charAt(index)) || line.charAt(index) == '$'){
					char command = line.charAt(index);
					String cmd = "";
					cmd += command;
					switch(command){
					case 'b':
						int bet;
						if(index+2 < line.length() && Character.isDigit(line.charAt(index+2))){
							int i = 2;
							//bet value can have more than one digit
							String betString = "";
							while(Character.isDigit(line.charAt(index+i)) && index+1 < line.length()){
								betString += line.charAt(index+2);
								i ++;
							}
							
							bet = Integer.parseInt(betString);
							index += i;
							cmd += (" " + bet);
						}else{
							bet = player.getLastBet();
							index += 2;
						}
						
						this.printCommand(cmd);
						if((bet <= 5) && (player.getCredit() - bet >= 0) && (bet > 0)){
							this.bet(player, bet);
						}else{
							System.out.println("b: illegal amount");
						}
						break;
					
					case '$':
						this.printCommand(cmd);
						this.credit(player);
						index += 2;
						break;
					
					case 'd':
						this.printCommand(cmd);
						this.deal(player);
						index += 2;
						break;
					
					case 'h':
						int[] indexes= new int[5];
						int n = 0;
						int i = 2;
						while(index+i < line.length() && Character.isDigit(line.charAt(index + i))){
							indexes[n] = Character.getNumericValue(line.charAt(index + i));
							n++;
							cmd += (" " + line.charAt(index + i));
							i += 2;
						}
						this.printCommand(cmd);
						this.holdAndResults(player, indexes, deck);
						index += 2*(n+1);
						break;
						
					case 'a':
						this.printCommand(cmd);
						this.getAdvise(player);
						index += 2;
						break;
						
					case 's':
						this.printCommand(cmd);
						this.getStatistics(player);
						index += 2;
						break;
					
					case 'q':
						this.printCommand(cmd);
						this.quit(player);
						break;
					
					default:
						this.printCommand(cmd);
						System.out.println(command + ": illegal command");
						index++;
						break;
					}
				}else
					index++;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return;
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#simulationMode(int, int, int)
	 */
	public void simulationMode(int initialCredit, int bet, int nbdeals){
		if(bet > 5 || bet < 1){
			System.out.println("b: illegal amount");
			System.exit(2);
		}
		
		Player player = new Player(initialCredit, nWinningHands);
		player.setLastBet(bet);
		
		for(int deal = 0; deal < nbdeals && player.getCredit() - bet >= 0; deal++){
			player.setCredit(player.getCredit() - bet);
			this.shuffle();
			player.hand = new Hand(deck);
			int[] cardsToHold = this.cardsToHold(player);
			int[] indexes = new int[5];
			int nCardsToHold = 0;
			for(int i = 0; i < 5; i++){
				if(cardsToHold[i] == 1){
					indexes[nCardsToHold] = i+1;
					nCardsToHold++;
				}
			}
			player.hand.hold(indexes, deck);
			int score = this.handScore(player);
			player.setCredit(player.getCredit() + reward(score, bet));
			player.incStatistics(score);
			player.incHandsPlayed();
		}
		this.statistics(player);
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#fillRewardTable()
	 */
	protected int[][] fillRewardTable(){
		int[][] rewardTable = new int[nWinningHands+1][nBets+1];
		for(int bet = 1; bet <= nBets; bet++){
			rewardTable[OTHER][bet] = 0;
			rewardTable[ROYAL_FLUSH][bet] = 250*bet;
			rewardTable[FOUR_ACES][bet] = 160*bet;
			rewardTable[FOUR_2_4][bet] = 80*bet;
			rewardTable[FOUR_5_K][bet] = 50*bet;
			rewardTable[STRAIGHT_FLUSH][bet] = 50*bet;
			rewardTable[FULL_HOUSE][bet] = 10*bet;
			rewardTable[FLUSH][bet] = 7*bet;
			rewardTable[STRAIGHT][bet] = 5*bet;
			rewardTable[THREE_OF_A_KIND][bet] = 3*bet;
			rewardTable[TWO_PAIR][bet] = bet;
			rewardTable[JACKS_OR_BETTER][bet] = bet;
		}
		rewardTable[ROYAL_FLUSH][5] = 4000;
		return rewardTable;
	}
	
	/* (non-Javadoc)
	 * @see videoPoker.VideoPoker#cardsToHold(player.Player)
	 */
	protected int[] cardsToHold(Player player){
 		
 		int[] indexes = {0,0,0,0,0};	//vector of cards to hold
 		
 		int score = this.handScore(player);	//Verifying the score of the hand
 		
 		int[] suits = player.hand.isSameSuit(); 
 		int maxSameSuit = 0;
 		int mostRepSuit = 0;
 		for(int i = 0; i < 5; i++){
 			if(suits[i] > maxSameSuit){
 				maxSameSuit = suits[i];
 				mostRepSuit = player.hand.hand[i].suit;
 			}
 		}
 		
 		int[] ranks = player.hand.isSameRank(); 
 		int maxSameRank = 0;
 		int mostRepRank = 0; //Rank that appears the most time
 		for(int i = 0; i < 5; i++){
 			if(ranks[i] > maxSameRank){
 				maxSameRank = ranks[i];
 				mostRepRank = player.hand.hand[i].rank;
 			}
 		}

 		/*****Play (1) - Straight flush, four of a kind, royal flush**********/
 		if(score > 0 && score <= 5){ //royal flush, four of a kind or straight flush
 			indexes[0] = 1;
 			indexes[1] = 1;
 			indexes[2] = 1;
 			indexes[3] = 1;
 			indexes[4] = 1;
 			return indexes;
 		}
 		
 		/*****Play (2) - 4 to a royal flush***********************************/
 		CardsToHighCards highCards = new CardsToHighCards();
 		highCards.compute(maxSameSuit, mostRepSuit, player.hand);
 		
 		if(highCards.royal && highCards.nCardsTo == 4)
 			return highCards.indexes;
 		
 		/*****Play(3) - Three aces********************************************/
 		if(maxSameRank == 3 && mostRepRank == 1){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 3)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(4) - Straight, flush, full house***************************/
 		if(score >= 6 && score <= 8){
 			indexes[0] = 1;
 			indexes[1] = 1;
 			indexes[2] = 1;
 			indexes[3] = 1;
 			indexes[4] = 1;
 			return indexes;
 		}
 		
 		/*****Play(5) - Three of a kind (except aces)*************************/
 		if(maxSameRank == 3){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 3)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(6) - 4 to a straight flush*********************************/
 		CardsToStraightFlush straightFlush = new CardsToStraightFlush();
 		straightFlush.compute(maxSameSuit, mostRepSuit, player.hand);
 		
 		if(straightFlush.nCardsTo == 4)
 			return straightFlush.indexes;
 		
 		/*****Play(7) - Two pair**********************************************/
 		if(score == 10){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(8) - High pair*********************************************/
 		if(score == 11){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(9) - 4 to a flush******************************************/
 		CardsToFlush flush = new CardsToFlush();
 		flush.compute(maxSameSuit, mostRepSuit, player.hand);
 		
 		if(flush.nCardsTo == 4)
 			return flush.indexes;
 			
 		/*****Play(10) - 3 to a royal flush***********************************/
 		if(highCards.royal && highCards.nCardsTo == 3)
 			return highCards.indexes;
 		
 		/*****Play(11) - 4 to an outside straight*****************************/
 		CardsToStraight straight = new CardsToStraight();
 		straight.compute(maxSameSuit, mostRepSuit, player.hand);
 		
 		if(straight.outside)
 			return straight.indexes;
    
 		/*****Play(12) - Low pair ********************************************/
 		if(maxSameRank == 2 && mostRepRank < 11){
 			for(int i = 0; i < 5; i++){
 				if(ranks[i] == 2)
 					indexes[i] = 1;
 			}
 			return indexes;
 		}
 		
 		/*****Play(13) - AKJQ unsuited ***************************************/
 		if(highCards.AKQJunsuited)
 			return highCards.indexes;

 		/*****Play(14) - 3 to a straight flush (type 1)***********************/
 		if(straightFlush.type == 1)
 			return straightFlush.indexes;
 		
 		/*****Play(15) - 4 to an inside straight with 3 high cards************/
 		if(!straight.outside && straight.nHighCards == 3)
 			return straight.indexes;
 			
 		
 		/*****Play(16) - QJ suited********************************************/
 		if(highCards.QJsuited)
 			return highCards.indexes;
 		
 		/*****Play(17) - 3 to a flush with 2 high cards***********************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 2)
 			return flush.indexes;
 		
 		/*****Play(18) - 2 suited high cards**********************************/
 		for(int i = 0; i < 5; i++){
 			if(player.hand.hand[i].isHighCard() && suits[i] >= 2){
 				for(int j = i+1; j < 5; j++){
 					if(player.hand.hand[j].isHighCard() && player.hand.hand[i].suit == player.hand.hand[j].suit){
 						indexes[i] = 1;
 						indexes[j] = 1;
 						return indexes;
 					}
 				}
 			}
 		}
 		
 		/*****Play(19) - 4 to an inside straight with 2 high cards************/
 		if(!straight.outside && straight.nHighCards == 2)
 			return straight.indexes;
 		
 		/*****Play(20) - 3 to a straight flush (type 2)***********************/
 		if(straightFlush.type == 2)
 			return straightFlush.indexes;
 		
 		/*****Play(21) - 4 to an inside straight with 1 high card*************/
 		if(!straight.outside && straight.nHighCards == 1)
 			return straight.indexes;
 		 		
 		/*****Play(22) - KQJ unsuited*****************************************/
 		if(highCards.KQJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(23) - JT suited********************************************/
 		if(highCards.JTsuited)
 			return highCards.indexes;
 		
 		/*****Play(24) - QJ unsuited******************************************/
 		if(highCards.QJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(25) - 3 to a flush with 1 high card************************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 1)
 			return flush.indexes;
 			
 		/*****Play(26) - QT suited********************************************/
 		if(highCards.QTsuited)
 			return highCards.indexes;
 		
 		/*****Play(27) - 3 to a straight flush (type 3)***********************/
 		if(straightFlush.type == 3)
 			return straightFlush.indexes;
 		
 		/*****Play(28) - KQ, KJ unsuited**************************************/
 		if(highCards.KQ_KJunsuited)
 			return highCards.indexes;
 		
 		/*****Play(29) - Ace**************************************************/
 		if(highCards.Ace)
 			return highCards.indexes;
 		
 		/*****Play(30) - KT suited********************************************/
 		if(highCards.KTsuited)
 			return highCards.indexes;
 		
 		/*****Play(31) - Jack, Queen or King**********************************/
 		if(highCards.K_Q_J)
 			return highCards.indexes;
 			
 		/*****Play(32) - 4 to an inside straight with no high cards***********/
		if(!straight.outside && straight.nHighCards == 0)
			return straight.indexes;
 		
 		/*****Play(33) - 3 to a flush with no high cards**********************/
 		if(flush.nCardsTo == 3 && flush.nHighCards == 0)
 			return flush.indexes;
 		
 		/*****Play(34) - Discard everything***********************************/
 		indexes[0] = -1; //flag to discard everything
 		return indexes;
 		
 	}//end cardsToHold
}
