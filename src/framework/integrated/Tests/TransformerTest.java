package framework.integrated.Tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import framework.DAL.Transformation.Transformer;
import framework.changeDetection.ArtefactType;
import framework.general.PropertyReader;
import framework.general.SequentialExecutionManager;

/**
 * 
 * Test the functionality of the Transformation component
 * Input: User supplies folder with XML files - specified at configuration
 * Output: GraphML files
 *
 */
public class TransformerTest 
{
	/** Properties file reader
	 * 
	 */
	private PropertyReader p = new PropertyReader();
	
	/**
	 * 
	 */
	SequentialExecutionManager mg = new SequentialExecutionManager();
	
	/**
	 * 
	 */
	PropertyReader prop = new PropertyReader();
	
	/**
	 * 
	 */
	Transformer tr = new Transformer(mg);
	
	/**
	 * 
	 */
	//@Test
	public void testTransformationSetup() 
	{
		//mg.register(tr);
		mg.doProcess();

		for(int i = 0; i < tr.getOutputPaths().size(); i++) 
		{
			System.out.println(tr.getOutputPaths().get(i) + tr.getOutputPaths().size());
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testTransformationSelectedFiles() 
	{
		List<String> list = new ArrayList<String>();
		
		list.add("D:\\CMPerformanceTest\\Service.java.xml");
		//list.add("C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\JBBPBitInputStream.java.xml");
		
		for(int i = 0; i < list.size(); i++) 
		{
			tr.transformSpecificFiles(list, ArtefactType.JAVA_SOURCE_CODE);
		}
	}
	
	
	public void testTransformationSelectedFiles2() 
	{
		List<String> list = new ArrayList<String>();
		list.add("C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\Requirement\\RequirementsSpecification.odt");
		
		for(int i = 0; i < list.size(); i++) 
		{
			tr.transformSpecificFiles(list, ArtefactType.REQUIREMENT_SPECIFICATION);
		}
	}
}
