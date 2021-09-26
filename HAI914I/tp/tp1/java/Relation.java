package Exemples;

import org.neo4j.graphdb.RelationshipType;


public enum Relation implements RelationshipType {

    WORKS_WITH, WORKS_FOR, FRIEND, OWNS, ENROLLED_IN
}