package kernel.stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import kernel.stock.Product.Unit;
import myUtils.Logger;

public class StockGestionnary implements Iterable<Product>{
	Set<Product> productStock;
	Map<Product, TreeMap<ExpirationDate, Float>> stock = new HashMap<>();
	
	
	public boolean existProduct(Product product) {
		return stock.containsKey(product);
	}
	
	
	public Set<Product> getProductSet() {
		return stock.keySet();
	}
	
	public Float getStockOfProduct(Product product) {
		Float inStock = 0f;
		if(!existProduct(product)) return inStock;
		for(Float q : stock.get(product).values()) {
			inStock += q;
		}
		return inStock;
	}
	
	public List<Ration> getRationsOfProduct(Product product) {
		List<Ration> l = new ArrayList<>();
		for(ExpirationDate date : stock.get(product).keySet()) {
			l.add(new Ration(date, stock.get(product).get(date)));
		}
		return l;
	}
	
	public boolean addProduct(Product product) {
		if(stock.containsKey(product)) return false;
		stock.put(product, new TreeMap<>());
		return true;
	}
	
	public boolean deleteProduct(Product product) {
		return stock.remove(product) != null;
	}
	
	
	public boolean addRation(Product product, Float quantity, ExpirationDate expiration) {
		if(!existProduct(product)) {
			myUtils.Logger.warning("produit n'existe pas");
			return false;
		}
		Map<ExpirationDate, Float> stockProduct = stock.get(product);
		if(stockProduct.containsKey(expiration)) {
			stockProduct.put(expiration, stockProduct.get(expiration) + quantity);
		} else {
			stockProduct.put(expiration, quantity);
		}
		return true;
	}
	
	public boolean addRation(Product product, Ration ration) {
		if(!existProduct(product)) return false;
		Map<ExpirationDate, Float> stockProduct = stock.get(product);
		if(stockProduct.containsKey(ration.getExpiration())) {
			stockProduct.put(ration.getExpiration(), stockProduct.get(ration.getExpiration()) + ration.getQuantity());
		} else {
			stockProduct.put(ration.getExpiration(), ration.getQuantity());
		}
		return true;
	}
	
	public Float removeRation(Product product, Float quantity, ExpirationDate expiration) {
		Float removed = 0f;
		if(!existProduct(product) || !stock.get(product).containsKey(expiration)) return removed;

		Float inStock = stock.get(product).get(expiration);
		if(inStock > quantity) {
			stock.get(product).put(expiration, inStock-quantity);
			removed = quantity;
		} else {
			removed = inStock;
			stock.get(product).remove(expiration);
		}
		return removed;
	}
	
	public Float removeRation(Product product, Ration ration) {
		return removeRation(product, ration.getQuantity(), ration.getExpiration());
	}
	
	public Float removeRation(Product product, Float quantity) {
		Float removed = 0f;
		if(!existProduct(product)) return removed;
		TreeMap<ExpirationDate, Float> inventory = stock.get(product);
		Float tmpRemoved;
		java.util.Map.Entry<ExpirationDate, Float> entry;
		while(quantity > 0f && !inventory.isEmpty()) {
			entry = inventory.firstEntry();
			tmpRemoved = removeRation(product, new Ration(entry.getKey(), quantity));
			removed += tmpRemoved;
			quantity -= tmpRemoved;
		}
		return removed;
	}
	
	public Float removeRation(Product product, ExpirationDate expiration) {
		if(!existProduct(product))
			return 0f;
		Map<ExpirationDate, Float> productStock = stock.get(product);
		Float removed = productStock.remove(expiration);
		if(removed == null) {
			Logger.warning("date introuvable");
			return 0f;
		}
		return removed;
	}
	
	public Float emptyStockOfProduct(Product product) {
		Map<ExpirationDate, Float> inStock = stock.get(product);
		Float removed = 0f;
		for(Float q : inStock.values())
			removed += q;
		stock.replace(product, new TreeMap<>());
		return removed;
	}
	
