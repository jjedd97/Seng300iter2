import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationCounter {
	
	public static void updateCounter(CompilationUnit cu) {
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(TypeDeclaration node) {
				ITypeBinding bind=node.resolveBinding();
				String qualifiedName = node.getName().getFullyQualifiedName(); //get node name
				if (bind!=null)
				{
					qualifiedName=bind.getQualifiedName();
				}
				if (Main.type.equals(qualifiedName)) { //compare
					Main.declarationsFound++; //if equal update counter
				}
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(AnonymousClassDeclaration node) {
				ITypeBinding bind =node.resolveBinding();
	
				String qualifiedName = bind.getQualifiedName();//get node name
				if (Main.type.equals(qualifiedName)) { //compare
					Main.declarationsFound++; //if equal update counter
				}
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(MarkerAnnotation node) {
				ITypeBinding bind =node.resolveTypeBinding();
				String qualifiedName = bind.getQualifiedName();//get node name
				System.out.println(bind.getQualifiedName());
				if (Main.type.equals(qualifiedName)) { //compare
					Main.declarationsFound++; //if equal update counter
				}
				return true;
			}
		});
	}

}
