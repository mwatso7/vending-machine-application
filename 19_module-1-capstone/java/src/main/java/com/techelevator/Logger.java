package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
	
	
	private final LocalDateTime currentDate = LocalDateTime.now();
	//private final String fileName = "vending_log_" + currentDate + ".txt";
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
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		String fileName = ("log_" + currentDate.format(formatDate) + ".txt");
		File logFile = new File(fileName);
		 try(PrintWriter writer = new PrintWriter(logFile)){
				
				for(String msg : loggedMessages) {
					
					writer.println(msg);
					
				}
				writer.close();
				/*
				for (String prod : products.keySet()) {
					System.out.println(products.get(prod));
				}*/
				
			}catch(IOException exception) {
				System.out.println("Unable to read from file" + exception);
			}
	}
}
