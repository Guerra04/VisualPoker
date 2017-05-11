package doublebonus10_7;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JLayeredPane;
import javax.swing.border.BevelBorder;

/**
 * The Class GUI.
 */
public class GUI {
	
	int[] cardsclicked = {0,0,0,0,0};
	int dealpressed = 0;
	int credits = 0;
	String input;
	
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
					DoubleBonus10_7 game = new DoubleBonus10_7();
					
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
		while(credits<5){	
			 input = JOptionPane.showInputDialog(null, "Input amount of credits to be used:");
			credits = Integer.parseInt(input);
			if(credits < 5){
				JOptionPane.showMessageDialog(null, "The credits inserted must be more than 5!");
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
		frame.getContentPane().setForeground(new Color(0, 0, 0));
		frame.setBounds(100, 100, 644, 356);
		frame.setLocationRelativeTo ( null );
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		//Buttons that represent the cards and the deck

		JButton card1 = new JButton();
		//Action that ocures when button is clicked
		card1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	//Action to be performed by pressing of the button
				if(cardsclicked[0] == 0){
					cardsclicked[0]=1;
				}else{
					cardsclicked[0]=0;
				}
			}
		});
		card1.setBounds(21, 184, 89, 119);
		frame.getContentPane().add(card1);
		
		JButton card2 = new JButton();
		card2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[1] == 0){
					cardsclicked[1]=1;
				}else{
					cardsclicked[1]=0;
				}
			}
		});
		card2.setBounds(141, 184, 89, 119);
		frame.getContentPane().add(card2);
		
		JButton card3 = new JButton();
		card3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[2] == 0){
					cardsclicked[2]=1;
				}else{
					cardsclicked[2]=0;
				}
			}
		});
		card3.setBounds(268, 184, 89, 119);
		frame.getContentPane().add(card3);
		
		JButton card4 = new JButton();
		card4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[3] == 0){
					cardsclicked[3]=1;
				}else{
					cardsclicked[3]=0;
				}
			}
		});
		card4.setBounds(391, 184, 89, 119);
		frame.getContentPane().add(card4);
		
		JButton card5 = new JButton();
		card5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(cardsclicked[4] == 0){
					cardsclicked[4]=1;
				}else{
					cardsclicked[4]=0;
				}
			}
		});
		card5.setBounds(515, 184, 89, 119);
		frame.getContentPane().add(card5);
		
		
		//Button that represents the deck
		JButton deck = new JButton();
		deck.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(dealpressed==0){
					dealpressed = 1;
				}else{
					dealpressed = 0;
					
				}
			}
		});
		deck.setBounds(515, 33, 89, 119);
		frame.getContentPane().add(deck);
		
		//Buttons that represent the different types of commands the user can do
		JButton btnHold = new JButton("Hold");
		btnHold.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<5; i++)	//Resetting the cards held vector
					cardsclicked[i]=0;
			}
		});
		btnHold.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnHold.setBounds(21, 107, 89, 23);
		frame.getContentPane().add(btnHold);
		
		//Bet action button with slider that represents the amount the player will bet.
		//After the slider is set, if the user presses bet, he will bet that amount 
		JButton btnBet = new JButton("Bet");
		btnBet.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnBet.setBounds(141, 107, 89, 23);
		frame.getContentPane().add(btnBet);
		
		JSlider Betslider = new JSlider();
		Betslider.setBackground(new Color(0, 128, 0));
		Betslider.setBounds(141, 141, 89, 26);
		frame.getContentPane().add(Betslider);
		
		//Advice action button
		JButton btnAdvise = new JButton("Advise");
		btnAdvise.setFont(new Font("Cambria Math", Font.PLAIN, 17));
		btnAdvise.setBounds(268, 107, 89, 23);
		frame.getContentPane().add(btnAdvise);

	}
}
