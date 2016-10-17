package framework.DAL.GraphDb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.xml.parsers.ParserConfigurationException;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import org.neo4j.tooling.GlobalGraphOperations;
import org.xml.sax.SAXException;
import framework.DAL.Transformation.RelationsXmlParser;
import framework.artefactRepresentation.Element;
import framework.artefactRepresentation.Field;
import framework.artefactRepresentation.Method;
import framework.artefactRepresentation.ObjectOrientedElement;
import framework.artefactRepresentation.Relation;
import framework.artefactRepresentation.SpecificationElement;
import framework.general.PropertyReader;
import framework.impactAnalysis.CustomEvaluator;
import framework.impactAnalysis.CustomParentChildEvaluator;

/**
 *
 */
public class Neo4jEmbeddedDao implements IGraphStoreDao
{
	/**
	 * 
	 */
	private Node aNode;

	/**
	 *  A utility list which contains nodes returned from the graph database
	 */
	protected List<Node> listOfVertices = new ArrayList<Node>();

	/**
	 * 
	 */
	private GraphDatabaseService db;

	/**
	 * 
	 * @return
	 */
	public GraphDatabaseService getDb() 
	{
		return db;
	}

	/** Read db path from config file
	 *
	 */
	private static PropertyReader propReader = new PropertyReader();
	private static final String DB_PATH = propReader.getProperties()[1];

	/** The Neo4jEmbeddedDao instance uses a single GraphDatabaseService
	 * 
	 */
	public Neo4jEmbeddedDao() 
	{
		System.out.println(DB_PATH);
		GraphDatabaseService dbService = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		this.db = dbService;
	}

	/**
	 * 
	 */
	public void shutDown()
	{
		System.out.println();
		System.out.println("Shutting down database ...");
		db.shutdown();
	}

	/**
	 * 
	 */
	public void registerShutdownHook(final GraphDatabaseService graphDb)
	{
		// Registers a shutdown hook for the Neo4j instance
		Runtime.getRuntime().addShutdownHook(new Thread()
		{
			@Override
			public void run()
			{
				graphDb.shutdown();
			}
		} );
	}

	//*******************************************
	// Constants
	//*******************************************
	/**
	 * 
	 */
	public static final String IDPROPERTY = "uniqueId";

	/**
	 * UniqueId node property
	 */
	public static final String VARIABLE_TYPE_PROPERTY = "variableType";

	/**
	 * VariableType node property
	 */
	public static final String RETURN_TYPE_PROPERTY = "returnType";

	/**
	 * ReturnType node property
	 */
	public static final String TYPEPROPERTY = "type";

	/**
	 * Name node property
	 */
	public static final String NAMEPROPERTY = "name";

	/**
	 * Parameters node property
	 */
	public static final String PARAMETERSPROPERTY = "parameters";

	/**
	 * Content node property
	 */
	public static final String CONTENTPROPERTY = "content";

	/**
	 * Visibility node property
	 */
	public static final String VISIBILITYPROPERTY = "visibility";

	/**
	 * Implements/Extends node property
	 */
	public static final String IMPEXPROPERTY = "implementsOrExtends";

	/**
	 * Annotations node property
	 */
	public static final String ANNOTATIONPROPERTY = "annotation";

	/**
	 * Title node property
	 */
	public static final String TITLEPROPERTY = "title";

	/**
	 * Annotations node property
	 */
	public static final String PRIORITYPROPERTY = "priority";

	/**
	 * Requirement type node property
	 */
	public static final String REQTYPEPROPERTY = "reqType";
	
	/** The property name of a graph edge
	 * 
	 */
	public static final String DB_EDGE_TRAVERSAL_PROPERTY = "visibility";
	
	/** The property value of a graph edge
	 * 
	 */
	public static final String DB_EDGE_TRAVERSAL_PROPERTY_VALUE = "Uses";
	
