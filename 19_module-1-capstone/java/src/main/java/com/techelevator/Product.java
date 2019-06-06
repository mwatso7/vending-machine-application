package com.techelevator;

import java.math.BigDecimal;

public class Product {
	private String name;
	private String type;
	private double cost;
	private int stock;
	
	public Product(String name, double cost, String type, int stock) {
		this.name = name;
		this.type = type;
		this.cost = cost;
		this.stock = stock;
	}
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setStock(int stock) {
		
		this.stock = (stock > 0) ? stock:0;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStock() {
		return stock;
	}
	
	public String getType() {
		return type;
	}
	
	public double getCost() {
		return cost;
	}
	
	public String toString(){
		return name + ", " + cost + ", " + type + ", " + stock;
	}
	private void updateStock(String index) {
		//selections.put(index, new Product(products.get(index).getName(), products.get(index).getCost(), products.get(index).getType(), selections.get(index).getStock() - 1));
	}
}
