package com.techelevator;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class VendingMachine {
	
	private Map<String, Product> products = new TreeMap<String, Product>();
	private Map<String, Product> selections = new TreeMap<String, Product>();
	private double moneyTendered = 0;
	
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

	public void displayProducts() {int i = 1;
		for (String prod : products.keySet()) {
			if(i % 4 == 0) {
				System.out.println();
			}
			
			System.out.printf("%s%s%-25s", prod,  products.get(prod).getName() , products.get(prod).getCost());
			
			
			//System.out.println(products.get(prod));
		}
		
		
	}
	
	public void selectProduct() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please select a product using A1-D4: ");
		String index = input.nextLine();
		
		if(selections.containsKey(index) && products.get(index).getStock() > 0) {
			//selections.put(index, new Product(products.get(index).getName(), products.get(index).getCost(), products.get(index).getType(), selections.get(index).getStock() + 1));
			selections.get(index).setStock(selections.get(index).getStock() + 1);
			products.get(index).setStock(products.get(index).getStock()-1);

		} else if(products.get(index).getStock() > 0){ 
			selections.put(index, new Product(products.get(index).getName(), products.get(index).getCost(), products.get(index).getType(), 1));
			products.get(index).setStock(products.get(index).getStock()-1);;
		}
		for (String prod : selections.keySet()) {
			
			System.out.printf("%s%s%s%-25s", prod,  selections.get(prod).getName() , selections.get(prod).getCost(), Integer.toString(selections.get(prod).getStock()));
		
	}
	}
	
	
	public void feedMoney() {
		System.out.println("Please feed money in increments (1, 2, 5, 10): ");
		Scanner feeder = new Scanner(System.in);
		String dollaBill = feeder.nextLine();
		moneyTendered += Double.parseDouble(dollaBill);
	}
	
	public void finishTransaction() {
		
	}
	
}
