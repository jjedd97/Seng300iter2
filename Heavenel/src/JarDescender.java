import java.io.*;
import java.util.jar.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class JarDescender {
	
	public static ArrayList jarList = new ArrayList();
	public static String source;
	
	public JarDescender(String jarFile) {
		source = jarFile;
	}
	
	public ArrayList addFile() throws IOException {
		JarFile jarFile = new JarFile(source);
		Enumeration allEntries = jarFile.entries();
		while (allEntries.hasMoreElements()) {
			process(allEntries.nextElement());
		}
		jarFile.close();
		return jarList;
	}

	private static void process(Object obj) {
		JarEntry entry = (JarEntry)obj;
		String name = entry.getName();
		if (name.endsWith(".java")) {
			jarList.add(name);
		}
	}
}