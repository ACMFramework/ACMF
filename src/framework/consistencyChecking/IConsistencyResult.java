package framework.consistencyChecking;

import framework.changeDetection.ChangeData;
import framework.impactAnalysis.CustomNodeRepresentation;

/**
 * 
 *
 */
public interface IConsistencyResult 
{
	/**
	 * 
	 */
	public String getApplicableRule();

	/**
	 * 
	 */
	public void setApplicableRule(String applicableRule);
	
	/**
	 * 
	 * @return
	 */
	public CustomNodeRepresentation getImpactNode();

	/**
	 * 
	 * @param impactNode
	 */
	public void setImpactNode(CustomNodeRepresentation impactNode);

	/**
	 * 
	 */
	public ChangeData getChangedNode();

	/**
	 * 
	 * @param changedNodeName
	 */
	public void setChangedNode(ChangeData change);
	
	/**
	 * 
	 * @return
	 */
	public CCMessage getMsg();

	/**
	 * 
	 * @param msg
	 */
	public void setMsg(CCMessage msg);
}
