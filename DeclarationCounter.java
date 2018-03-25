import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.Expression;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.ReturnStatement;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class DeclarationCounter {
	private int count=0; //Initialize count to 0
	//create a static list of all declarations found
	private static List<String> listdeclaration=new ArrayList<>();

	public List<String> getList(){
		return listdeclaration;
		
	}

	
	
	public void updateCounter(CompilationUnit cu) {
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(TypeDeclaration node) {
				ITypeBinding bind=node.resolveBinding();
				String qualifiedName = node.getName().getFullyQualifiedName(); //get node name
				if (bind!=null)
				{
					qualifiedName=bind.getQualifiedName();
					listdeclaration.add(qualifiedName);
					//System.out.println(qualifiedName);
				}
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(AnonymousClassDeclaration node) {
				ITypeBinding bind =node.resolveBinding();
	
				String qualifiedName = bind.getQualifiedName();//get node name
				listdeclaration.add(qualifiedName);
				//System.out.println(qualifiedName);
				return true;
			}
		});
		
		cu.accept(new ASTVisitor() { //create visitor for TypeDeclaration
			// Count Declarations
			public boolean visit(MarkerAnnotation node) {
				ITypeBinding bind =node.resolveTypeBinding();
				String qualifiedName = bind.getQualifiedName();//get node name
				listdeclaration.add(qualifiedName);
				//System.out.println(qualifiedName);
				return true;
			}
		});
		

	}

}
