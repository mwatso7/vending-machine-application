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
	
	/*
	 * Member Data
	 */
	
	private final LocalDateTime currentDate = LocalDateTime.now();
	private List<String> loggedMessages = new ArrayList<String>();
	
	/*
	 * Methods -- other
	 */
	
	// add log message to list of messages
	public void logMessage(String message) {
		LocalDateTime logDate = LocalDateTime.now();
		String messageToLog = logDate + " " + message;
		loggedMessages.add(messageToLog);
	}
	
	// print all logged messages to log file
	public void printToLogFile() {
		DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		String fileName = ("log_" + currentDate.format(formatDate) + ".txt");
		File logFile = new File(fileName);
		
		try(PrintWriter writer = new PrintWriter(logFile)){
			for(String msg : loggedMessages) {
				writer.println(msg);	
			}
			writer.close();	
		}catch(IOException exception) {
			System.out.println("Unable to read from file" + exception);
		}
	}
}
