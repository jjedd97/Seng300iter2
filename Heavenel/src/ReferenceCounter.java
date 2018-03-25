import java.util.Iterator;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.ArrayAccess;
import org.eclipse.jdt.core.dom.ArrayCreation;
import org.eclipse.jdt.core.dom.ArrayInitializer;
import org.eclipse.jdt.core.dom.CastExpression;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.IAnnotationBinding;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.IPackageBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.ImportDeclaration;
import org.eclipse.jdt.core.dom.MarkerAnnotation;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.NormalAnnotation;
import org.eclipse.jdt.core.dom.PackageDeclaration;
import org.eclipse.jdt.core.dom.SingleMemberAnnotation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.Type;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;
import org.eclipse.jdt.core.dom.VariableDeclarationStatement;


public class ReferenceCounter {

	public static void updateCounter(CompilationUnit cu) {
		
		cu.accept(new ASTVisitor() {public boolean 	 visit(VariableDeclarationStatement node){
			for (Iterator iter = node.fragments().iterator(); iter.hasNext();) {	 
				VariableDeclarationFragment fragment = (VariableDeclarationFragment) iter.next();
				
				String name = fragment.getName().resolveBinding().toString();
				String[] parts = name.split(" ");
				String qualifiedName = parts[0];
				
				if (Main.type.equals(qualifiedName))
					Main.referencesFound++;
			}
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean 	visit(FieldDeclaration node) { 
	          ITypeBinding bind =  node.getType().resolveBinding();
	          String qualifiedName = node.getType().toString();
	          
	          if (bind != null)
	        	qualifiedName= bind.getQualifiedName();
	          if (Main.type.equals(qualifiedName))
				Main.referencesFound++;
	          
			return true;
		}});
	
		cu.accept(new ASTVisitor() {public boolean 	visit(ImportDeclaration node) { 
	        String name = node.toString(); 
	        
	        String[] parts = name.split(" ");
	        String nameC = parts[parts.length - 1];
	        String[] parts2 = nameC.split(";");
	        String qualifiedName = parts2[0];
	        
			if (Main.type.equals(qualifiedName))
				Main.referencesFound++;
				
			return true;
			}});
		
		cu.accept(new ASTVisitor() {public boolean visit(MethodDeclaration node) {
			Type returnType = node.getReturnType2();		
			
			if (returnType != null) {
				ITypeBinding parameterBind = returnType.resolveBinding();
				String qualifiedName = parameterBind.getQualifiedName();
				
				if (Main.type.equals(qualifiedName))
					Main.referencesFound++;
			}
			
			for (Object aParameter : node.parameters()) {
				SingleVariableDeclaration svd = (SingleVariableDeclaration) aParameter;
				
				if (Main.type.equals(svd.getType().toString())) 
					Main.referencesFound++;
			}
			
			return true;
		}});

		cu.accept(new ASTVisitor() { public boolean visit(ClassInstanceCreation node) {
			ITypeBinding bind=node.resolveTypeBinding();
			String qualifiedName = node.getType().toString();
			
			if (bind != null)
	        	qualifiedName= bind.getQualifiedName();
			if (Main.type.equals(qualifiedName)) 
				Main.referencesFound++;	
				
			return true; 
		}});  
			
		cu.accept(new ASTVisitor() {public boolean visit(EnumDeclaration node) {
			ITypeBinding bind = node.resolveBinding();
			String[] parts = bind.toString().split(" ");
			String name = parts[4];
			String[] parts2 = name.toString().split("<");
			String qualifiedName = parts2[0];
			
			if (Main.type.equals(qualifiedName)) 
					Main.referencesFound++;	
			
			if (node.resolveBinding().getInterfaces() != null) {
				ITypeBinding[] interfaces = node.resolveBinding().getInterfaces();
				
				for (ITypeBinding i : interfaces) 
					if (Main.type.equals(i.getQualifiedName())) 
						Main.referencesFound++;	
			}
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(EnumConstantDeclaration node) {
			ITypeBinding bind = node.resolveVariable().getType();
			String[] parts = bind.toString().split(" ");
			String name = parts[4];
			String[] parts2 = name.toString().split("<");
			String qualifiedName = parts2[0];
			
			if (Main.type.equals(qualifiedName)) 
				Main.referencesFound++;	
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(ArrayAccess node) {	
			ITypeBinding bindedNode = node.resolveTypeBinding();
			String nodeName = bindedNode.getQualifiedName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(ArrayInitializer node) {	
			ITypeBinding bindedNode = node.resolveTypeBinding();
			String nodeName = bindedNode.getQualifiedName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(ArrayCreation node) {	
			ITypeBinding bindedNode = node.resolveTypeBinding();
			String nodeName = bindedNode.getQualifiedName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(AnonymousClassDeclaration node) {	
			ITypeBinding bindedNode = node.resolveBinding();
			String nodeName = bindedNode.getQualifiedName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(CastExpression node) {	
			ITypeBinding bindedNode = node.resolveTypeBinding();
			String nodeName = bindedNode.getQualifiedName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(PackageDeclaration node) {	
			IPackageBinding bindedNode = node.resolveBinding();
			String nodeName = bindedNode.getName();
			
			if (Main.type.equals(nodeName)) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(AnnotationTypeDeclaration node) {	
			if (Main.type.equals("java.lang.annotation")) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(AnnotationTypeMemberDeclaration node) {			
			if (Main.type.equals("java.lang.annotation")) 
				Main.referencesFound++; 
			
			return true;
		}});
		
		cu.accept(new ASTVisitor() {public boolean visit(NormalAnnotation node) {	
			if (Main.type.equals("java.lang.annotation")) 
				Main.referencesFound++; 
			return true;
		}});
		
		// Visitor for MarkerAnnotation node
		cu.accept(new ASTVisitor() {public boolean visit(MarkerAnnotation node) {
				ITypeBinding bindedNode = node.resolveTypeBinding();
				String nodeName = bindedNode.getQualifiedName();
				
				if (Main.type.equals("java.lang.annotation")) 
					Main.referencesFound++; 

				return true;
			}});
		
		// Visitor for MarkerAnnotation node
		cu.accept(new ASTVisitor() {public boolean visit(SingleMemberAnnotation node) {
				ITypeBinding bindedNode = node.resolveTypeBinding();
				String nodeName = bindedNode.getQualifiedName();
				
				if (Main.type.equals("java.lang.annotation")) 
					Main.referencesFound++; 

				return true;
			}});
		
		
	}	
}
