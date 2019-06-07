package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
	
	
	private final LocalDateTime currentDate = LocalDateTime.now();
	private final String fileName = "vending_log_" + currentDate + ".txt";
	private List<String> loggedMessages = new ArrayList<String>();
	
	
	public void logMessage(String message) {
		LocalDateTime logDate = LocalDateTime.now();
		String messageToLog = logDate + " " + message;
		loggedMessages.add(messageToLog);
		for (String msg : loggedMessages) {
			System.out.println(msg);
		}
			
	}
	
	public void printToLogFile() {
		File logFile = new File(fileName);
		 try(Scanner feeder = new Scanner(logFile)){
				
				while(feeder.hasNextLine()) {
					
					String[] item = feeder.nextLine().split(",");
					//products.put(item[0], new Product(item[1], Double.parseDouble(item[2]), item[3], 5));
					
				}
				
				/*
				for (String prod : products.keySet()) {
					System.out.println(products.get(prod));
				}*/
				
			}catch(IOException exception) {
				System.out.println("Unable to read from file" + exception);
			}
	}
}
