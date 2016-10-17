package framework.consistencyChecking;

import framework.changeDetection.ChangeData;
import framework.impactAnalysis.CustomNodeRepresentation;

/** Class representing the results  of intra consistency checking
 * 
 */
public class IntraConsistencyCheckResult implements IConsistencyResult
{
	/**
	 * 
	 */
	private String applicableIntraRule;
	
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
	 */
	@Override
	public String getApplicableRule() 
	{
		return applicableIntraRule;
	}

	/**
	 * 
	 */
	@Override
	public void setApplicableRule(String applicableIntraRule) 
	{
		this.applicableIntraRule = applicableIntraRule;
	}
	
	/**
	 * 
	 * @return
	 */
	@Override
	public CustomNodeRepresentation getImpactNode() 
	{
		return impactNode;
	}

	/**
	 * 
	 * @param impactNode
	 */
	@Override
	public void setImpactNode(CustomNodeRepresentation impactNode) 
	{
		this.impactNode = impactNode;
	}

	/**
	 * 
	 */
	@Override
	public ChangeData getChangedNode() 
	{
		return changedEntity;
	}

	/**
	 * 
	 * @param changedNodeName
	 */
	@Override
	public void setChangedNode(ChangeData change) 
	{
		this.changedEntity = change;
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
		sb.append("Applicable intra consistency rule:" + getApplicableRule());
		return sb.toString();
	}

	/**
	 * 
	 */
	@Override
	public CCMessage getMsg() 
	{
		return msg;
	}

	/**
	 * 
	 */
	@Override
	public void setMsg(CCMessage msg) 
	{
		this.msg = msg;
	}
}
