package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Stats extends Main implements ActionListener {

	String login, userFile, historyString, wins, loses, draws, time;
	JFrame statsFrame;
	int minutes, seconds, hours;
	JLabel statsLabel, historyLabel;
	List<String> history = new ArrayList<String>();
	
	public Stats(String name){
		
		login = name;
		userFile = login + ".txt";
		
		statsLabel = new JLabel();
		statsLabel.setBounds(10, 10, 500, 140);
		statsLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		historyLabel = new JLabel();
		historyLabel.setBounds(10, 170, 500, 600);
		historyLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		historyLabel.setVerticalAlignment(JLabel.TOP);
		
		statsFrame = new JFrame();
		statsFrame.setTitle("Statystyki");
		statsFrame.setLayout(null);
		statsFrame.setResizable(false);
		statsFrame.setSize(500, 800);
		statsFrame.setVisible(true);
		statsFrame.getContentPane().setBackground(new Color(254, 228, 143));
		statsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		statsFrame.add(statsLabel);
		statsFrame.add(historyLabel);
		
		File userFileRead = new File(userFile), historyRead = new File("historia.txt");
		Scanner userReader, historyReader;
		try {
			userReader = new Scanner(userFileRead);
			wins = userReader.nextLine();
			loses = userReader.nextLine();
			draws = userReader.nextLine();
			time = userReader.nextLine();
			
			historyReader = new Scanner(historyRead);
			while (historyReader.hasNextLine()) {
				history.add(historyReader.nextLine());
				}
			int time1 = Integer.parseInt(time);
			hours = time1/3600;
			minutes = time1/60 %60;
			seconds = time1%60;
			historyString = "Historia ostatnich gier: ";
			for(int i = 0; i < Math.min(history.size(),20); i++) {
				historyString +="\n";
				historyString += history.get(history.size()-i-1);
			}
			
			statsLabel.setText(String.format("<html>Gracz: %s<br><br>Zwyciêstwa: %s<br>Pora¿ki: %s<br>Remisy: %s<br>£¹czny czas gry: %sh:%sm:%ss",login, wins, loses, draws, hours, minutes, seconds));
			historyLabel.setText(convertToMultiline(historyString));
			
		} 
		catch (FileNotFoundException e) {}
		
		}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static String convertToMultiline(String orig)
	{
	    return "<html>" + orig.replaceAll("\n", "<br>");
	}

}
