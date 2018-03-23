import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Reader {
	private File[] files = null;
	
	public Reader() {
		files = finder(Main.pathName);
	}
	
	private File[] finder(String path){
        File dir = new File(path);
        
        return dir.listFiles(new FilenameFilter() { 
        	public boolean accept(File dir, String fileName) {
        		return fileName.endsWith(".java");
        	}
        });    
    }
	
	public void parseFiles() throws IOException {
		for (File file : files) {
			if (file.isFile()) {
				String fileString = new String(Files.readAllBytes(Paths.get(file.toString())));
				
				Parser parser = new Parser();
				parser.parse(fileString);
			}
		}
	}
	
}