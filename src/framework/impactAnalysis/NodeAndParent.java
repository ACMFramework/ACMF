package framework.impactAnalysis;

import org.neo4j.graphdb.Node;

/**
 * 
 */
public class NodeAndParent 
{
	private Node node;
	private String nodeId;
	private String nodeType;
	
	private Node parent;
	private String parentId;
	private String parentType;
	
	public Node getNode() 
	{
		return node;
	}
	
	public void setNode(Node node) 
	{
		this.node = node;
	}
	
	public String getNodeId() 
	{
		return nodeId;
	}
	
	public void setNodeId(String nodeId)
	{
		this.nodeId = nodeId;
	}
	
	public String getNodeType() 
	{
		return nodeType;
	}
	
	public void setNodeType(String nodeType) 
	{
		this.nodeType = nodeType;
	}
	public Node getParent() {
		return parent;
	}
	
	public void setParent(Node parent) 
	{
		this.parent = parent;
	}
	
	public String getParentId() 
	{
		return parentId;
	}
	
	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	
	public String getParentType() 
	{
		return parentType;
	}
	
	public void setParentType(String parentType) 
	{
		this.parentType = parentType;
	}
}
