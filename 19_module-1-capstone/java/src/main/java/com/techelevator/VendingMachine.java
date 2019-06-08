package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class VendingMachine {
	
	/*
	 * Member Data
	 */

	private Map<String, Product> products = new TreeMap<String, Product>();
	private Map<String, Product> selections = new TreeMap<String, Product>();
	private final String inventoryFileName = "vendingmachine.txt";
	private double moneyTendered = 0;
	private double runningBalance = 0;

	private Logger vendingMachineLogger = new Logger();

	/*
	 * Constructors -- build map from text file
	 */

	public VendingMachine() {
		// create the file input and build inventory
		File inventory = new File(inventoryFileName);
		try (Scanner feeder = new Scanner(inventory)) {
			// Read in the inventory file; each line is parsed into a map of products
			while (feeder.hasNextLine()) {
				// create and array of strings for each character delimited value and place in to active inventory object
				String[] item = feeder.nextLine().split(",");
				products.put(item[0], new Product(item[1], Double.parseDouble(item[2]), item[3], 5));
			}
		} catch (IOException exception) {
			System.out.println("Unable to read from file" + exception);
		}

	}
	
	/*
	 * Methods -- getters
	 */
	
	// get the current value for money fed into vending machine
	public double getMoneyTendered() {
		return moneyTendered;
	}
	
	/*
	 * Methods -- other
	 */

	// Display a list of all available products in the vending machine
	public void displayProducts() {
		int i = 0;
		for (String prod : products.keySet()) {
			// create a new line after every 4 products(row of category)
			if (i % 4 == 0) {
				System.out.println();
			}
			i++;
			// display product and attributes 
			System.out.printf("%s", prod + " " + products.get(prod).getName() + " $" + products.get(prod).getCost() + " (" + products.get(prod).getStock() + ")");
			System.out.println();

		}
	}

	// Retrieve product selected for purchase
	public void selectProduct() {
		Scanner input = new Scanner(System.in);
		System.out.println("Please select a product using A1-D4: ");
		String index = input.nextLine();
		
		// Invalid product key requested
		if(!products.containsKey(index)) {
			System.out.println("This product does not exist: ");
		}
		// Selected product is out of stock
		else if(products.get(index).getStock() == 0) {
			System.out.println("Item is out of stock. Sorry :( ");
		}
		// Product was already selected; increase stock of selections by one
		else if (selections.containsKey(index)) {
			selections.get(index).setStock(selections.get(index).getStock() + 1);
			products.get(index).setStock(products.get(index).getStock() - 1);
			// log transaction
			vendingMachineLogger
					.logMessage(String.format("%-20s$%.2f\t$%.2f", selections.get(index).getName() + " " + index,
							runningBalance, +(runningBalance - selections.get(index).getCost())));
			runningBalance -= (selections.get(index).getCost());

		} 
		// if a new valid product selection, add product to selections
		else {
			selections.put(index, new Product(products.get(index).getName(), products.get(index).getCost(),
					products.get(index).getType(), 1));
			products.get(index).setStock(products.get(index).getStock() - 1);
			// log transaction
			vendingMachineLogger
					.logMessage(String.format("%-20s$%.2f\t$%.2f", selections.get(index).getName() + " " + index,
							runningBalance, +(runningBalance - selections.get(index).getCost())));
			runningBalance -= (selections.get(index).getCost());
		}
	}
	
	// Feed money into vending machine as requested
	public void feedMoney() {
		System.out.println("Please feed money in increments (1, 2, 5, 10): ");
		Scanner feeder = new Scanner(System.in);
		String dollaBill = feeder.nextLine();
		// Only accept legitimate currency as requested
		if(!dollaBill.equals("1") && !dollaBill.equals("2") && !dollaBill.equals("5") && !dollaBill.equals("10")) {
			System.out.println("Not a valid dollar amount. Try again.");
			feedMoney(); return;
		}
		// Updated money tendered and the running balance
		double currentTendered = Double.parseDouble(dollaBill);
		moneyTendered += Double.parseDouble(dollaBill);
		runningBalance += currentTendered;
		// log transaction
		vendingMachineLogger
				.logMessage(String.format("%-20s$%.2f" + "\t$%.2f", "Feed Money:", currentTendered, runningBalance));
	}

	// Calculate all purchases and dispense difference back to customer
	public void finishTransaction() {

		double totalCost = 0;
		double change;
		for (String prod : selections.keySet()) {
			totalCost += selections.get(prod).getCost() * selections.get(prod).getStock();
		}
		change = moneyTendered - totalCost;
		System.out.print("Money Tender: " + moneyTendered + "\nTotal Cost: " + totalCost + "\nChange: "
				+ makeChange(change) + "\n");

		// Customer eats all purchased products immediately for instant gratification
		productsConsumed();
		// create log file
		vendingMachineLogger.printToLogFile();
		// create sales report
		salesReport();
		// set transaction data to zero state following completion
		moneyTendered = 0;
		selections.clear();
	}
	
	// calculate exact change to return to customer
	private String makeChange(double change) {
		// check for loss of data in integer casting
		int coins = (Double.parseDouble(Integer.toString((int) (change * 100))) == change * 100.0)
				? (int) (change * 100)
				: (int) (change * 100) + 1;
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		quarters = (int) (coins / 25);
		coins %= 25;
		dimes = (int) (coins / 10);
		coins %= 10;
		nickels = (int) (coins / 5);

		return quarters + " Quarters, " + dimes + " Dimes, " + nickels + " Nickels";

	}
	
	// Create sounds of customer eating his purchases because he/she is incapable?
	private void productsConsumed() {
		for (String prod : selections.keySet()) {
			switch (selections.get(prod).getType()) {
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
	
	// read existing sales report, modify, and print back into file of same name
	public void salesReport() {
		File salesFile = new File("Sales_Report.txt");
		Map<String, Integer> sales = new TreeMap<String, Integer>();
		double totalSales = 0;
		// read existing sales report
		try (Scanner reader = new Scanner(salesFile)) {

			while (reader.hasNextLine()) {
				String rline= reader.nextLine();
				if (rline.equals("")) {
					continue;
				}
				if (rline.startsWith("**TOTAL SALES**")) {
					String[] report = rline.split(" ");
					totalSales = Double.parseDouble(report[2].substring(1));
					continue;
				}
				String[] report = rline.split(",");
				sales.put(report[0], Integer.parseInt(report[1]));
		
			}
		} catch (IOException exception) {
			System.out.println("Unable to read from file" + exception);
		}
		// update totals
		for (String prod : selections.keySet()) {
			if(sales.containsKey(selections.get(prod).getName())) {
				sales.put(selections.get(prod).getName(), sales.get(selections.get(prod).getName())+ selections.get(prod).getStock());
			}	
		}
		// right updated totals to sale report
		try {
			PrintWriter writer = new PrintWriter(salesFile);
			for(String prod : sales.keySet()) {
				writer.println(prod + "," + sales.get(prod));
				
			}
			totalSales += runningBalance;
			writer.printf("\n**TOTAL SALES** $%.2f", totalSales);
			writer.close();
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
	}

}
