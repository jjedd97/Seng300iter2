
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.ClassInstanceCreation;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.IMethodBinding;
import org.eclipse.jdt.core.dom.ITypeBinding;
import org.eclipse.jdt.core.dom.IVariableBinding;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SingleVariableDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

/**
* Parser Class, takes the String file Path and String type to recursively go through nodes to 
* search for occurrences of type references and type declarations.
**/

public class Parser {
	private String type = Main.type.toLowerCase();
	private int references = 0;
	private int declarations = 0;
	
	public void parse(String source) {
		Map option = JavaCore.getOptions();
		option.put(JavaCore.COMPILER_COMPLIANCE, JavaCore.VERSION_1_5);
		option.put(JavaCore.COMPILER_CODEGEN_TARGET_PLATFORM, JavaCore.VERSION_1_5);
		option.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_5);
		
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setCompilerOptions(option);
		parser.setSource(source.toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setResolveBindings(true);
	
		final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
	 
		cu.accept(new ASTVisitor() {
			public boolean visit(TypeDeclaration node) {	
				String name = node.getName().getFullyQualifiedName();
				ITypeBinding binding = node.resolveBinding();
				
				if (hasPackage) {
					if (binding.getPackage() != null) {
						name = binding.getPackage().getName() + "." + name;
					}
				}	
				
				if (type.equals(name)) {
					declarations++;
				}
				
				if (node.getSuperclassType() != null) {
					if (hasPackage) {
						ITypeBinding superBinding = node.getSuperclassType().resolveBinding();
						if (superBinding.getPackage() != null) {
							String superName = superBinding.getPackage().getName() + "." + node.getSuperclassType();
							if (type.equals(superName)) {
								references++;
							}
						}
					} else {
						if (type.equals(node.getSuperclassType().toString())) {
							references++;
						}
					}
				}
				
				if (binding.getInterfaces() != null) {
					ITypeBinding[] inter = binding.getInterfaces();
					if (hasPackage) {
						for (ITypeBinding i : inter) {
							if (type.equals(i.getQualifiedName())) {
								references++;
							}
						}
					} else {
						for (ITypeBinding i : inter) {
							if (type.equals(i.getName())) {
								references++;
							}
						}
					}
				}
				return super.visit(node);
			}	
			
			public boolean visit(VariableDeclarationFragment node) {	
				String name = "";
				if (hasPackage) {
					name = node.resolveBinding().getType().getQualifiedName();
				} else {
					name = node.resolveBinding().getType().getName();
				}
				if (type.equals(name)) {
					references++;
				}
				return super.visit(node);
			}	
			
			public boolean visit(MethodDeclaration node) {	
				String name = "";
				IMethodBinding iMethBinding = node.resolveBinding();
				if (hasPackage) {
					name = iMethBinding.getReturnType().getQualifiedName();
					if (type.equals(name)) {
						references ++;
					}
				}
				else {
					name = iMethBinding.getReturnType().toString();
					if (type.equals(name)) {
						references ++;
					}
				}		
				for (Object o : node.parameters()) {
					SingleVariableDeclaration singleVarDec = (SingleVariableDeclaration) o;
					if (hasPackage) {
						IVariableBinding binding = singleVarDec.resolveBinding();
						name = binding.getType().getQualifiedName();
						if (type.equals(name)) {
							references++;						
						}
					} else {
						name = singleVarDec.getType().toString();
						if (type.equals(name)) {
							references++;
						}
					}
				}
				return super.visit(node);
			}
			
			public boolean visit(MethodInvocation node) {
				return super.visit(node);
			}
			
			public boolean visit(ClassInstanceCreation node) {
				String name = "";
				if (hasPackage) {
					name = node.resolveTypeBinding().getQualifiedName();			
				} else {
					name = node.getType().toString();
				}
				if (type.equals(name)) {
					references++;				
				}

				return false;
			}
			
			public boolean visit(AnnotationTypeDeclaration node) {
				String name;
				if (hasPackage) {
					name = node.resolveBinding().getQualifiedName();		
				} else {
					name = node.getName().getFullyQualifiedName();
				}
				if (type.equals(name)) {
					declarations++;
				}
								
				return false; 
			}
			
			public boolean visit(EnumDeclaration node) {
				String name;
				if (hasPackage) {
					name = node.resolveBinding().getQualifiedName();		
				} else {
					name = node.getName().getFullyQualifiedName();
				}
				if (type.equals(name)) {
					declarations++;
				}
								
				return false;
			}
		});
		
		Main.referencesFound = references;
		Main.declarationsFound = declarations;
	}
	

}
