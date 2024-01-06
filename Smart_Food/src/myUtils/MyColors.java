package myUtils;

public class MyColors {
	private static String END = "\33[0m";
	private static String ERROR = "\033[38;5;196m";
	private static String WARN = "\033[38;5;226m";
	private static String ORANGE = "\033[38;5;214m";
	private static String LIGHT_ORANGE = "\033[38;5;222m";
	private static String BLUE = "\033[38;5;159m";
	private static String GREEN = "\033[38;5;46m";
	
	public MyColors() {
		super();
	}
	
	public static String end() {
		return END;
	}
	
	public static String error() {
		return ERROR;
	}
	
	public static String warn() {
		return WARN;
	}
	
	public static String success() {
		return GREEN;
	}
	
	public static String yellow() {
		return WARN;
	}
	
	public static String red() {
		return ERROR;
	}
	
	public static String orange() {
		return ORANGE;
	}
	
	public static String lightOrange() {
		return LIGHT_ORANGE;
	}
	
	public static String blue() {
		return BLUE;
	}
	
	public static String green() {
		return GREEN;
	}

}
