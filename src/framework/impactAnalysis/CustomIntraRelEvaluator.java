package framework.impactAnalysis;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;
import framework.DAL.GraphDb.Neo4jEmbeddedDao;

/**
 * Custom Evaluator class defining what to return from a a traversal
 */
public class CustomIntraRelEvaluator implements Evaluator
{
	@Override
	public Evaluation evaluate(Path p) 
	{
		Node endNode = p.endNode();

		for(org.neo4j.graphdb.Relationship rel : endNode.getRelationships()) 
		{
			if(rel.getProperty(Neo4jEmbeddedDao.DB_EDGE_TRAVERSAL_PROPERTY).toString().equals(Neo4jEmbeddedDao.DB_EDGE_TRAVERSAL_PROPERTY_VALUE)) 
			{
				System.out.println("Found specific intra rel");
				return Evaluation.INCLUDE_AND_CONTINUE;
			}
		}
		// else continue
		return Evaluation.EXCLUDE_AND_CONTINUE;
	}
}