	public void emptyStock() {
		stock = new HashMap<>();
	}
	
	@Override
	public Iterator<Product> iterator() {
		return stock.keySet().iterator();
	}
	
	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("\t===== STOCK ===== \n");
		for(Product p : stock.keySet()) {
			str.append(" > " + p.toString()+"\n");
			for(ExpirationDate date : stock.get(p).keySet()) {
				str.append("\t- " + date.toString() + " : ");
				str.append(stock.get(p).get(date).toString() + "\n");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
	
	public void exportToCSV(String filePath) {
		System.out.println("Export stock at : " + filePath);
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Nom du Produit,Unite,Date d'Expiration,Quantite\n");

            for (Product product : stock.keySet()) {
                Map<ExpirationDate, Float> expirations = stock.get(product);
                writer.append(product.getName())
                .append(",")
                .append(product.getUnit().toString())
                .append(",")
                .append("0")
                .append(",")
                .append("0")
                .append("\n");
                for (ExpirationDate date : expirations.keySet()) {
                    writer.append(product.getName())
                          .append(",")
                          .append(product.getUnit().toString())
                          .append(",")
                          .append(date.toString())
                          .append(",")
                          .append(expirations.get(date).toString())
                          .append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	public boolean importFromCSV(String filePath) {
//	    System.out.println("Import stock from : " + filePath);
	    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
	        String line;
	        reader.readLine();

	        while ((line = reader.readLine()) != null) {
	            String[] fields = line.split(",");

	            if (fields.length == 4) {
	                String productName = fields[0];
	                Unit productUnit = Unit.valueOf(fields[1]);
	                Product product = new Product(productName, productUnit);
	                addProduct(product);
	                
	                if(!fields[2].equals("0")) {
		                LocalDate expirationDate = LocalDate.parse(fields[2], DateTimeFormatter.ISO_LOCAL_DATE);
		                Float quantity = Float.parseFloat(fields[3]);
		                if(!addRation(product, quantity, new ExpirationDate(expirationDate)))
		                	myUtils.Logger.warning("echec ajout produit : " + product.toString());
	                }

	            }
	        }
	    } catch (IOException e) {
	    	Logger.error("echec lecture données du CSV");
	        e.printStackTrace();
	        return false;
	    } catch (IllegalArgumentException e) {
	    	Logger.error("valeurs invalide");
	    	e.printStackTrace();
	    	return false;
	    }
	    return true;
	}
	

	
	
	public static void main(String[] args) {
		StockGestionnary stock = new StockGestionnary();
//		Product tomate = new Product("tomate", Unit.KG);
//		Product lait = new Product("Lait", Unit.L);
//		Product fromage = new Product("Formage", Unit.KG);
//		
//		stock.addProduct(tomate);
//		stock.addProduct(lait);
//		
//		stock.addRation(tomate,2f, new ExpirationDate(LocalDate.now().plusDays(1)));
//		stock.addRation(tomate, 12f, new ExpirationDate(LocalDate.now()));
//		stock.addRation(tomate, 2f, new ExpirationDate(LocalDate.now()));
//		
//		stock.addRation(lait, 3f, new ExpirationDate(LocalDate.now()));
//		stock.addRation(fromage, 5f, new ExpirationDate(LocalDate.now()));
//	
//		System.out.println(stock);
//		
//		stock.exportToCSV("\\Users\\ugova\\eclipse-workspace\\SmartFood\\data\\fichier.csv");
////		
		stock.importFromCSV("\\Users\\ugova\\eclipse-workspace\\SmartFood\\data\\fichier.csv");
		
		System.out.println(stock);
//		
//		CommandeShell cmd = new CommandeShell();
//		cmd.executerOcaml();
//		
//		System.out.println("Execution ocaml terminée");
//		
		
	}
	
	
	
	

	

}
