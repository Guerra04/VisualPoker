package doubleBonusTenSeven;

import java.awt.EventQueue;

import javax.swing.*;

import javax.imageio.ImageIO;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;
import player.Player;
import java.awt.Image;

import com.sun.glass.events.WindowEvent;

import doubleBonus.DoubleBonus;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

//import javax.swing.event.AncestorEvent;

public class GUI extends DoubleBonusTenSeven {
	
	String print = "";
	int[] cardsclicked = new int[5];
	int dealpressed = 0;
	static int credits = 0;
	String input;
	DoubleBonusTenSeven game = new DoubleBonusTenSeven();
	Player player;
	int ValueBet = 5;
	int c;
	ImageIcon cardBack;
	private JFrame frame;
	private JTextField textField;
	
	
	int statVisibility;
	JFrame statsFrame = new JFrame();
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}//End of main

	/**
	 * Create the application.
	 */
	public GUI() {
		while(credits<=0){	
			try{
				input = JOptionPane.showInputDialog(null, "Input amount of credits to be used:");
				if(input==null){
					System.exit(0);
				}
				credits = Integer.parseInt(input);
				if(credits <= 0){
					JOptionPane.showMessageDialog(null, "You must insert credits!");
				}
				if(credits >= 1000000){
					JOptionPane.showMessageDialog(null, "Please insert an amount inferior to 99999999.");
				}
				player = new Player(credits, DoubleBonus.nWinningHands);
			}catch(NumberFormatException e){
				JOptionPane.showMessageDialog(null, "Credits must be a numeric value!");
				
			}
		}
		initialize();
	}//End of GUI function

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		//Creating the frame/window of the GUI(the place where all the interface will be located)
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(0, 128, 0));
		frame.getContentPane().setForeground(new Color(255, 255, 224));
		frame.setBounds(100, 100, 644, 356);
		frame.setLocationRelativeTo ( null );
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);	
		
		
		//Setting a statistics window frame setiings
		statsFrame.setResizable(false);
		statsFrame.getContentPane().setBackground(Color.BLACK);
		statsFrame.getContentPane().setForeground(new Color(255, 255, 224));
		statsFrame.setLocationRelativeTo ( null );
		statsFrame.setResizable(false);
		statsFrame.setBounds(100, 100, 425, 425);
		statsFrame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent evt) {
		    	statVisibility = 0;
		    	statsFrame.setVisible(false);
		    	System.out.println(statVisibility);
		  }
		});
		
		
		JButton[] base = new JButton[5];
		
		//Label that displays the player's credits
		JLabel lblCredits = new JLabel("");
		lblCredits.setForeground(new Color(255, 250, 250));
		lblCredits.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		lblCredits.setBounds(21, 39, 89, 23);
		frame.getContentPane().add(lblCredits);
		lblCredits.setText(Integer.toString(player.getCredit()));
	
		//Text Field to display, player winnings and losses, etc
		textField = new JTextField();
		textField.setFont(new Font("Malgun Gothic", Font.ITALIC, 16));
		textField.setEnabled(false);
		textField.setEditable(false);
		textField.setForeground(Color.WHITE);
		textField.setBackground(Color.BLACK);
		textField.setBounds(152, 19, 289, 43);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		//Layered pane to place buttons on-top of shadows
		JLayeredPane pane = new JLayeredPane();
		pane.setBounds(0, 163, 638, 164);
		frame.getContentPane().add(pane);
		
		//Buttons that represent the cards and the 
		JButton[] card = new JButton[5];
		//Generating hand cards, as buttons
		for(c=0; c<5; c++){
			card[c] = new JButton();
			card[c].setBounds(20+(120*c),10, 89, 119);
			pane.add(card[c], 2, 0);
		}
		
		card[0].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[0] == 0 && game.getState()==DEALING){
					cardsclicked[0]=1;
					highlightbtn(card[0],0);
				}else{
					cardsclicked[0]=0;
					resetbtn(card[0],0);
				}
			}
		});
		card[1].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[1] == 0 && game.getState()==DEALING){
					cardsclicked[1]=1;
					highlightbtn(card[1],1);
				}else{
					cardsclicked[1]=0;
					resetbtn(card[1],1);
				}
			}
		});
		card[2].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[2] == 0 && game.getState()==DEALING){
					cardsclicked[2]=1;
					highlightbtn(card[2],2);
				}else{
					cardsclicked[2]=0;
					resetbtn(card[2],2);
				}
			}
		});
		card[3].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[3] == 0 && game.getState()==DEALING){
					cardsclicked[3]=1;
					highlightbtn(card[3],3);
				}else{
					cardsclicked[3]=0;
					resetbtn(card[3],3);
				}
			}
		});
		card[4].addActionListener(new ActionListener() {	//Card Action
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[4] == 0 && game.getState()==DEALING){
					cardsclicked[4]=1;
					highlightbtn(card[4],4);
				}else{
					cardsclicked[4]=0;
					resetbtn(card[4],4);
				}
			}
		});		
		
		
		//Buttons that represent the different types of commands the user can do
		JButton btnHold = new JButton("Hold");
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] indexes = new int[5];
				int nCardsToHold = 0;
				for(int i = 0; i < 5; i++){
					if(cardsclicked[i] == 1){
						indexes[nCardsToHold] = i+1;
						nCardsToHold++;
					}
				}
				if(game.getState() == DEALING){
					player.hand.hold(indexes, game.getDeck());
					paintHand(card, player);
					int score = game.handScore(player);
					player.setCredit(player.getCredit() + reward(score, player.getLastBet()));
					if(score == 0)
						textField.setText("Player had nothing in hand");
					else
						textField.setText("Player wins with a " + game.scoreToString(score));
					
					player.incStatistics(score);
					player.incHandsPlayed();
					game.setState(BETTING);
					/**If the statistics frame is visible, the sats will be updated**/
					if(statsFrame.isVisible()){
						framestatistics(player);
					}
				}else
					JOptionPane.showMessageDialog(null,"You can't hold right now!");
				
				if(player.getCredit() == 0){
					JOptionPane.showMessageDialog(null,"GAME OVER!\nBye!\n");
					System.exit(3);
				}
				
				lblCredits.setText(Integer.toString(player.getCredit()));
				for(int i=0; i<5; i++)	//Resetting the cards held vector
					cardsclicked[i]=0;
				resetallbtn(card);
			}
		});
		btnHold.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnHold.setBounds(21, 96, 89, 23);
		frame.getContentPane().add(btnHold);
		
		//Bet action button with slider that represents the amount the player will bet.
		//After the slider is set, if the user presses bet, he will bet that amount 
		
		JSlider Betslider = new JSlider(1,5,5);
		Betslider.setPaintLabels(true);
		Betslider.setMinorTickSpacing(1);
		Betslider.setMajorTickSpacing(1);
		Betslider.setSnapToTicks(true);
		Betslider.setPaintTicks(true);
		Betslider.setBackground(new Color(0, 128, 0));
		Betslider.setBounds(141, 128, 89, 36);
		frame.getContentPane().add(Betslider);
		
		JButton btnBet = new JButton("Bet");
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ValueBet = Betslider.getValue();
				if(game.getState() == BETTING||game.getState()==INITIATING)
					if(ValueBet > player.getCredit())
						JOptionPane.showMessageDialog(null, "You can't bet more that the amount of credits you have!");
					else{
						lblCredits.setText(Integer.toString(player.getCredit()-ValueBet));
						textField.setText("Player bet: "+ ValueBet);
						game.bet(player, ValueBet);
					}
				else
					JOptionPane.showMessageDialog(null, "You can only bet before dealing!");
			}
		});
		btnBet.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnBet.setBounds(141, 96, 89, 23);
		frame.getContentPane().add(btnBet);
		
		
		//Advice action button
		JButton btnAdvise = new JButton("Advise");
		btnAdvise.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.getState()==DEALING){
					int[] shouldhold = new int[5];
					shouldhold = AdviseDoubleBonusTenSeven.cardsToHold(player, game);
					adviseHighlight(shouldhold, card);
					cardsclicked=shouldhold;
					for(int i=0;i<5;i++){
						if(shouldhold[i]!=1)
							resetbtn(card[i],i);
					}
				}else
					JOptionPane.showMessageDialog(null, "You can only get advise after dealing!");
			}
		});
		btnAdvise.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnAdvise.setBounds(268, 96, 89, 23);
		frame.getContentPane().add(btnAdvise);
		
		//Credits title label
		JLabel lblPlayerCredits = new JLabel("Credits:");
		lblPlayerCredits.setForeground(new Color(255, 255, 224));
		lblPlayerCredits.setFont(new Font("Cambria Math", Font.BOLD, 17));
		lblPlayerCredits.setBounds(21, 17, 70, 23);
		frame.getContentPane().add(lblPlayerCredits);
		
		
		//Button that represents the deck
		JButton deck = new JButton();
		deck.setToolTipText("Press this button to deal(bets last bet value, if Bet isn't pressed) the cards");
		try{
			Image back= ImageIO.read((GUI.class.getResource("/cardsPNG/403px-Card_back-Overwatch.png")));
			back = back.getScaledInstance(89, 119, Image.SCALE_SMOOTH);
			cardBack = new ImageIcon(back);;
		}catch(Exception e){
			e.printStackTrace();
		}
		deck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(game.getState()== BETTING){
					if(ValueBet > player.getCredit())
						JOptionPane.showMessageDialog(null, "You can't bet more that the amount of credits you have!");
					else{
						resetallbtn(card);
						game.shuffle();
						game.deal(player);
						paintHand(card, player);
						game.setState(DEALING);
						textField.setText("Player bet: "+ ValueBet);
					}
					
				}else
					JOptionPane.showMessageDialog(null, "You can only deal after you bet or after this turned is resolved!");
			}
		});
		deck.setIcon(cardBack);
		deck.setBounds(517, 19, 89, 119);
		frame.getContentPane().add(deck);
		
		//Button that prompts up the statistics board
		JButton statisticsBtn = new JButton("Statistics");
		statisticsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				framestatistics(player);
			}
		});
		statisticsBtn.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		statisticsBtn.setBounds(383, 96, 105, 23);
		frame.getContentPane().add(statisticsBtn);
				
		//Highlighting buttons
		for(int i=0; i<5; i++){
			base[i] = new JButton();
			base[i].setBackground(Color.DARK_GRAY);
			base[i].setEnabled(false);
			base[i].setBounds(20+(120*i),10, 89, 119);
			pane.add(base[i], 1, 0);
		}

		paintHand(card, player);
	}
	
	//Function that elevates a single button to represent that it is being held
	void highlightbtn(JButton card, int index){			
		card.setBounds(30+(120*index),0, 89, 119);
	}
	//Function that elevates some buttons to represent that it should be held
	void adviseHighlight(int[] shouldhold, JButton[] card){
		for(int i=0; i<5; i++){
			if(shouldhold[i]==1){
				card[i].setBounds(30+(120*i), 0, 89, 119);
			}
		}
	}
	//Function that resets a single button to its original position
	void resetbtn(JButton card, int index){				
		card.setBounds(20+(120*index), 10, 89, 119);	
	}
	//Function that resets all buttons to its original position
	void resetallbtn(JButton[] card){	
		for(int i=0; i<5; i++){
			card[i].setBounds(20+(120*i), 10, 89, 119);
		}
	}
	//Method that places images on the cards
	void paintHand(JButton[] card,Player player){
		String image ;
		for(int i = 0; i<5; i++){
			if(game.getState()==INITIATING)
				image = "/cardsPNG/403px-Card_back-Overwatch.png";
			else
				image = "/cardsPNG/"+ player.hand.hand[i].toString() +".png";
				try{
					Image back= ImageIO.read((GUI.class.getResource(image)));
					back = back.getScaledInstance(89, 119, Image.SCALE_SMOOTH);
					cardBack = new ImageIcon(back);
				}catch(Exception e){
					e.printStackTrace();
				}
				card[i].setIcon(cardBack);			
		}
	}
	
	//method that calls a new frame
	void framestatistics(Player player){
			
		
			statsFrame.setVisible(true);
			statVisibility = 1;
			/**Adding a window action so, the statistics frame close button just hides it's visibility**/

	        JTextArea statArea = new JTextArea(statsFrame.getHeight(),statsFrame.getWidth());
			statArea.setFont(new Font("Malgun Gothic", Font.PLAIN, 16));
			statArea.setEnabled(false);
			statArea.setEditable(false);
			statArea.setForeground(Color.WHITE);
			statArea.setBackground(Color.BLACK);
			statsFrame.getContentPane().add(statArea);
			
			statArea.append("\n   Hand\t\tNb\n");
			statArea.append("   --------------------------------------------\n");
			statArea.append("   Jacks or Better"+"\t" + String.valueOf(player.statistics[JACKS_OR_BETTER])+ "\n");
			statArea.append("   Two Pair" +"\t\t" + String.valueOf(player.statistics[TWO_PAIR]+ "\n"));
			statArea.append("   Three of a Kind" +"\t" + String.valueOf(player.statistics[THREE_OF_A_KIND]+ "\n"));
			statArea.append("   Straight" +"\t\t"+  String.valueOf(player.statistics[STRAIGHT])+ "\n");
			statArea.append("   Flush" +"\t\t"+ String.valueOf(player.statistics[FLUSH])+ "\n");
			statArea.append("   Full house" +"\t\t"+ String.valueOf(player.statistics[FULL_HOUSE])+ "\n");
			statArea.append("   Four of a Kind" +"\t"+ String.valueOf(player.statistics[FOUR_ACES] 
						+ player.statistics[FOUR_2_4] + player.statistics[FOUR_5_K])+ "\n");
			statArea.append("   Straight Flush" +"\t"+ String.valueOf(player.statistics[STRAIGHT_FLUSH])+ "\n");
			statArea.append("   Royal Flush" +"\t\t"+ String.valueOf(player.statistics[ROYAL_FLUSH])+ "\n");
			statArea.append("   Other" +"\t\t"+ String.valueOf(player.statistics[OTHER])+ "\n");
			statArea.append("   --------------------------------------------\n");
			statArea.append("   Total" +"\t\t"+ String.valueOf(player.handsPlayed)+ "\n");
			statArea.append("   --------------------------------------------\n");
			statArea.append("   Credit" +"\t\t"+ String.valueOf(player.getCredit())+"(" +
					String.valueOf(((double)player.getCredit())/player.getInitialCredit()*100)+")");
	}
}