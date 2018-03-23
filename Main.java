import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
	
	public static File[] path;
	public static String type;
	public static int declarationsFound;
	public static int referencesFound;

	public static void main(String[] args) {
		Program program = new Program(args[0], args[1]);

		// Code taken from: http://adam-bien.com/roller/abien/entry/java_8_reading_a_file
		for (File getJavaFile:Program.path) {
			String fileToString;

			try {
				fileToString = new String(Files.readAllBytes(Paths.get(getJavaFile.toString())));
			} catch (IOException ioe) {
				continue;
			}

			program.parse(fileToString);
		}

		System.out.println(type + ". Declarations found: " + declarationsFound + "; references found: " + referencesFound + ".");
	}
}