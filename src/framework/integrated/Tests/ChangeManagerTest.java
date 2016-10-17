package framework.integrated.Tests;

import java.io.IOException;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ChangeDetectionManager;
import framework.changeDetection.ChangeExtractor;
import framework.changeDetection.ChangeIdentifier;
import framework.changeDetection.GraphDbUpdater;
import framework.general.SequentialExecutionManager;

/** Test change detection functionality 
 * a, through manager class
 * b, through individual executable classes
 */
public class ChangeManagerTest 
{
	private SequentialExecutionManager manager = new SequentialExecutionManager();
	private ChangeExtractor extractor = new ChangeExtractor(manager);
	private ChangeIdentifier identifier = new ChangeIdentifier(manager);
	//private GraphDbUpdater dbUpdater = new GraphDbUpdater(manager);
	
	/**
	 * 
	 */
	private ChangeDetectionManager changeManager = new ChangeDetectionManager();
	
	/**
	 * @throws IOException 
	 *
	 */
	@BeforeClass
	public static void setup() throws IOException 
	{		
		
	}
	
	/**
	 * 
	 */
	
	public void testChangeDetectionOrchestration() 
	{
		manager.register(extractor);
		manager.register(identifier);
		//manager.register(dbUpdater);
		manager.doProcess();
	}
	
	/**
	 * 
	 */
	@Test
	public void testChangeExtraction() 
	{
		extractor.Execute();
		extractor.getAdded();
		extractor.getDeleted();
		extractor.getEdited();
	}
	
	/** Test change detection for edit changes = when items in the repository are edited
	 * This tests the change identification algorithm
	 * For this test the following .java files were used: TitanGraphComputer
	 * Results are manually checked at the end of the test
	 * The same tests are applicable to JUnit test cases
	 */
	@Test
	public void testEditChangeIdentificationSourceCode() 
	{
		// 1. Edit class visibililty: correctly passed
		// 2. Edit class name: correctly flagged up as a delete and add change
		// 3. Edit class inheritance / implementation: correctly passed
		// *4. Add new modifier - e.g. static: not caught TODO: not part of graphml file
		// 5. Add new method: correctly passed
		// 6. Add new field: correctly passed
		// 7. Delete existing field: correctly passed
		// 8. Delete existing method: correctly passed
		// 9. Edit method name: correctly flagged up as delete and add
		// 10. Edit method parameters: correctly passed
		// 11. Edit method visibility: correctly passed
		// 12. Edit method return type: correctly passed
		// 13. Edit method contents: correctly passed
		// 14. Edit field name: correctly flagged up as a delete (of the old name) and add change (of the new name)
		// 15. Edit field visibility: correctly passed
		// 16. Edit field type: correctly passed
		// 17. Edit existing field and delete another one: correctly passed
		// 18. Edit existing field and add another one: correctly passed
		
		List<ChangeData> editChanges = null;
		editChanges = changeManager.doEditChangeDetection();
	}
	
	/** Test change detection for add changes = when items are added to the repository
	 * The same tests are applicable to JUnit test cases
	 */
	@Test
	public void testAddSourceCode() 
	{
		// 1. Add new .java files to repository
		//changeManager.doAddChangeDetection();
	}
	
	/** Test change detection for delete changes - when items are deleted from the repository
	 * The same tests are applicable to JUnit test cases
	 */
	@Test
	public void testDeleteSourceCode() 
	{
		// 1. Delete .java files
		//changeManager.doDeleteChangeDetection();
	}
	
	/** Test change detection for delete changes - when items are deleted from the repository
	 * 
	 */
	@Test
	public void testDeleteUML() 
	{
		// Delete UML diagram
	}
	
	/** Test change detection for add changes = when items are added to the repository
	 * The same tests are applicable to JUnit test cases
	 */
	@Test
	public void testAddUML() 
	{
		//changeManager.doAddChangeDetection();
	}
	
	/** Test change detection for edit changes = when items in the repository are edited
	 * This tests the change identification algorithm
	 */
	@Test
	public void testEditChangeIdentificationUML() 
	{
		// This is a subset of source code edit change identification tests
	}
	
	/** Test change detection for edit changes = when items in the repository are edited
	 * This tests the change identification algorithm
	 */
	@Test
	public void testEditChangeIdentificationRequirements() 
	{
		// 1. Edit requirement title
		// 2. Edit requirement description
		// 3. Edit requirement priority
		// 4. Edit requirement type
	}
	
	/** Test change detection for delete changes - when items are deleted from the repository
	 * 
	 */
	@Test
	public void testDeleteRequirement() 
	{
		// Delete a requirement specification
	}
	
	/** Test change detection for add changes = when items are added to the repository
	 * The same tests are applicable to JUnit test cases
	 */
	@Test
	public void testAddRequirement() 
	{
		// Add new requirement specification
	}
}
