import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class HandleJar {
	
	private ArrayList jarAR;						//ArrayList of Jar Files found
	private int iter = 0;							//Iterator
	private String jarPath;

	public HandleJar(String jarPath, ArrayList jarArray) {
		jarAR = jarArray;
		jarPath = jarPath;
	}
	
	public String ParseJar() {
		/*
		 * Converting between ArrayList to String Array and then back ??/
		 */
		String[] convJarArray = new String[jarAR.size()];
		jarAR.toArray(convJarArray);
		
		String processed = null;
		
		while(iter < jarAR.size()) {
			JarFile cwFile;						//current working File (cwFile)
			JarEntry entry = null;
			InputStream input;
			try {
				cwFile = new JarFile(jarPath);
				entry = cwFile.getJarEntry(convJarArray[iter]);
				input = cwFile.getInputStream(entry);
				
				processed += process(input);
				
				cwFile.close();
				iter++;
				

				
			}catch (IOException e) {
				System.out.println("IOException");
				
			}	
			
		}
		return processed;
	}
	
	/* Code taken from: http://www.java2s.com/Code/Java/File-Input-Output/Readingatextfilefromajarfilewithoutunzipping.htm
	 * 
	 */
	public static String process(InputStream input) throws IOException {
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
		
		
		
		
/*		
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
		}*/
		
		
	
	
}

