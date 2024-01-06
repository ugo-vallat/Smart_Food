package kernel.stock;


public class Ration implements Comparable<Ration>{
	private ExpirationDate expiration;
	private Float quantity;
	
	public Ration(ExpirationDate expiration, Float quantity) {
		this.expiration = expiration;
		this.quantity = quantity;
	}
	
	public ExpirationDate getExpiration() {
		return expiration;
	}
	
	public Float getQuantity() {
		return quantity;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Ration)) return false;
		Ration r = (Ration)obj;
		return r.quantity.equals(quantity) &&
				r.expiration.equals(expiration);			
	}
	
	@Override
	public int hashCode() {
		return quantity.hashCode()*expiration.hashCode()*31;
	}
	
	@Override
	public int compareTo(Ration o) {
		if(expiration.compareTo(o.expiration) == 0) {
			return quantity.compareTo(o.quantity);
		}
		return expiration.compareTo(o.expiration);
	}
	
	@Override
	public String toString() {
		return expiration.toString() + " : " + quantity.toString();
	}
	
	public boolean isValid() {
		return expiration!=null && quantity != null;
	}
	
}
