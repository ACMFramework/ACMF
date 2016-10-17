package framework.DAL.graphmlInput;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.impls.neo4j2.Neo4j2Graph;
import com.tinkerpop.blueprints.util.io.graphml.GraphMLReader;
import framework.general.IExecutable;
import framework.general.IExecutionManager;

/** Import a single or multiple graphml file(s) to Neo4j
*
*/
public class GraphMLImporter implements IExecutable
{
	/** Blueprints Graph and GraphML objects and a list storing all the input files
	 *
	 */
	private Graph graph;
	private GraphMLReader reader;
	private String[] listOfInputFiles = null;

	/**
	 * 
	 */
	private List<String> inputFiles;
	
	/**
	 * 
	 * @return
	 */
	public List<String> getInputFiles()
	{
		return this.inputFiles;
	}
	
	/**
	 * 
	 * @param files
	 */
	public void setInputFiles(List<String> files) 
	{
		this.inputFiles = files;
	}
	
	/**
	 * @param dbPath
	 * The file path for the Neo4j database instance
	 */
	public GraphMLImporter(String dbPath) 
	{
		graph = new Neo4j2Graph(dbPath);
		reader = new GraphMLReader(graph);
	}
	
	/** Constructor to be used if GraphMLImporter is used as part of a sequential process
	 * @param dbPath
	 * The file path for the Neo4j database instance
	 */
	public GraphMLImporter(String dbPath, IExecutionManager manager) 
	{
		manager.register(this);
		graph = new Neo4j2Graph(dbPath);
		reader = new GraphMLReader(graph);
	}

	/**
	 *
	 */
	public String[] getList() 
	{
		return listOfInputFiles;
	}

	/**
	 *
	 */
	public void setList(String[] list) 
	{
		this.listOfInputFiles = list;
	}

	/**
	 *
	 */
	public Graph getGraph() 
	{
		return graph;
	}

	/**
	 * @param filePath
	 * The file path of the file to be imported
	 */
	public void importSingleFile(String filePath)
	{
		try 
		{
			InputStream is = new BufferedInputStream(new FileInputStream(filePath));
			reader.inputGraph(is);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/**
	 * @param listOfInputFilePaths
	 * An array containing the file paths of the files to be imported
	 */
	public void importInputFiles(String[] listOfInputFilePaths)
	{
		setList(listOfInputFilePaths);
		for(int i = 0; i < getList().length; i++) 
		{
			try 
			{
				InputStream is = new BufferedInputStream(new FileInputStream(getList()[i]));
				reader.inputGraph(is);
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}
	}

	public void closeDb() 
	{
		if(getGraph() != null) 
		{
			try 
			{
				System.out.println("Shutting down database...");
				getGraph().shutdown();
				System.out.println("Database is shut down");
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("Database did not stop cleanly. " + e.getMessage());
			}
		}
	}
	
	/** Execute import
	 * 
	 */
	@Override
	public void Execute() 
	{
		getInputFiles();
		for(int i = 0; i < getInputFiles().size(); i++) 
		{
			importSingleFile(getInputFiles().get(i));
		}
		closeDb();
	}
}
