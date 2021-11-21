package visitors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SuperMethodInvocation;

public class MethodInvocationsCollector extends ASTVisitor {
	/* ATTRIBUTES */
	private List<MethodInvocation> methodInvocations = new ArrayList<>();
	private List<SuperMethodInvocation> superMethodInvocations = new ArrayList<>();
	
	/* METHODS */
	@Override
	public boolean visit(MethodInvocation methodInvocation) {
		methodInvocations.add(methodInvocation);
		return super.visit(methodInvocation);
	}
	
	@Override
	public boolean visit(SuperMethodInvocation superMethodInvocation) {
		superMethodInvocations.add(superMethodInvocation);
		return super.visit(superMethodInvocation);
	}
	
	public List<MethodInvocation> getMethodInvocations(){
		return methodInvocations;
	}
	
	public List<SuperMethodInvocation> getSuperMethodInvocations() {
		return superMethodInvocations;
	}
	
	public boolean isEmpty() {
		return methodInvocations.isEmpty() && superMethodInvocations.isEmpty();
	}
}
