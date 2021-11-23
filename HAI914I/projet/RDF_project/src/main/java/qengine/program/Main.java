package qengine.program;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.eclipse.rdf4j.query.algebra.Projection;
import org.eclipse.rdf4j.query.algebra.StatementPattern;
import org.eclipse.rdf4j.query.algebra.helpers.AbstractQueryModelVisitor;
import org.eclipse.rdf4j.query.algebra.helpers.StatementPatternCollector;
import org.eclipse.rdf4j.query.parser.ParsedQuery;
import org.eclipse.rdf4j.query.parser.sparql.SPARQLParser;
import org.eclipse.rdf4j.rio.RDFFormat;
import org.eclipse.rdf4j.rio.RDFParser;
import org.eclipse.rdf4j.rio.Rio;
import qengine.program.logs.Log;
import qengine.program.parsers.DictionaryRDFHandler;
import qengine.program.parsers.IndexationRDFHandler;
import qengine.program.parsers.MainRDFHandler;
import qengine.program.utils.Utils;

final class Main {
	static final String baseURI = null;
	static final String queryFilename = "sample_query.queryset";
	static final String dataFilename = "sample_data.nt";

	/**
	 * Votre répertoire de travail où vont se trouver les fichiers à lire
	 */
	static final String workingDir = "data/";

	/**
	 * Fichier contenant les requêtes sparql
	 */
	static final String queryFile = workingDir + queryFilename;

	/**
	 * Fichier contenant des données rdf
	 */
	static final String dataFile = workingDir + dataFilename;

	static final Dictionary dictionary = Dictionary.getInstance();

	static final Indexation indexation = Indexation.getInstance();
	// ========================================================================

	/**
	 * Méthode utilisée ici lors du parsing de requête sparql pour agir sur l'objet obtenu.
	 */
	public static void processAQuery(ParsedQuery query) {


		List<StatementPattern> patterns = StatementPatternCollector.process(query.getTupleExpr());

		System.out.println("first pattern : " + patterns.get(0));

		System.out.println("object of the first pattern : " + patterns.get(0).getObjectVar().getValue());

		System.out.println("variables to project : ");

		// Utilisation d'une classe anonyme
		query.getTupleExpr().visit(new AbstractQueryModelVisitor<RuntimeException>() {

			public void meet(Projection projection) {
				System.out.println(projection.getProjectionElemList().getElements());
			}
		});
	}

	/**
	 * Entrée du programme
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("# Parsing data ----------------------------------------");
		parseData();

		System.out.println("# Parsing queries ----------------------------------------");
		parseQueries();

		indexation.displayIndexByName("spo");
		indexation.displayIndexByName("sop");

		// Display on the console and save the logs
		Log.save();
	}

	// ========================================================================

	/**
	 * Traite chaque requête lue dans {@link #queryFile} avec {@link #processAQuery(ParsedQuery)}.
	 */
	private static void parseQueries() throws FileNotFoundException, IOException {
		/**
		 * Try-with-resources
		 * 
		 * @see <a href="https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html">Try-with-resources</a>
		 */
		/*
		 * On utilise un stream pour lire les lignes une par une, sans avoir à toutes les stocker
		 * entièrement dans une collection.
		 */
		try (Stream<String> lineStream = Files.lines(Paths.get(queryFile))) {
			SPARQLParser sparqlParser = new SPARQLParser();
			Iterator<String> lineIterator = lineStream.iterator();
			StringBuilder queryString = new StringBuilder();

			while (lineIterator.hasNext())
			/*
			 * On stocke plusieurs lignes jusqu'à ce que l'une d'entre elles se termine par un '}'
			 * On considère alors que c'est la fin d'une requête
			 */
			{
				String line = lineIterator.next();
				queryString.append(line);

				if (line.trim().endsWith("}")) {
					ParsedQuery query = sparqlParser.parseQuery(queryString.toString(), baseURI);

					processAQuery(query); // Traitement de la requête, à adapter/réécrire pour votre programme

					queryString.setLength(0); // Reset le buffer de la requête en chaine vide
				}
			}
		}
	}

	/**
	 * Traite chaque triple lu dans {@link #dataFile} avec {@link MainRDFHandler}.
	 */
	private static void parseData() throws FileNotFoundException, IOException {

		// Utiliser pour stocker le temps de départ et de fin d'évaluation
		long startTimer;
		long endTimer;

		// Mise en place du dictionnaire --------------------------------------------------
		try (Reader dataReader = new FileReader(dataFile)) {
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			DictionaryRDFHandler dictionaryRDFHandler = new DictionaryRDFHandler();
			rdfParser.setRDFHandler(dictionaryRDFHandler);

			startTimer = Utils.getCurrentTime();
			rdfParser.parse(dataReader, baseURI);

			endTimer = System.currentTimeMillis() - startTimer;
			System.out.println("[+] Dictionary done! ("+endTimer+"ms)");
			Log.setExecTimeDictionary(endTimer);
		}
		// --------------------------------------------------------------------------------

		// Mise en place de l'indexation --------------------------------------------------
		try (Reader dataReader = new FileReader(dataFile)) {
			RDFParser rdfParser = Rio.createParser(RDFFormat.NTRIPLES);

			IndexationRDFHandler indexationRDFHandler = new IndexationRDFHandler();
			rdfParser.setRDFHandler(indexationRDFHandler);

			startTimer = Utils.getCurrentTime();
			rdfParser.parse(dataReader, baseURI);

			endTimer = System.currentTimeMillis() - startTimer;
			System.out.println("[+] Indexation done! ("+endTimer+"ms)");
			Log.setExecTimeIndexation(endTimer);
		}
		// --------------------------------------------------------------------------------
	}
}
