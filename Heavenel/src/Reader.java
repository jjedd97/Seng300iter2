import java.io.BufferedReader;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class Reader {
	private static List<java.io.File> files = new ArrayList<java.io.File>();
	private static ArrayList jarList;
	
	public Reader() throws IOException {
		files = find();
	}
	
	private static void search(File root) {   
		if(root.isDirectory() ) 
	        for(File file : root.listFiles()) 
	            search(file);
	            
	    else if(root.isFile() && root.getName().endsWith(".java")) 
	        files.add(root);
	}
	
	private static List<java.io.File> find() throws IOException {
		if (Main.pathName.endsWith(".jar")) 
			jarList = JarDescender.addFile(Main.pathName);
			
		else {
			File dir = new File(Main.pathName);
			search(dir);
		}
		return files;
	}
	
	private static String processEntry (InputStream input) throws IOException {
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
				
				Parser.parse(processEntry(input), jarArray[i]);
				
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