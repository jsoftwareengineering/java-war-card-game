import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//James Budday 800769601
//I included a screenshot of what the program looks like on my computer
//in case the sizing and things change on your computer

public class WarDriver {
	//these declared globally for access in listeners
	
	//label constants
	static final String pLab = "   Player: ";
	static final String cLab = "              Computer: ";
	
	//gui declarations
	static JFrame window = new JFrame("War Card Game");
	static JPanel cardZone = new JPanel(new GridLayout(0,7));
	static JPanel playerStack = new JPanel();
	static JPanel computerStack = new JPanel();
	static JButton nextButton = new JButton("Begin Game!");
	static JPanel header = new JPanel(new GridLayout(0,2));
	static JLabel playerLabel = new JLabel(pLab + "26 Cards");
	static JLabel computerLabel = new JLabel(cLab + "26 Cards");
	static JLabel winLabel = new JLabel("");
	static JLabel turnLabel = new JLabel("Turn: 0");
	
	//card labels
	static JLabel playerTop = new JLabel("X");
	static JLabel playerSuit = new JLabel(" X");
	static JLabel playerBottom = new JLabel("    X");
	static JLabel computerTop = new JLabel("X");
	static JLabel computerSuit = new JLabel(" X");
	static JLabel computerBottom = new JLabel("    X");
	
	
	//card groupings here being used as queues
	static LinkedList<Card> playerCards = new LinkedList<>();
	static LinkedList<Card> computerCards = new LinkedList<>();
	static LinkedList<Card> prizeCards = new LinkedList<>();
	
	static Integer counter = 0;
	static boolean finished = false;
	
	public static void main(String[] shot) {
		
		//window options
		window.setSize(700, 500);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//add some label fonts
		playerLabel.setFont(new Font("Sans Serif", Font.BOLD, 22));
		computerLabel.setFont(new Font("Sans Serif", Font.BOLD, 22));
		winLabel.setFont(new Font("Sans Serif", Font.BOLD, 13));
		
		//group of left card panels
		JPanel playerA = new JPanel();
		playerA.setBackground(Color.WHITE);
		JPanel playerB = new JPanel(new BorderLayout());
		playerB.setBackground(Color.WHITE);
		JPanel playerC = new JPanel(new BorderLayout());
		playerC.setBackground(Color.WHITE);
		
		//center panel
		JPanel middlePanel = new JPanel(new BorderLayout());
		middlePanel.setBackground(Color.lightGray);
		
		//group of right card panels
		JPanel computerA = new JPanel();
		computerA.setBackground(Color.WHITE);
		JPanel computerB = new JPanel(new BorderLayout());
		computerB.setBackground(Color.WHITE);
		JPanel computerC = new JPanel(new BorderLayout());
		computerC.setBackground(Color.WHITE);
		
		
		cardZone.add(playerA); 
		cardZone.add(playerB);
		cardZone.add(playerC);
		
		cardZone.add(middlePanel);
		
		cardZone.add(computerA); 
		cardZone.add(computerB);
		cardZone.add(computerC);
		
		//card labels
		playerTop.setFont(new Font("Serif", Font.PLAIN, 30));
		playerSuit.setFont(new Font("Serif", Font.PLAIN, 70));
		playerBottom.setFont(new Font("Serif", Font.PLAIN, 30));
		computerTop.setFont(new Font("Serif", Font.PLAIN, 30));
		computerSuit.setFont(new Font("Serif", Font.PLAIN, 70));
		computerBottom.setFont(new Font("Serif", Font.PLAIN, 30));
		computerTop.setForeground(Color.RED);
		computerSuit.setForeground(Color.RED);
		computerBottom.setForeground(Color.RED);
		
		window.add(cardZone, BorderLayout.CENTER);
		
		playerA.add(playerTop);
		playerB.add(playerSuit, BorderLayout.CENTER);
		playerC.add(playerBottom, BorderLayout.SOUTH);
		
		middlePanel.add(turnLabel, BorderLayout.NORTH);
		middlePanel.add(winLabel, BorderLayout.CENTER);
		
		computerA.add(computerTop);
		computerB.add(computerSuit, BorderLayout.CENTER);
		computerC.add(computerBottom, BorderLayout.SOUTH);

		
		playerStack.setBackground(Color.WHITE);
		computerStack.setBackground(Color.WHITE);
		
		window.add(nextButton, BorderLayout.SOUTH);
		
		header.add(playerLabel);
		header.add(computerLabel);
		window.add(header, BorderLayout.NORTH);
		
		
		//shuffles and deals the cards to the player and cpu
		shuffleAndDeal();
		
		window.show();
		
		//everything that happens when the next button is pressed
		class NextListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent action) {
				counter++;
				nextButton.setText("Next Hand!");
				finished = compareCards(false);
				if(computerCards.size() == 0 || playerCards.size() == 0) {
					finished = true;
				}
				//update cards left labels
				if(playerCards.size() != 1) {
					playerLabel.setText(pLab + playerCards.size() + " Cards");
				} else {
					playerLabel.setText(pLab + "1 Card");
				}
				
				if(computerCards.size() != 1) {
					computerLabel.setText(cLab + computerCards.size() + " Cards");
				} else {
					computerLabel.setText(pLab + "1 Card");
				}
				
