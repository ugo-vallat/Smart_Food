package kernel.stock;

import java.time.LocalDate;
import java.time.Period;

public class ExpirationDate implements Comparable<ExpirationDate> {
	
	LocalDate date;

	public ExpirationDate(int day, int month, int year) {
		this.date = LocalDate.of(year, month, day);
	}
	
	public ExpirationDate(LocalDate date) {
		this.date = LocalDate.from(date);
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public boolean isExpirated() {
		return date.isBefore(LocalDate.now());
	}
	
	public Period getTimeBeforeExpiration() {
		return Period.between(LocalDate.now(), date);
	}
	
	public boolean expiresBeforeDay(long day) {
		return LocalDate.now().plusDays(day).isAfter(date);
	}
	
	public boolean expiresBeforeDate(LocalDate d) {
		return date.isBefore(d);
	}
	
	@Override
	public int compareTo(ExpirationDate o) {
		return date.compareTo(o.date);
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof ExpirationDate)) return false;
		ExpirationDate d = (ExpirationDate)obj;
		return d.date.equals(date);
	}
	
	@Override
	public int hashCode() {
		return date.hashCode()*31;
	}
	
	@Override
	public String toString() {
		return date.toString();
	}

}
