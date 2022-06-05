package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game extends Main implements ActionListener {
	
	String player1Name, player2Name, cardTheme, backgroundTheme, whoseTurn;
	int cardNumber, clicks = 0, scale, player1Score = 0, player2Score = 0, random1, random2;
	boolean isVsAi, randomFlag, botFlag;
	JFrame gameFrame;
	JPanel statsPanel;
	JLabel player1Label, player2Label, turnLabel;
	ImagePanel gamePanel;
	Card[] cards;
	Integer[] shuffleArray;
	TimerThread timer;
	Sounds sounds = new Sounds();
	int time, cardsDiscovered = 0;
	List<Card> pickedCards = new ArrayList<>();
	
	public Game(String name1, String name2, int cardNumber1, String cardTheme1, String backgroundTheme1, boolean isVsAi1) {
		
		player1Name = name1;
		player2Name = name2;
		cardNumber = cardNumber1;
		cardTheme = cardTheme1;
		backgroundTheme = backgroundTheme1;
		isVsAi = isVsAi1;
		
		statsPanel = new JPanel();
		statsPanel.setBackground(new Color(166,166,166));
		statsPanel.setBounds(0, 0, 1000, 100);
		
		player1Label = new JLabel();
		player1Label.setBounds(10, 10, 200, 50);
		player1Label.setFont(new Font("Arial", Font.PLAIN, 20));
		player1Label.setText(String.format("<html> %s<br>Punkty: %d", player1Name, player1Score));
		
		player2Label = new JLabel();
		player2Label.setBounds(800, 10, 200, 50);
		player2Label.setFont(new Font("Arial", Font.PLAIN, 20));
		player2Label.setText(String.format("<html> %s<br>Punkty: %d", player2Name, player2Score));
		
		turnLabel = new JLabel();
		turnLabel.setBounds(300, 5, 400, 40);
		turnLabel.setFont(new Font("Arial", Font.BOLD, 20));
		turnLabel.setText(String.format("Tura gracza: %s", whoseTurn = player1Name));
		turnLabel.setHorizontalAlignment(JTextField.CENTER);
		
		if (backgroundTheme.equals("koszyk")) {gamePanel = new ImagePanel("images\\koszyk.png");}
		else if (backgroundTheme.equals("okno")) {gamePanel = new ImagePanel("images\\okno.png");}
		else if (backgroundTheme.equals("kosmos")) {gamePanel = new ImagePanel("images\\kosmos.png");}
		else {gamePanel = new ImagePanel("images\\szare.png");}
		gamePanel.setBounds(0, 100, 1000, 750);
		if (cardNumber == 12) {gamePanel.setLayout(new GridLayout(4,3)); scale = 175;}
		else if (cardNumber == 20) {gamePanel.setLayout(new GridLayout(5,4)); scale = 125;}
		else {gamePanel.setLayout(new GridLayout(6,5)); scale = 100;}
		
		shuffleArray = new Integer[cardNumber];
		for (int i = 0; i < cardNumber; i++) {
			shuffleArray[i] = i;
		}
		List<Integer> shuffleList = Arrays.asList(shuffleArray);
		Collections.shuffle(shuffleList);
		shuffleList.toArray(shuffleArray);
		
		cards = new Card[cardNumber];
		for (int i = 0; i < cardNumber; i++) {
			cards[i] = new Card(i/2, cardTheme, scale, scale);
			cards[i].addActionListener(this);
		}	
		
		for (int i = 0; i < cardNumber; i++) {
			gamePanel.add(cards[shuffleArray[i]]);
		}
		
		timer = new TimerThread();
		timer.run();
		gameFrame = new JFrame();
		gameFrame.setTitle("Odkrywanie par");
		gameFrame.setLayout(null);
		gameFrame.setResizable(false);
		gameFrame.setSize(1000, 890);
		gameFrame.setVisible(true);
		gameFrame.getContentPane().setBackground(new Color(255,255,255));
		gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		gameFrame.add(player1Label);
		gameFrame.add(player2Label);
		gameFrame.add(timer);
		gameFrame.add(turnLabel);
		gameFrame.add(statsPanel);
		gameFrame.add(gamePanel);

		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (!isVsAi) {																	//Gracz vs Gracz
			if(whoseTurn.equals(player1Name)) {
				if(clicks%3 == 0) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							clicks++;
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							break;
						}
					}
				}
				else if(clicks%3 == 1) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							if(pickedCards.get(0).getPair() == pickedCards.get(1).getPair()) {
								pickedCards.get(0).setEnabled(false);
								pickedCards.get(1).setEnabled(false);
								pickedCards.clear();
								clicks--;
								player1Score++;
								player1Label.setText(String.format("<html> %s<br>Punkty: %d", player1Name, player1Score));
								if(player1Score + player2Score == cardNumber/2) {
									sounds.terminate();
									time = timer.stop();
									if(player1Score > player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player1Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 1, time);
								    	savingThread.run();
										new ResultScreen(player1Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else if(player1Score < player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player2Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, -1, time);
								    	savingThread.run();
										new ResultScreen(player2Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else {
										turnLabel.setText("Remis!");
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 0, time);
								    	savingThread.run();
										new ResultScreen("Remis", time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
								}
							}
							else {
								clicks++;
							}
							
						}
					}
				}
				else if(clicks%3 == 2) {
					sounds.klik();
					clicks++;
					pickedCards.get(0).changeIcon();
					pickedCards.get(1).changeIcon();
					pickedCards.get(0).isPicked(false);
					pickedCards.get(1).isPicked(false);
					pickedCards.clear();
					turnLabel.setText(String.format("Tura gracza: %s", whoseTurn = player2Name));
				}
			}
			else if(whoseTurn.equals(player2Name)) {
				if(clicks%3 == 0) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							clicks++;
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							break;
						}
					}
				}
				else if(clicks%3 == 1) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							if(pickedCards.get(0).getPair() == pickedCards.get(1).getPair()) {
								pickedCards.get(0).setEnabled(false);
								pickedCards.get(1).setEnabled(false);
								pickedCards.clear();
								clicks--;
								player2Score++;
								player2Label.setText(String.format("<html> %s<br>Punkty: %d", player2Name, player2Score));
								if(player1Score + player2Score == cardNumber/2) {
									sounds.terminate();
									time = timer.stop();
									if(player1Score > player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player1Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 1, time);
								    	savingThread.run();
										new ResultScreen(player2Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else if(player1Score < player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player2Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, -1, time);
								    	savingThread.run();
										new ResultScreen(player2Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else {
										turnLabel.setText("Remis!");
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 0, time);
								    	savingThread.run();
										new ResultScreen("Remis", time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
								}
							}
							else {
								clicks++;
							}
							
						}
					}
				}
				else if(clicks%3 == 2) {
					sounds.klik();
					clicks++;	
					pickedCards.get(0).changeIcon();
					pickedCards.get(1).changeIcon();
					pickedCards.get(0).isPicked(false);
					pickedCards.get(1).isPicked(false);
					pickedCards.clear();
					turnLabel.setText(String.format("Tura gracza: %s", whoseTurn = player1Name));
				}
			}
		}
		
		else {																						//vs AI
			if(true) {
				if(clicks%4 == 0) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							clicks++;
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							break;
						}
					}
				}
				else if(clicks%4 == 1) {
					for(Card card : cards) {
						if(e.getSource() == card && !(card.isPicked)) {
							sounds.klik();
							card.changeIcon();
							card.isPicked(true);
							pickedCards.add(card);
							if(pickedCards.get(0).getPair() == pickedCards.get(1).getPair()) {
								pickedCards.get(0).setEnabled(false);
								pickedCards.get(1).setEnabled(false);
								pickedCards.clear();
								clicks--;
								player1Score++;
								cardsDiscovered++;
								player1Label.setText(String.format("<html> %s<br>Punkty: %d", player1Name, player1Score));
								if(player1Score + player2Score == cardNumber/2) {
									sounds.terminate();
									time = timer.stop();
									if(player1Score > player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player1Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 1, time);
								    	savingThread.run();
										new ResultScreen(player1Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else if(player1Score < player2Score) {
										turnLabel.setText(String.format("Wygra³: %s",player2Name));
										SavingThread savingThread = new SavingThread(player1Name, player2Name, -1, time);
								    	savingThread.run();
										new ResultScreen(player2Name, time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
									else {
										turnLabel.setText("Remis!");
										SavingThread savingThread = new SavingThread(player1Name, player2Name, 0, time);
								    	savingThread.run();
										new ResultScreen("Remis", time);
										new Menu(player1Name);
										gameFrame.dispose();
									}
								}
							}
							else {
								clicks++;
							}
							
						}
					}
				}
				else if(clicks%4 == 2) {
					sounds.klik();
					clicks++;
					pickedCards.get(0).changeIcon();
					pickedCards.get(1).changeIcon();
					pickedCards.get(0).isPicked(false);
					pickedCards.get(1).isPicked(false);
					pickedCards.clear();
					turnLabel.setText(String.format("Tura gracza: %s", whoseTurn = player2Name));
					
					botFlag = true;
					while (botFlag) {
						randomFlag = true;
						while(randomFlag) {
							random1 = (int) (Math.random() * cardNumber);
							random2 = (int) (Math.random() * cardNumber);
							if (!cards[random1].isPicked && !cards[random2].isPicked && random1 != random2) {
								randomFlag = false;
							}
						}
						if (cards[random1].getPair() == cards[random2].getPair()) {
							cards[random1].isPicked(true);
							cards[random2].isPicked(true);
							cards[random1].setEnabled(false);
							cards[random2].setEnabled(false);
							player2Score++;
							player2Label.setText(String.format("<html> %s<br>Punkty: %d", player2Name, player2Score));
							
						}
						else {
							cards[random1].isPicked(false);
							cards[random2].isPicked(false);
							botFlag = false;
						}
						cards[random1].changeIcon();
						cards[random2].changeIcon();
						if(player1Score + player2Score == cardNumber/2) {
							botFlag = false;
							sounds.terminate();
							time = timer.stop();
							if(player1Score > player2Score) {
								turnLabel.setText(String.format("Wygra³: %s",player1Name));
								SavingThread savingThread = new SavingThread(player1Name, player2Name, 1, time);
						    	savingThread.run();
								new ResultScreen(player1Name, time);
								new Menu(player1Name);
								gameFrame.dispose();
							}
							else if(player1Score < player2Score) {
								turnLabel.setText(String.format("Wygra³: %s",player2Name));
								SavingThread savingThread = new SavingThread(player1Name, player2Name, -1, time);
						    	savingThread.run();
								new ResultScreen(player2Name, time);
								new Menu(player1Name);
								gameFrame.dispose();
							}
							else {
								turnLabel.setText("Remis!");
								SavingThread savingThread = new SavingThread(player1Name, player2Name, 0, time);
						    	savingThread.run();
								new ResultScreen("Remis", time);
								new Menu(player1Name);
								gameFrame.dispose();
							}
							
						}
					}
				}
				else if(clicks%4 == 3) {
					cards[random1].changeIcon();
					cards[random2].changeIcon();
					sounds.klik();
					clicks++;
					pickedCards.clear();
					turnLabel.setText(String.format("Tura gracza: %s", whoseTurn = player1Name));
				}
			}
		}
	}
}	
