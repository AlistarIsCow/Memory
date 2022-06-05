package project;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ResultScreen extends Main {
	
	String winner;
	int time;
	JFrame resultFrame;
	JLabel resultLabel;
	
	ResultScreen(String name, int seconds){
		
		time = seconds;
		winner = name;
		
		resultLabel = new JLabel();
		resultLabel.setBounds(0, 0, 400, 100);
		resultLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		resultLabel.setHorizontalAlignment(JTextField.CENTER);
		resultLabel.setVerticalAlignment(JTextField.CENTER);
		resultLabel.setText(String.format("Zwyciê¿y³ %s w czasie %d sekund!!!", winner, time));
		if (name.equals("Remis")) {
			resultLabel.setText(String.format("Remis w czasie %d sekund!", time));
		}
		
		
		resultFrame = new JFrame();
		resultFrame.setTitle("Wynik spotkania");
		resultFrame.setLayout(null);
		resultFrame.setResizable(false);
		resultFrame.setSize(400, 150);
		resultFrame.setVisible(true);
		resultFrame.getContentPane().setBackground(Color.white);
		resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		resultFrame.add(resultLabel);
		
	}

}
