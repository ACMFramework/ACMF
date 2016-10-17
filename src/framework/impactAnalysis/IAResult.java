package framework.impactAnalysis;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import framework.changeDetection.ChangeData;

/**
 * Class representing the results of impact analysis
 */
public class IAResult 
{
	/**
	 * 
	 */
	private ChangeData change = new ChangeData();
	
	/**
	 * 
	 */
	private Node changedNode = null;
	
	/** Node representation of the parent of the changed node
	 * 
	 */
	private Node parentOfChangedNode = null;
	
	/** IAResult object holding the inter traversal results of the parent of the changed node
	 * 
	 */
	private ParentIAResult parentsInterResults = new ParentIAResult();

	/**
	 * 
	 */
	private ArrayList<Path> intraTraversalDefaultResults = new ArrayList<Path>();
	private ArrayList<Path> intraTraversalParentChildResults = new ArrayList<Path>();
	private ArrayList<Path> interTraversalResults = new ArrayList<Path>();
	
	/** Impact nodes
	 * 
	 */
	private ArrayList<Node> intraTraversalDefaultNodeResults = new ArrayList<Node>();
	private ArrayList<Node> intraTraversalParentChildNodeResults = new ArrayList<Node>();
	private ArrayList<Node> interTraversalNodeResults = new ArrayList<Node>();

	/** A list holding the specifics of the impact nodes for inter consistency checking
	 * 
	 */
	private List<CustomNodeRepresentation> listOfImpactNodes = new ArrayList<CustomNodeRepresentation>();
	
	/** A list holding the specifics of the parent nodes of the changed nodes
	 * 
	 */
	private List<CustomNodeRepresentation> customParentNodeResult = new ArrayList<CustomNodeRepresentation>();

	/** A list holding the specifics of the given impact node for intra consistency checking
	 * 
	 */
	private List<CustomNodeRepresentation> customNodeIntraResult = new ArrayList<CustomNodeRepresentation>();
	
	private List<NodeAndParent> nodeAndParent;
	
	public List<NodeAndParent> getNodeAndParent() 
	{
		return nodeAndParent;
	}

	public void setNodeAndParent(List<NodeAndParent> nodeAndParent) 
	{
		this.nodeAndParent = nodeAndParent;
	}

	/**
	 * 
	 */
	private IAMessage msg;

	/**
	 * 
	 * @return
	 */
	public ChangeData getChange() 
	{
		return change;
	}
	
	/**
	 * 
	 * @param change
	 */
	public void setChange(ChangeData change) 
	{
		this.change = change;
	}
	
	/**
	 * 
	 * @return
	 */
	public Node getChangedNode() 
	{
		return changedNode;
	}
	
	/**
	 * 
	 * @param node
	 */
	public void setChangedNode(Node node) 
	{
		this.changedNode = node;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Path> getIntraTraversalDefaultResults() 
	{
		return intraTraversalDefaultResults;
	}

	/**
	 * 
	 * @param intraTraversalDefaultResults
	 */
	public void setIntraTraversalDefaultResults(ArrayList<Path> intraTraversalDefaultResults) 
	{
		this.intraTraversalDefaultResults = intraTraversalDefaultResults;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Path> getIntraTraversalParentChildResults() 
	{
		return intraTraversalParentChildResults;
	}

	/**
	 * 
	 * @param intraTraversalParentChildResults
	 */
	public void setIntraTraversalParentChildResults(ArrayList<Path> intraTraversalParentChildResults) 
	{
		this.intraTraversalParentChildResults = intraTraversalParentChildResults;
	}

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
	 * @return
	 */
	public ArrayList<Node> getIntraTraversalDefaultNodeResults() 
	{
		return intraTraversalDefaultNodeResults;
	}

	/**
	 * 
	 * @param intraTraversalDefaultNodeResults
	 */
	public void setIntraTraversalDefaultNodeResults(ArrayList<Node> intraTraversalDefaultNodeResults) 
	{
		this.intraTraversalDefaultNodeResults = intraTraversalDefaultNodeResults;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Node> getIntraTraversalParentChildNodeResults() 
	{
		return intraTraversalParentChildNodeResults;
	}

	/**
	 * 
	 * @param intraTraversalParentChildNodeResults
	 */
	public void setIntraTraversalParentChildNodeResults(ArrayList<Node> intraTraversalParentChildNodeResults) 
	{
		this.intraTraversalParentChildNodeResults = intraTraversalParentChildNodeResults;
	}

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
	
	/**
	 * 
	 * @return
	 */
	public Node getContainerNodeOfChangedNode() 
	{
		return parentOfChangedNode;
	}

	/**
	 * 
	 * @param containerNodeOfChangedNode
	 */
	public void setContainerNodeOfChangedNode(Node containerNodeOfChangedNode) 
	{
		this.parentOfChangedNode = containerNodeOfChangedNode;
	}
	
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

	/**
	 * 
	 */
	public List<CustomNodeRepresentation> getCustomParentNodeResult() 
	{
		return customParentNodeResult;
	}

	/**
	 * 
	 */
	public void setCustomParentNodeResult(List<CustomNodeRepresentation> customParentNodeResult) 
	{
		this.customParentNodeResult.addAll(customParentNodeResult);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<CustomNodeRepresentation> getCustomNodeIntraResult() 
	{
		return customNodeIntraResult;
	}

	/**
	 * 
	 * @param customNodeIntraResult
	 */
	public void setCustomNodeIntraResult(List<CustomNodeRepresentation> customNodeIntraResult) 
	{
		this.customNodeIntraResult = customNodeIntraResult;
	}

	/**
	 * 
	 * @return
	 */
	public ParentIAResult getParentsInterResults() 
	{
		return parentsInterResults;
	}

	/**
	 * 
	 * @param parentsInterResults
	 */
	public void setParentsInterResults(ParentIAResult parentsInterResults) 
	{
		this.parentsInterResults = parentsInterResults;
	}
	
	/**
	 * 
	 * @return
	 */
	public IAMessage getMsg() 
	{
		return msg;
	}

	/**
	 * 
	 * @param msg
	 */
	public void setMsg(IAMessage msg) 
	{
		this.msg = msg;
	}
}
