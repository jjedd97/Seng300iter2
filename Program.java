import org.eclipse.jdt.core.dom.*;
import java.io.*;
import java.util.jar.JarFile;

public class Program extends Main {

	public Program(String pathName, String typeName) {
		path = finder(pathName);
		type = typeName;
	}

	// Code taken from: https://stackoverflow.com/questions/1384947/java-find-txt-files-in-specified-folder
	public File[] finder(String dirName) {
		if (dirName.endsWith(".jar")) {
			JarDir jarDir = new JarDir(dirName);
			File dir = new File(jarDir);
			return dir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".java");
				}
			});
		}
		else {
			File dir = new File(dirName);
			return dir.listFiles(new FilenameFilter() { 
				public boolean accept(File dir, String filename) {
					return filename.endsWith(".java");
				}
			});
		}
	}

	// Code taken from: http://www.javased.com/index.php?api=org.eclipse.jdt.core.dom.ASTParser
	public void parse(String source) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);

		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source.toCharArray());
		parser.setResolveBindings(true);
	    //parser.setBindingsRecovery(true);

		CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		// Code taken from: https://stackoverflow.com/questions/49179071/how-to-get-the-fully-qualified-name-of-node-in-an-ast
		cu.accept(new ASTVisitor() {
			public boolean visit(TypeDeclaration node) {
				node.resolveBinding();
				String fullyQualifiedName = node.getName().getFullyQualifiedName();
				if (type.equals(fullyQualifiedName)) {
					declarationsFound++;
				}
				return true;
			}

//			public boolean visit(FieldDeclaration node) {
//				String fullyQualifiedName = node.getType().toString();
//				if (type.equals(fullyQualifiedName)) {
//					referencesFound++;
//				}
//				return true;
//			}
			
			public boolean visit(FieldDeclaration node) {
				ITypeBinding bind = node.getType().resolveBinding();
				String fullyQualifiedName = node.getType().toString();
				if (bind != null) {
					fullyQualifiedName = bind.getQualifiedName();
				} if (type.equals(fullyQualifiedName)) { 
					referencesFound++;
				}
				return true;
			}
		});
	}
}