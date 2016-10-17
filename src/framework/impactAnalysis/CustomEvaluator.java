package framework.impactAnalysis;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.traversal.Evaluation;
import org.neo4j.graphdb.traversal.Evaluator;

/**
 * Custom Evaluator class defining what to return from a a traversal
 * Impact analysis rules are implemented by this class because they define if a given node should be added
 * to the result set
 */
public class CustomEvaluator implements Evaluator
{
	@Override
	public Evaluation evaluate(Path p) 
	{
		Node endNode = p.endNode();
		Node startNode = p.startNode();
		if(endNode.hasProperty("type")) 
		{
			return Evaluation.INCLUDE_AND_CONTINUE;
		}
		// else continue
		return Evaluation.EXCLUDE_AND_CONTINUE;
	}
}
