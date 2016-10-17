package framework.integrated.Tests;

import static org.junit.Assert.*;
import org.junit.Test;
import framework.changeDetection.ChangeExtractor;
import framework.general.SequentialExecutionManager;

/**
 * 
 * Test the functionality of the ChangeExtractor component
 * Input: User initiation
 * Output: List of files added/edited/deleted
 *
 */
public class ChangeExtractorTest 
{	
	/**
	 * 
	 */
	SequentialExecutionManager mg = new SequentialExecutionManager();
	
	/**
	 * 
	 */
	ChangeExtractor tr = new ChangeExtractor(mg);
	
	/**
	 * 
	 */
	@Test
	public void testChangeExtraction() 
	{
		mg.doProcess();
	}
}
