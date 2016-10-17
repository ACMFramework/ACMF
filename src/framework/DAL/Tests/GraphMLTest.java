package framework.DAL.Tests;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Node;
import framework.DAL.GraphML.GraphMLProcessor;

/** @author Ildiko Pete
 * Test the functionality of the GraphMLProcessor class
 */
public class GraphMLTest 
{
	static GraphMLProcessor processor;
	static String testPath = "C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml";

	/**
	 *
	 */
	@BeforeClass
	public static void setup() 
	{
		processor = new GraphMLProcessor(testPath);
	}
	
	/**
	 *
	 */
	@AfterClass
	public static void tearDown() 
	{
		
	}
	
	/**
	 *
	 */

	public void testPathNotNull() 
	{
		assertFalse(processor.getPath().equals(null));
	}
	
	/**
	 *
	 */
	
	public void testEdgeNum() 
	{
		assertTrue(processor.getNumberOfEdges() > 0);
		System.out.println("Edges: " + processor.getNumberOfEdges());
	}
	
	/**
	 *
	 */
	
	public void testNodeNum() 
	{
		assertTrue(processor.getNumberOfNodes() > 0);
		System.out.println("Nodes: " + processor.getNumberOfNodes());
	}

	
	/**
	 *
	 */
	
	public void testGetSpecificNode() 
	{
		Node node = processor.getSpecificNode(GraphMLProcessor.ID_DATA_KEY_VALUE, "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml");
		assertTrue(processor.getDataKeyValue(node, GraphMLProcessor.TYPE_DATA_KEY_VALUE).equals("class"));
	}
	
	/**
	 *
	 */
	
	public void testGetNameBasedOnNameAndType() 
	{
		processor = new GraphMLProcessor("C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\TransactionId.graphml");
		Node node = processor.setUniqueIdBasedOnNameAndType("TransactionId", "ANOTHERCHANGE", "", "THISISTHESETUNIQUEID");
		System.out.println(node.getTextContent());
		//assertTrue(processor.getDataKeyValue(node, GraphMLProcessor.TYPE_DATA_KEY_VALUE).equals("class"));
	}

	/**
	 *
	 */
	
	public void testGetMaxUniqueId() 
	{
		System.out.println("Max unique id: " + processor.getMaxUniqueId());
	}
	
	/**
	 *
	 */
	
	public void testReturnNodeNodeList() 
	{
		processor.returnNodeNodeList();
	}
	
	/**
	 *
	 */
	
	public void testReturnEdgeNodeList() 
	{
		processor.returnEdgeNodeList();
		System.out.println("Max unique id: " + processor.getMaxUniqueId());
	}
	
	/**
	 *
	 */

	public void testReturnDataNodeList() 
	{
		processor.returnDataNodeList();
	}
	
	/**
	 *
	 */

	public void testGetNodeBasedOnUniqueId() 
	{
		Node node = processor.getNodeBasedOnUniqueId(GraphMLProcessor.ID_DATA_KEY_VALUE, "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml");
		assertTrue(node != null);
		System.out.println(node.getTextContent());
	}
	
	/**
	 *
	 */
	
	public void testGetDataKeyValue() 
	{
		Node node = processor.getNodeBasedOnUniqueId(GraphMLProcessor.ID_DATA_KEY_VALUE, "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml");
		if(node != null) 
		{
			assertTrue(processor.getDataKeyValue(node, GraphMLProcessor.NAME_DATA_KEY_VALUE).equals("JBBPBitInputStream"));
		}
	}
	
	/**
	 *
	 */
	
	public void testGetUniqueIdOfNode() 
	{
		Node node = processor.getNodeBasedOnUniqueId(GraphMLProcessor.ID_DATA_KEY_VALUE, "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml");
		if(node != null) 
		{
			assertTrue(processor.getUniqueIdOfNode(node).equals("sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\SourceCode\\io\\JBBPBitInputStream.graphml"));
		}
	}
	
	/**
	 * 
	 */
	@Test
	public void testGetUniqueIds() 
	{
		processor = new GraphMLProcessor("C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\TitanId.graphml");
		/*ArrayList<String> ids = new ArrayList<String>();
		ids.add("1");
		ids.add("2");
		ids.add("3");
		ids.add("4");
		ids.add("5");
		processor.updateUniqueIdOfAllNodes(ids);*/
		
		
		
		
		ArrayList<String> ids2 = processor.getUniqueIds();
		System.out.println(ids2.get(0));
	}
}
