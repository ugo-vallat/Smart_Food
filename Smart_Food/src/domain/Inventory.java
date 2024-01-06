package domain;

import java.util.Scanner;

public class Inventory {
	Ingredient []listIngredient;
	
	
	public void menu() {
		Scanner scanner = new Scanner(System.in);
		
		//display menu
		System.out.println("1. Voir l'inventaire");
        System.out.println("2. Trouver un ingrédient");
        System.out.println("3. Ajouter un ingredient");
        System.out.println("4. Supprimer un ingredient");
        System.out.println("5. Utiliser un ingredient");
        System.out.println("0. Exit\n");
        
        //Get user input
        try {
        	System.out.println("Que voulez-vous faire ? : ");
        	int userInput = scanner.nextInt();
        	
        	if (userInput >= 0 && userInput <=6) {
		        if (userInput == 1) displayInventory();
		        if (userInput == 2) findIngredient();
		        if (userInput == 3) addIngredient();
		        if (userInput == 4) deleteIngredient();
		        if (userInput == 5) useIngredient();
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

	
	
	public void displayInventory() {
		// TODO Auto-generated method stub

	}
	
	public void findIngredient() {
		// TODO Auto-generated method stub

	}
	
	public void addIngredient() {
		// TODO Auto-generated method stub

	}
	
	public void deleteIngredient() {
		// TODO Auto-generated method stub

	}
	
	public void useIngredient() {
		// TODO Auto-generated method stub

	}
	

	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
