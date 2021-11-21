package processors;

import parsers.EclipseJDTASTParser;

public abstract class ASTProcessor extends Processor<EclipseJDTASTParser>{
	/* CONSTRUCTOR */
	public ASTProcessor(String projectPath) {
		super(projectPath);
	}
	
	/* METHODS */
	@Override
	public void setParser(String projectPath) {
		parser = new EclipseJDTASTParser(projectPath);
	}
	
	public void setParser(EclipseJDTASTParser parser) {
		this.parser = parser;
	}
}
