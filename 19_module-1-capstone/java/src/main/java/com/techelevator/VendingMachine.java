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

	public void displayProducts() {
		int i = 1;
		for (String prod : products.keySet()) {
			if(i % 4 == 0) {
				System.out.println();
			}
			i++;
			System.out.printf("%s%s%-25s", prod,  products.get(prod).getName() , products.get(prod).getCost());
			
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
		
		double totalCost = 0;
		double change;
		for (String prod : selections.keySet()) {
			totalCost += selections.get(prod).getCost() * selections.get(prod).getStock();
		}
		change = moneyTendered - totalCost;
		System.out.print("Money Tender: " + moneyTendered + "\nTotal Cost: " + totalCost + "\nChange: " + makeChange(change) + "\n");
		
		productsConsumed();
		
		moneyTendered = 0;
		selections.clear();
	}
	
	private String makeChange(double change) {
		/*BigDecimal cents = new BigDecimal(change*100.00);
		BigDecimal quarters = new BigDecimal(0.00);
		BigDecimal dimes = new BigDecimal(0.00);
		BigDecimal nickels = new BigDecimal(0.00);
		if (cents.compareTo(new BigDecimal(25.00)) >= 0) {
			quarters = cents.divide(new BigDecimal(25.00));
			cents = cents.subtract(quarters.multiply(new BigDecimal(25.00)));
		}
		if (cents.compareTo(new BigDecimal(10.00)) >= 0) {
			dimes = cents.divide(new BigDecimal(10.00));
			cents = cents.subtract(dimes.multiply(new BigDecimal(10.00)));
		}
		if (cents.compareTo(new BigDecimal(5.00)) >= 0) {
			nickels = cents.divide(new BigDecimal(5.00));
			cents = cents.subtract(nickels.multiply(new BigDecimal(5.00)));
		}*/
		//System.out.println(change);
		int coins = (int)(change*100) + 1;
		//System.out.println(coins);
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		quarters = (int)(coins/25);
	    coins %= 25;
	    dimes = (int)(coins/10);
	    coins %= 10;
	    nickels = (int)(coins/5);
	    coins %= 5;

		return quarters + " Quarters, " + dimes + " Dimes, " + nickels + " Nickels";
	    
	}
	
	private void productsConsumed() {
		for (String prod : selections.keySet()) {
			switch(selections.get(prod).getType()) {
				case "Chip":
					System.out.println("Crunch Crunch, Yum!");
					break;
				case "Candy":
					System.out.println("Munch Munch, Yum!");
					break;
				case "Drink":
					System.out.println("Glug Glug, Yum!");
					break;
				case "Gum":
					System.out.println("Chew Chew, Yum!");
					break;
			}
		}
		
	}
	
	
	
	
	
}
