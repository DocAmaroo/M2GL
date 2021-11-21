package analyse;

import org.eclipse.jdt.core.dom.*;

import java.util.HashSet;
import java.util.Set;

public class JDTMain {

	public static void main(String[] args) {
        
        ASTParser parser = ASTParser.newParser(AST.JLS3); 
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
//		parser.setSource("public class A { int i = 9;  \\n int j; \\n ArrayList<Integer> al = new ArrayList<Integer>();j=1000; }".toCharArray()); // set source
		parser.setSource(ClassAsString.classAsString.toCharArray()); // set source
		parser.setResolveBindings(true); // we need bindings later on
		CompilationUnit cu = (CompilationUnit) parser.createAST(null /* IProgressMonitor */); // parse
		
		cu.accept(new ASTVisitor() {
			
			Set names = new HashSet();
			
			public boolean visit(VariableDeclarationFragment node) {
				SimpleName name= node.getName();
				this.names.add(name.getIdentifier());
				
				//récupère l'AST du code analysé 
				 AST ast = cu.getAST();
				
				 //on récupère le nom de la var
             	SimpleName nodename = ast.newSimpleName(node.getName().toString());

				//Crée les noeuds pour les méthodes getter et setter
				MethodDeclaration getterDeclaration = ast.newMethodDeclaration();
				getterDeclaration.setName( ast.newSimpleName("get"+ node.getName()));
				
				MethodDeclaration setterDeclaration = ast.newMethodDeclaration();
				setterDeclaration.setName(ast.newSimpleName("set" + node.getName()));

			    //je remonte sur le parent du noeud et je regarde si c'est un champ de declaration
			    if(node.getParent() instanceof FieldDeclaration){
			    	//je récupère le neoud de declaration
	                FieldDeclaration declaration = ((FieldDeclaration) node.getParent());
	                
	                //je crée mes variables pourles attr du setter
				    VariableDeclaration var = cu.getAST().newSingleVariableDeclaration(); 

	                //si c'est un type simple
	                if(declaration.getType().isSimpleType()){
	                	//je recupère le nom du type
	                    String typeSimpleName = declaration.getType().toString();
	                    //et je recupère le type
	                    Type type = declaration.getType();
	                    
	                    //je set le type de retour
	                	getterDeclaration.setReturnType2(ast.newSimpleType(ast.newName(typeSimpleName)));
	                	var.setStructuralProperty(SingleVariableDeclaration.TYPE_PROPERTY, 
	                							  cu.getAST().newSimpleType(ast.newName(typeSimpleName)));
	                	var.setName(nodename);
	                	//j'ajoute le parametre a la method set
	                	setterDeclaration.parameters().add(var);
	                } else {
	                	//je set le type de retour
	                	getterDeclaration.setReturnType2(ast.newPrimitiveType(PrimitiveType.INT));
	                	var.setStructuralProperty(SingleVariableDeclaration.TYPE_PROPERTY, 
	                							  ast.newPrimitiveType(PrimitiveType.INT));
	                	var.setName(nodename);
	                	setterDeclaration.parameters().add(var);
	                }
	            }
			    //return statement pour la methode getter
				org.eclipse.jdt.core.dom.Block codeGetter = ast.newBlock();
				ReturnStatement statement =  ast.newReturnStatement();
				SimpleName sn= ast.newSimpleName(node.getName().toString());
				statement.setExpression(sn);
				codeGetter.statements().add(statement);
				getterDeclaration.setBody(codeGetter);
				
				//corp pour la methode setter
				org.eclipse.jdt.core.dom.Block codeSetter = ast.newBlock();
				//on crée une assignement pour l'affectation de var
			    Assignment newAssignment = ast.newAssignment();
			    
			    //on crée un field pour faire le this.var
				FieldAccess fieldAccess = ast.newFieldAccess();
				fieldAccess.setExpression(ast.newThisExpression());
				fieldAccess.setName(ast.newSimpleName(node.getName().toString()));
				newAssignment.setLeftHandSide(fieldAccess);
				//on set l'operateur d'affectation
				newAssignment.setOperator(Assignment.Operator.ASSIGN);
				//on met la partie droite de l'affectation
				newAssignment.setRightHandSide(ast.newSimpleName(node.getName().toString()));
				    
				//on ajoute l'assignment aux statements de la methode
				codeSetter.statements().add(ast.newExpressionStatement(newAssignment));
				//on ajoute le block dans le body de la methode
				setterDeclaration.setBody(codeSetter);
				
				//ajout des methodes a la classe
				TypeDeclaration td = (TypeDeclaration )cu.types().get(0);
				td.bodyDeclarations().add(getterDeclaration);
				td.bodyDeclarations().add(setterDeclaration);

				return false; // do not continue to avoid usage info
			}
 
			public boolean visit(SimpleName node) {
				if (this.names.contains(node.getIdentifier())) {
				//System.out.println("Usage of '" + node + "' at line " +	cu.getLineNumber(node.getStartPosition()));
					System.out.println("simple " + node.getIdentifier() );

				}
				return true;
			}
			
			
			public boolean visit(MethodDeclaration m) {
				
				System.out.print(m.getReturnType2() + " " + m.getName() + "(");
				if(m.parameters().size() > 0) {
				for(int i =0; i< m.parameters().size() -1; i++) {
					System.out.print(m.parameters().get(i).toString() + ", ");
				}
				System.out.print(m.parameters().get(m.parameters().size()-1));
				}
				System.out.print(")");
				System.out.print(m.getBody().toString());
				return false;
				
			}
			
		});
	}
}
