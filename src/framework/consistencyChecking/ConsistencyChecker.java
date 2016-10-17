package framework.consistencyChecking;

import java.util.ArrayList;
import java.util.List;
import framework.changeDetection.ChangeData;
import framework.changeDetection.FileLevelChangeType;
import framework.general.IExecutable;
import framework.general.IExecutionManager;
import framework.impactAnalysis.CustomNodeRepresentation;
import framework.impactAnalysis.IAResult;

/** Consistency checking functionality
 * 
 *
 */
public class ConsistencyChecker implements IExecutable
{
	/**Consistency check result object
	 * 
	 */
	ConsistencyCheckResult result;
	
	/**
	 * 
	 */
	IntraConsistencyCheckResult intraResult;

	/** The input of consistency checking is a list of ChangeData objects and the results of impact analysis
	 * 
	 */
	List<IAResult> input = null;

	/**
	 * 
	 * @return
	 */
	public List<IAResult> getInput()
	{
		return input;
	}

	/**
	 * 
	 * @param input
	 */
	public void setInput(List<IAResult> input) 
	{
		this.input = input;
	}

	/** The output of inter consistency checking is a list of ConsistencyCheckResults objects
	 * 
	 */
	private List<ConsistencyCheckResult> interOutput = null;
	
	/** The output of intra consistency checking is a list of IntraConsistencyCheckResults objects
	 * 
	 */
	private List<IntraConsistencyCheckResult> intraOutput = null;

	/**
	 * 
	 * @return
	 */
	public List<ConsistencyCheckResult> getInterOutput() 
	{
		return this.interOutput;
	}

	/**
	 * 
	 * @param output
	 */
	public void setInterOutput(List<ConsistencyCheckResult> output) 
	{
		this.interOutput = new ArrayList<ConsistencyCheckResult>();
		this.interOutput.addAll(output);
	}

	/**
	 * 
	 * @return
	 */
	public List<IntraConsistencyCheckResult> getIntraOutput() 
	{
		return this.intraOutput;
	}
	
	/**
	 * 
	 * @param output
	 */
	public void setIntraOutput(List<IntraConsistencyCheckResult> output) 
	{
		this.intraOutput = new ArrayList<IntraConsistencyCheckResult>();
		this.intraOutput.addAll(output);
	}
	
	/**
	 * 
	 */
	public ConsistencyChecker(IExecutionManager mg) 
	{
		mg.register(this);
	}

