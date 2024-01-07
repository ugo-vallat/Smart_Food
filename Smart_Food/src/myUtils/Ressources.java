package myUtils;

public class Ressources {
	private static String pathIn = ".\\data\\fichier.csv";
	private static String pathHistorical = ".\\data\\history.csv";
	
	private Ressources() {
		super();
	}
	
	public static String getPathIn() {
		return pathIn;
	}
	
	public static String getPathOut() {
		return pathIn;
	}
	
	public static String getPathHistory() {
		return pathHistorical;
	}

}
