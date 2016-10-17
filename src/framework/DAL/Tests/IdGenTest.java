/**
 * 
 */
package framework.DAL.Tests;

import java.util.ArrayList;
import framework.DAL.GraphML.GraphMLProcessor;
import framework.DAL.Transformation.IDGenerator;
import framework.general.PropertyReader;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Test the functionality of the IDGenerator class
 */
public class IdGenTest 
{
	static String testPath = "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\Service.graphml";
	static IDGenerator gen = new IDGenerator(testPath);
	static PropertyReader prop = new PropertyReader();
	static String folder = prop.getProperties()[0];
	
	/**
	 *
	 */
	@Test
	public void testPathNotNull() 
	{
		assertFalse(gen.getGraphmlOperations().getPath().equals(null));
	}
	
	/**
	 *
	 */
	@Test
	public void testGenNotNull() 
	{
		assertFalse(gen.equals(null));
	}

	/**
	 *
	 */
	@Test
	public void testNumberOfNodes() 
	{
		System.out.println("Nodes: " + gen.getNoOfNodes());
	}
	
	/**
	 *
	 */
	@Test
	public void testNumberOfEdges() 
	{
		System.out.println("Edges: " + gen.getNoOfEdges());
	}

	/** Generate unique ids
	 *
	 */
	@Test
	public void testUniqueIdGen() 
	{
		ArrayList<String> ids = gen.generateListOfUniqueIds(GraphMLProcessor.SC_ID_PREFIX);
		for(String id : ids) 
		{
			System.out.println(id);
		}
		assertTrue(ids.size() == gen.getNoOfNodes());
	}
	
	/** Generate edge ids
	 *
	 */
	@Test
	public void testEdgeIdGen() 
	{
		ArrayList<String> ids = gen.generateListOfEdgeIds(GraphMLProcessor.EDGE_ID_PREFIX);
		for(String id : ids) 
		{
			System.out.println(id);
		}
		assertTrue(ids.size() == gen.getNoOfEdges());
	}
	
	/** Test client id and intra link generation (single graphml file)
	 *
	 */
	@Test
	public void testIdGenClient() 
	{
		gen.generateUniqueIdsForGraphmlFile(GraphMLProcessor.SC_ID_PREFIX);
	}
	
	/** Test client id and intra link generation (multiple graphml files)
	 *
	 */
	@Test
	public void testIdGenMultipleFilesClient() 
	{
		String folder2 = "C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Change detection\\constraints\\Example graphs";
		gen.generateUniqueIdsForMultipleGraphmlFiles(GraphMLProcessor.SC_ID_PREFIX, folder2);
	}
}