	/** The property value of a graph edge
	 * 
	 */
	public static final String DB_EDGE_TRAVERSAL_PROPERTY_VALUE_PC = "Parent_Child";
	
	//****************************************************************
	// Overriden methods
	//****************************************************************

	/** Adds news nodes to the graph db from a list of element objects
	 * @param listOfElements - the list of elements to be saved as nodes
	 */
	@Override
	public void createNodesFromList(ArrayList<Element> listOfElements) 
	{
		try (Transaction tx = db.beginTx())
		{
			for (int i =0; i < listOfElements.size(); i++) 
			{
				aNode = db.createNode();
				Element el = listOfElements.get(i);

				if (el instanceof Method)
				{
					Method method = (Method) el;
					aNode.setProperty("idOfElement", method.getId());
					aNode.setProperty("nameOfElement", method.getName());
				}

				else if(el instanceof Field)
				{
					Field field = (Field) el;
					aNode.setProperty("idOfElement", field.getId());
					aNode.setProperty("nameOfElement", field.getName());
				}

				else if (el instanceof SpecificationElement)
				{
					SpecificationElement spec = (SpecificationElement) el;
					aNode.setProperty("idOfElement", spec.getId());
					aNode.setProperty("nameOfElement", spec.getName());
				}

				else if(el instanceof ObjectOrientedElement)
				{
					ObjectOrientedElement oo = (ObjectOrientedElement) el;
					aNode.setProperty("idOfElement", oo.getId());
					aNode.setProperty("nameOfElement", oo.getName());
				}	
			}
			tx.success();
		}
	}

	/**
	 * 
	 */
	@Override
	public void createNode(Element newNode) 
	{
		try (Transaction tx = db.beginTx())
		{
			aNode = db.createNode();

			if (newNode instanceof Method)
			{
				Method method = (Method) newNode;
				aNode.setProperty("idOfElement", method.getId());
				aNode.setProperty("nameOfElement", method.getName());
			}

			else if(newNode instanceof Field)
			{
				Field field = (Field) newNode;
				aNode.setProperty("idOfElement", field.getId());
				aNode.setProperty("nameOfElement", field.getName());
			}

			else if (newNode instanceof SpecificationElement)
			{
				SpecificationElement spec = (SpecificationElement) newNode;
				aNode.setProperty("idOfElement", spec.getId());
				aNode.setProperty("nameOfElement", spec.getName());
			}

			else if(newNode instanceof ObjectOrientedElement)
			{
				ObjectOrientedElement oo = (ObjectOrientedElement) newNode;
				aNode.setProperty("idOfElement", oo.getId());
				aNode.setProperty("nameOfElement", oo.getName());
			}	
			tx.success();
		}
	}

	@Override
	public void createNodeWithRelationship(Relation relation) {
	}

	/**
	 * @param relType
	 * Relationship type - mandatory element of all relationships, it is also used to navigate the graph  
	 * @param relationsFilePath
	 * The path of the Relations.xml file
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	@Override
	public void createRelationships(RelationshipType relType, String relationsFilePath) throws ParserConfigurationException, SAXException, IOException 
	{
		// Get list of Relation objects from specified Relations.xml file
		RelationsXmlParser relParser = new RelationsXmlParser();
		List<Relation> listOfRels = relParser.getRelsListFromXML(relationsFilePath);
		
		// Get all nodes from db
		List<Node> nodes = getNodes();
		
		try (Transaction tx = db.beginTx())
		{
			for(int i = 0; i < listOfRels.size(); i++) 
			{
				Node sourceVertex = getMatchingNode(listOfRels.get(i).getSourceId(), nodes);
				Node targetVertex = getMatchingNode(listOfRels.get(i).getTargetId(), nodes);			

				sourceVertex.createRelationshipTo(targetVertex, relType);
				tx.success();
			}
			tx.success();
		}
	}

	/** Return a specific node based on its node id
	 * @param id
	 * Id property
	 */
	@Override
	public Node getNode(long id) 
	{
		return db.getNodeById((long) id);
	}

