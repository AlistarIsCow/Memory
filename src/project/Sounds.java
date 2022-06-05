package project;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.io.File;

public class Sounds extends Thread{
    Clip clip;
    FloatControl gainControl;
    boolean play = false, exit = false;
    public void run(){
    	if(!exit) {
    		try{
    			File musicPath = new File("klik.wav");
    			AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
    			clip = AudioSystem.getClip();
    			clip.open(audioInput);
    			if (play) {
    				clip.start();
    			}
    			gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    			gainControl.setValue(-25);  
    		} catch(Exception e){}
    	}
    }
    public void klik(){
    	play = true;
    	this.run();
    }
    public void terminate() {
    	exit = true;
    }
}