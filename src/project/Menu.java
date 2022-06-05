package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;


public class Menu extends Main implements ActionListener{

	String login;
	JFrame menuFrame;
	JLabel sizeLabel, amountLabel, backgroundLabel, themeLabel;
	JPanel optionsPanel;
	JButton pvpButton, pveButton, tournamentButton, statsButton, exitButton;
	JSlider sizeSlider;
	JComboBox backgroundBox, themeBox;
	String [] backgroundTable = {"koszyk", "okno", "kosmos", "szare"}, themeTable = {"owocki", "zwierzaczki", "potworki", "kolory"};
	Sounds sounds = new Sounds();
	int size;
	
	public Menu(String name){
		
		optionsPanel = new JPanel();
		optionsPanel.setBackground(new Color(255,255,255));
		optionsPanel.setBounds(875, 0, 325, 600);
		
		pvpButton = new JButton();
		pvpButton.setBounds(100, 100, 300, 150);;
		pvpButton.addActionListener(this);
		pvpButton.setFocusable(false);
		pvpButton.setText("Zagraj vs gracz");
		pvpButton.setFont(new Font("Arial", Font.PLAIN, 38));
		
		pveButton = new JButton();
		pveButton.setBounds(450, 100, 300, 150);;
		pveButton.addActionListener(this);
		pveButton.setFocusable(false);
		pveButton.setText("Zagraj vs AI");
		pveButton.setFont(new Font("Arial", Font.PLAIN, 38));
		
		tournamentButton = new JButton();
		tournamentButton.setBounds(275, 300, 300, 150);;
		tournamentButton.addActionListener(this);
		tournamentButton.setFocusable(false);
		tournamentButton.setText("Turniej");
		tournamentButton.setFont(new Font("Arial", Font.PLAIN, 60));
		
		sizeLabel = new JLabel();
		sizeLabel.setText("Iloœæ kart:");
		sizeLabel.setBounds(995, 10, 150, 20);
		sizeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		sizeSlider = new JSlider(0,2,0);
		sizeSlider.setBounds(960, 30, 140, 20);
		sizeSlider.setBackground(Color.white);
		sizeSlider.setPaintLabels(true);
		sizeSlider.setPaintTrack(false);
		
		amountLabel = new JLabel();
		amountLabel.setText("12         20        30");
		amountLabel.setBounds(957, 50, 150, 20);
		amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		backgroundLabel = new JLabel();
		backgroundLabel.setText("Wybór t³a:");
		backgroundLabel.setBounds(990, 90, 150, 20);
		backgroundLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		backgroundBox = new JComboBox(backgroundTable);
		backgroundBox.setBounds(960, 115, 150, 20);
		
		themeLabel = new JLabel();
		themeLabel.setText("Wybór motywu:");
		themeLabel.setBounds(975, 160, 150, 20);
		themeLabel.setFont(new Font("Arial", Font.PLAIN, 18));
		
		themeBox = new JComboBox(themeTable);
		themeBox.setBounds(960, 185, 150, 20);
		
		statsButton = new JButton();
		statsButton.setBounds(874, 265, 310, 150);;
		statsButton.addActionListener(this);
		statsButton.setFocusable(false);
		statsButton.setText("Statystyki");
		statsButton.setFont(new Font("Arial", Font.PLAIN, 50));
		
		exitButton = new JButton();
		exitButton.setBounds(874, 415, 310, 150);;
		exitButton.addActionListener(this);
		exitButton.setFocusable(false);
		exitButton.setText("Wyjœcie");
		exitButton.setFont(new Font("Arial", Font.PLAIN, 50));
		
		login = name;
		menuFrame = new JFrame();
		menuFrame.setTitle("Menu");
		menuFrame.setLayout(null);
		menuFrame.setResizable(false);
		menuFrame.setSize(1200, 600);
		menuFrame.setVisible(true);
		menuFrame.getContentPane().setBackground(new Color(0, 255, 255));
		menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuFrame.add(pvpButton);
		menuFrame.add(pveButton);
		menuFrame.add(tournamentButton);
		menuFrame.add(sizeLabel);
		menuFrame.add(sizeSlider);
		menuFrame.add(amountLabel);
		menuFrame.add(backgroundLabel);
		menuFrame.add(backgroundBox);
		menuFrame.add(themeLabel);
		menuFrame.add(themeBox);
		menuFrame.add(statsButton);
		menuFrame.add(exitButton);
		menuFrame.add(optionsPanel);
		sounds.start();

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == exitButton) {
			sounds.klik();
			menuFrame.dispatchEvent(new WindowEvent(menuFrame, WindowEvent.WINDOW_CLOSING));
		}
		else if(e.getSource() == statsButton) {
			sounds.klik();
			new Stats(login);
		}
		else if (e.getSource() == pvpButton) {
			if (sizeSlider.getValue() == 0) {size = 12;}
			else if (sizeSlider.getValue() == 1) {size = 20;}
			else  {size = 30;}
			sounds.klik();
			sounds.terminate();
			menuFrame.dispose();
			new Game(login, "Gracz nr 2", size, themeBox.getSelectedItem().toString(), backgroundBox.getSelectedItem().toString(), false);
		}
		else if (e.getSource() == pveButton) {
			if (sizeSlider.getValue() == 0) {size = 12;}
			else if (sizeSlider.getValue() == 1) {size = 20;}
			else  {size = 30;}
			sounds.klik();
			sounds.terminate();
			menuFrame.dispose();
			new Game(login, "Bot", size, themeBox.getSelectedItem().toString(), backgroundBox.getSelectedItem().toString(), true);
		}
		else if (e.getSource() == tournamentButton) {
			if (sizeSlider.getValue() == 0) {size = 12;}
			else if (sizeSlider.getValue() == 1) {size = 20;}
			else  {size = 30;}
			sounds.klik();
			sounds.terminate();
			try {
				new Tournament(login, size, themeBox.getSelectedItem().toString(), backgroundBox.getSelectedItem().toString());
			} catch (IOException e1) {}
			menuFrame.dispose();
		}
		
	}

}
