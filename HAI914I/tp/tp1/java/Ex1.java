package Exemples;

import java.io.File;
import java.util.Map;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;



public class Ex1 {
	   private static final File DB_Folder = new File("/xxxxxx/neo4j-community-3.5.21/data/databases/graph.db");

	   private static enum Ex_Labels implements Label {
	        DOCTOR,
	        TEACHER,
	        STUDENT,
	        SCHOOL
	    }
	   
	   
	   public static void main(String[] args) {
	      GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
	      GraphDatabaseService graphDB = dbFactory.newEmbeddedDatabase(DB_Folder);

	      try (Transaction tx =graphDB.beginTx()) {
	    	  
	    	   Node tom_node =graphDB.createNode(Ex_Labels.STUDENT);	
	           Node um_node =graphDB.createNode(Ex_Labels.SCHOOL);
	           um_node.setProperty("name", "UM");
	           um_node.setProperty("city", "Montpellier");
	           um_node.setProperty("from", "France");
	           tom_node.setProperty("name", "Tom");
	           tom_node.createRelationshipTo(um_node, Relation.ENROLLED_IN);
	           
	           Result result = graphDB.execute(
	 				  "MATCH (u:SCHOOL) <-[ENROLLED_IN]- (s:STUDENT) " +
	 				  "WHERE s.name = 'Tom'" +
	 				  "RETURN u.name, u.city");
	 		 while ( result.hasNext() )
	 	     {
	 	         Map<String, Object> row = result.next();
	 	         for ( String key : result.columns() )
	 	         {
	 	             System.out.printf( "%s = %s%n", key, row.get( key ) );
	 	         }
	 	     }
	           
	           
	           tx.success();
	      }
	   }
}
