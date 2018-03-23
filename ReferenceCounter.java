import java.util.Arrays;
import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.Name;
import org.eclipse.jdt.core.dom.SimpleType;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

public class ReferenceCounter {
	
private int count=0; //Initialize count to 0
	
	public int getcount(){
		return count; //return current reference count
	}
	
	public void updateCounter(CompilationUnit cu, String type) {
		// Count References
		cu.accept(new ASTVisitor() {public boolean 	 visit(VariableDeclarationStatement node){
			for (Iterator iter = node.fragments().iterator(); iter.hasNext();) {	 
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
				IBinding binding = fragment.getName().resolveBinding();
				String name = fragment.getName().resolveBinding().toString();
				String[] parts = name.split(" ");
				String qualifiedName = parts[0];
				if (type.equals(qualifiedName)) { //compare
					count++; //if equal update counter
				}
			}
			return true;
		}
				});
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
		cu.accept(new ASTVisitor() {public boolean 	visit(ImportDeclaration node) { 
			IBinding bind=  node.resolveBinding();
	        String name= node.toString(); 
	        String[] parts = name.split(" ");
	        String namec=parts[parts.length-1];
	        String[] parts2 = namec.split(";");
	        String qualifiedName= parts2[0];
				if (type.equals(qualifiedName)) { 
					count++; //if equal update counter 
				} 
				return true;
			}
		});
	}

}
