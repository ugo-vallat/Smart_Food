package boundary;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import controller.ControllerDisplayStock;
import controller.ControllerDisplayStock.OrderProduct;
import controller.ControllerStockManager;
import controller.ProductSheet;
import kernel.stock.ExpirationDate;
import kernel.stock.Product;
import kernel.stock.Product.Unit;
import kernel.stock.Ration;
import kernel.stock.StockManager;
import myUtils.Logger;
import myUtils.MyColors;
import myUtils.Ressources;

public class BoundaryStockManager implements IBoundary{
	private ControllerStockManager controller;
	private BoundaryDisplayStock boundaryDisplay;
	
	public BoundaryStockManager(ControllerStockManager controller, BoundaryDisplayStock boundaryDisplay) {
		this.controller = controller;
		this.boundaryDisplay = boundaryDisplay;
	}
	
	public void call() {
		boolean exit = false;
		while(!exit) {
			switch (callMenu()) {
		        case 1:
		        	boundaryDisplay.displayStock();
		            break;
		        case 2:
		        	modificationStock();
		            break;
		        case 3:
		        	boundaryDisplay.call();
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
		str.append("\t╔══════════════════════════╗\n");
		str.append("\t║  GESTIONNAIRE DE STOCKS  ║\n");
		str.append("\t╚══════════════════════════╝\n");
		
		str.append("\n\t 1 - Aperçu des stocks\n");
		str.append("\t 2 - Modifier les stocks\n");
		str.append("\t 3 - Voir les stocks (mode avancé)\n");
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
	
	public void modificationStock() {
		boolean exit = false;
		while(!exit) {
			switch (menuModificationStock()) {
		        case 1:
		            boundaryDisplay.displayStock();
		            break;
		        case 2:
		            addProduct();
		            break;
		        case 3:
		        	removeProduct();
		            break;
		        case 4:
		        	initStock();
		            break;
		        case 0:
		            exit = true;
		            break;
		        default:
		            Logger.warning("Choix non valide");
			}
		}
	}
	
	
	
	private int menuModificationStock() {
		StringBuilder str = new StringBuilder();
		str.append(MyColors.blue() + "\n\n\n\n");
		str.append("\t╔════════════════════╗\n");
		str.append("\t║    MODIFICATION    ║\n");
		str.append("\t╚════════════════════╝\n");
		
		str.append("\n\t 1 - Aperçu des stocks\n");
		str.append("\t 2 - Ajouter un produit/rations\n");
		str.append("\t 3 - Retirer un produit\n");
		str.append("\t 4 - Réinitialiser les stocks\n");
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
	
	public void addProduct() {
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		switch (menuAddProduct()) {
	        case 1:
	        	createProduct();
	            break;
	        case 2:
	        	addRation();
	            break;
	        case 0:
	        	return;
	        default:
	        	Logger.warning("Choix non valide");
		}
	}
	
	private int menuAddProduct() {
		StringBuilder str = new StringBuilder();
		str.append(MyColors.blue());

		str.append("\n\t 1 - Créer un produit\n");
		str.append("\t 2 - Compléter les stocks\n");
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
	
	public void removeProduct() {
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		switch (menuRemoveProduct()) {
	        case 1:
	        	deleteProduct();
	            break;
	        case 2:
	        	removeRationWithQuantity();
	            break;
	        case 3:
	        	removeRationWithDate();
	            break;
	        case 4:
	        	removeRationWithQuantityAndDate();
	            break;
	        case 5:
	        	emptyStockOfProduct();
	            break;
	        case 6:
	        	emptyStock();
	            break;
	        case 0:
	        	return;
	        default:
	            Logger.warning("Choix non valide");
		}
	}
	
	private int menuRemoveProduct() {
		StringBuilder str = new StringBuilder();
		str.append(MyColors.blue());

		str.append("\n\t 1 - Supprimer un produit\n");
		str.append("\t 2 - Retirer une ration (par quantité)\n");
		str.append("\t 3 - Retirer une ration (par date)\n");
		str.append("\t 4 - Retirer une ration (par quantité et date)\n");
		str.append("\t 5 - Vider le stock d'un produit\n");
		str.append("\t 6 - Vider tous les stocks\n");
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
	
	
	public void createProduct() {
		String name, strUnit;
		Unit unit;
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    System.out.print(MyColors.blue() + "Unité (KG / G / L / U ) : " + MyColors.end());
	    strUnit = scanner.next().toUpperCase();
	    
	    switch (strUnit.toUpperCase()) {
		case "G": {
			unit = Unit.G;
			break;
		}
		case "KG": {
			unit = Unit.KG;
			break;
		}
		case "L": {
			unit = Unit.L;
			break;
		}
		case "U": {
			unit = Unit.U;
			break;
		}
		default:
			Logger.format("mauvaise unité "+strUnit);
	    	return;
		}

	    
	    if(!controller.createProduct(new ProductSheet(name, unit))) {
	    	Logger.warning("echec de la création du produit");
	    } else {
	    	Logger.success("produit " + name + " ajouté");
	    }
	    
	}
	
	public void addRation() {
		String name;
		
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    Product product = new Product(name, Unit.NONE);
	    while(!controller.existProduct(product)) {
	    	Logger.warning("Produit \"" + name + "\" n'existe pas");
	    	System.out.print(MyColors.blue() + "Voulez-vous créer ce produit ? [y/n] : " + MyColors.end());
    	    String res = scanner.next();
    	    
    	    switch(res.toLowerCase()) {
    	    case "y": {
    	    	createProduct();
    	    	break;
    	    }
    	    case "n": {
    	    	return;
    	    }
    	    default : {
    	    	Logger.format("reponse \"" + res +"\" invalide");
    	    	return;
    	    }
    	    
    	    }
	    }
	    
	    try {
	    	Float quantity = 0f;
	    	while(quantity <= 0) {
	    		System.out.print(MyColors.blue() + "Quantité (Ex: 2,3) : " + MyColors.end());
	    		quantity = scanner.nextFloat();
	    		if(quantity <= 0)
	    			Logger.warning("quantité doit être > 0");
	    	}
		    
		    System.out.print(MyColors.blue() + "Date de péremption (AAAA-MM-JJ) : " + MyColors.end());
		    LocalDate date = LocalDate.parse(scanner.next());
		    
		    ProductSheet productSheet = new ProductSheet(name, Unit.NONE);
		    productSheet.addRation(new Ration(new ExpirationDate(date), quantity));
		    if(!controller.addStockToProduct(productSheet))
		    	Logger.warning("Echec d'ajout au stock");
		    else {
		    	Logger.success("ration ajoutée");
		    	boundaryDisplay.displayStockOfProduct(product);
		    }
		} catch (InputMismatchException | DateTimeParseException e) {
			e.printStackTrace();
		} 
	}
	
	public void deleteProduct() {
		String name;
		
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    Product product = new Product(name, Unit.NONE);
	    if(!controller.deleteProduct(product))
	    	Logger.warning("echec suppression produit "+name);
	    else 
	    	Logger.success("suppression réussie");
	}
	
	public void removeRationWithQuantity() {
String name;
		
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    
	    Product product = new Product(name, Unit.NONE);
	    if(!controller.existProduct(product)) {
	    	Logger.warning("Produit introuvable");
	    	return;
	    }
	    boundaryDisplay.displayStockOfProduct(product);
	    System.out.print(MyColors.blue() + "Quantitée : " + MyColors.end());
	    Float quantity = -1f;
	    try {
	    	while (quantity < 0f) {
	    		quantity = scanner.nextFloat();
	    		if(quantity < 0f)
	    			Logger.warning("quantité retirée doit être >= 0");
	    	}
	    } catch (InputMismatchException e) {
	    	Logger.warning("Valeur invalide");
	    	e.printStackTrace();
	    	return;
	    }
	    
	    
	    Float removed = controller.removeRationWithQuantity(product, quantity);
	    if(removed <= 0f)
	    	Logger.warning("echec suppression produit "+name);
	    else 
	    	Logger.success("suppression réussie de "+removed.toString());
	}
	
	public void removeRationWithDate() {
		String name;
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    
	    Product product = new Product(name, Unit.NONE);
	    if(!controller.existProduct(product)) {
	    	Logger.warning("Produit introuvable");
	    	return;
	    }
	    boundaryDisplay.displayStockOfProduct(product);
	    System.out.print(MyColors.blue() + "Date (AAAA-MM-JJ) : " + MyColors.end());
	   	LocalDate date;
	    try {
	    	date = LocalDate.parse(scanner.next());
	    } catch (DateTimeParseException e) {
	    	Logger.warning("Valeur invalide");
	    	e.printStackTrace();
	    	return;
	    }
	    Float quantity = controller.removeRationWithDate(product, new ExpirationDate(date));
	    if(quantity <= 0f)
	    	Logger.warning("echec retirer ration");
	    else
	    	Logger.success("suppression réussie de "+quantity.toString());
	    
	    
	    
	    
	}
	
	public void removeRationWithQuantityAndDate() {
		String name;
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    
	    Product product = new Product(name, Unit.NONE);
	    if(!controller.existProduct(product)) {
	    	Logger.warning("Produit introuvable");
	    	return;
	    }
	    boundaryDisplay.displayStockOfProduct(product);
	    System.out.print(MyColors.blue() + "Date (AAAA-MM-JJ) : " + MyColors.end());
	   	LocalDate date;
	    try {
	    	date = LocalDate.parse(scanner.next());
	    } catch (DateTimeParseException e) {
	    	Logger.warning("Valeur invalide");
	    	e.printStackTrace();
	    	return;
	    }
	    System.out.print(MyColors.blue() + "Quantitée : " + MyColors.end());
	    Float quantity = -1f;
	    try {
	    	while (quantity < 0f) {
	    		quantity = scanner.nextFloat();
	    		if(quantity < 0f)
	    			Logger.warning("quantité retirée doit être >= 0");
	    	}
	    } catch (InputMismatchException e) {
	    	Logger.warning("Valeur invalide");
	    	e.printStackTrace();
	    	return;
	    }
	    Float removed = controller.removeRationWithQuantityAndDate(product, new ExpirationDate(date), quantity);
	    if(removed <= 0f)
	    	Logger.warning("echec retirer ration");
	    else
	    	Logger.success("suppression réussie de "+removed.toString());
	}
	
	public void emptyStockOfProduct() {
		String name;
		boundaryDisplay.displaySetProducts(OrderProduct.NAME);
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Nom du produit : " + MyColors.end());
	    name = scanner.next().toLowerCase();
	    
	    
	    Product product = new Product(name, Unit.NONE);
	    if(!controller.existProduct(product)) {
	    	Logger.warning("Produit introuvable");
	    	return;
	    }
	    boundaryDisplay.displayStockOfProduct(product);
	    System.out.print(MyColors.blue() + "Vider tout le stock ? (y/n) : " + MyColors.end());
	   	
	    String res = scanner.next();
	    Float removed = -1f;
	    
	    switch(res.toLowerCase()) {
		    case "y": {
		    	removed = controller.emptyStockOfProduct(product);
		    	break;
		    }
		    case "n": {
		    	Logger.warning("oppération annulée");
		    	return;
		    }
		    default : {
		    	Logger.format("reponse \"" + res +"\" invalide");
		    	return;
		    }
	    }
	    
	    if(removed < 0f)
	    	Logger.warning("echec vider stock");
	    else
	    	Logger.success("suppression réussie de "+removed.toString());
	    
	}
	
	public void emptyStock() {
		boundaryDisplay.displayStock();
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Vider tous les stocks ? (y/n) : " + MyColors.end());
	   	
	    String res = scanner.next();
	    
	    switch(res.toLowerCase()) {
		    case "y": {
		    	controller.emptyStock();
		    	break;
		    }
		    case "n": {
		    	Logger.warning("oppération annulée");
		    	return;
		    }
		    default : {
		    	Logger.format("reponse \"" + res +"\" invalide");
		    	return;
		    }
	    }
	    
	    Logger.success("stock vide");
	}
	
	public void initStock() {
		boundaryDisplay.displayStock();
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print(MyColors.blue() + "Réinitialiser les stocks à l'état d'origine ? (y/n) : " + MyColors.end());
	   	
	    String res = scanner.next();
	    
	    switch(res.toLowerCase()) {
		    case "y": {
		    	controller.emptyStock();
		    	controller.importFromCSV(Ressources.getPathIn());
		    	break;
		    }
		    case "n": {
		    	Logger.warning("oppération annulée");
		    	return;
		    }
		    default : {
		    	Logger.format("reponse \"" + res +"\" invalide");
		    	return;
		    }
	    }
	    Logger.success("stock réinitialisé");
	    boundaryDisplay.displayStock();
	}
	
	
	
	

	
	public static void main(String[] args) {
		StockManager stockGestionnary = new StockManager();
		ControllerStockManager controllerStockGestionnary = new ControllerStockManager(stockGestionnary);
		ControllerDisplayStock controllerDisplayStock = new ControllerDisplayStock(stockGestionnary);
		BoundaryDisplayStock displayBoundary = new BoundaryDisplayStock(controllerStockGestionnary,controllerDisplayStock);
		BoundaryStockManager boundary = new BoundaryStockManager(controllerStockGestionnary, displayBoundary);
		String path = "\\Users\\ugova\\eclipse-workspace\\SmartFood\\data\\fichier.csv";
		if(!stockGestionnary.importFromCSV(path)) {
			Logger.error("echec lecture CSV à " + path);
			return;
		}
		boundary.call();
		stockGestionnary.exportToCSV("\\Users\\ugova\\eclipse-workspace\\SmartFood\\data\\fichier.csv");

	}
	

	
 
	
	

}
