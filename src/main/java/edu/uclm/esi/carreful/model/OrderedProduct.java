package edu.uclm.esi.carreful.model;

public class OrderedProduct {
	private Product product;
	private double amount=0;
	private double prize=0;//cantidad de productos que compramos y precio
	
	public OrderedProduct(Product product, double amount, double prize) {
		this.product = product;
		this.amount= amount;
		this.prize=prize;
	}

	public double getPrize() {
		return prize;
	}

	public void setPrize(double prize) {
		this.prize = prize;
	}

	public void addAmount(double amount) {
		this.amount+=amount;
		
	}
	
	public void deleteAmount(double amount) {
		this.amount-=amount;
		
	}
	
	public double getAmount() {
		return amount;
		
	}
	public String getName() {
		return this.product.getNombre();
	}
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}
}
