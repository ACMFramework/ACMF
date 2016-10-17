package framework.consistencyManagement.Tests;

import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.ChangePropagation.ChangePropagator;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ChangeDetectionManager;
import framework.changeDetection.ChangeExtractor;
import framework.changeDetection.ChangeIdentifier;
import framework.consistencyChecking.ConsistencyCheckResult;
import framework.consistencyChecking.ConsistencyChecker;
import framework.general.SequentialExecutionManager;
import framework.impactAnalysis.ChangeImpactAnalyser;
import framework.impactAnalysis.IAResult;

/**
 * Test change impact analysis functionality
 */
public class ConsistencyManagementTester 
{
	/**
	 * 
	 */
	static SequentialExecutionManager mg = new SequentialExecutionManager();

	/**
	 * 
	 */
	static ChangeExtractor ce = new ChangeExtractor(mg);

	/**
	 * 
	 */
	static ChangeIdentifier ci = new ChangeIdentifier(mg);

	/**
	 * 
	 */
	static ChangeImpactAnalyser impact = new ChangeImpactAnalyser(mg);

	/**
	 * 
	 */
	static ConsistencyChecker cons = new ConsistencyChecker(mg);

	/**
	 * 
	 */
	static ChangePropagator cp = new ChangePropagator(mg);

	/**
	 * 
	 */
	static List<IAResult> impactOutput;

	/**
	 * 
	 */
	List<ConsistencyCheckResult> ccResults;

	/**
	 *
	 */
	@BeforeClass
	public static void setup() 
	{
		ChangeDetectionManager m = new ChangeDetectionManager();
		long startTime = System.nanoTime();
		m.manageChangeDetection();
		List<ChangeData> changes = m.getChangeDetectionOutput();

		impact.setInput(changes);
		impact.Execute();
		impactOutput = impact.getOutput();

		cons.setInput(impactOutput);
		cons.Execute();
		System.out.println("**************");
		System.out.println("CONSISTENCY CHECKING OUTPUT");
		System.out.println("**************");
		for(int i = 0; i < cons.getInterOutput().size(); i++) 
		{
			System.out.println(cons.getInterOutput().get(i).toString());
		}
		cp.setInterInput(cons.getInterOutput());
		cp.Execute();
		long endTime = System.nanoTime();
		long changeDetectionDuration = endTime - startTime;
		System.out.println("Duration of consistency management: "+ changeDetectionDuration /1E9);
	}

	@Test
	public void test() 
	{
	}
}