	@Override
	public Node getNode(Object id) 
	{
		//long id = 218L;
		return db.getNodeById((long) id);
	}
	
	
	@Override
	public List<String> getPropertyNames() 
	{
		// TODO Auto-generated method stub
		return null;
	}

	/** Return a node based on a single property
	 * @param query - Cypher query 
	 * @param propertyName - the name of the property to be queried
	 * @param propertyValue - the value the property will be updated to
	 * @return Node
	 */
	@Override
	public Node getNodeBasedOnProperty(String query, String propertyName, String propertyValue) 
	{
		Node n = null;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			Iterator<Node> r = result.columnAs("n");
			while(r.hasNext()) 
			{
				n = r.next();
			}
			transaction.success();
		}
		catch(NullPointerException e) 
		{
			System.out.println(e.getMessage() + "node was not found in the database, check if property name is correct");
		}
		finally 
		{
			String res = result.dumpToString();
		}

		return n;
	}

	/** Return a node based on some property values
	 * @param query - Cypher query 
	 * @param propertyName - the name of the property to be queried
	 * @param propertyValue - the value the property will be updated to
	 * @return Node
	 */
	@Override
	public Node getNodeBasedOnProperties(String query, String propertyName, String propertyValue, 
			String propertyName2, String propertyValue2)
	{
		Node n = null;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		params.put(propertyName2, propertyValue2);
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			Iterator<Node> r = result.columnAs("n");
			while(r.hasNext()) 
			{
				n = r.next();
			}
			transaction.success();
		}
		catch(NullPointerException e) 
		{
			System.out.println(e.getMessage() + "node was not found in the database, check if property names are correct");
		}
		finally 
		{
			String res = result.dumpToString();
		}

		return n;
	}

	/**
	 * Return the number of nodes in graph database
	 */
	@Override
	public int getNumberOfNodes() 
	{
		int count = 0;

		try (Transaction tx = db.beginTx())
		{
			GlobalGraphOperations g = GlobalGraphOperations.at(db);
			Iterator<Node> itr = g.getAllNodes().iterator();

			while (itr.hasNext()) 
			{
				itr.next();
				count++;
			}
			tx.success();
		}
		System.out.println("Size of Graph DB: " + count);
		return count;
	}

	/**
	 * 
	 */
	@Override
	public boolean executeQuery(String query) 
	{
		boolean success = false;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query);
			transaction.success();
			success = true;
		}
		finally 
		{
			String res = result.dumpToString();
			System.out.println(res);
		}
		return success;
	}
	
	/**
	 * 
	 */
	@Override
	public Node executeQueryReturnNode(String query) 
	{
		Node n = null;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query);
			Iterator<Node> r = result.columnAs("n");
			while(r.hasNext()) 
			{
				n = r.next();
			}
			transaction.success();
		}
		finally 
		{
			String res = result.dumpToString();
			System.out.println(res);
		}
		return n;
	}
	
	/**
	 * 
	 */
	@Override
	public Node executeQueryWithParamsReturnNode(String query, String propertyName,
			String propertyValue) 
	{
		Node n = null;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			Iterator<Node> r = result.columnAs("n");
			while(r.hasNext()) 
			{
				n = r.next();
			}
			transaction.success();
		}
		finally 
		{
			String res = result.dumpToString();
			System.out.println(res);
		}
		return n;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean executeQuery(String query, String propertyName,
			String propertyValue) 
	{
		boolean success = false;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);

		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			transaction.success();
			success = true;
			String res = result.dumpToString();
			System.out.println(res);
		}
		return success;
	}

	/**
	 * 
	 */
	@Override
	public boolean executeQuery(String query, String propertyName,
			String propertyValue, String propertyName2, String propertyValue2) 
	{	
		boolean success = false;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		params.put(propertyName2, propertyValue2);

		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			transaction.success();
			success = true;
			String res = result.dumpToString();
			System.out.println(res);
		}
		return success;
	}
	
	@Override
	public String executeQueryReturnStringValue(String query,
			String propertyName, String propertyValue) 
	{
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(propertyName, propertyValue);
		String res = null;
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			transaction.success();
			res = result.dumpToString();
			System.out.println(res);
		}
		return res;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean executeQuery(String query, String propertyName,
			String propertyValue, String propertyValue2, String propertyName2, String propertyValue3,
			String propertyValue4) 
	{
		boolean success = false;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		ParameterObject pmName = new ParameterObject();
		pmName.setValue1(propertyValue);
		pmName.setValue2(propertyValue2);
		
		ParameterObject pmType = new ParameterObject();
		pmType.setValue1(propertyValue3);
		pmType.setValue2(propertyValue4);
		
		params.put(propertyName, pmName.getValue1());
		params.put(propertyName2, pmType.getValue1());
		params.put(propertyName, pmName.getValue2());
		params.put(propertyName2, pmType.getValue2());
	
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query, params);
			transaction.success();
			success = true;
			String res = result.dumpToString();
			System.out.println(res);
		}
		return success;
	}
	
	/**
	 * 
	 */
	@Override
	public boolean executeQuery(String query, String propertyName,
			String propertyValue1, String propertyValue2) 
	{
		boolean success = false;
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
	
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(query);
			transaction.success();
			success = true;
			String res = result.dumpToString();
			System.out.println(res);
		}
		
		return success;
	}

	/**
	 * 
	 */
	@Override
	public void closeDb() 
	{
		shutDown();	
	}

	//****************
	// Utility methods
	//****************
	
	/** Method to return a Node object where the id property of the given node is the same as
	 * the id property string value passed in
	 */
	private Node getMatchingNode(String idProperty, List<Node> nodeList) 
	{
		int i = 0;
		while(!nodeList.get(i).getProperty(IDPROPERTY).equals(idProperty)) 
		{
			i ++;
		} 

		return nodeList.get(i);
	}

	/** Method for executing general queries based on 3 query parameters passed in
	 * 
	 */
	private void executeQueryWithThreeParams(String cypherQuery, String name, String type, String visibility, String uniqueId)
	{
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(NAMEPROPERTY, name);
		params.put(TYPEPROPERTY, type);
		params.put(VISIBILITYPROPERTY, visibility);
		params.put(IDPROPERTY, uniqueId);

		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(cypherQuery, params);
			transaction.success();
		}
		finally 
		{
			String res = result.dumpToString();
			System.out.println(res);
		}
	}
	
	/** Method for executing general queries where unique id is required and params are not known in advance
	 * 
	 */
	public void executeQueryWithDynamicParams(String cypherQuery, String uniqueId, 
			Map<String, String> incomingParams)
	{
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(IDPROPERTY, uniqueId);
		Iterator<Entry<String, String>> it2 = incomingParams.entrySet().iterator();
		while(it2.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it2.next();
			params.put(pair.getKey().toString(), pair.getValue());
		}
		
		System.out.println(cypherQuery);
		Iterator<Entry<String, Object>> it3 = params.entrySet().iterator();
		while(it3.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it3.next();
			System.out.println("Param name: " + pair.getKey().toString() + " Param value:" + pair.getValue());
		}
		
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(cypherQuery, params);
			transaction.success();
			String res = result.dumpToString();
			System.out.println(res);
		}
	}
	
	/** Method for executing general queries where unique id is required and params are not known in advance
	 * 
	 */
	public void executeQuerySimpleDynamicParams(String cypherQuery, Map<String, String> incomingParams)
	{
		ExecutionEngine engine = new ExecutionEngine(db);
		ExecutionResult result = null;
		Map<String, Object> params = new HashMap<String, Object>();
		Iterator<Entry<String, String>> it2 = incomingParams.entrySet().iterator();
		while(it2.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it2.next();
			params.put(pair.getKey().toString(), pair.getValue());
		}
		try (Transaction transaction = db.beginTx())
		{
			result = engine.execute(cypherQuery, params);
			transaction.success();
		}
		finally 
		{
			String res = result.dumpToString();
			System.out.println(res);
		}
	}

	/** return the nodes of the graph data as iterable
	 * 
	 */
	public Iterable<Node> returnDbIterableNodes() 
	{
		Iterable<Node> nodes = null;

		try (Transaction tx = db.beginTx())
		{			
			for (Node n : GlobalGraphOperations.at(db).getAllNodes()) 
			{
				nodes = GlobalGraphOperations.at(db).getAllNodes();
			}
			tx.success();
		}
		finally 
		{	
		}
		return nodes;
	}

	/**
	 *  return the relationship of the graph data as iterable
	 */
	public Iterable<Relationship> returnDbIterableRelationships() 
	{
		Iterable<Relationship> rels = null;

		try (Transaction tx = db.beginTx())
		{			
			for (Relationship rel : GlobalGraphOperations.at(db).getAllRelationships()) 
			{
				rels = GlobalGraphOperations.at(db).getAllRelationships();
			}
			tx.success();
		}
		finally 
		{	
		}
		return rels;
	}

	/**
	 * 
	 */
	@Override
	public List<Object> getAllNodes() 
	{
		List<Object> nodes = new ArrayList<Object>();
		try (Transaction tx = db.beginTx())
		{			
			for (Node n : GlobalGraphOperations.at(db).getAllNodes()) 
			{
				nodes.add(n);
			}
			tx.success();
		}
		finally {}
		return nodes;
	}
	
	/** Get all nodes from the db as a list
	 * 
	 */
	private List<Node> getNodes() 
	{
		List<Node> nodes = new ArrayList<Node>();
		try (Transaction tx = db.beginTx())
		{			
			for (Node n : GlobalGraphOperations.at(db).getAllNodes()) 
			{
				nodes.add(n);
			}
			tx.success();
		}
		finally {}
		return nodes;
	}

	/**
	 * 
	 */
	@Override
	public List<Object> getAllRelationships() 
	{
		List<Object> rels = new ArrayList<Object>();
		try (Transaction tx = db.beginTx())
		{			
			for (Relationship rel : GlobalGraphOperations.at(db).getAllRelationships()) 
			{
				rels.add(rel);
			}
			tx.success();
		}
		finally {}
		return rels;
	}

	/**
	 * 
	 */
	@Override
	public void checkIfDbEmpty() 
	{
		executeQuery(CypherQuery.CHECK_IF_DB_EMPTY);
	}

	@Override
	public Node findParentNode(Node node) 
	{
		TraversalDescription td = getDb().traversalDescription()
				.breadthFirst()
				.relationships(RelTypes._default)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.toDepth(1))
				.evaluator(new CustomParentChildEvaluator());
				Traverser t = td.traverse(node);
				
				ArrayList<Node> nodes = new ArrayList<Node>();
				Traverser custom = t;
				for (Path p : custom)
				{
					nodes.add(p.endNode());    	
				}
		return nodes.get(0);
	}
	
	/**
	 * 
	 */
	public List<Node> traverseInterRels(Node node) 
	{
		TraversalDescription td = getDb().traversalDescription()
				.breadthFirst()
				.relationships(RelTypes.INTER_REL)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(new CustomEvaluator());
				Traverser t = td.traverse(node);
				
				ArrayList<Node> nodes = new ArrayList<Node>();
				Traverser custom = t;
				for (Path p : custom)
				{
					nodes.add(p.endNode());
				}
				return nodes;
	}

	@Override
	public boolean executeQuery(String query, String propertyName,
			String propertyValue, String propertyName2, String propertyValue2,
			String propertyName3, String propertyValue3, String propertyName4,
			String propertyValue4) {
		return false;
	}
}
