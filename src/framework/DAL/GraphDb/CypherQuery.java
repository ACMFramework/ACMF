package framework.DAL.GraphDb;

/** Represents the queries required for db CRUD operations
 *
 */
public final class CypherQuery 
{
	/**
	 * Delete all data from graph db query
	 */
	public static final String DELETE_ALL_DATA = "START n = node(*) OPTIONAL MATCH n-[r]-() WHERE (ID(n)>0 AND ID(n)<10000) DELETE n, r;";

	/**
	 * Delete node with any associated relationships
	 */
	public static final String REMOVE_NODE = "START n = node(*) OPTIONAL MATCH n-[r]-() WHERE (ID(n)>0 AND ID(n)<10000) DELETE n, r;";

	/**
	 * Search for node based on unique id - with a literal value
	 */
	public static final String SEARCH_NODE_QUERYv1 = "match (n) where n.uniqueId = 'rq2Path' return n;";

	/**
	 * Search for node based on unique id
	 */
	public static final String SEARCH_NODE_QUERY = "match (n) where n.uniqueId = {uniqueId} return n;";

	/**
	 * Get node type property based on unique id
	 */
	public static final String GET_NODE_TYPE = "match (n) where n.uniqueId = {uniqueId} return n.type;";
	
	/**
	 * Get node based on id (not property id) where id = specific number
	 */
	public static final String GET_NODE_QUERY = "match (n) where id(n) = 0 return n;";

	/**
	 * Get for node based on type and name 
	 */
	public static final String GET_NODE_BASED_ON_TYPE_AND_NAME_PROPERTY = 
			"match (n) where (n.type = {type} AND n.name = {name}) return n;";

	/**
	 * Set to be deleted label on specific nodes (retrieved using their unique id)
	 */
	public static final String SET_NODE_LABEL = "MATCH (n { uniqueId: {uniqueId} }) SET n :ToRemove RETURN n";

	/**
	 * Set label on specific nodes (retrieved using their unique id) indicating that they belong to set of impacted nodes
	 */
	public static final String SET_IMPACTED_NODE_LABEL = "MATCH (n { uniqueId: {uniqueId} }) SET n :Impacted RETURN n";

	/**
	 * Return nodes labelled "ToRemove"
	 */
	public static final String GET_LABELLED_NODES = "MATCH (n: ToRemove) RETURN n";

	/**
	 * Delete nodes labelled "ToRemove"
	 */
	public static final String DELETE_LABELLED_NODES = "MATCH (n: ToRemove) DELETE n";
	//"MATCH (n) WHERE ToRemove DELETE n";

	/**
	 * Delete node whose unique id was specified
	 */
	public static final String DELETE_SPECIFIC_NODE = "match (n{uniqueId:{uniqueId}}) optional match (n)-[r]-() delete n,r";

	/**
	 * Set 'name', 'type', 'visibility' property of a specific node to a new value
	 */
	public static final String UPDATE_NAME_TYPE_VISIBILITY = "match (n {uniqueId: {uniqueId}}) set n.visibility = {visibility}, n.name = {name}, n.type = {type}";

	/**
	 * Set 'visibility' property of a specific node to a new value
	 */
	public static final String UPDATE_VISIBILITY = "match (n {uniqueId: {uniqueId}}) set n.visibility = {visibility}";

	/**
	 * Set 'name' property of a specific node to a new value
	 */
	public static final String UPDATE_NAME = "match (n {uniqueId: {uniqueId}}) set n.name = {name}";

	/**
	 * Set 'type' property of a specific node to a new value
	 */
	public static final String UPDATE_TYPE = "match (n {uniqueId: {uniqueId}}) set n.type = {type}";

	/**
	 * Set 'content' property of a specific node to a new value
	 */
	public static final String UPDATE_CONTENT = "match (n {uniqueId: {uniqueId}}) set n.content = {content}";

	/**
	 * Set 'parameters' property of a specific node to a new value
	 */
	public static final String UPDATE_PARAMETERS = "match (n {uniqueId: {uniqueId}}) set n.parameters = {parameters}";

	/**
	 * Set 'returnType' property of a specific node to a new value
	 */
	public static final String UPDATE_RETURNTYPE = "match (n {uniqueId: {uniqueId}}) set n.returnType = {returnType}";

	/**
	 * Set 'variableType' property of a specific node to a new value
	 */
	public static final String UPDATE_VARIABLETYPE = "match (n {uniqueId: {uniqueId}}) set n.variableType = {variableType}";
	
	/**
	 * Check if database is empty
	 */
	public static final String CHECK_IF_DB_EMPTY = "MATCH (n) RETURN n IS NULL AS isEmpty LIMIT 1;";
	
	/**
	 * Return nodes that are not connected through the specified relation
	 */
	public static final String GET_NOT_CONNECTED_NODES = "OPTIONAL MATCH n-[r:_default]-m WHERE r is null RETURN n";
	
	/** Create relationship between two specific nodes
	 * 
	 */
	public static final String CREATE_REL_SPECIFIC_NODES = "MATCH n, m where (n.name= {name} and m.name= {name} and n.type= {type} and m.type= {type}) CREATE (n)-[:INTER_REL]->(m)";
	
	/** Create relationship based on unique id of nodes
	 * 
	 */
	public static final String CREATE_INTRA_REL_BASED_ON_UNIQUE_ID= "MATCH n, m where (n.uniqueId= {uniqueId} and m.uniqueId= {uniqueId}) CREATE (n)-[r:_default]->(m) set r.relType='Parent_Child'";
}
