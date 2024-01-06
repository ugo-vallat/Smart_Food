package testsKernel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kernel.stock.ExpirationDate;
import kernel.stock.Product;
import kernel.stock.Product.Unit;
import kernel.stock.StockGestionnary;
import kernel.stock.Ration;



class testsStockGestionnary {
	StockGestionnary stock;
	Product product1;
	Product product1bis;
	Product product2;
	Product product3;
	ExpirationDate date1;
	ExpirationDate date2;
	ExpirationDate date3;
	Ration ration1;
	Ration ration2;
	Ration ration3;
	
	@BeforeEach
	void init() {
		stock = new StockGestionnary();
		product1 =  new Product("produit1", Unit.G);
		product1bis =  new Product("produit1", Unit.KG);
		product2 =  new Product("produit2", Unit.G);
		product3 =  new Product("produit3", Unit.L);
		date1 = new ExpirationDate(LocalDate.now());
		date2 = new ExpirationDate(LocalDate.now().plusDays(1));
		date3 = new ExpirationDate(LocalDate.now().plusDays(1).plusWeeks(1).plusYears(1));
		ration1 = new Ration(date1, 1f);
		ration2 = new Ration(date2, 2f);
		ration3 = new Ration(date3, 3f);
	}

	@Test
	void testAddProduct() {	
		System.out.println("<+>------ testAddProduct ------<+>");

		stock.addProduct(product1);
		assertTrue(stock.existProduct(product1));
		assertTrue(stock.existProduct(product1bis));
		assertEquals(1, stock.getProductSet().size());
		
		stock.addProduct(product2);
		stock.addProduct(product3);
		assertTrue(stock.existProduct(product1));
		assertTrue(stock.existProduct(product2));
		assertTrue(stock.existProduct(product3));
		assertEquals(3, stock.getProductSet().size());
		
		stock.addProduct(product1);
		assertTrue(stock.existProduct(product1));
		assertEquals(3, stock.getProductSet().size());
		
		stock.addProduct(product1bis);
		assertTrue(stock.existProduct(product1));
		assertTrue(stock.existProduct(product1bis));
		assertEquals(3, stock.getProductSet().size());
		
		
	}
	
	@Test
	void testaddRationV1() {
		System.out.println("<+>------ testaddRationV1 ------<+>");
		assertFalse(stock.addRation(product1, 2f, date1));
		assertFalse(stock.addRation(product1, null, null));
		
		stock.addProduct(product1);
		
		assertTrue(stock.addRation(product1, 1f, date1));
		assertFalse(stock.addRation(product2, 1f, date1));
		assertEquals(1f, stock.getStockOfProduct(product1));
		assertTrue(stock.addRation(product1, 1f, date1));
		assertEquals(2f, stock.getStockOfProduct(product1));
		
		stock.addProduct(product2);
		stock.addProduct(product3);
		assertTrue(stock.addRation(product2, 2f, date1));
		assertEquals(2f, stock.getStockOfProduct(product2));
		assertTrue(stock.addRation(product2, 1f, date2));
		assertEquals(3f, stock.getStockOfProduct(product2));
		assertEquals(2f, stock.getStockOfProduct(product1));
		
	}
	
	@Test
	void testaddRationV2() {
		System.out.println("<+>------ testaddRationV2 ------<+>");
		assertFalse(stock.addRation(product1, ration1));
		assertFalse(stock.addRation(product1, null));
		
		stock.addProduct(product1);
		
		assertTrue(stock.addRation(product1, ration1));
		assertFalse(stock.addRation(product2, ration1));
		assertEquals(1f, stock.getStockOfProduct(product1));
		assertTrue(stock.addRation(product1, ration1));
		assertEquals(2f, stock.getStockOfProduct(product1));
		
		stock.addProduct(product2);
		stock.addProduct(product3);
		assertTrue(stock.addRation(product2, ration2));
		assertEquals(2f, stock.getStockOfProduct(product2));
		assertTrue(stock.addRation(product2, ration2));
		assertEquals(4f, stock.getStockOfProduct(product2));
		assertEquals(2f, stock.getStockOfProduct(product1));
		
	}
	
	@Test
	void testRemoveProduct() {
		System.out.println("<+>------ testRemoveProduct ------<+>");
		stock.addProduct(product1);
		stock.addProduct(product2);
		stock.addProduct(product3);
		
		stock.addRation(product1, ration1);
		stock.addRation(product1, ration2);
		stock.addRation(product1, ration3);
		stock.addRation(product2, ration2); 
		stock.addRation(product3, ration3);
		
		
		assertEquals(0f, stock.removeRation(product1, 0f));
		assertEquals(6f, stock.getStockOfProduct(product1));
		assertEquals(1f, stock.removeRation(product1, 1f));
		assertEquals(5f, stock.getStockOfProduct(product1));
		List<Ration> rations = stock.getRationsOfProduct(product1);
		assertFalse(rations.contains(new Ration(date1, 0f)));
		assertFalse(rations.contains(new Ration(date1, 1f)));
		assertEquals(5f, stock.removeRation(product1, 5f));
		assertEquals(0f, stock.getStockOfProduct(product1));
		assertTrue(stock.getRationsOfProduct(product1).isEmpty());

		
		assertEquals(0f, stock.removeRation(product2, 0f));
		assertEquals(2f, stock.getStockOfProduct(product2));
		assertEquals(1f, stock.removeRation(product2, 1f));
		assertEquals(1f, stock.getStockOfProduct(product2));
		assertEquals(1f, stock.removeRation(product2, 2f));
		assertEquals(0f, stock.getStockOfProduct(product2));
		assertEquals(0f, stock.removeRation(product2, 1f));
		assertEquals(0f, stock.getStockOfProduct(product2));
		
		assertEquals(0f, stock.removeRation(product3, 1f, date2));
		assertEquals(3f, stock.getStockOfProduct(product3));
		assertEquals(1f, stock.removeRation(product3, 1f, date3));
		assertEquals(2f, stock.getStockOfProduct(product3));
		assertEquals(2f, stock.removeRation(product3, 4f, date3));
		assertEquals(0f, stock.getStockOfProduct(product3));
		
	}
	

}
