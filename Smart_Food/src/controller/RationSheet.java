package controller;

import kernel.stock.ExpirationDate;
import kernel.stock.Product;
import kernel.stock.Product.Unit;
import kernel.stock.Ration;

public class RationSheet implements Comparable<RationSheet>{
	private Product product;
	private ExpirationDate expiration;
	private Float quantity;
	
	public RationSheet(Product product, ExpirationDate expiration, Float quantity) {
		this.product = product;
		this.expiration = expiration;
		this.quantity = quantity;
	}
	
	public RationSheet(Product product, Ration ration) {
		this.product = product;
		this.expiration = ration.getExpiration();
		this.quantity = ration.getQuantity();
	}
	
	public Product getProduct() {
		return product;
	}
	
	public ExpirationDate getExpiration() {
		return expiration;
	}
	
	public Float getQuantity() {
		return quantity;
	}
	
	public String getProductName() {
		return product.getName();
	}
	
	public Unit getProductUnit() {
		return product.getUnit();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof RationSheet)) return false;
		RationSheet r = (RationSheet)obj;
		return product.equals(r.product) &&
				expiration.equals(r.expiration) &&
				quantity.equals(r.quantity);
	}
	
	@Override
	public int hashCode() {
		return product.hashCode()*expiration.hashCode()*quantity.hashCode()*31;
	}
	
	@Override
	public int compareTo(RationSheet o) {
		int order = product.compareTo(o.product);
		if(order != 0) return order;
		order = expiration.compareTo(o.expiration);
		if(order != 0) return order;
		return quantity.compareTo(o.quantity);
	}
	
	
	
	

}
