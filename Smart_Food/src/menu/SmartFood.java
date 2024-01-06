package menu;

import java.util.Scanner;

import domain.Inventory;


public class SmartFood {
	Inventory inventory = new Inventory();
	
	
	
	
	public SmartFood(Inventory inventory) {
		super();
		this.inventory = inventory;
	}



	public void menu(){
		
		Scanner scanner = new Scanner(System.in);
		
		//display menu
		System.out.println("1. Gérer son inventaire");
        System.out.println("2. Faire une liste de courses");
        System.out.println("3. Préparer une recette");
        System.out.println("4. Suivi nutritionel");
        System.out.println("5. Paramêtres et informations");
        System.out.println("0. Exit\n");
        
        //Get user input
        try {
        	System.out.println("Que voulez-vous faire ? : ");
        	int userInput = scanner.nextInt();
        	
        	if (userInput >= 0 && userInput <=5) {
		        if (userInput == 1) manageInventory();
		        if (userInput == 2) makeList();
		        if (userInput == 3) prepareRecipe();
		        if (userInput == 4) statsNutrition();
		        if (userInput == 5) settings();
		        if (userInput == 0) System.exit(0);
        	} else {
        		System.out.println("Choisir un nombre entre 0 - 5");
            	menu();
        	}
        } catch (NumberFormatException e) {
        	System.out.println("Choisir un nombre entre 0 - 5");
        	menu();
        }
    }

	
	
	private void settings() {
		System.out.println("En cours de développement");
		menu();
		
	}

	private void statsNutrition() {
		System.out.println("En cours de développement");
		menu();
		
	}

	private void prepareRecipe() {
		System.out.println("En cours de développement");
		menu();
		
	}

	private void makeList() {
		System.out.println("En cours de développement");
		menu();
		
	}

	private void manageInventory() {
		inventory.menu();
		
		
	}

	public static void main(String[] args) {
		SmartFood app = new SmartFood(linkInventory);
		app.menu();

	}

}
