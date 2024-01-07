package controller;

import java.time.LocalDate;

import kernel.stock.HistoryManager;
import kernel.stock.Product;
import kernel.stock.Ration;

public class ControllerHistoryManager {
	private HistoryManager historyManager;
	public ControllerHistoryManager(HistoryManager manager) {
		this.historyManager = manager;
	}
	
	
	public static void addToHistorical(String path, Product product, LocalDate date, Float quantity) {
		HistoryManager.addToHistorical(path, product, date, quantity);
	}

}
