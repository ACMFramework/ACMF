package framework.ChangePropagation;

import java.util.List;

import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.FileLevelChangeType;
import framework.consistencyChecking.CCMessage;
import framework.consistencyChecking.ConsistencyCheckResult;
import framework.consistencyChecking.IConsistencyResult;
import framework.consistencyChecking.IntraConsistencyCheckResult;
import framework.general.IExecutable;
import framework.general.IExecutionManager;

/** Change propagation functionality
 *
 *
 */
public class ChangePropagator implements IExecutable
{

	/** The input of change propagation
	 * 
	 */
	List<ConsistencyCheckResult> interInput = null;

	/** The input of change propagation
	 * 
	 */
	List<IntraConsistencyCheckResult> intraInput = null;

	/**
	 * 
	 * @return
	 */
	public List<ConsistencyCheckResult> getInterInput()
	{
		return interInput;
	}

	/**
	 * 
	 * @param input
	 */
	public void setInterInput(List<ConsistencyCheckResult> input) 
	{
		this.interInput = input;
	}

	/**
	 * 
	 * @return
	 */
	public List<IntraConsistencyCheckResult> getIntraInput()
	{
		return intraInput;
	}

	/**
	 * 
	 * @param input
	 */
	public void setIntraInput(List<IntraConsistencyCheckResult> input) 
	{
		this.intraInput = input;
	}

	/**
	 * 
	 * @param mg
	 */
	public ChangePropagator(IExecutionManager mg) 
	{
		mg.register(this);
	}

	/**
	 * 
	 * @param rule
	 * @return
	 */
	private Boolean isPotentialConsistencyIssue(String rule) 
	{
		boolean inconsistent = rule.contains("inconsistent") ? true : false;
		return inconsistent;
	}

	/** 
	 * @param ConsistencyCheckResult ccResult - the results of consistency checking
	 */
	public void suggestResolution(IConsistencyResult ccResult) 
	{
		if(ccResult.getChangedNode().getTypeOfFileLevelChange().equals(FileLevelChangeType.ADD)) 
		{
			System.out.println("The " + ccResult.getChangedNode().getCurrentName() + " " + ccResult.getChangedNode().getTypeOfElement_current() + " has been added.");
			System.out.println("Change propagation suggestion: ");
			System.out.println("Add entity to related artefacts");
		}
		else if(ccResult.getChangedNode().getTypeOfFileLevelChange().equals(FileLevelChangeType.DELETE)) 
		{
			System.out.println("The " + ccResult.getChangedNode().getCurrentName() + " " + ccResult.getChangedNode().getTypeOfElement_current() + " has been deleted.");
			System.out.println("Change propagation suggestion: ");
			System.out.println("Delete " + ccResult.getImpactNode());
		}
		else 
		{
			System.out.println("The " + ccResult.getChangedNode().getCurrentName() + " " + ccResult.getChangedNode().getTypeOfElement_current() + " has been edited. Change type = " + ccResult.getChangedNode().getTypeOfChange());
			System.out.println("Change propagation suggestion: ");
			System.out.println("Edit " + ccResult.getImpactNode());
		}
	}

	/**  Execute change propagation
	 * 
	 */
	@Override
	public void Execute() 
	{
		List<ConsistencyCheckResult> ccResults = getInterInput();
		List<IntraConsistencyCheckResult> intraCCresults = getIntraInput();

		for(int i = 0; i < ccResults.size(); i++) 
		{
			if(ccResults.get(i).getMsg().equals(CCMessage.ERROR) || ccResults.get(i).getMsg().equals(CCMessage.NOCC) 
					|| ccResults.get(i).getMsg().equals(CCMessage.NOINTERCC)) 
			{
				System.out.println("No inter impact analysis and inter consistency checking took place. " +
						"Check connections are in place.");
			}
			else 
			{
				if (isPotentialConsistencyIssue(ccResults.get(i).getApplicableRule()))
					suggestResolution(ccResults.get(i));
				else
					printSummary(ccResults.get(i));
			}
		}

		for(int i = 0; i < intraCCresults.size(); i++) 
		{
			if(intraCCresults.get(i).getMsg().equals(CCMessage.NOINTRACC)) 
			{
				System.out.println("No intra impact analysis and intra consistency checking took place. " +
						"Check intra connections are in place.");
			}
			else 
			{
				if (isPotentialConsistencyIssue(intraCCresults.get(i).getApplicableRule()))
					suggestResolution(intraCCresults.get(i));
				else
					printSummary(intraCCresults.get(i));
			}
		}
	}

	/** Print message to user
	 * 
	 * @param ccResult
	 */
	private void printSummary(IConsistencyResult ccResult) 
	{
		System.out.println("Changed entity:" + ccResult.getChangedNode().getCurrentName());
		System.out.println("The framework did not identify potential inconsistencies, the user may however " +
				"check the following potentially impacted entity: " + ccResult.getImpactNode());
	}
}