	/** Execute consistency checking based on input and produce consistency checking output
	 * 
	 */
	@Override
	public void Execute() 
	{
		List<ConsistencyCheckResult> ccResults = new ArrayList<ConsistencyCheckResult>();
		List<IntraConsistencyCheckResult> intraCCResults = new ArrayList<IntraConsistencyCheckResult>();
		List<IAResult> iaResults = getInput();

		for(int i = 0; i < iaResults.size(); i++) 
		{
			ChangeData change = iaResults.get(i).getChange();
			if(change.getTypeOfFileLevelChange().equals(FileLevelChangeType.ADD)) 
			{
				result = checkInterConsistencyAfterAdd(change);
				result.setChangedNode(change);
				ccResults.add(result);
			}
			else if(change.getTypeOfFileLevelChange().equals(FileLevelChangeType.DELETE)) 
			{
				// For each impact node in the impact node set, perform consistency checking
				if(iaResults.get(i).getInterTraversalNodeResults().size() > 0) 
				{
					for(int j = 0; j < iaResults.get(i).getInterTraversalNodeResults().size(); j++) 
					{
						result = checkInterConsistencyAfterDelete
								(change, iaResults.get(i).getCustomImpactNodeList().get(j));
						result.setMsg(CCMessage.NOMSG);
						ccResults.add(result);
					}
				}
				else 
				{
					result = new ConsistencyCheckResult();
					result.setMsg(CCMessage.NOINTERCC);
					result.setChangedNode(change);
					ccResults.add(result);
				}
			}
			// Edit changes
			else 
			{
				// For each impact node in the impact node set, perform consistency checking
				if(iaResults.get(i).getInterTraversalNodeResults().size() > 0) 
				{
					for(int j = 0; j < iaResults.get(i).getInterTraversalNodeResults().size(); j++) 
					{
						result = checkInterConsistencyAfterEdit
								(change, iaResults.get(i).getCustomImpactNodeList().get(j));
						result.setMsg(CCMessage.NOMSG);
						ccResults.add(result);
					}
				}
				// If there are no impact nodes as the node was added, do consistency checking of new nodes's parent
				// Otherwise return no cc result
				else 
				{
					if(iaResults.get(i).getParentsInterResults().getInterTraversalResults().size() > 0) 
					{
						for(int j = 0; j < iaResults.get(i).getParentsInterResults().getInterTraversalResults().size(); j++) 
						{
							result = checkInterConsistencyAfterEdit
									(change, iaResults.get(i).getParentsInterResults().getCustomImpactNodeList().get(j));
							result.setMsg(CCMessage.NOMSG);
							ccResults.add(result);
						}
					}
					else 
					{
						result = new ConsistencyCheckResult();
						result.setMsg(CCMessage.NOCC);
						result.setChangedNode(change);
						ccResults.add(result);
					}
				}
				// Also perform intra consistency checking
				if(iaResults.get(i).getCustomNodeIntraResult().size() > 0) 
				{
					// There is usually one parent, hence get first item from intra results
					intraResult = checkIntraConsistencyAfterEdit(change, iaResults.get(i).getCustomNodeIntraResult().get(0));
					intraResult.setMsg(CCMessage.NOMSG);
					intraCCResults.add(intraResult);
					
				}
				else 
				{
					intraResult = new IntraConsistencyCheckResult();
					intraResult.setMsg(CCMessage.NOINTRACC);
					intraResult.setChangedNode(change);
					intraCCResults.add(intraResult);
				}
			}
		}
		setInterOutput(ccResults);
		setIntraOutput(intraCCResults);
	}

	/** Run consistency check and return applicable consistency rule for the given change - impact node pair
	 * 
	 * @param change
	 * @param impactNode
	 * @return
	 */
	public ConsistencyCheckResult checkInterConsistencyAfterDelete(ChangeData change, CustomNodeRepresentation nodeResults) 
	{
		ConsistencyCheckResult result = new ConsistencyCheckResult();
		RuleBaseParser rulebaseParser = new RuleBaseParser();
		result.setChangedNode(change);
		result.setImpactNode(nodeResults);
		result.setApplicableRule(rulebaseParser.findDeleteInterRule(change, nodeResults));
		return result;
	}
	
	/** 
	 * Method for running consistency checks for add type changes
	 * @param change - the details of the change
	 */
	public ConsistencyCheckResult checkInterConsistencyAfterAdd(ChangeData change) 
	{
		ConsistencyCheckResult result = new ConsistencyCheckResult();
		result.setMsg(CCMessage.NOCC);
		System.out.println("Add File level change has been applied. Establish trace links of the new " 
		+ change.getCurrentName() + " " + change.getTypeOfElement_current());
		return result;
	}

	/** Method for running consistency checks for edit type changes
	 * 
	 * @param change
	 * @param nodeResults
	 * @return
	 */
	public ConsistencyCheckResult checkInterConsistencyAfterEdit(ChangeData change, CustomNodeRepresentation node) 
	{
		ConsistencyCheckResult result = new ConsistencyCheckResult();
		RuleBaseParser rulebaseParser = new RuleBaseParser();
		result.setChangedNode(change);
		result.setImpactNode(node);
		result.setApplicableRule(rulebaseParser.findEditInterRule(change, node));
		return result;
	}
	
	/** Method for running consistency checks for edit type changes and to return intra consistency rules
	 * 
	 * @param change
	 * @param nodeResults
	 * @return
	 */
	public IntraConsistencyCheckResult checkIntraConsistencyAfterEdit(ChangeData change, CustomNodeRepresentation nodeResults) 
	{
		IntraConsistencyCheckResult result = new IntraConsistencyCheckResult();
		RuleBaseParser rulebaseParser = new RuleBaseParser();
		result.setChangedNode(change);
		result.setImpactNode(nodeResults);
		result.setApplicableRule(rulebaseParser.findIntraRules(change, nodeResults));
		return result;
	}
}