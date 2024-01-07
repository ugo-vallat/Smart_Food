package kernel.stock;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

public class HistoryManager {

	public HistoryManager() {
		super();
	}
	
	public static void addToHistorical(String filePath, Product product, LocalDate date, Float quantity) {
		System.out.println("Export stock at : " + filePath);
        try (FileWriter writer = new FileWriter(filePath, true)) {
            writer.append(product.getName())
                  .append(",")
                  .append(product.getUnit().toString())
                  .append(",")
                  .append(date.toString())
                  .append(",")
                  .append(quantity.toString())
                  .append("\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	

	
	
	
	
	
	
}
