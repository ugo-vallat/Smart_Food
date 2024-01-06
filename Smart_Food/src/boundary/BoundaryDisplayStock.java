package boundary;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import controller.ControllerDisplayStock;
import controller.ControllerDisplayStock.OrderProduct;
import controller.ControllerDisplayStock.OrderRation;
import controller.ControllerStockGestionnary;
import controller.RationSheet;
import kernel.stock.Product;
import kernel.stock.Ration;
import kernel.stock.StockGestionnary;
import myUtils.Logger;
import myUtils.MyColors;
import myUtils.Ressources;

public class BoundaryDisplayStock implements IBoundary{
	ControllerStockGestionnary controllerStock;
	ControllerDisplayStock controllerDisplay;
	
	
	public BoundaryDisplayStock(ControllerStockGestionnary controllerStock, ControllerDisplayStock controllerDisplay) {
		this.controllerStock = controllerStock;
		this.controllerDisplay = controllerDisplay;
	}
	
	@Override
	public void call() {
		boolean exit = false;
		while(!exit) {
			switch (callMenu()) {
		        case 1:
		        	displayStock();
		            break;
		        case 2:
		        	seeProducts();
		            break;
		        case 3:
		        	seeStocks();
		            break;
		        case 0:
		            exit = true;
		            break;
		        default:
		        	Logger.warning("Choix non valide");
			}
		}
	}
	
	private int callMenu() {
		StringBuilder str = new StringBuilder();
		str.append(MyColors.blue() + "\n\n\n\n");
		str.append("\t╔════════════════════════════╗\n");
		str.append("\t║  VISUALISATION DES STOCKS  ║\n");
		str.append("\t╚════════════════════════════╝\n");
		
		str.append("\n\t 1 - Aperçu des stocks\n");
		str.append("\t 2 - Voir les produits\n");
		str.append("\t 3 - Voir les stocks\n");
		str.append("\n\t 0 - Retour\n");
		System.out.println(str.toString());
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Votre choix : ");
	    System.out.println(MyColors.end());
	    int choix = 0;
	    try {
	    	choix = scanner.nextInt();
	    } catch (InputMismatchException e) {
	    	return -1;
	    }
	    return choix;
	}
	
	
	public void seeProducts() {
		int choix;
		OrderProduct order;
		StringBuilder str = new StringBuilder();
		
		str.append("\nTrier selon :\n")
		.append("  1 - Ordre alphabétique\n")
		.append("  2 - Quantité en stock par ordre croissant\n")
		.append("  3 - Quantité en stock par ordre décroissant\n\n");
		System.out.println(MyColors.blue() + str.toString() + MyColors.end());
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Order de trie : " + MyColors.end());
	    choix = scanner.nextInt();
	    
	    
	    
	    switch (choix) {
	    case 1: {
	    	order = OrderProduct.NAME;
	    	break;
	    }
	    case 2: {
	    	order = OrderProduct.IN_STOCK_INCREASING;
	    	break;
	    }
	    case 3: {
	    	order = OrderProduct.IN_STOCK_DECREASING;
	    	break;
	    }
		default:
			Logger.warning("Choix non valide");
	    	return;
		}

	    
	    displaySetProducts(order);
	}
	
	public void seeStocks() {
		int choix;
		OrderRation order;
		StringBuilder str = new StringBuilder();
		
		str.append("\nTrier selon :\n")
		.append("  1 - Date de péremption croissante\n")
		.append("  2 - Date de péremption décroissante\n");
		System.out.println(MyColors.blue() + str.toString() + MyColors.end());
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Order de trie : " + MyColors.end());
	    choix = scanner.nextInt();
	    
	    
	    
	    switch (choix) {
	    case 1: {
	    	order = OrderRation.DATE_INCREASING;
	    	break;
	    }
	    case 2: {
	    	order = OrderRation.DATE_DECREASING;
	    	break;
	    }
		default:
			Logger.warning("Choix non valide");
	    	return;
		}

	    
	    displaySortedStock(order);
	}
	
	
	
	
	
	
	
	
	
	
	
	public void displaySetProducts(OrderProduct order) {
		List<Product> product = controllerDisplay.getSortedListProducts(order);
		
		StringBuilder str = new StringBuilder();
		str.append(MyColors.orange()+"\n    ===== PRODUITS EN STOCK =====\n");
		str.append("<+>\n");
		for(Product p : product) {
			str.append(" | ")
			.append(p.getName())
			.append(" : ")
			.append(controllerStock.getStockOfProduct(p).toString())
			.append(" ")
			.append(p.getUnit().toString())
			.append("\n");
		}
		str.append(" |\n<+>\n" + MyColors.end());
		System.out.println(str.toString());
	}
	
	public void displayStockOfProduct(Product product) {
		List<Ration> stock = controllerStock.getRationsOfProduct(product);
		
		StringBuilder str = new StringBuilder();
		str.append(MyColors.orange()+"<+>\n");
		str.append(" | "+product.toString()+"\n");
		for(Ration r : stock) {
			str.append(" |  - "+r.toString()+"\n");
		}
		str.append(" |\n<+>\n" + MyColors.end());
		System.out.println(str.toString());
	}
	
	public void displayStock() {
		Set<Product> product = controllerStock.getProductSet();
		
		StringBuilder str = new StringBuilder();
		str.append(MyColors.orange()+"\n    ===== STOCK =====\n");
		str.append("<+>\n");
		for(Product p : product) {
			str.append(" | ")
			.append(p.getName())
			.append(" : ")
			.append(controllerStock.getStockOfProduct(p).toString())
			.append(" ")
			.append(p.getUnit().toString())
			.append("\n");
			for(Ration r : controllerStock.getRationsOfProduct(p)) {
				str.append(MyColors.lightOrange())
				.append(" |  - ")
				.append(r.toString())
				.append("\n")
				.append(MyColors.orange());
			}
			str.append(" | \n");
		}
		str.append("<+>\n" + MyColors.end());
		System.out.println(str.toString());
	}
	
	public void displaySortedStock(OrderRation order) {
		List<RationSheet> rations = controllerDisplay.getSortedListRation(order);
		
		StringBuilder str = new StringBuilder();
		str.append(MyColors.orange()+"\n    ===== STOCKS TRIES =====\n");
		str.append("<+>\n");
		for(RationSheet r : rations) {
			if(r.getExpiration().isExpirated())
				str.append(MyColors.red());
			str.append(" | ")
			.append(r.getExpiration())
			.append(" -> ")
			.append(r.getProductName())
			.append("(" + r.getProductUnit() + ")")
			.append(" : ")
			.append(r.getQuantity())
			.append("\n");
			if(r.getExpiration().isExpirated())
				str.append(MyColors.orange());
		}
		str.append(" |\n<+>\n" + MyColors.end());
		System.out.println(str.toString());
	}
	
	
	public static void main(String[] args) {
		StockGestionnary stock = new StockGestionnary();
		stock.importFromCSV(Ressources.getPathIn());
		ControllerDisplayStock controllerDisplayStock = new ControllerDisplayStock(stock);
		ControllerStockGestionnary controllerStockGestionnary = new ControllerStockGestionnary(stock);
		BoundaryDisplayStock boundaryDisplayStock = new BoundaryDisplayStock(controllerStockGestionnary, controllerDisplayStock);
		
		boundaryDisplayStock.call();
		Logger.warning(" ### END ###");
	}
	

}
