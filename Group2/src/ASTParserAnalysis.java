import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ASTParserAnalysis {

	public static String[] primitive = {"int", "char", "float", "boolean", "double", "long", "String"};
	private static String type;
	private static int declCount = 0;
	private static int refCount = 0;
	private static String abspath;

	public static void main(String[] args) throws IOException {
		String path = args[0];
		type = args[1];

		abspath = (new File("").getAbsolutePath().concat(path));

		ParseFilesInDir(abspath);
		printCounts();
	}

	//read file content into a string
	public static String readFileToString(String filePath) throws IOException {
		StringBuilder fileData = new StringBuilder(1000);
		BufferedReader reader = new BufferedReader(new FileReader(filePath));

		char[] buf = new char[10];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
			buf = new char[1024];
		}

		reader.close();

		return  fileData.toString();
	}

	//loop directory to get file list
	public static void ParseFilesInDir(String path) throws IOException{
		File root = new File(path);
		File[] files = root.listFiles();

		TypeFinderVisitor t = new TypeFinderVisitor();

		 for (File f : files ) {
			 if ((f.isFile() && f.getName().endsWith(".java"))) {
				 t.parse(readFileToString(f.getAbsolutePath()).toCharArray());
			 }
		 }
		 setDeclCount(t.getdeclCount());
		 refCount = t.getrefCount();
	}

	//Print output
	public static void printCounts() {
		System.out.print(type + "." + " Declarations found: " + getDeclCount() + "; ");
		System.out.println("references found: " + refCount);
	}

	public static String getType() {
		return type;
	}

	public static int getDeclCount() {
		return declCount;
	}

	/**
	 * @param declCount the declCount to set
	 */
	public static void setDeclCount(int declCount) {
		ASTParserAnalysis.declCount = declCount;
	}
}