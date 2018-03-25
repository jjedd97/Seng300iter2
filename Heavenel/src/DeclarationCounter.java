import java.util.ArrayList;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationCounter {
	
	private static ArrayList<String> declarations = new ArrayList<>();
	
	public static void updateCounter(CompilationUnit cu) {
		
		// Visitor for TypeDeclaration node
		cu.accept(new ASTVisitor() { 
			public boolean visit(TypeDeclaration node) {
				String nodeName = node.getName().getFullyQualifiedName();
				ITypeBinding bindedNode = node.resolveBinding();
				nodeName = bindedNode.getQualifiedName();
				node.getParent().getAST();
				
				if (Main.type.equals(nodeName)) 
					Main.declarationsFound++; 
					
				return true;
			}});
		
		// Visitor for AnonymousClassDeclaration node
		cu.accept(new ASTVisitor() {
			public boolean visit(AnonymousClassDeclaration node) {
				ITypeBinding bindedNode = node.resolveBinding();
				String nodeName = bindedNode.getQualifiedName();
				
				if (Main.type.equals(nodeName)) 
					Main.declarationsFound++; 
					
				return true;
			}});
	
	}
	
	public ArrayList<String> getDeclarations(){
		return declarations;
		
	}

}
