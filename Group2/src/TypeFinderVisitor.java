import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class TypeFinderVisitor extends ASTVisitor {

	private int declCount = 0;
	private int refCount = 0;

	public void parse( char[] fileData ) {

		//Instantiate a JLS8 parser object
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(fileData);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setBindingsRecovery(true);
		parser.setResolveBindings(true);

		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		parser.setCompilerOptions(options);

		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);

		ASTVisitor v = new ASTVisitor() {
			@SuppressWarnings( "rawtypes" )
			Set names = new HashSet();

			public boolean visit(VariableDeclarationFragment node) {
				String parent = node.getParent().toString();
				SimpleName name = node.getName();
				this.names.add(name.getIdentifier());

				//Code borrowed from stackoverflow
				//finds index of a substring in a string
				int index = 0;

				while (index != -1) {
					index = parent.indexOf(ASTParserAnalysis.getType(), index);

					if (index != -1) {
						declCount++;
						index += ASTParserAnalysis.getType().length();
					}
				}

				if (node.toString().lastIndexOf("=") != -1) {
					for ( Object nodeName : names) {
						String ndName = (String) nodeName;
						if (node.toString().substring(node.toString().lastIndexOf("="), node.toString().length()).contains(ndName)) {
							refCount++;
						}

					}
				}
				return false;
			}

		};

		cu.accept(v);

	}

	/**
	 * @return the declCount
	 */
	public int getdeclCount() {
		return declCount;
	}

	/**
	 * @return the refCount
	 */
	public int getrefCount() {
		return refCount;
	}
}