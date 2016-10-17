package framework.DAL.GraphDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.xml.sax.SAXException;
import framework.artefactRepresentation.Element;
import framework.artefactRepresentation.Relation;

/** Basic graph database CRUD functionality: create nodes and relationships, query nodes & relationships (retrieve, update, delete)
 *
 */
public interface IGraphStoreDao 
{
	/**
	 * Add new nodes to the database from a list of element objects
	 */
	public void createNodesFromList(ArrayList<Element> listOfElements);
	
	/**
	 * Add a single node to the database
	 */
	public void createNode(Element newNode);
	
	/**
	 * Add a node to the database and connect it to other nodes through edges
	 */
	public void createNodeWithRelationship(Relation relation);
	
	/**
	 * Add edges to existing nodes from a list of relation objects
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	public void createRelationships(RelationshipType relType, String relationsFilePath) throws ParserConfigurationException, SAXException, IOException;
	
	/**
	 * Generic method to return a node from the database based on a parameter passed in
	 */
	public Object getNode(Object param);
	
	/**
	 * Get all property names from the database
	 */
	public List<String> getPropertyNames();
	
	/**
	 * Return a node from the db using a query language and a specific parameter
	 */
	public Object getNodeBasedOnProperty(String query, String propertyName, String propertyValue);
	
	/**
	 * Return a node from the db using a query language and two specific parameters
	 */
	public Object getNodeBasedOnProperties(String query, String propertyName, String propertyValue, 
											String propertyName2, String propertyValue2);
	
	/**
	 * Return the number of nodes
	 */
	public int getNumberOfNodes();
	
	/**
	 * Return all nodes
	 */
	public List<Object> getAllNodes();
	
	/**
	 * Return all relationships
	 */
	public List<Object> getAllRelationships();
	
	/**
	 * Execute query using query language and without any parameters. It returns true upon successful query execution
	 */
	public boolean executeQuery(String query);
	
	/**
	 * Execute query using query language and a parameter. It returns true upon successful query execution
	 */
	public boolean executeQuery(String query, String propertyName, String propertyValue);
	
	/**
	 * Execute query using query language and two parameters. It returns true upon successful query execution
	 */
	public boolean executeQuery(String query, String propertyName, String propertyValue, 
								String propertyName2, String propertyValue2);
	
	/**
	 * Execute query using query language and four parameters. It returns true upon successful query execution
	 */
	public boolean executeQuery(String query, String propertyName, String propertyValue, 
								String propertyName2, String propertyValue2, String propertyName3, String propertyValue3,
								String propertyName4, String propertyValue4);
	
	/**
	 * Check if database is empty
	 */
	public void checkIfDbEmpty();
	
	/**
	 * Shut down graph database / connection
	 */
	public void closeDb();

	/**
	 * Execute query using query language and a parameter. It returns the value queried
	 */
	public String executeQueryReturnStringValue(String query, String propertyName, String propertyValue);
	
	/**
	 * 
	 * @param query
	 * @return
	 */
	Node executeQueryReturnNode(String query);

	/** Execute query with parameter and return node
	 * 
	 * @param query
	 * @param propertyName
	 * @param propertyValue
	 * @return
	 */
	Node executeQueryWithParamsReturnNode(String query, String propertyName,
			String propertyValue);

	/**
	 * 
	 * @return
	 */
	public GraphDatabaseService getDb();
	
	/**
	 * 
	 * @param cypherQuery
	 * @param uniqueIdValue
	 * @param incomingParams
	 */
	public void executeQueryWithDynamicParams(String cypherQuery, String uniqueIdValue, 
			Map<String, String> incomingParams);
	
	/**
	 * 
	 * @param cypherQuery
	 * @param incomingParams
	 */
	public void executeQuerySimpleDynamicParams(String cypherQuery,
			Map<String, String> incomingParams);
	
	/**

	 */
	public Node findParentNode(Node node);
	
	/**

	 */
	public List<Node> traverseInterRels(Node node);

	/**
	 * 
	 * @param id
	 * @return
	 */
	Node getNode(long id);

	/** Execute query with 2 parameters
	 * 
	 * @param query
	 * @param propertyName
	 * @param propertyValue
	 * @param propertyValue2
	 * @param propertyName2
	 * @param propertyValue3
	 * @param propertyValue4
	 * @return
	 */
	boolean executeQuery(String query, String propertyName,
			String propertyValue, String propertyValue2, String propertyName2,
			String propertyValue3, String propertyValue4);

	/**
	 * 
	 * @param query
	 * @param propertyName
	 * @param propertyValue
	 * @param propertyValue2
	 * @return
	 */
	boolean executeQuery(String query, String propertyName,
			String propertyValue, String propertyValue2);
}
