import java.io.IOException;
import java.util.Scanner;

public class Main {

	public static String pathName = null;
	public static String type = null;
	public static int declarationsFound;
	public static int referencesFound;
	
	public static void checkArgs(String[] args) {
		// Check if user gives 2 arguments
		try { 
			pathName = args[0];
			type = args[1];
		}
		
		// Throws exception if input does not have two arguments
		catch (java.lang.ArrayIndexOutOfBoundsException e){ 
			System.out.println("Invalid usage. Format: JAVAPROGRAM PATH TYPE!");
			
			Scanner reader = new Scanner(System.in);
			System.out.println("Enter a path: ");
			pathName = reader.next();
			System.out.println("Enter a java type: ");
			type = reader.next();
			reader.close();
			
			// Program terminates if user does not provide any type
			if (type.length() == 0) {
				System.out.println("Did not enter a Java type!");
				System.exit(-1);
			}
		}
	}
	
	public static void main(String[] args) throws IOException {
		checkArgs(args);

		Reader reader = new Reader();
		reader.parseFiles();

		System.out.println(type + ". Declarations found: " + declarationsFound + 
				"; References found: " + referencesFound + ".");
	}
}


