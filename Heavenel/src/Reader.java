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
	public static ArrayList jarArray;
	
	public Reader() {
		files = finder(Main.pathName);
	}
	
	public File[] finder(String dirName) {
		if (dirName.endsWith(".jar")) {
			JarDescender jarSearch = new JarDescender(dirName);

			try {
				jarArray = jarSearch.addFile();
				String jarString = String.join(", ", jarArray);
				File jarFile = new File(jarString);
				File[] jarList = new File[] {jarFile};
				return jarList;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else {
			File dir = new File(dirName);
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
	
	public void parseFiles(String[] args) throws IOException {
	
		if (args[0].endsWith(".jar")) {
			int i = 0;
			String[] jayArray = new String[jarArray.size()];
			jarArray.toArray(jayArray);
			
			while (i < jarArray.size()) {
				JarFile jarFile = new JarFile(args[0]);
				JarEntry entry = jarFile.getJarEntry(jayArray[i]);
				InputStream input = jarFile.getInputStream(entry);
				Parser.parse(processer(input), jayArray[i]);
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