package step2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

public class TypeDeclarationVisitor extends ASTVisitor {
	List<TypeDeclaration> types = new ArrayList<TypeDeclaration>();
	List<FieldDeclaration> attributs = new ArrayList<FieldDeclaration>();
	
	int length = 0;
	
	public boolean visit(TypeDeclaration node) {
		types.add(node);
		this.length = node.getLength();
		this.attributs = Arrays.asList(node.getFields());
		return super.visit(node);
	}
	
	public List<TypeDeclaration> getTypes() {
		return types;
	}
	
	public int getLength() {
		return length;
	}
	
	public List<FieldDeclaration> getAttribus() {
		return attributs;
	}
}
