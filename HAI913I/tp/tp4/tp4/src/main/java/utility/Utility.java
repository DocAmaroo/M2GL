package utility;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class Utility {
	
	public static String getClassFullyQualifiedName(TypeDeclaration typeDeclaration) {
		String name = typeDeclaration.getName().getIdentifier();
		
		if (typeDeclaration.getRoot().getClass() == CompilationUnit.class) {
			CompilationUnit root = (CompilationUnit) typeDeclaration.getRoot();
			
			if (root.getPackage() != null)
				name = root.getPackage().getName().getFullyQualifiedName() + "." + name;
		}
		
		return name;
	}
	
	public static String getMethodFullyQualifiedName(TypeDeclaration cls, MethodDeclaration method) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(getClassFullyQualifiedName(cls));
		buffer.append("::");
		buffer.append(method.getName());
		
		return buffer.toString();
	}
}
