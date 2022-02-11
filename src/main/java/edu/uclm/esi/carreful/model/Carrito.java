package edu.uclm.esi.carreful.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Carrito {

	private HashMap<String, OrderedProduct> products;
	private double totalPrice;

	public Carrito() {
		this.products = new HashMap<>();
	}

	public void add(Product product, double amount) {
		// aniadir producto a una coleccion de productos ordenados
		double productoPrize = 0;
		double totalPrize = 0;
		OrderedProduct orderedProduct = this.products.get(product.getIdProduct());
		if (orderedProduct == null) {
			orderedProduct = new OrderedProduct(product, amount, Math.round(product.getPrecio() * 100.0) / 100.0);
			if (orderedProduct.getAmount() <= product.getStock() && product.getStock() > 0)
				this.products.put(product.getIdProduct(), orderedProduct);
		} else {// si está sumamos al producto ordenado la cantidad
			if (orderedProduct.getAmount() + 1 <= product.getStock()) {
				orderedProduct.addAmount(amount);
				orderedProduct.setPrize(orderedProduct.getAmount() * Math.round(product.getPrecio() * 100.0) / 100.0);
			}
		}

		Collection<OrderedProduct> cProducts = this.products.values();
		for (OrderedProduct op : cProducts) {
			productoPrize = op.getPrize();
			totalPrize += productoPrize;
			totalPrize = Math.round(totalPrize * 100.0) / 100.0;
		}
		this.totalPrice = totalPrize;
	}

	public void delete(Product product, double amount) {
		// aniadir producto a una coleccion de productos ordenados
		double productoPrize = 0;
		double totalPrize = 0;

		OrderedProduct orderedProduct = this.products.get(product.getIdProduct());
		if (orderedProduct.getAmount() == 1) {
			this.products.remove(product.getIdProduct(), orderedProduct);
		}

		orderedProduct.deleteAmount(amount);
		orderedProduct.setPrize(orderedProduct.getAmount() * Math.round(product.getPrecio() * 100.0) / 100.0);
		Collection<OrderedProduct> cProducts = this.products.values();
		for (OrderedProduct op : cProducts) {
			productoPrize = op.getPrize();
			totalPrize += productoPrize;
			totalPrize = Math.round(totalPrize * 100.0) / 100.0;
		}
		this.totalPrice = totalPrize;
	}

	public Collection<OrderedProduct> getProducts() {
		return products.values();
	}

	public Collection<Product> productosCarrito() {
		Collection<Product> collection = new ArrayList<>();
		for (Map.Entry<String, OrderedProduct> op : products.entrySet()) {
			collection.add(op.getValue().getProduct());
		}
		return collection;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
}
