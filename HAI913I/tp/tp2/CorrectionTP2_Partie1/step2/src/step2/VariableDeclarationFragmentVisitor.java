package step2;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.VariableDeclarationFragment;

public class VariableDeclarationFragmentVisitor extends ASTVisitor {
	private List<VariableDeclarationFragment> variables = new ArrayList<VariableDeclarationFragment>();
	
	public boolean visit(VariableDeclarationFragment node) {
		variables.add(node);
		return super.visit(node);
	}
	
	public List<VariableDeclarationFragment> getVariables() {
		return variables;
	}
}
