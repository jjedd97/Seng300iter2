import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationCounter {
	private int count=0; //Initialize count to 0
	
	public int getCount(){
		return count; //return current declaration count
	}
	
	
	public void updateCounter(CompilationUnit cu, String type) {
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(TypeDeclaration node) {
				ITypeBinding bind=node.resolveBinding();
				String qualifiedName = node.getName().getFullyQualifiedName(); //get node name
				if (bind!=null)
				{
					qualifiedName=bind.getQualifiedName();
				}
				if (type.equals(qualifiedName)) { //compare
					count++; //if equal update counter
				}
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(AnonymousClassDeclaration node) {
				ITypeBinding bind =node.resolveBinding();
	
				String qualifiedName = bind.getQualifiedName();//get node name
				if (type.equals(qualifiedName)) { //compare
					count++; //if equal update counter
				}
				return true;
			}
		});
	}

}
