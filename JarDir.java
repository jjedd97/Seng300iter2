import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarDir {
	
	public static String jar;

	public JarDir(String jarName) {
		jar = listJar(jarName);
	}

	public void listJar(String newName) throws IOException {
		JarFile jarFile = new JarFile(newName);
		Enumeration allEntries = jarFile.entries();
		while (allEntries.hasMoreElements()) {
			JarEntry entry = (JarEntry) allEntries.nextElement();
			String name = entry.getName();
        }
    }
}