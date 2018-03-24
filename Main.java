import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.jar.*;

public class Main {
	
	public static File[] path;
	public static String type;
	public static int declarationsFound;
	public static int referencesFound;

	public static void main(String[] args) {
		Program program = new Program(args[0], args[1]);
		
		if (args[0].endsWith(".jar")) {
			int i = 0;
			ArrayList jarArrayList = program.jarArray;
			String []jayArray = new String[jarArrayList.size()];
			jarArrayList.toArray(jayArray);
			
			while (i < jarArrayList.size()) {
				JarFile jarFile;
				try {
					jarFile = new JarFile(args[0]);
					JarEntry entry = jarFile.getJarEntry(jayArray[i]);
					InputStream input = jarFile.getInputStream(entry);
					program.parse(process(input));
				    jarFile.close();
					i++;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}

		// Code taken from: http://adam-bien.com/roller/abien/entry/java_8_reading_a_file
		else {
			for (File getJavaFile:Program.path) {
				String fileToString;

				try {
					fileToString = new String(Files.readAllBytes(Paths.get(getJavaFile.toString())));
				} catch (IOException ioe) {
					continue;
				}

				program.parse(fileToString);
			}
		}
		
		System.out.println(type + ". Declarations found: " + declarationsFound + "; references found: " + referencesFound + ".");
	}
	
	// Code taken from: http://www.java2s.com/Code/Java/File-Input-Output/Readingatextfilefromajarfilewithoutunzipping.htm
	private static String process(InputStream input) throws IOException {
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader reader = new BufferedReader(isr);
		String line;
		String src = "";
		while ((line = reader.readLine()) != null) {
			src += line;
		}
		
		reader.close();
		return src;
	}
}