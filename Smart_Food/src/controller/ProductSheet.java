package controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import kernel.stock.Product.Unit;
import kernel.stock.Ration;

public class ProductSheet {
	private String name;
	private Unit unit;
	private List<Ration> rations;

	public ProductSheet(String name, Unit unit) {
		this.name = name;
		this.unit = unit;
		this.rations = new ArrayList<>();
	}
	
	public List<Ration> getRations() {
		return rations;
	}
	
	public String getName() {
		return name;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	public void addRation(Ration ration) {
		rations.add(ration);
	}
	
	public void sortStock() {
		Collections.sort(rations);
	}
	
	public boolean isDegenerated() {
		return name == null || unit == null;
	}
	

}
