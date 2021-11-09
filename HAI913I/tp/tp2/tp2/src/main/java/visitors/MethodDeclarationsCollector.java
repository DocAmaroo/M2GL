package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class MethodDeclarationsCollector extends ASTVisitor {
	/* ATTRIBUTES */
	private List<MethodDeclaration> methods = new ArrayList<>();

	/* METHODS */
	@Override
	public boolean visit(MethodDeclaration methodDeclaration) {
		methods.add(methodDeclaration);
		return false;
	}
	
	public List<MethodDeclaration> getMethods() {
		return methods;
	}
}
