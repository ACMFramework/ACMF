package framework.DAL.Tests;

import static org.junit.Assert.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import framework.DAL.GraphDb.CypherQuery;
import framework.DAL.GraphDb.DaoFactory;
import framework.DAL.GraphDb.IGraphStoreDao;
import framework.DAL.GraphDb.Neo4jEmbeddedDao;
import framework.DAL.GraphDb.RelTypes;

/** Test database query functionality.
 */
public class GraphDAOTest 
{
	/**
	 *
	 */
	private static IGraphStoreDao embedded;
	
	/** Relations.xml file path
	 *
	 */
	private static String relationsFilePath = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\graphmltest\\RelationsFile.xml";
	
	/** Retrieve Cypher queries from concrete Neo4j IGraphStoreDao class or define them here
	 *
	 */
	private static final String CREATE_NODE = "CREATE (n)";
	private static final String GET_NODE_COUNT = "MATCH (n) RETURN COUNT(n)";
	
	/**
	 *
	 */
	@BeforeClass
	public static void setup() 
	{
		embedded = DaoFactory.getDataStore(DaoFactory.NEO4J);
		System.out.println(embedded.getClass());
	}
	
	/**
	 *
	 */
	@AfterClass
	public static void tearDown() 
	{
		embedded.closeDb();
	}
	
	/**
	 *
	 */
	
	public void testAddingNewNodes() 
	{
		embedded.executeQuery(GET_NODE_COUNT);
		assertTrue(embedded.executeQuery(CREATE_NODE));
		embedded.executeQuery(GET_NODE_COUNT);
	}
	
	/**
	 *
	 */
	
	public void testDeletingNodes() 
	{
		
	}
	
	/**
	 *
	 */
	
	public void testUpdatingNodes() 
	{
		
	}
	
	/**
	 *
	 */
	@Test
	public void testDbUpdateWithOneParam() 
	{
		assertTrue(embedded.executeQuery(CypherQuery.UPDATE_VISIBILITY, Neo4jEmbeddedDao.IDPROPERTY, "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\MazeSolver\\Evaluation Files\\GeneratedXML\\Revision19\\OldVersion\\MazeView.graphml", 
				Neo4jEmbeddedDao.VISIBILITYPROPERTY, "protected"));
	}
	
	/**
	 *
	 */
	
	public void testGetAllNodes()
	{
		List<Object> nodes = new ArrayList<Object>();
		nodes = embedded.getAllNodes();
		System.out.println("Number of nodes in the database: " + nodes.size());
		assertTrue(!nodes.isEmpty());
	}
	
	/** Test adding relationships between existing nodes
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 *
	 */
	
	public void testCreateRelsFromRelationsFile() throws ParserConfigurationException, SAXException, IOException 
	{
		embedded.createRelationships(RelTypes.INTER_REL, relationsFilePath);
	}
}
