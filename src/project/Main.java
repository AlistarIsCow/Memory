package project;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;  
import javax.swing.JLabel;  

public class Main{
	
    public static void main(String[] args) throws InterruptedException, IOException {

    	Login login = new Login();
    	Music music = new Music();
    	music.start();
    	
    	//Menu menu = new Menu("login1");
    	//Stats stats = new Stats("login1");
    	//Game game = new Game("login1", "login2", 30, "potworki", "kosmos", false);			1v1
    	//Game game = new Game("login1", "Bot", 12, "potworki", "kosmos", true;					vs AI
    	//Tournament tournament = new Tournament("login1", 30, "owocki", "kosmos");
    	//SavingThread savingThread = new SavingThread("login1", "drugi gracz", -1, 100);
    	//savingThread.run();
    	//TimerThread timer = new TimerThread();
    }
}