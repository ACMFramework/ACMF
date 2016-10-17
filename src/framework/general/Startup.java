package framework.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import framework.ChangePropagation.ChangePropagator;
import framework.DAL.Transformation.Extractor;
import framework.DAL.Transformation.JavaExtractor;
import framework.DAL.Transformation.Transformer;
import framework.DAL.graphmlInput.GraphMLImporter;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ChangeDetectionManager;
import framework.consistencyChecking.ConsistencyChecker;
import framework.impactAnalysis.ChangeImpactAnalyser;
import framework.impactAnalysis.IAResult;
import framework.traceability.TraceLinkCreator;

/** Main class to start framework and to perform framework functionality (consistency management)
 *
 */
public class Startup 
{
	/**
	 * 
	 */
	PropertyReader prop = new PropertyReader();

	/**
	 * 
	 */
	private SequentialExecutionManager manager = new SequentialExecutionManager();
	
	/**
	 * 
	 */
	private Extractor extractor = new JavaExtractor(manager);
	
	/**
	 * 
	 */
	private Transformer tr= new Transformer(manager);
	
	/**
	 * 
	 */
	private ConfigurationHandler config = new ConfigurationHandler();
	
	/**
	 * 
	 */
	private TraceLinkCreator creator = new TraceLinkCreator(manager);
	
	/**
	 * 
	 */
	private GraphMLImporter importer;
	
	/**
	 * 
	 */
	private String DB_PATH = prop.getProperties()[1];

	/**
	 * 
	 */
	private ChangeImpactAnalyser impact = new ChangeImpactAnalyser(manager);
	
	/**
	 * 
	 */
	private ConsistencyChecker cons = new ConsistencyChecker(manager);
	
	/**
	 * 
	 */
	private ChangePropagator cp = new ChangePropagator(manager);
	
	/**
	 * 
	 */
	private List<IAResult> impactOutput;
	
	
	/** Manage the setup process
	 * @throws IOException 
	 * 
	 */
	public void manageSetup() throws IOException 
	{
		// Do configuration - specify framework root folder, graph database path, remote and local repository paths
		// Examples:
		String rootFolderPath = "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\";
		String dbPath = "C:\\Users\\Ildi\\Documents\\Neo4j\\default.graphdb";
		String remoteRepo = "http://Ildi-PC:8000";
		String localRepo = "D:\\LocalRepo";
		//ConfigurationHandler.doConfiguration(rootFolderPath, dbPath, remoteRepo, localRepo);
		
		// Initiate Artefact Data setup
		extractor.Execute();
		// Supply list of files extracted to the transformer and create graphml files
		List<String> extractionOutput = new ArrayList<String>();
		extractionOutput = extractor.getOutputs();
		tr.Execute();
		// Get list of transformed graphml files
		List<String> transformedFiles = tr.getOutputPaths();
		// Import them to the database
		importer = new GraphMLImporter(DB_PATH);
		importer.setInputFiles(transformedFiles);
		importer.Execute();
	}
	
	/** Manage the trace link creation process
	 * @throws IOException 
	 * 
	 */
	public void manageTraceLinkCreation(String xmlTraceabilityInput) throws IOException 
	{
		creator.setXMLInput(xmlTraceabilityInput);
		creator.Execute();
	}
	
	/** Perform consistency management
	 * 
	 */
	public void manageConsistency() 
	{
		ChangeDetectionManager m = new ChangeDetectionManager();
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
	}
	
	
	//*************************************************************************************************
	// FRAMEWORK STARTUP AND CONSISTENCY MANAGEMENT
	//*************************************************************************************************
	/** Startup framework and consistency management
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException 
	{	
		Startup framework = new Startup();
		//framework.manageSetup();
		framework.manageTraceLinkCreation("C:\\Users\\Ildi\\Desktop\\r.xml");
		//framework.manageConsistency();
	}
}
