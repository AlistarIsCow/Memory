package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerThread extends JLabel implements Runnable {
    int elapsedTime = 0;
    int seconds = 0;
    Timer timer;
    String time_string = String.format("%01d",seconds);
    public TimerThread(){
        this.setText(time_string+"s");
        this.setFont(new Font("Arial", Font.BOLD,20));
        this.setBounds(380,30,200,40);
        this.setOpaque(false);
        this.setHorizontalAlignment(JTextField.CENTER);
    }
    @Override
    public void run() {

        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 1000;
                seconds = elapsedTime/1000;
                time_string = String.format("%01d",seconds);
                TimerThread.super.setText(time_string+"s");
            }
        });
        timer.start();
    }
    public int stop(){
        timer.stop();
        return seconds;
    }
}
