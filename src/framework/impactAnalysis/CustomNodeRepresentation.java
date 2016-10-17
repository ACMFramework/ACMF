package framework.impactAnalysis;

import org.neo4j.graphdb.Node;

/**
 * 
 */
public class CustomNodeRepresentation 
{
	private Node node;
	private String type;
	private String id;

	private Node parent;
	private String parentType;
	private String parentId;
	
	/**
	 * 
	 * @param node2
	 * @param type2
	 * @param id2
	 */
	public CustomNodeRepresentation(Node node2, String type2, String id2) 
	{
		this.node = node2;
		this.type = type2;
		this.id = id2;
	}

	public Node getNode() 
	{
		return node;
	}

	public void setNode(Node node) 
	{
		this.node = node;
	}

	public String getType() 
	{
		return type;
	}

	public void setType(String type) 
	{
		this.type = type;
	}

	public String getId() 
	{
		return id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public Node getParent() 
	{
		return parent;
	}

	public void setParent(Node parent) 
	{
		this.parent = parent;
	}

	public String getParentType() 
	{
		return parentType;
	}

	public void setParentType(String parentType) 
	{
		this.parentType = parentType;
	}

	public String getParentId() 
	{
		return parentId;
	}

	public void setParentId(String parentId) 
	{
		this.parentId = parentId;
	}
	
	/**
	 *Overriden toString() method to supply required details
	 */
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Node id:" + getId());
		sb.append(", ");
		sb.append("Node type:" + getType());
		sb.append(", ");
		if(getParentId() != null) 
		{
			sb.append("Parent id:" + getParentId());
			sb.append(", ");
		}
		if(getParentType() != null) 
		{
			sb.append("Parent type:" + getParentType());
		}
		return sb.toString();
	}
}
