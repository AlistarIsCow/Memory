package project;

import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Card extends JButton{

	int pair; 
	String theme;
	boolean isPicked = false;
	ImageIcon scaledReverseIcon, scaledFrontIcon;
	
	public Card(int pair, String theme, int width, int height) {
		
		this.pair = pair;
		this.theme = theme;
		
		this.setBorderPainted(false);
		this.setSize(Math.floorDiv(width + height, 2), height);
		this.setOpaque(false);
		this.setFocusable(false);
		this.setBackground(Color.LIGHT_GRAY);
		
		ImageIcon reverseIcon = null , frontIcon = null;
		if (theme.equals("kolory")) {reverseIcon = new ImageIcon("images\\kolory\\reverse.png");}
		else if (theme.equals("owocki")) {reverseIcon = new ImageIcon("images\\owocki\\reverse.png");}
		else if (theme.equals("potworki")) {reverseIcon = new ImageIcon("images\\potworki\\reverse.png");}
		else if (theme.equals("zwierzaczki")) {reverseIcon = new ImageIcon("images\\zwierzaczki\\reverse.png");}
		
		Image image = reverseIcon.getImage();
		Image imageScaled;
		if(width > height) {imageScaled = image.getScaledInstance(height, height, Image.SCALE_SMOOTH);}
		else {imageScaled = image.getScaledInstance(width, width, Image.SCALE_SMOOTH);}
		scaledReverseIcon = new ImageIcon(imageScaled);
		this.setIcon(scaledReverseIcon);
		
		if (theme.equals("kolory")) {frontIcon = new ImageIcon("images\\kolory\\" + pair + ".png");}
		else if  (theme.equals("owocki")) {frontIcon = new ImageIcon("images\\owocki\\" + pair + ".png");}
		else if  (theme.equals("potworki")) {frontIcon = new ImageIcon("images\\potworki\\" + pair + ".png");}
		else if  (theme.equals("zwierzaczki")) {frontIcon = new ImageIcon("images\\zwierzaczki\\" + pair + ".png");}
	
		image = frontIcon.getImage();
		if(width > height) {imageScaled = image.getScaledInstance(height, height, Image.SCALE_SMOOTH);}
		else {imageScaled = image.getScaledInstance(width, width, Image.SCALE_SMOOTH);}
		scaledFrontIcon = new ImageIcon(imageScaled);
	}
	
	public void changeIcon() {
		if(this.getIcon() == scaledReverseIcon) {this.setIcon(scaledFrontIcon);}
		else {this.setIcon(scaledReverseIcon);}
	}
		
	public void isPicked(boolean picked) {
		isPicked = picked;
	}
	
	public int getPair() {
		return pair;
	}
}
