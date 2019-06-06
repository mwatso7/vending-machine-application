package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class VendingMachine {
	
	private Map<String, Product> products = new HashMap<String, Product>();
	
	/*
	 * Constructors -- build map from text file
	 */
	
	public VendingMachine() {
		String inventoryFileName ="vendingmachine.txt";
		File inventory = new File(inventoryFileName);
		int i = 0;
		try(Scanner feeder = new Scanner(inventory)){
			
			while(feeder.hasNextLine()) {
				
				String[] item = feeder.nextLine().split(",");
				products.put(item[0], new Product(item[1], Double.parseDouble(item[2]), item[3], 5));
				i++;
				
				
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
