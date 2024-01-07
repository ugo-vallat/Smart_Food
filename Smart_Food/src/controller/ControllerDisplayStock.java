package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import kernel.stock.Product;
import kernel.stock.Product.Unit;
import kernel.stock.Ration;
import kernel.stock.StockManager;

public class ControllerDisplayStock {
	private StockManager stock;
	
	public ControllerDisplayStock(StockManager stock) {
		this.stock = stock;
	}
	
	public List<Product> getSortedListProducts (OrderProduct order) {
		Comparator<Product> comparator;
		switch(order) {
			case NAME:{
				comparator = (Product o1, Product o2) -> 
					o1.getName().compareTo(o2.getName());
				break;
			}
			case IN_STOCK_INCREASING: {
				comparator = (Product o1, Product o2) -> {
						Float q1 = stock.getStockOfProduct(o1);
						Float q2 = stock.getStockOfProduct(o2);
						if(o1.getUnit() == Unit.KG) q1 *= 1000;
						if(o2.getUnit() == Unit.KG) q2 *= 1000;
						return q1.compareTo(q2);
				};
				break;
			}
			case IN_STOCK_DECREASING: {
				comparator = (Product o1, Product o2) -> {
						Float q1 = stock.getStockOfProduct(o1);
						Float q2 = stock.getStockOfProduct(o2);
						if(o1.getUnit() == Unit.KG) q1 *= 1000;
						if(o2.getUnit() == Unit.KG) q2 *= 1000;
						return q2.compareTo(q1);
				};
				break;
			}
			default: {
				comparator = Comparable::compareTo;
				break;
			}
		}
		
		List<Product> inStock = new ArrayList<>(stock.getProductSet());
		Collections.sort(inStock, comparator);
		return inStock;
	}
	
	public List<RationSheet> getSortedListRation (OrderRation order) {
		Comparator<RationSheet> comparator;
		switch(order) {
			case DATE_INCREASING:{
				comparator = (RationSheet o1, RationSheet o2) -> 
					o1.getExpiration().compareTo(o2.getExpiration());
				break;
			}
			case DATE_DECREASING: {
				comparator = (RationSheet o1, RationSheet o2) -> 
				o2.getExpiration().compareTo(o1.getExpiration());
				break;
			}
			default: {
				comparator = Comparable::compareTo;
				break;
			}
		}
		
		Set<Product> setProduct = stock.getProductSet();
		List<RationSheet> rations = new ArrayList<>();
		for(Product p : setProduct) {
			for(Ration r : stock.getRationsOfProduct(p)) {
				rations.add(new RationSheet(p, r));
			}
		}
		
		Collections.sort(rations, comparator);
		return rations;
	}
	
	
	
	public enum OrderProduct {
		NAME, IN_STOCK_INCREASING, IN_STOCK_DECREASING;
	}
	
	public enum OrderRation {
		DATE_INCREASING, DATE_DECREASING;
	}
}
