import java.io.IOException;
import java.util.ArrayList;

public class Main {

	public static String pathName = null;
	public static String type = null;
	public static int declarationsFound;
	public static int referencesFound;
	
	public static void checkArgs(String[] args) {
		try { 
			pathName = args[0];
			type = args[1];
		}
		
		catch (java.lang.ArrayIndexOutOfBoundsException e){ 
			 throw new IllegalStateException("Usage: 'PROGRAM' 'PATH' 'TYPE'");
		}
	}

	public static void main(String[] args) throws IOException {
		checkArgs(args);

		Reader reader = new Reader();
		reader.parseFiles();
		
		Parser.counter();
	}
}


