package boundary;

import java.util.InputMismatchException;
import java.util.Scanner;

import controller.ControllerHistoryManager;
import kernel.stock.HistoryManager;
import kernel.stock.StockManager;
import myUtils.Logger;
import myUtils.MyColors;
import myUtils.Ressources;
import apiOcaml.*;

public class BoundaryHistoryManager implements IBoundary {
	private ControllerHistoryManager controllerHistorical;
	
	public BoundaryHistoryManager(ControllerHistoryManager controller) {
		this.controllerHistorical = controller;
	}
	
	@Override
	public void call() {
		boolean exit = false;
		while(!exit) {
			switch (callMenu()) {
		        case 1:
		        	displayHistorical();
		            break;
		        case 2:
		        	displayQuantityOfProduct();
		            break;
		        case 3:
		        	displayLastDay();
		            break;
		        case 4:
		        	displayLastMonth();
		            break;
		        case 5:
		        	displayLastYear();
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
		str.append("\t╔══════════════╗\n");
		str.append("\t║  Historique  ║\n");
		str.append("\t╚══════════════╝\n");
		
		str.append("\n\t 1 - Aficher historique complet\n");
		str.append("\t 2 - Quantité d'un produit \n");
		str.append("\t 3 - historique dernier jour \n");
		str.append("\t 4 - historique dernier mois \n");
		str.append("\t 5 - historique dernière année \n");
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
	
	public void displayHistorical() {
		CommandeShell cmd = new CommandeShell();
		cmd.executerOcaml("DISPLAY");
	}
	
	public void displayQuantityOfProduct() {
		displayHistorical();
		
		Scanner scanner = new Scanner(System.in);
	    System.out.print("Quel produit voulez vous voir : ");
	    String product = scanner.next();
	    
		CommandeShell cmd = new CommandeShell();
		cmd.executerOcaml("QUANTITY " + product);
	}
	
	public void displayLastDay() {
		CommandeShell cmd = new CommandeShell();
		cmd.executerOcaml("LAST_DAY");
	}
	
	public void displayLastMonth() {
		CommandeShell cmd = new CommandeShell();
		cmd.executerOcaml("LAST_MONTH");
	}
	
	public void displayLastYear() {
		CommandeShell cmd = new CommandeShell();
		cmd.executerOcaml("LAST_YEAR");
	}

	
	public static void main(String[] args) {
		HistoryManager historyManager = new HistoryManager();
		ControllerHistoryManager controllerHistorical = new ControllerHistoryManager(historyManager);
		BoundaryHistoryManager boundaryHistorical = new BoundaryHistoryManager(controllerHistorical);
		
		boundaryHistorical.call();
		Logger.log(" ### FIN ### ");
	}

}
