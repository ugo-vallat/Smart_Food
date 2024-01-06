package kernel.stock;


public class Product implements Comparable<Product>{
	String name;
	Unit unit;

	public Product(String name, Unit unit) {
		this.name = name;
		this.unit = unit;
	}
	
	public Product(String name) {
		this.name = name;
		this.unit = Unit.NONE;
	}
	
	public String getName() {
		return name;
	}
	
	public Unit getUnit() {
		return unit;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Product)) return false;
		return ((Product)obj).name.equals(name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
	
	@Override
	public String toString() {
		StringBuilder str  = new StringBuilder();
		str.append(name);
		if(unit != Unit.NONE)
			str.append("("+unit.toString()+")");
		return str.toString();
	}
	
	@Override
	public int compareTo(Product o) {
		return this.name.compareTo(o.getName());
	}
	
	public enum Unit {
		KG("KG"), G("G"),L("L"),U("U"), NONE("NONE");
		
		private String name;
		
		private Unit(String name) {
			this.name = name;
		}
		
		@Override
		public String toString() {
			return name;
		}
		
	}
	
	
	
	

	


}
