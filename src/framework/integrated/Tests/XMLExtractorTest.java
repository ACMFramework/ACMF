package framework.integrated.Tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.DAL.Transformation.Extractor;
import framework.DAL.Transformation.JavaExtractor;
import framework.general.SequentialExecutionManager;

/**
 * Test the functionality of XML extraction from .java files
 * Input: User configures the folder where .java files are located
 * Output: .java.xml files
 */
public class XMLExtractorTest 
{
	/**
	 * 
	 */
	private Extractor extractor;
	
	/**
	 * 
	 */
	private SequentialExecutionManager mg = new SequentialExecutionManager();
	
	@BeforeClass
	public static void setup() 
	{

	}
	
	@AfterClass
	public static void tearDown() 
	{
		
	}
	
	@Test
	public void testJavaExtraction()  
	{
		extractor = new JavaExtractor(mg);
		extractor.Execute();
		
		for(int i = 0; i < extractor.getOutputs().size(); i++) 
		{
		System.out.println(extractor.getOutputs().get(i));
		}
		System.out.println(extractor.getOutputs().size());
	}
	
	
	public void testJavaExtractionSpecificFiles() 
	{
		List<String> files = new ArrayList<String>();
		extractor = new JavaExtractor(mg);
		files.add("D:\\CMPerformanceTest\\Service.java");
		//files.add("D:\\LocalRepo\\AbstractKCVSTest2.java");
		extractor.ExtractSpecificFiles(files);
	}
}
