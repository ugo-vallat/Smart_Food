package global;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import boundary.BoundaryDisplayStock;
import boundary.BoundaryStockGestionnary;
import controller.ControllerDisplayStock;
import controller.ControllerStockGestionnary;
import kernel.stock.StockGestionnary;
import myUtils.Logger;
import myUtils.MyColors;
import myUtils.Ressources;


public class Main {
	private BoundaryStockGestionnary boundaryStockGestionnary;
	private ControllerStockGestionnary controllerStockGestionnary;
	private ControllerDisplayStock controllerDisplayStock;
	private StockGestionnary stockGestionnary;
	private BoundaryDisplayStock boundaryDisplayStock;
	

	public Main() {
		stockGestionnary = new StockGestionnary();
		if(!stockGestionnary.importFromCSV(Ressources.getPathIn())) {
			Logger.error("Echec du chargement des données à "+Ressources.getPathIn());
		}
		System.out.println(stockGestionnary.toString());
		controllerStockGestionnary = new ControllerStockGestionnary(stockGestionnary);
		controllerDisplayStock = new ControllerDisplayStock(stockGestionnary);
		boundaryDisplayStock = new BoundaryDisplayStock(controllerStockGestionnary, controllerDisplayStock);
		boundaryStockGestionnary = new BoundaryStockGestionnary(controllerStockGestionnary, boundaryDisplayStock);
	}
	
	public void launch() {
		boolean exit = false;
		while(!exit) {
			switch (menu()) {
		        case 1:
		            boundaryStockGestionnary.call();
		            break;
		        case 2:
		            System.out.println(" ### ZONE EN TRAVAUX ### ");
		            break;
		        case 3:
		        	System.out.println(" ### ZONE EN TRAVAUX ### ");
		            break;
		        case 0:
		            exit = true;
		            break;
		        default:
		            System.out.println(MyColors.warn() + "< Choix non valide >\n\033[0m" + MyColors.end());
			}
		}
		
		stockGestionnary.exportToCSV(Ressources.getPathOut());
		
	}
	
	private String menuBox() {
		StringBuilder str = new StringBuilder();
		str.append("\t╔════════════════════╗\n");
		str.append("\t║        MENU        ║\n");
		str.append("\t╚════════════════════╝\n");
		return str.toString();
	}
	
	private int menu() {
		System.out.println(MyColors.blue() + "\n\n\n\n"+menuBox());
		
		StringBuilder str  = new StringBuilder();
		str.append("\t 1 - Gestion des stocks\n");
		str.append("\t 2 - Recettes\n");
		str.append("\t 3 - Historique\n");
		str.append("\n\t 0 - Fermer\n");
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
	
	
	public static void main(String[] args) {
		Main app = new Main();
		
		app.launch();
		System.out.println(MyColors.warn() + "< Fermeture de l'application >" + MyColors.end());
	}

}
