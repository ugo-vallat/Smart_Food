package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import kernel.stock.ExpirationDate;
import kernel.stock.Product;
import kernel.stock.Ration;
import kernel.stock.StockManager;
import myUtils.Logger;

public class ControllerStockManager {
	private StockManager stock;
	
	public ControllerStockManager(StockManager stock) {
		this.stock = stock;
	}
	
	public boolean createProduct(ProductSheet sproduct) {
		if(sproduct == null || sproduct.isDegenerated())return false;
		return stock.addProduct(new Product(sproduct.getName(), sproduct.getUnit()));	
	}
	
	public boolean addStockToProduct(ProductSheet sproduct) {
		if(sproduct == null || sproduct.getName() == null)
			return false;

		Product p = new Product(sproduct.getName(), sproduct.getUnit());
		if(!stock.existProduct(p))
			return false;
		
		boolean res = true;
		for(Ration r : sproduct.getRations()) {
			if(r != null && r.isValid()) {
				res &= stock.addRation(p, r);
			} else { 
				res = false;
			}
		}
		return res;
	}
	
	public boolean removeStockToProduct(ProductSheet sproduct) {
		if(sproduct == null || sproduct.getName() == null)
			return false;
		
		Product p = new Product(sproduct.getName(), sproduct.getUnit());
		if(!stock.existProduct(p))
			return false;
		
		boolean res = true;
		for(Ration r : sproduct.getRations()) {
			if(r != null && r.isValid()) {
				res &= r.getQuantity().equals(stock.removeRation(p, r));
			}
		}
		return res;
	}
	
	public boolean existProduct(Product product) {
		return stock.existProduct(product);
	}
	
	public String previewStock() {
		return stock.toString();
	}
	
	public Set<Product> getProductSet() {
		return stock.getProductSet();
	}
	
	public List<Ration> getRationsOfProduct(Product product) {
		if(product == null) {
			Logger.warning("produit null");
			return new ArrayList<>();
		}
		if(product.getName() == null) {
			Logger.warning("nom produit null");
			return new ArrayList<>();
		}
		return stock.getRationsOfProduct(product);
	}
	
	public Float getStockOfProduct(Product product) {
		if(product == null || product.getName() == null)
			return 0f;
		return stock.getStockOfProduct(product);
	}
	
	public boolean deleteProduct(Product product) {
		if(product == null) {
			Logger.error("product null");
			return false;
		}
		if(!stock.existProduct(product)) {
			Logger.warning("produit introuvable");
			return false;
		}
		return stock.deleteProduct(product);
	}
	
	public Float removeRationWithQuantity(Product product, Float quantity) {
		if(!existProduct(product)) {
			Logger.warning("Produit introuvable");
			return 0f;
		}
		return stock.removeRation(product, quantity);
	}
	
	public Float removeRationWithDate(Product product, ExpirationDate date) {
		return stock.removeRation(product, date);
	}
	
	public Float removeRationWithQuantityAndDate(Product product, ExpirationDate expiration, Float quantity) {
		return stock.removeRation(product, quantity, expiration);
	}
	
	
	public Float emptyStockOfProduct(Product product) {
		if(product == null) {
			Logger.error("product null");
			return -1f;
		}
		if(!stock.existProduct(product)) {
			Logger.warning("produit introuvable");
			return -1f;
		}
		
		return stock.emptyStockOfProduct(product);
	}
	
	public void emptyStock() {
		stock.emptyStock();
	}
	
	public void importFromCSV(String path) {
		stock.importFromCSV(path);
	}
	
	public void exportToCSV(String path) {
		stock.exportToCSV(path);
	}

}
