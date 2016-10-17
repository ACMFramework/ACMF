package framework.impactAnalysis;

import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

import framework.DAL.GraphDb.Neo4jEmbeddedDao;

/**
 * Custom Evaluator class defining what to return from a traversal
 */
public class CustomParentChildEvaluator implements Evaluator
{
	@Override
	public Evaluation evaluate(Path p) 
	{
		for(org.neo4j.graphdb.Relationship rel : p.relationships()) 
		{
			// If this is a parent-child relationship, include in the results
			if(rel.getProperty(rel.getPropertyKeys().iterator().next().toString()).equals(Neo4jEmbeddedDao.DB_EDGE_TRAVERSAL_PROPERTY_VALUE_PC))
			{
				return Evaluation.INCLUDE_AND_CONTINUE;
			}
		}	
		// else continue
		return Evaluation.EXCLUDE_AND_CONTINUE;
	}
}
