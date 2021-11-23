package qengine.program.parsers;

import org.eclipse.rdf4j.model.Statement;
import org.eclipse.rdf4j.rio.helpers.AbstractRDFHandler;
import qengine.program.Dictionary;

public final class DictionaryRDFHandler extends AbstractRDFHandler {

	private final Dictionary dictionary;

	public DictionaryRDFHandler() {
		dictionary = Dictionary.getInstance();
	}

	@Override
	public void handleStatement(Statement st) {
		// For debug purpose only
		// System.out.println("[i] Statement: " + st.getSubject() + "\t " + st.getPredicate() + "\t " + st.getObject());

		dictionary.addWord(st.getSubject().stringValue());
		dictionary.addPredicate(st.getPredicate().stringValue());
		dictionary.addWord(st.getObject().stringValue());
	};
}