import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;

public class ReferenceCounter {
	
private int count=0; //Initialize count to 0
	
	public int getcount(){
		return count; //return current reference count
	}
	
	public void updateCounter(CompilationUnit cu, String type) {
		// Count References
		cu.accept(new ASTVisitor() {public boolean 	visit(FieldDeclaration node) { 
			          ITypeBinding bind=  node.getType().resolveBinding();
			          String qualifiedName= node.getType().toString(); //get field type
			          if (bind!=null)
			          {
			        	qualifiedName= bind.getQualifiedName();
			          }
			        
						if (type.equals(qualifiedName)) { 
							count++; //if equal update counter 
						}
						return true;
					}
				});
	}

}