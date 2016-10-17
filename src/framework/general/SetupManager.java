package framework.general;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import framework.DAL.GraphDb.IGraphStoreDao;
import framework.DAL.Transformation.Extractor;
import framework.DAL.Transformation.JavaExtractor;
import framework.DAL.Transformation.Transformer;
import framework.DAL.graphmlInput.GraphMLImporter;
import framework.traceability.TraceLinkCreator;

/** Manage the setup process
 * 
 * input: framework prerequisites 
 * output: graph database populated with artefacts and their links
 *
 */
public class SetupManager 
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
	private String xmlTraceabilityInput = "C:\\Users\\Ildi\\Desktop\\r.xml";
	/**
	 * 
	 */
	private IGraphStoreDao graphStore;
	
	/**
	 * 
	 */
	private GraphMLImporter importer;
	
	/**
	 * 
	 */
	private String DB_PATH = prop.getProperties()[1];

	/** Manage the setup process
	 * @throws IOException 
	 * 
	 */
	public void manageSetup() throws IOException 
	{
		// Do configuration - specify framework root folder, graph database path, remote and local repository paths
		String rootFolderPath = "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\";
		String dbPath = "C:\\Users\\Ildi\\Documents\\Neo4j\\default.graphdb";
		String remoteRepo = "http://Ildi-PC:8000";
		String localRepo = "D:\\LocalRepo";
		ConfigurationHandler.doConfiguration(rootFolderPath, dbPath, remoteRepo, localRepo);
		// Create framework root folder and subdirectories
		//FileUtilities.createFrameworkDirAndSubdirs(rootFolderPath);
		// Initiate Artefact Data setup
		extractor.Execute();
		// Supply list of files extracted to the transformer and create graphml files
		List<String> extractionOutput = new ArrayList<String>();
		extractionOutput = extractor.getOutputs();
		tr.Execute();
		// Get list of transformed graphml files
		List<String> transformedFiles = tr.getOutputPaths();
		// Import them to the database
		/*importer = new GraphMLImporter(DB_PATH);
		importer.setInputFiles(transformedFiles);
		importer.Execute();*/
	}
	
	/** Manage the trace link creation process
	 * @throws IOException 
	 * 
	 */
	public void manageTraceLinkCreation() throws IOException 
	{
		creator.setXMLInput(xmlTraceabilityInput);
		creator.Execute();
	}
	
	public static void main(String[] args) throws IOException 
	{
		SetupManager manager = new SetupManager();
		manager.manageSetup();
		//manager.manageTraceLinkCreation();
	}
}
