package global;

import java.util.InputMismatchException;
import java.util.Scanner;

import boundary.BoundaryDisplayStock;
import boundary.BoundaryHistoryManager;
import boundary.BoundaryStockManager;
import controller.ControllerDisplayStock;
import controller.ControllerHistoryManager;
import controller.ControllerStockManager;
import kernel.stock.HistoryManager;
import kernel.stock.StockManager;
import myUtils.Logger;
import myUtils.MyColors;
import myUtils.Ressources;


public class Main {
	private StockManager stockManager;
	private HistoryManager historyManager;
	private ControllerStockManager controllerStockManager;
	private ControllerDisplayStock controllerDisplayStock;
	private ControllerHistoryManager controllerHistorical;
	private BoundaryStockManager boundaryStockManager;
	private BoundaryDisplayStock boundaryDisplayStock;
	private BoundaryHistoryManager boundaryHistorical;
	

	public Main() {
		stockManager = new StockManager();
		if(!stockManager.importFromCSV(Ressources.getPathIn())) {
			Logger.error("Echec du chargement des données à "+Ressources.getPathIn());
			Logger.warning("Current dir : " + System.getProperty("user.dir"));
		}
		historyManager = new HistoryManager();
		controllerStockManager = new ControllerStockManager(stockManager);
		controllerDisplayStock = new ControllerDisplayStock(stockManager);
		controllerHistorical = new ControllerHistoryManager(historyManager);
		boundaryDisplayStock = new BoundaryDisplayStock(controllerStockManager, controllerDisplayStock);
		boundaryStockManager = new BoundaryStockManager(controllerStockManager, boundaryDisplayStock);
		boundaryHistorical = new BoundaryHistoryManager(controllerHistorical);
	}
	
	public void launch() {
		boolean exit = false;
		while(!exit) {
			switch (menu()) {
		        case 1:
		            boundaryStockManager.call();
		            break;
		        case 2:
		        	boundaryHistorical.call();
		            break;
		        case 0:
		            exit = true;
		            break;
		        default:
		            System.out.println(MyColors.warn() + "< Choix non valide >\n\033[0m" + MyColors.end());
			}
		}
		
		stockManager.exportToCSV(Ressources.getPathOut());
		
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
		str.append("\t 2 - Historique\n");
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
