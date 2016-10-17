package framework.consistencyChecking;

import framework.changeDetection.ChangeData;
import framework.impactAnalysis.CustomNodeRepresentation;

/**
 * Class representing the results of consistency checking
 */
public class ConsistencyCheckResult implements IConsistencyResult
{
	/**
	 * 
	 */
	private String applicableRule;

	/**
	 * 
	 */
	private ChangeData changedEntity;
	
	/**
	 * 
	 */
	private CustomNodeRepresentation impactNode;
	
	/**
	 * 
	 */
	private CCMessage msg;

	/**
	 * 
	 * @return
	 */
	public CustomNodeRepresentation getImpactNode() 
	{
		return impactNode;
	}

	/**
	 * 
	 * @param impactNode
	 */
	public void setImpactNode(CustomNodeRepresentation impactNode) 
	{
		this.impactNode = impactNode;
	}

	/**
	 * 
	 * @return
	 */
	public String getApplicableRule() 
	{
		return applicableRule;
	}

	/**
	 * 
	 * @param applicableIntraRule
	 */
	public void setApplicableRule(String applicableRule) 
	{
		this.applicableRule = applicableRule;
	}
	
	/**
	 * 
	 */
	public ChangeData getChangedNode() 
	{
		return changedEntity;
	}

	/**
	 * 
	 * @param changedNodeName
	 */
	public void setChangedNode(ChangeData change) 
	{
		this.changedEntity = change;
	}
	
	/**
	 * 
	 * @return
	 */
	public CCMessage getMsg() 
	{
		return msg;
	}

	/**
	 * 
	 * @param msg
	 */
	public void setMsg(CCMessage msg) 
	{
		this.msg = msg;
	}
	
	/**
	 *Overriden toString() method to supply required details
	 */
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Changed node:" + getChangedNode());
		sb.append(", ");
		sb.append("Impacted node:" + getImpactNode());
		sb.append(", ");
		sb.append("Applicable inter consistency rule:" + getApplicableRule());
		return sb.toString();
	}
}
