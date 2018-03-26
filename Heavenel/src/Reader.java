import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Reader {
	private File[] files = null;
	public static ArrayList jarList;
	
	public Reader() throws IOException {
		files = finder();
	}
	
	private File[] finder() throws IOException {
		if (Main.pathName.endsWith(".jar")) 
			jarList = JarDescender.addFile(Main.pathName);
			
		else {
			File dir = new File(Main.pathName);
			return dir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".java");
				}
			});
		}
		return null;
	}
	
	private static String processer (InputStream input) throws IOException {
		InputStreamReader isr = new InputStreamReader(input);
		BufferedReader reader = new BufferedReader(isr);
		String line;
		String src = "";
		
		while ((line = reader.readLine()) != null)
			src += line;
		
		reader.close();
		return src;
	}
	
	public void parseFiles() throws IOException {
	
		if (Main.pathName.endsWith(".jar")) {
			int i = 0;
			
			String[] jarArray = new String[jarList.size()];
			jarList.toArray(jarArray);
			
			while (i < jarList.size()) {
				JarFile jarFile = new JarFile(Main.pathName);
				JarEntry entry = jarFile.getJarEntry(jarArray[i]);
				InputStream input = jarFile.getInputStream(entry);
				
				Parser.parse(processer(input), jarArray[i]);
				
			    jarFile.close();
				i++;
			}
		}
		
		else {
			for (File file : files) {
				if (file.isFile()) {
					String fileString = new String(Files.readAllBytes(Paths.get(file.toString())));
					Parser.parse(fileString, file.getName());
				}
			}
		}
	}
	
}