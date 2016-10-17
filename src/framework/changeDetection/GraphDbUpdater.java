package framework.changeDetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.neo4j.graphdb.Node;
import framework.DAL.GraphDb.CypherQuery;
import framework.DAL.GraphDb.DaoFactory;
import framework.DAL.GraphDb.IGraphStoreDao;
import framework.DAL.GraphDb.Neo4jEmbeddedDao;
import framework.DAL.GraphDb.RelTypes;
import framework.DAL.GraphML.GraphMLProcessor;
import framework.general.IExecutable;
import framework.general.IExecutionManager;

/** Executable class performing the graph database update step of change detection
 * 
 *
 */
public class GraphDbUpdater implements IExecutable
{
	/**
	 * 
	 */
	IGraphStoreDao graphStore;

	/**
	 * 
	 * @return
	 */
	public IGraphStoreDao getGraphStore() 
	{
		return graphStore;
	}

	// Get this input from previous process step
	/**
	 * 
	 */
	List<ChangeData> changes = new ArrayList<ChangeData>();

	/**
	 * 
	 * @return
	 */
	public List<ChangeData> getChanges() 
	{
		return changes;
	}

	/** Obtain changes from previous process step - change identification
	 * 
	 * @param changes
	 */
	public void setChanges(List<ChangeData> changes) 
	{
		this.changes = changes;
	}

	/**
	 * 
	 * @param manager
	 */
	public GraphDbUpdater(IExecutionManager manager) 
	{
		manager.register(this);
		graphStore = DaoFactory.getDataStore(DaoFactory.NEO4J);
	}

	@Override
	public void Execute() 
	{
	}

	/** Add inter link between the parent node of the child node passed in and every element connected to it through 
	 * an inter link
	 * 
	 */
	public void addInterLinks(List<String> uniqueIds) 
	{
		for(String id : uniqueIds) 
		{
			String type = graphStore.executeQueryReturnStringValue(CypherQuery.GET_NODE_TYPE, 
					Neo4jEmbeddedDao.IDPROPERTY, id);
			// If it is a child element, get its parent
			if(ElementType.isChild(type)) 
			{	
				Node node = graphStore.executeQueryReturnNode(CypherQuery.SEARCH_NODE_QUERY);
				// get parent of node
				Node parentNode = graphStore.findParentNode(node);
				// Traverse parent node inter rels
				List<Node> interRels = graphStore.traverseInterRels(parentNode);
				for(Node connectedNodes : interRels) 
				{
					// Connect connectedNodes to node
					connectedNodes.createRelationshipTo(node, RelTypes.INTER_REL);
				}
			}

		}
	}

	/** Label nodes to be deleted based on unique id and cyper query
	 * 
	 */
	public void labelToBeRemovedNodes(List<String> uniqueIds) 
	{
		System.out.println("IDS NUMBER:" + uniqueIds.size());
		for(int i = 0; i < uniqueIds.size(); i++) 
		{
			graphStore.executeQuery(CypherQuery.SET_NODE_LABEL, 
					Neo4jEmbeddedDao.IDPROPERTY, uniqueIds.get(i).toString());
		}
	}

	/** Label node to be deleted based on unique id and cyper query
	 * 
	 */
	public void labelToBeRemovedNode(String uniqueId) 
	{
		graphStore.executeQuery(CypherQuery.SET_NODE_LABEL, 
				Neo4jEmbeddedDao.IDPROPERTY, uniqueId);
	}

