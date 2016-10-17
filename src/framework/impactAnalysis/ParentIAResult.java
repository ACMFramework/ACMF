package framework.impactAnalysis;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;

/** ParentIAResult class to represent the inter traversal results of a node's parent node
 *
 */
public class ParentIAResult 
{
	/**
	 * 
	 */
	private Node parentOfChangedNode = null;
	/**
	 * 
	 * @return
	 */
	public Node getParentOfChangedNode() 
	{
		return parentOfChangedNode;
	}
	
	/**
	 * 
	 * @param node
	 */
	public void setParentOfChangedNode(Node node) 
	{
		this.parentOfChangedNode = node;
	}

	/**
	 * 
	 */
	private ArrayList<Path> interTraversalResults = new ArrayList<Path>();
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Path> getInterTraversalResults() 
	{
		return interTraversalResults;
	}

	/**
	 * 
	 * @param interTraversalResults
	 */
	public void setInterTraversalResults(ArrayList<Path> interTraversalResults) 
	{
		this.interTraversalResults = interTraversalResults;
	}
	
	/**
	 * 
	 */
	private ArrayList<Node> interTraversalNodeResults = new ArrayList<Node>();
	
	/**
	 * 
	 * @return
	 */
	public ArrayList<Node> getInterTraversalNodeResults() 
	{
		return interTraversalNodeResults;
	}

	/**
	 * 
	 * @param interTraversalNodeResults
	 */
	public void setInterTraversalNodeResults(ArrayList<Node> interTraversalNodeResults) 
	{
		this.interTraversalNodeResults = interTraversalNodeResults;
	}
	
	/** A list holding the specifics of the impact nodes for inter consistency checking
	 * 
	 */
	private List<CustomNodeRepresentation> listOfImpactNodes = new ArrayList<CustomNodeRepresentation>();
	
	/**
	 * 
	 * @return
	 */
	public List<CustomNodeRepresentation> getCustomImpactNodeList() 
	{
		return listOfImpactNodes;
	}

	/**
	 * 
	 * @param customNodeResult
	 */
	public void setCustomImpactNodeList(List<CustomNodeRepresentation> customNodeResult) 
	{
		this.listOfImpactNodes.addAll(customNodeResult);
	}
}
