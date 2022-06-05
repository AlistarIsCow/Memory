package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Tournament extends Main implements ActionListener {

	JFrame tournamentFrame;
	String login, login1, login2, login3, login4, login5, login6, cardTheme, backgroundTheme;
	JButton returnButton, addPlayerButton, removePlayerButton, acceptButton, loginButton;
	JLabel playerNumberLabel, loginLabel, passwordLabel, infoLabel, errorLabel;
	JTextField loginField, passwordField;
	JPanel loginPanel;
	int playerNumber = 2, cardNumber, logged = 1;
	Sounds sounds = new Sounds();
	
	public Tournament(String name1, int cardNumber1, String cardTheme1, String backgroundTheme1) throws IOException {
		
		login = name1;
		login1 = login;
		cardNumber = cardNumber1;
		cardTheme = cardTheme1;
		backgroundTheme = backgroundTheme1;
		
		playerNumberLabel = new JLabel();
		playerNumberLabel.setBounds(500, 10, 200, 30);
		playerNumberLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		playerNumberLabel.setText(String.format("Liczba zawodników: %d", playerNumber));
		
		infoLabel = new JLabel();
		infoLabel.setBounds(480, 170, 300, 30);
		infoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		infoLabel.setText(String.format("Niech zaloguje sie gracz %d", logged + 1));
		
		errorLabel = new JLabel();
		errorLabel.setBounds(520, 355, 300, 30);
		errorLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		errorLabel.setForeground(Color.red);
		errorLabel.setVisible(false);
		errorLabel.setText("Niepoprawne dane");
		
		loginLabel = new JLabel();
		loginLabel.setBounds(575, 200, 200, 30);
		loginLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		loginLabel.setText("Login");
		
		passwordLabel = new JLabel();
		passwordLabel.setBounds(575, 275, 200, 30);
		passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordLabel.setText("Has³o");
		
		loginField = new JTextField();
		loginField.setBounds(450, 225, 300, 40);
		
		passwordField = new JTextField();
		passwordField.setBounds(450, 300, 300, 40);
		
		acceptButton = new JButton();
		acceptButton.setBounds(500, 50, 200, 60);
		acceptButton.setFont(new Font("Arial", Font.PLAIN, 25));
		acceptButton.setText("Akceptuj");
		acceptButton.addActionListener(this);
		acceptButton.setFocusable(false);
		
		loginButton = new JButton();
		loginButton.setBounds(450, 400, 300, 50);
		loginButton.setFont(new Font("Arial", Font.PLAIN, 25));
		loginButton.setText("Zaloguj");
		loginButton.addActionListener(this);
		loginButton.setFocusable(false);
		loginButton.setEnabled(false);
		
		addPlayerButton = new JButton();
		addPlayerButton.setBounds(700, 0, 50, 50);
		addPlayerButton.setText("+");
		addPlayerButton.addActionListener(this);
		addPlayerButton.setFocusable(false);
		
		removePlayerButton = new JButton();
		removePlayerButton.setBounds(450, 0, 50, 50);
		removePlayerButton.setText("-");
		removePlayerButton.addActionListener(this);
		removePlayerButton.setFocusable(false);
		
		returnButton = new JButton();
		returnButton.setBounds(0, 0, 100, 50);
		returnButton.setText("Wróæ");
		returnButton.addActionListener(this);
		returnButton.setFocusable(false);
		
		loginPanel = new JPanel();
		loginPanel.setBounds(450, 200, 300, 250);
		
		
		
		tournamentFrame = new JFrame();
		tournamentFrame.setTitle("Turniej");
		tournamentFrame.setLayout(null);
		tournamentFrame.setResizable(false);
		tournamentFrame.setSize(1200, 600);
		tournamentFrame.setVisible(true);
		tournamentFrame.getContentPane().setBackground(new Color(210, 210, 210));
		tournamentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tournamentFrame.add(returnButton);
		tournamentFrame.add(playerNumberLabel);
		tournamentFrame.add(addPlayerButton);
		tournamentFrame.add(removePlayerButton);
		tournamentFrame.add(acceptButton);
		tournamentFrame.add(loginLabel);
		tournamentFrame.add(passwordLabel);
		tournamentFrame.add(loginField);
		tournamentFrame.add(passwordField);
		tournamentFrame.add(loginButton);
		tournamentFrame.add(infoLabel);
		tournamentFrame.add(errorLabel);
		
		tournamentFrame.add(loginPanel);
		
		FileWriter playersWriter = new FileWriter("turniej\\gracze.txt", false);
		playersWriter.write(login1);
		playersWriter.write("\n");
		playersWriter.close();
		
		FileWriter settingsWriter = new FileWriter("turniej\\ustawienia.txt", false);
		settingsWriter.write("true");
		settingsWriter.write("\n");
		settingsWriter.write(Integer.toString(cardNumber));
		settingsWriter.write("\n");
		settingsWriter.write(cardTheme);
		settingsWriter.write("\n");
		settingsWriter.write(backgroundTheme);
		settingsWriter.write("\n");
		settingsWriter.close();
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == returnButton) {
			sounds.klik();
			sounds.terminate();
			new Menu(login);
			tournamentFrame.dispose();
		}
		else if(e.getSource() == addPlayerButton && playerNumber < 6) {
			sounds.klik();
			playerNumber++;
			playerNumberLabel.setText(String.format("Liczba zawodników: %d", playerNumber));
		}
		else if(e.getSource() == removePlayerButton && playerNumber > 2) {
			sounds.klik();
			playerNumber--;
			playerNumberLabel.setText(String.format("Liczba zawodników: %d", playerNumber));
		}
		else if(e.getSource() == acceptButton) {
			sounds.klik();
			addPlayerButton.setEnabled(false);
			removePlayerButton.setEnabled(false);
			acceptButton.setEnabled(false);
			loginPanel.setVisible(true);
			loginButton.setEnabled(true);
		}
		else if (e.getSource() == loginButton) {
			sounds.klik();
			if (logged != playerNumber) {
				try {
					File uzytkownicyTXT = new File("uzytkownicy.txt");
					Scanner reader;
					reader = new Scanner(uzytkownicyTXT);
					while (reader.hasNextLine()) {
						String tmp = reader.nextLine();
						String[] data = tmp.split(" ", 0);
						String userLogin = data[0], userPassword = data[1];
						if (userLogin.equals(loginField.getText()) && userPassword.equals(passwordField.getText())) {
							FileWriter playersWriter = new FileWriter("turniej\\gracze.txt", true);
							logged++;
							playersWriter.write(userLogin);
							playersWriter.write("\n");
							playersWriter.close();
							loginField.setText("");
							passwordField.setText("");
							infoLabel.setText(String.format("Niech zaloguje sie gracz %d", logged + 1));
							if (logged == playerNumber) {
								loginButton.setText("Stwórz");
								infoLabel.setVisible(false);
							}
						}
						
					}
				} 
				catch (IOException e1) {}
			}
			else {
				System.out.println("koniec logowania");
			}
		}
	}

}
