package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class ClassDeclarationsCollector extends ASTVisitor {
	/* ATTRIBUTES */
	private List<TypeDeclaration> classes = new ArrayList<>();
	
	/* METHODS */
	@Override
	public boolean visit(TypeDeclaration typeDeclaration) {
		if(!typeDeclaration.isInterface())
			classes.add(typeDeclaration);
		
		return false;
	}
	
	public List<TypeDeclaration> getClasses(){
		return classes;
	}
}