	/** Mapping data key values to graph database property keys
	 * 
	 * @param dataKey
	 * @return the property key
	 */
	public String mapDataKeyToProperty(String dataKey)
	{
		String property = null;
		if(dataKey.equals(GraphMLProcessor.CONTENT_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.CONTENTPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.IDPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.NAMEPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.PARAM_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.PARAMETERSPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.TYPEPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.RETURNTYPE_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.RETURN_TYPE_PROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.VISIBILITY_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.VISIBILITYPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.VARIABLETYPE_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.VARIABLE_TYPE_PROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.IMPEXT_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.IMPEXPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.TITLE_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.TITLEPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.PRIORITY_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.PRIORITYPROPERTY;
		}
		if(dataKey.equals(GraphMLProcessor.REQTYPE_DATA_KEY_VALUE_SHORT))
		{
			property = Neo4jEmbeddedDao.REQTYPEPROPERTY;
		}
		return property;
	}

	/** Update node properties - property names are dynamically generated and property values are obtained from
	 * change data
	 * 
	 */
	public void updateNodeProperties(ChangeData change) 
	{
		// Build cypher query and parameters dynamically from change data
		Map<String, String> queryParameters = new HashMap <String, String>();
		for(int i = 0; i < change.getEdits().size(); i++) 
		{
			for(String key: change.getEdits().get(i).keySet()) 
			{
				queryParameters.put(mapDataKeyToProperty(key), mapDataKeyToProperty(key));
			}
		}
		String updateQuery = "match (n {uniqueId: {uniqueId}}) set ";
		Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
		while(it.hasNext()) 
		{
			Map.Entry pair = (Map.Entry)it.next();
			updateQuery = updateQuery +"n."+ pair.getKey().toString() + "" + "=" + "{" + pair.getValue() + "}, ";
		}
		updateQuery = updateQuery.substring(0, updateQuery.length()-2);
		//System.out.println(updateQuery);

		// Build param keys and values
		Map<String, String> parameters = new HashMap<String, String>();
		for(int i = 0; i < change.getEdits().size(); i++) 
		{
			for(String key: change.getEdits().get(i).keySet()) 
			{
				parameters.put(mapDataKeyToProperty(key), change.getEdits().get(i).get(key).rightValue());
			}
		}
		// Execute query on database
		graphStore.executeQueryWithDynamicParams(updateQuery, change.getUniqueId(), parameters);
	}

	/** Update multiple nodes' properties - property names are dynamically generated and property values are obtained from
	 * change data
	 * 
	 */
	public void updateMultipleNodesProperties(List<ChangeData> changes) 
	{
		for(ChangeData change : changes) 
		{
			// Build cypher query and parameters dynamically from change data
			Map<String, String> queryParameters = new HashMap <String, String>();
			for(int i = 0; i < change.getEdits().size(); i++) 
			{
				for(String key: change.getEdits().get(i).keySet()) 
				{
					queryParameters.put(mapDataKeyToProperty(key), mapDataKeyToProperty(key));
				}
			}
			String updateQuery = "match (n {uniqueId: {uniqueId}}) set ";
			Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
			while(it.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it.next();
				updateQuery = updateQuery +"n."+ pair.getKey().toString() + "" + "=" + "{" + pair.getValue() + "}, ";
			}
			updateQuery = updateQuery.substring(0, updateQuery.length()-2);
			//System.out.println(updateQuery);

			// Build param keys and values
			Map<String, String> parameters = new HashMap<String, String>();
			for(int i = 0; i < change.getEdits().size(); i++) 
			{
				for(String key: change.getEdits().get(i).keySet()) 
				{
					parameters.put(mapDataKeyToProperty(key), change.getEdits().get(i).get(key).rightValue());
				}
			}
			// Execute query on database
			graphStore.executeQueryWithDynamicParams(updateQuery, change.getUniqueId(), parameters);
		}
	}

	/**
	 * 
	 * @param changes
	 */
	public void addNewNodes(List<ChangeData> changes) 
	{
		for(ChangeData change : changes) 
		{
			// Build cypher query and parameters dynamically from change data
			// Query format "CREATE (n {property:'value'})";

			Map<String, String> queryParameters = new HashMap<String, String>();
			for(int i = 0; i < change.getAdded().size(); i++) 
			{
				for(String key: change.getAdded().get(i).keySet()) 
				{
					//queryParameters.put(mapDataKeyToProperty(key), change.getAdded().get(i).get(key));
					queryParameters.put(mapDataKeyToProperty(key), mapDataKeyToProperty(key));
				}
			}

			String createQuery = "CREATE (n {";
			Iterator<Entry<String, String>> it = queryParameters.entrySet().iterator();
			while(it.hasNext()) 
			{
				Map.Entry pair = (Map.Entry)it.next();
				createQuery = createQuery + pair.getKey().toString() + ":" + "{" + pair.getValue() + "}, ";
			}
			createQuery = createQuery.substring(0, createQuery.length()-2) + "})";

			// Build param keys and values
			Map<String, String> parameters = new HashMap<String, String>();
			for(int i = 0; i < change.getAdded().size(); i++) 
			{
				for(String key: change.getAdded().get(i).keySet()) 
				{
					parameters.put(mapDataKeyToProperty(key), change.getAdded().get(i).get(key));
				}
			}
			// Execute query 
			graphStore.executeQuerySimpleDynamicParams(createQuery, parameters);
		}
	}

	/** Create inter relationship between two nodes of the specified name
	 * 
	 * @param name
	 * @param name2
	 */
	public void createInterRelBasedOnName(String name, String name2) 
	{
		graphStore.executeQuery(CypherQuery.CREATE_REL_SPECIFIC_NODES, Neo4jEmbeddedDao.NAMEPROPERTY, 
				name, name2, Neo4jEmbeddedDao.TYPEPROPERTY, "class", "class");
	}

	/** Create intra relationship between two nodes of specified by their unique id
	 * 
	 * @param changes
	 * @param parentUniqueId
	 */
	public void createRelationshipExistingNodes(List<ChangeData> adds, String parentUniqueId) 
	{
		String query = CypherQuery.CREATE_INTRA_REL_BASED_ON_UNIQUE_ID;
		List<String> ids = new ArrayList<String>();
		for(ChangeData change : adds) 
		{
			String id = change.getAdded().get(0).get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT);
			ids.add(id);
		}
		// Transform unique ids as Neo4j requires double slash or forward slash instead of backslash characters
		parentUniqueId = parentUniqueId.replace("\\", "\\\\");
		System.out.println("Parent id: " + parentUniqueId);
		for(String id : ids) 
		{
			id = id.replace("\\", "\\\\");
			System.out.println("Id: " + id);
			// Hard coded query as parameters are not correctly passed to the execution engine
			String query1="MATCH n, m where (n.uniqueId='";
			String q3 = "' and m.uniqueId= '";
			String q4 ="') CREATE (n)-[r:_default]->(m)  set r.relType='Parent_Child'";
			String finalQuery = query1+id+q3+parentUniqueId+q4;
			graphStore.executeQuery(finalQuery, Neo4jEmbeddedDao.IDPROPERTY, id, parentUniqueId);
		}
	}
}
