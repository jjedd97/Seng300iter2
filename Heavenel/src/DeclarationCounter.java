import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationCounter {
	
	public static void updateCounter(CompilationUnit cu) {
		
		// Visitor for TypeDeclaration node
		cu.accept(new ASTVisitor() { 
			public boolean visit(TypeDeclaration node) {
				String nodeName = node.getName().getFullyQualifiedName();
				ITypeBinding bindedNode = node.resolveBinding();
				nodeName = bindedNode.getQualifiedName();
				
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
		
		// Visitor for MarkerAnnotation node
		cu.accept(new ASTVisitor() { 
			public boolean visit(MarkerAnnotation node) {
				ITypeBinding bindedNode = node.resolveTypeBinding();
				String nodeName = bindedNode.getQualifiedName();
				
				if (Main.type.equals(nodeName)) 
					Main.declarationsFound++; 

				return true;
			}});
		
	}
}
