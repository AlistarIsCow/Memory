package project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SavingThread extends Main implements Runnable{

	String player, opponent, fileName;
	int result, time, wins, loses, draws, seconds;
	
	public SavingThread(String name1, String name2, int result1, int time1){
		
		player = name1;
		opponent = name2;
		result = result1;
		time = time1;
		fileName = player + ".txt";
	}
	
	
	@Override
	public void run() {
		try {
			File fileFile = new File(fileName), historyFile = new File("historia.txt");
			Scanner fileReader = new Scanner(fileFile);
			wins = Integer.parseInt(fileReader.nextLine());
			loses = Integer.parseInt(fileReader.nextLine());
			draws = Integer.parseInt(fileReader.nextLine());
			seconds = Integer.parseInt(fileReader.nextLine());
			seconds += time;
			FileWriter historyWriter = new FileWriter(historyFile, true);
			if (result == 1) {
				wins++;
				historyWriter.write(String.format("%s pokonal %s w czasie %d sekund.", player, opponent, time));
				historyWriter.write("\n");
				historyWriter.close();
			}
			else if (result == 0) {
				draws++;
				historyWriter.write(String.format("%s zremisowal z %s po czasie %d sekund.", player, opponent, time));
				historyWriter.write("\n");
				historyWriter.close();
			}
			else if (result == -1) {
				loses++;
				historyWriter.write(String.format("%s przegral z %s w czasie %d sekund.", player, opponent, time));
				historyWriter.write("\n");
				historyWriter.close();
			}
			FileWriter fileWriter = new FileWriter(fileFile, false);
			fileWriter.write(Integer.toString(wins));
			fileWriter.write("\n");
			fileWriter.close();
			fileWriter = new FileWriter(fileFile, true);
			fileWriter.write(Integer.toString(loses));
			fileWriter.write("\n");
			fileWriter.write(Integer.toString(draws));
			fileWriter.write("\n");
			fileWriter.write(Integer.toString(seconds));
			fileWriter.write("\n");
			fileWriter.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
