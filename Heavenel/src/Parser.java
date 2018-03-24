import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

/**
* Parser Class, takes the String file Path and String type to recursively go through nodes to 
* search for occurrences of type references and type declarations.
**/

public class Parser {
	
	public void parse(String source, File file) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source.toCharArray());
		
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
		
		parser.setSource(source.toCharArray());
	    parser.setResolveBindings(true);
	    parser.setBindingsRecovery(true);
	    
	    String[] pathArray = {Main.pathName};
		parser.setEnvironment(null, pathArray, null, true);
		parser.setUnitName(file.getName());

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

	    ReferenceCounter.updateCounter(cu);
	    DeclarationCounter.updateCounter(cu);
	}
	

}
