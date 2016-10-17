package framework.integrated.Tests;

import org.junit.Ignore;
import org.junit.Test;
import framework.DAL.Transformation.Extractor;
import framework.DAL.Transformation.JavaExtractor;
import framework.DAL.Transformation.Transformer;
import framework.DAL.graphmlInput.GraphMLImporter;
import framework.general.PropertyReader;
import framework.general.SequentialExecutionManager;

/** Test the integration of extraction, transformation...
 * 
 *
 */
public class SetupTest 
{
	/** Properties file reader
	 * 
	 */
	private PropertyReader p = new PropertyReader();
	
	/**
	 * 
	 */
	private String DB_PATH = p.getProperties()[1];
	
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
	private Extractor extractor = new JavaExtractor(mg);
	
	/**
	 * 
	 */
	Transformer tr = new Transformer(mg);
	
	/**
	 * 
	 */
	GraphMLImporter importer = new GraphMLImporter(DB_PATH, mg);
	

	/** Test extraction and transformation
	 * 
	 */
	@Test
	public void testExtractionAndTransformation() 
	{
		extractor.Execute();
		tr.Execute();
		for(int i = 0; i < tr.getOutputPaths().size(); i++)
		{
			System.out.println(tr.getOutputPaths().get(i));
		}
	}
	
	/** Test extraction and transformation using the SequentialExecutionManager
	 * 
	 */
	@Ignore
	@Test
	public void testExtractionAndTransformationSequentialProcess() 
	{
		// The order of registering is important when caling the SequentialExecutionManager
		mg.doProcess();
	}
	
	/** Test extraction and transformation and data import to graph database
	 * 
	 */
	@Ignore
	@Test
	public void testExtractionAndTransformationAndImport() 
	{
		//extractor.Execute();
		tr.Execute();
		importer.setInputFiles(tr.getOutputPaths());
		importer.Execute();
	}
}
