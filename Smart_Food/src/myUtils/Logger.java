package myUtils;

public class Logger {

	public Logger() {
		super();
	}
	
	public static void warning(String str) {
		System.err.println(MyColors.warn() + " Warning : < " + str + " >"+MyColors.end());
	}
	
	public static void error(String str) {
		System.err.println(MyColors.error() + " Error : < " + str + " >"+MyColors.end());
	}
	
	public static void format(String str) {
		System.err.println(MyColors.warn() + " Format : < " + str + " >"+MyColors.end());
	}
	
	public static void success(String str) {
		System.err.println(MyColors.success() + " Success : < " + str + " >"+MyColors.end());
	}
	
	

}
