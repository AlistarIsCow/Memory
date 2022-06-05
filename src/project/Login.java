package project;

import java.awt.Color;
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
import javax.swing.JTextField;

public class Login extends Main implements ActionListener{

	JButton newAccountButton, loginButton;
	JLabel loginLabel, passwordLabel, errorMessage;
	JTextField loginField, passwordField;
	JFrame loginFrame;
	boolean logged = false, loginExists = false;
	Sounds sounds = new Sounds();
	
	public Login(){
		
		loginLabel = new JLabel();
		loginLabel.setText("Nazwa u¿ytkownika:");
		loginLabel.setBounds(20, 20, 120, 15);
		
		passwordLabel = new JLabel();
		passwordLabel.setText("Has³o:");
		passwordLabel.setBounds(100, 60, 150, 15);
		
		errorMessage = new JLabel();
		errorMessage.setText("Niepoprawne dane logowania");
		errorMessage.setForeground(Color.red);
		errorMessage.setBounds(100, 90, 200, 15);
		errorMessage.setVisible(false);
		
		loginField = new JTextField();
		loginField.setBounds(140, 18, 200, 20);
		
		passwordField = new JTextField();
		passwordField.setBounds(140, 58, 200, 20);
		
		newAccountButton = new JButton();
		newAccountButton.setBounds(0, 111, 150, 50);;
		newAccountButton.addActionListener(this);
		newAccountButton.setFocusable(false);
		newAccountButton.setText("Stwórz konto");
		
		loginButton = new JButton();
		loginButton.setBounds(184, 111, 200, 50);;
		loginButton.addActionListener(this);
		loginButton.setFocusable(false);
		loginButton.setText("Zaloguj");
		
		loginFrame = new JFrame();
		loginFrame.setTitle("Okno logowania");
		loginFrame.setLayout(null);
		loginFrame.setResizable(false);
		loginFrame.setSize(400, 200);
		loginFrame.setVisible(true);
		loginFrame.getContentPane().setBackground(Color.white);
		loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		loginFrame.add(newAccountButton);
		loginFrame.add(loginButton);
		loginFrame.add(loginLabel);
		loginFrame.add(passwordLabel);
		loginFrame.add(loginField);
		loginFrame.add(passwordField);
		loginFrame.add(errorMessage);
		sounds.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == newAccountButton) {	
			sounds.klik();
			errorMessage.setVisible(false);
			errorMessage.setText("Login zajêty");
			loginButton.setText("Stwórz");
			loginExists = false;
			loginField.setText("");
			passwordField.setText("");
			newAccountButton.setVisible(false);
								
		}
		
		if(e.getSource() == loginButton && loginButton.getText().equals("Stwórz")) {
			loginExists = false;
			sounds.klik();
			try {
	    		File uzytkownicyTXTread = new File("uzytkownicy.txt");
				Scanner reader = new Scanner(uzytkownicyTXTread);
				while (reader.hasNextLine() && !loginExists) {
					String tmp = reader.nextLine();
					String[] data = tmp.split(" ", 0);
					String userLogin = data[0];
					if (userLogin.equals(loginField.getText())) {
						loginExists = true;
						errorMessage.setVisible(true);			
					}
				}
				if (!loginExists) {
					String userLogin = loginField.getText(), userPassword = passwordField.getText(), loginTXT = userLogin + ".txt";
					String loginPassword = userLogin + " " + userPassword;
					logged = true;
					
					File uzytkownicyTXTwrite = new File("uzytkownicy.txt");
					FileWriter uzytkownicyWriter = new FileWriter(uzytkownicyTXTwrite, true);
					uzytkownicyWriter.write(loginPassword);
					uzytkownicyWriter.write("\n");
					uzytkownicyWriter.close();
					
					File tmpFile = new File(loginTXT);
					tmpFile.createNewFile();
					FileWriter loginWriter = new FileWriter(tmpFile, true);
					loginWriter.write("0");
					loginWriter.write("\n");
					loginWriter.write("0");
					loginWriter.write("\n");
					loginWriter.write("0");
					loginWriter.write("\n");
					loginWriter.write("0");
					loginWriter.write("\n");
					loginWriter.close();
					sounds.terminate();
					loginFrame.dispose();
					Menu menu = new Menu(userLogin);
				}
			} 
			catch (FileNotFoundException ee) {
				System.out.println("B³¹d");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		if(e.getSource() == loginButton && loginButton.getText().equals("Zaloguj")) {
			sounds.klik();
			try {
	    		File uzytkownicyTXT = new File("uzytkownicy.txt");
				Scanner reader = new Scanner(uzytkownicyTXT);
				while (reader.hasNextLine() && !logged) {
					String tmp = reader.nextLine();
					String[] data = tmp.split(" ", 0);
					String userLogin = data[0], userPassword = data[1];
					if (userLogin.equals(loginField.getText()) && userPassword.equals(passwordField.getText())) {
						logged = true;
						sounds.terminate();
						loginFrame.dispose();
						Menu menu = new Menu(userLogin);
					}
				}
				if (!logged) {
					errorMessage.setVisible(true);
				}

			} 
			catch (FileNotFoundException ee) {
				System.out.println("B³¹d");
			}
		}
		
	}
	
}
