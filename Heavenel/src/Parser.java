import java.util.ArrayList;
import java.util.HashSet;
import java.util.Collections;
import java.util.Map;
import java.io.File;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;

public class Parser {
	
	public static void counter() {
		
		ArrayList <String> references = ReferenceCounter.getList();
		ArrayList <String> declarations = DeclarationCounter.getList();
		
		HashSet<String> referencesSet = new HashSet<String>(references);
		
		HashSet<String> list = new HashSet<String>();
		list.addAll(referencesSet);
		list.addAll(declarations);
		
		for (String type : list) {
			Main.referencesFound = Collections.frequency(references, type);
			Main.declarationsFound = Collections.frequency(declarations, type);
			System.out.println(type + ". Declarations found: " + Main.declarationsFound +  "; References found: " + Main.referencesFound + ".");
		}
			
	}
				  
	public void parse(String source, File file) {
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setSource(source.toCharArray());
		
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
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