				//update turns passed
				turnLabel.setText("Turn: " + counter);
				
				if(finished) {
					//end game
					nextButton.setEnabled(false);
					String winner = "";
					if(computerCards.size() > playerCards.size()) {
						winner = "Computer";
						computerLabel.setText(cLab + "52 Cards");
						playerLabel.setText(pLab + "0 Cards");
						winLabel.setText("CPU Wins >");
					} else {
						winner = "Player";
						computerLabel.setText(cLab + "0 Cards");
						playerLabel.setText(pLab + "52 Cards");
						winLabel.setText("< Player Wins");
					}
					JOptionPane.showMessageDialog(window, "The game is over and " + winner + " won.\nRestart the program to play again!");
				}
			}
		}
		
		ActionListener nList = new NextListener();
		nextButton.addActionListener(nList);
	}
	
	public static void shuffleAndDeal() {
		playerCards = new LinkedList<>();
		computerCards = new LinkedList<>();
		//first put all cards in a pile
		for(int i = 0 ; i < 52 ; i++) {
			playerCards.add(new Card(i));
		}
		
		//shuffle them
		Collections.shuffle(playerCards);
		
		//split between two groups
		for(int i = 0 ; i < 26 ; i++) {
			computerCards.add(playerCards.removeLast());
		}
	}
	
	public static boolean compareCards(boolean isWar) {
		//function is called recursively if there is war
		//so this and similar ifs account for that
		if(isWar) {
			
			//stop prematurely if one player doesn't have enough cards
			if(computerCards.size() < 3 || playerCards.size() < 3) {
				System.out.println("Premature victory");
				return true;
			}
			JOptionPane.showMessageDialog(window, "War!");
			prizeCards.addLast(computerCards.removeFirst());
			prizeCards.addLast(playerCards.removeFirst());
			prizeCards.addLast(computerCards.removeFirst());
			prizeCards.addLast(playerCards.removeFirst());
			
			ListIterator<Card> iterator = prizeCards.listIterator();
			String messageString = "";
			while (iterator.hasNext()) {
				Card c = iterator.next();
				messageString += c.getCardInfo() + "\n";
			}
			JOptionPane.showMessageDialog(window, "Prize Cards!\n" + messageString);
		}
		
		Card cCard = computerCards.peekFirst();
		Card pCard = playerCards.peekFirst();
		System.out.println("C CardValue: " + cCard.getValue() + " Info: " + cCard.getCardInfo());
		
		//make computer card
		computerTop.setText(cCard.getCardNumber());
		computerSuit.setText(" " + cCard.getCardSymbol());
		computerBottom.setText("    " + cCard.getCardNumber());
		//account for color
		if(cCard.getValue() <= 25) {
			//set red
			computerTop.setForeground(Color.RED);
			computerSuit.setForeground(Color.RED);
			computerBottom.setForeground(Color.RED);
		} else {
			//set black
			computerTop.setForeground(Color.BLACK);
			computerSuit.setForeground(Color.BLACK);
			computerBottom.setForeground(Color.BLACK);
		}
		
		System.out.println("P CardValue: " + pCard.getValue() + " Info: " + pCard.getCardInfo());
		//make player card
		playerTop.setText(pCard.getCardNumber());
		playerSuit.setText(" " + pCard.getCardSymbol());
		playerBottom.setText("    " + pCard.getCardNumber());
		//account for color
		if(pCard.getValue() <= 25) {
			//set red
			playerTop.setForeground(Color.RED);
			playerSuit.setForeground(Color.RED);
			playerBottom.setForeground(Color.RED);
		} else {
			//set black
			playerTop.setForeground(Color.BLACK);
			playerSuit.setForeground(Color.BLACK);
			playerBottom.setForeground(Color.BLACK);
		}
		
		//compare the cards and award based on value received
		int compare = cCard.compareTo(pCard);
		if(compare == 1) {
			System.out.println("CPU WIN");
			winLabel.setText("CPU Win >");
			
			//if this is war, we need to give all of the cards from the stack too
			if(isWar) {
				ListIterator<Card> iterator = prizeCards.listIterator();
				while (iterator.hasNext()) {
					Card c = iterator.next();
					iterator.remove();
					computerCards.addLast(c);
				}
			}
			
			//receive both cards from the exchange
			computerCards.addLast(computerCards.removeFirst());
			computerCards.addLast(playerCards.removeFirst());
			
		} else if (compare == 0) {
			System.out.println("WAR");
			winLabel.setText("WAR!");
			
			//recursive call to this function for war condition
			return compareCards(true);
		} else {
			System.out.println("Player WIN");
			winLabel.setText("< Player Win");
			
			//if this is war, we need to give all of the cards from the stack too
			if(isWar) {
				ListIterator<Card> iterator = prizeCards.listIterator();
				while (iterator.hasNext()) {
					Card c = iterator.next();
					iterator.remove();
					playerCards.addLast(c);
				}
			}
			
			//receive both cards from the exchange
			playerCards.addLast(playerCards.removeFirst());
			playerCards.addLast(computerCards.removeFirst());
		}
		
		//return false means we aren't finished with the whole deck yet
		//we don't really know this for sure here, we just didn't
		//end prematurely due to a lack of cards in a war (seen at the beginning)
		return false;
	}
}
