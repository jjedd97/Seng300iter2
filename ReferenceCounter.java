
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
<<<<<<< HEAD
import org.eclipse.jdt.core.dom.VariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
=======
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;

<<<<<<< HEAD
import com.sun.glass.ui.CommonDialogs.Type;
>>>>>>> 0be563616e6d3b12f55f5fe82f3966651b1d4e74
=======
>>>>>>> f8f4c65c9792e16266433c7cd70d9b236e98b4f8

public class ReferenceCounter {

private static List<String> listreferences=new ArrayList<>(); //creates a static list of references found

	public List<String> getList(){
		return listreferences;
		
	}
	
	public void updateCounter(CompilationUnit cu) {
		// Count References
		//code based on: https://www.programcreek.com/2014/01/how-to-resolve-bindings-when-using-eclipse-jdt-astparser/
		cu.accept(new ASTVisitor() {public boolean 	 visit(VariableDeclarationStatement node){
			for (Iterator iter = node.fragments().iterator(); iter.hasNext();) {	 
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
				IBinding binding = fragment.getName().resolveBinding();
				String name = fragment.getName().resolveBinding().toString();
				String[] parts = name.split(" ");
				String qualifiedName = parts[0];
				listreferences.add(qualifiedName);
				//System.out.println(qualifiedName);
			}
			return true;
		}
				});
		cu.accept(new ASTVisitor() {public boolean 	visit(FieldDeclaration node) { 
<<<<<<< HEAD
			          ITypeBinding bind=  node.getType().resolveBinding();
			          String qualifiedName= node.getType().toString(); //get field type
			          System.out.println(qualifiedName+" one");
			          if (bind!=null)
			          {
			        	qualifiedName= bind.getQualifiedName();
			        	System.out.println("Bind " +qualifiedName);
			          }
						if (type.equals(qualifiedName)) { 
							count++; //if equal update counter 
						}
						return true;
					}
				});
		cu.accept(new ASTVisitor() {public boolean 	visit(VariableDeclaration node) { 
			VariableDeclarationFragment fragment = (VariableDeclarationFragment) node ;
			IBinding bind = fragment.getName().resolveBinding();
	          String qualifiedName= node.toString(); //get field type
	          System.out.println(qualifiedName+" two");
	          if (bind!=null)
	          {
	        	//qualifiedName= bind.getQualifiedName();
	        	//System.out.println("Bind " +qualifiedName);
	          }
				if (type.equals(qualifiedName)) { 
					count++; //if equal update counter 
				}
				return true;
			}
		});
	}
=======
	          ITypeBinding bind=  node.getType().resolveBinding();
	          String qualifiedName= node.getType().toString(); //get field type
	          if (bind!=null)
	          {
	        	qualifiedName= bind.getQualifiedName();
	        	listreferences.add(qualifiedName);
	        	//System.out.println(qualifiedName);
	          }
	        
				return true;
			}
		});
		cu.accept(new ASTVisitor() {public boolean 	visit(ImportDeclaration node) { 
			IBinding bind=  node.resolveBinding();
			String qualifiedName=bind.getName();
			listreferences.add(qualifiedName);
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() {public boolean visit(MethodDeclaration node) {
			org.eclipse.jdt.core.dom.Type returntype=node.getReturnType2();
			ITypeBinding bind1=returntype.resolveBinding();
			String qualifiedName=bind1.getQualifiedName();
			listreferences.add(qualifiedName);
			//System.out.println(qualifiedName);
			for (Object o : node.parameters()) {
 				SingleVariableDeclaration svd = (SingleVariableDeclaration) o;
 				IVariableBinding bind=svd.resolveBinding();
 				String name=bind.toString();
 				String[] parts = name.split(" ");
				String qualifiedName2 = parts[0];
				listreferences.add(qualifiedName2);
 					
				}
			
			return false;
		}});
		

		
		

		cu.accept(new ASTVisitor() { public boolean visit(ClassInstanceCreation node) {
			ITypeBinding bind=node.resolveTypeBinding();
			String qualifiedName = node.getType().toString();
			if (bind!=null)
	          {
	        	qualifiedName= bind.getQualifiedName();
	        	listreferences.add(qualifiedName);
	        	//System.out.println(qualifiedName);
	          }
			return false; // do not continue 
	}});  
		
		

		
		
		cu.accept(new ASTVisitor() {public boolean visit(EnumDeclaration node) {
			String name = node.getName().getFullyQualifiedName();				
			ITypeBinding e = node.resolveBinding();
			if (e.getInterfaces() != null) {
				ITypeBinding[] interfaces = e.getInterfaces();
				for (ITypeBinding i : interfaces) {
					listreferences.add(i.getQualifiedName());			
				}
			}
			return false; // do not continue 
		}});
		
>>>>>>> 0be563616e6d3b12f55f5fe82f3966651b1d4e74

		
		
		
	
	}
}
