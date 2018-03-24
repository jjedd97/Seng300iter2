import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class ReferenceCounter {

	public static void updateCounter(CompilationUnit cu) {
		// Count References
		cu.accept(new ASTVisitor() {public boolean 	 visit(VariableDeclarationStatement node){
			for (Iterator iter = node.fragments().iterator(); iter.hasNext();) {	 
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
				IBinding binding = fragment.getName().resolveBinding();
				String name = fragment.getName().resolveBinding().toString();
				String[] parts = name.split(" ");
				String qualifiedName = parts[0];
				if (Main.type.equals(qualifiedName)) { //compare
					Main.referencesFound++; //if equal update counter
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
	        
				if (Main.type.equals(qualifiedName)) { 
					Main.referencesFound++; //if equal update counter 
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
				if (Main.type.equals(qualifiedName)) { 
					Main.referencesFound++; //if equal update counter
				} 
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() {public boolean visit(MethodDeclaration node) {
			org.eclipse.jdt.core.dom.Type returntype=node.getReturnType2();
			ITypeBinding bind1=returntype.resolveBinding();
			String qualifiedName=bind1.getQualifiedName();
			if (Main.type.equals(qualifiedName)) { 
				Main.referencesFound++; //if equal update counter 
			} 
			
			for (Object o : node.parameters()) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
				IVariableBinding bind=svd.resolveBinding();
				if (Main.type.equals(svd.getType().toString())) { 
					Main.referencesFound++;
				}
			}
			
			return false;
		}});
		

		
		

		cu.accept(new ASTVisitor() { public boolean visit(ClassInstanceCreation node) {
			ITypeBinding bind=node.resolveTypeBinding();
			String qualifiedName = node.getType().toString();
			if (bind!=null)
	          {
	        	qualifiedName= bind.getQualifiedName();
	          }
			if (Main.type.equals(qualifiedName)) {
				Main.referencesFound++;	
			}
			return false; // do not continue 
	}});  
		
		

		
		
		cu.accept(new ASTVisitor() {public boolean visit(EnumDeclaration node) {
			String name = node.getName().getFullyQualifiedName();				
			ITypeBinding e = node.resolveBinding();
			if (e.getInterfaces() != null) {
				ITypeBinding[] interfaces = e.getInterfaces();
				for (ITypeBinding i : interfaces) {
					if (Main.type.equals(i.getQualifiedName())) {
						Main.referencesFound++;
					}
					
				}
			}
			

			
			return false; // do not continue 
		}});
	
	}
}
