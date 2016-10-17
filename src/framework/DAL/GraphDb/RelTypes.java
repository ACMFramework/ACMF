package framework.DAL.GraphDb;

import org.neo4j.graphdb.RelationshipType;

/** Enum representing relationship types used in the framework
*
*/
public enum RelTypes implements RelationshipType
{
	INTER_REL,
	_default
}
