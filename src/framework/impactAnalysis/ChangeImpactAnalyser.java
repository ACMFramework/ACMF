package framework.impactAnalysis;

import java.util.ArrayList;
import java.util.List;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Traverser;
import framework.DAL.GraphDb.CypherQuery;
import framework.DAL.GraphDb.DaoFactory;
import framework.DAL.GraphDb.IGraphStoreDao;
import framework.DAL.GraphDb.Neo4jEmbeddedDao;
import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.ArtefactType;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ElementType;
import framework.changeDetection.FileLevelChangeType;
import framework.DAL.GraphDb.RelTypes;
import framework.general.IExecutable;
import framework.general.IExecutionManager;

/**
 * Class implementing change impact analysis functionality
 */
public class ChangeImpactAnalyser implements IExecutable
{
	/**IAResult object capturing impact analysis output
	 * 
	 */
	IAResult result;

	/** ParentIAResult object capturing the result of impact analysis for the container element of the changed node
	 * 
	 */
	private ParentIAResult containerNodeResult;

	/**
	 *
	 */
	private static IGraphStoreDao embedded;

	/** The input of change impact analysis is a list of ChangeData objects
	 * 
	 */
	List<ChangeData> input = null;

	public List<ChangeData> getInput()
	{
		return input;
	}

	/**
	 * 
	 * @param input
	 */
	public void setInput(List<ChangeData> input) 
	{
		this.input = input;
	}

	/** The output of impact analysis is a list of IAResult objects
	 * 
	 */
	private List<IAResult> output = null;

	/**
	 * 
	 * @return
	 */
	public List<IAResult> getOutput() 
	{
		return output;
	}

	/**
	 * 
	 * @param output
	 */
	public void setOutput(List<IAResult> output) 
	{
		this.output = new ArrayList<IAResult>();
		this.output.addAll(output);
	}

	/**
	 * 
	 */
	public ChangeImpactAnalyser(IExecutionManager mg) 
	{
		mg.register(this);
	}

	
	/** Execute impact analysis on input change data list - for each change, return an IAResult object
	 *  The output is a list of IAResult objects
	 * 
	 */
	@Override
	public void Execute() 
	{
		List<IAResult> listOfIARes = new ArrayList<IAResult>();
		List<CustomNodeRepresentation> customImpactList = new ArrayList<CustomNodeRepresentation>();
		List<CustomNodeRepresentation> customParentNodeResultList = new ArrayList<CustomNodeRepresentation>();
		List<CustomNodeRepresentation> customIntraNodeResultList = new ArrayList<CustomNodeRepresentation>();
		embedded = DaoFactory.getDataStore(DaoFactory.NEO4J);
		Node changedNode = null;

		try (Transaction tx = embedded.getDb().beginTx())
		{
			for(ChangeData change: getInput()) 
			{
				result = new IAResult();
				containerNodeResult = new ParentIAResult();
				CustomNodeRepresentation customRes;
				changedNode = getChangedNode(change);
				result.setChange(change);
				result.setChangedNode(changedNode);
				result.setMsg(null);
				result.setIntraTraversalParentChildResults(getIntraTraversalHierarchyResultsAsPaths(changedNode));
				result.setInterTraversalResults(getInterTraversalResultsAsPaths(changedNode));
				result.setInterTraversalNodeResults(getInterTraversalResultsAsNodes(changedNode));
				result.setIntraTraversalDefaultResults(getIntraTraversalResultsAsPaths(changedNode));
				
				// If add file level change
				if(change.getTypeOfFileLevelChange().equals(FileLevelChangeType.ADD)) 
				{
					result.setMsg(IAMessage.NOIA);
				}
				// If delete file level change
				else if(change.getTypeOfFileLevelChange().equals(FileLevelChangeType.DELETE)) 
				{
					// Get container element from 
				} 
				// If edit file level change
				else
				{
					// Edit and Delete artefact element level changes follow the same logic
					if(change.getTypeOfChange().equals(ArtefactElementChangeType.DELETE) 
							|| change.getTypeOfChange().equals(ArtefactElementChangeType.EDIT)) 
					{
						// 1. do inter traversal and 2. do intra traversal
						// Have to represent nodes and their parents (if applicable) using custom representation
						// due to specifics of the Traversal API - applicable to both inter and intra traversals
						
						// Check to see if there are inter traversal results, if not, intra traversals still take place
						if(result.getInterTraversalNodeResults().size() > 0) 
						{
							for(int i = 0; i < result.getInterTraversalNodeResults().size(); i++) 
							{
								String type = result.getInterTraversalNodeResults().get(i).getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).toString();
								String id = result.getInterTraversalNodeResults().get(i).getProperty(Neo4jEmbeddedDao.IDPROPERTY).toString();
								customRes = new CustomNodeRepresentation(changedNode, type, id);
								// Get parent of this impact node - only if it is a member element
								if(!ElementType.isContainer(getIntraTraversalHierarchyResultsAsPaths(customRes.getNode()).get(0).startNode().getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).
										toString())) 
								{
									customRes.setParent(getIntraTraversalHierarchyResultsAsPaths(customRes.getNode()).get(0).endNode());
									customRes.setParentId(customRes.getParent().getProperty(Neo4jEmbeddedDao.IDPROPERTY).toString());
									customRes.setParentType(customRes.getParent().getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).toString());
								}
								customImpactList.add(customRes);			
							}
							result.setCustomImpactNodeList(customImpactList);
							for(int i = 0 ; i < result.getCustomImpactNodeList().size(); i ++) 
							{
								System.out.println("List of impact nodes using custom representation: " 
										+ result.getCustomImpactNodeList().get(i).toString());
							}
						}
						else 
						{
							// If there are no inter results, return message
							result.setMsg(IAMessage.NOINTERLINKS);
						}
						
						if(result.getIntraTraversalParentChildResults().size() > 0) 
						{
							// Specifics of impact nodes connected through intra links
							for(int i =0; i < result.getIntraTraversalParentChildResults().size(); i++) 
							{
								String type = result.getIntraTraversalParentChildResults().get(i).endNode().getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).toString();
								String id = result.getIntraTraversalParentChildResults().get(i).endNode().getProperty(Neo4jEmbeddedDao.IDPROPERTY).toString();
								customRes = new CustomNodeRepresentation(changedNode, type, id);
								customIntraNodeResultList.add(customRes);
							}
							result.setCustomNodeIntraResult(customIntraNodeResultList);
						}
						else 
						{
							result.setMsg(IAMessage.NOINTRALINKS);
						}
					}
					// Handle add artefact element changes differently
					else 
					{
						// If artefact type = Java SC, UML class d., JUnit test, do inter traversal of parents for member elements
						if(change.getTypeOfArtefact().equals(ArtefactType.JAVA_SOURCE_CODE) || change.getTypeOfArtefact().equals(ArtefactType.UNIT_TEST)
								|| change.getTypeOfArtefact().equals(ArtefactType.UML_CLASS_DIAGRAM)) 
						{
							// If added element is a member get parent and do inter traversal of parent
							if(!ElementType.isContainer(changedNode.getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).toString())) 
							{
								result.setContainerNodeOfChangedNode(result.getIntraTraversalParentChildResults().get(0).endNode());
								// Do inter traversal for parent and store it in containerNodeResult
								if(result.getIntraTraversalParentChildResults().size()>0) 
								{
									Node parentNode = result.getContainerNodeOfChangedNode();
									containerNodeResult.setInterTraversalResults(getInterTraversalResultsAsPaths(parentNode));
									// If container node has inter connections
									if(containerNodeResult.getInterTraversalResults().size() > 0) 
									{
										// Represent inter results of parent node using custom representation
										for(int i = 0; i < containerNodeResult.getInterTraversalResults().size(); i++) 
										{
											String type = containerNodeResult.getInterTraversalResults().get(i).endNode().getProperty(Neo4jEmbeddedDao.TYPEPROPERTY).toString();
											String id = containerNodeResult.getInterTraversalResults().get(i).endNode().getProperty(Neo4jEmbeddedDao.IDPROPERTY).toString();
											customRes = new CustomNodeRepresentation(changedNode, type, id);
											customParentNodeResultList.add(customRes);
										}
										containerNodeResult.setCustomImpactNodeList(customParentNodeResultList);
										result.setParentsInterResults(containerNodeResult);
									}
									else 
									{
										// parent has no inter relations, don't do IA
										result.setMsg(IAMessage.NOIA);
									}
									
								}
							}
							else 
							{
								// no inter or intra traversal
								result.setMsg(IAMessage.NOIA);
							}
						}
						else 
						{
							// Don't do impact analysis - as there is no parent-child relationship and there are no inter links
							result.setMsg(IAMessage.NOIA);
						}
					}
				}
				listOfIARes.add(result);
			}
			tx.success();
		}
		setOutput(listOfIARes);
		embedded.closeDb();
	}

	/** Return database node representation of changed entity based on its unique id
	 * 
	 * @param changeData
	 * @return
	 */
	public Node getChangedNode(ChangeData changeData) 
	{
		Node changedNode = null;
		// Get deleted and edited nodes based on unique id
		changedNode = embedded.executeQueryWithParamsReturnNode(CypherQuery.SEARCH_NODE_QUERY, 
				Neo4jEmbeddedDao.IDPROPERTY, changeData.getUniqueId());
		return changedNode;
	}

	// Wrapper method for running inter traversals in a transaction
	public List<Node> runInterTraversalGetNodes(Node node)
	{
		List<Node> result = new ArrayList<Node>();
		try (Transaction tx = embedded.getDb().beginTx())
		{
			for(int i = 0; i < getInterTraversalResultsAsPaths(node).size(); i++) 
			{
				result.add(getInterTraversalResultsAsPaths(node).get(i).endNode());
			}
			System.out.println(getInterTraversalResultsAsPaths(node));
			tx.success();
		}
		return result;
	}

	// Wrapper method for running intra traversals in a transaction
	public List<Node> runIntraTraversalGetNodes(Node node)
	{
		List<Node> result = new ArrayList<Node>();
		try (Transaction tx = embedded.getDb().beginTx())
		{
			for(int i = 0; i < getIntraTraversalResultsAsPaths(node).size(); i++) 
			{
				result.add(getIntraTraversalResultsAsPaths(node).get(i).endNode());
			}
			System.out.println(getIntraTraversalResultsAsPaths(node));
			tx.success();
		}
		return result;
	}

	// Wrapper method for running inter traversals in a transaction
	public List<Path> runInterTraversalGetPaths(Node node)
	{
		List<Path> result = new ArrayList<Path>();
		try (Transaction tx = embedded.getDb().beginTx())
		{
			for(int i = 0; i < getInterTraversalResultsAsPaths(node).size(); i++) 
			{
				result.add(getInterTraversalResultsAsPaths(node).get(i));
			}
			System.out.println(getInterTraversalResultsAsPaths(node));
			tx.success();
		}
		return result;
	}

	// Wrapper method for running intra traversals in a transaction
	public List<Path> runIntraTraversalGetPaths(Node node)
	{
		List<Path> result = new ArrayList<Path>();
		try (Transaction tx = embedded.getDb().beginTx())
		{
			for(int i = 0; i < getIntraTraversalResultsAsPaths(node).size(); i++) 
			{
				result.add(getIntraTraversalResultsAsPaths(node).get(i));
			}
			System.out.println(getIntraTraversalResultsAsPaths(node));
			tx.success();
		}
		return result;
	}

	/**
	 * Method returning traversal results as a list of path objects
	 * @param node - the starting node of the traversal
	 */
	public ArrayList<Path> getIntraTraversalResultsAsPaths(Node node) 
	{	
		ArrayList<Path> paths = new ArrayList<Path>();
		Traverser custom = getIntraRelTraverser(node);
		for (Path p : custom)
		{
			paths.add(p);    	
		}

		return paths;
	}

	/**
	 * Method returning traversal results as a list of path objects
	 * @param node - the starting node of the traversal
	 */
	public ArrayList<Path> getIntraTraversalHierarchyResultsAsPaths(Node node) 
	{	
		ArrayList<Path> paths = new ArrayList<Path>();
		Traverser custom = getHierarchyIntraRelTraverser(node);

		for (Path p : custom)
		{
			paths.add(p);    	
		}
		return paths;
	}

	/**
	 * Method returning traversal results as a list of path objects
	 * @param node - the starting node of the traversal
	 */
	public ArrayList<Path> getInterTraversalResultsAsPaths(Node node) 
	{	
		ArrayList<Path> paths = new ArrayList<Path>();
		Traverser custom = getInterTraverser(node);
		for (Path p : custom)
		{
			paths.add(p);
			//output += "At depth " + p.length() + " => " 
			//+ p.endNode().getId() + p.endNode().getProperty( "type" ) + "\n";      	
		}
		return paths;
	}

	/**
	 * Method returning traversal results as a list of path objects
	 * @param node - the starting node of the traversal
	 */
	public ArrayList<Node> getInterTraversalResultsAsNodes(Node node) 
	{	
		ArrayList<Node> nodes = new ArrayList<Node>();
		Traverser custom = getInterTraverser(node);
		for (Path p : custom)
		{
			nodes.add(p.endNode());
		}
		return nodes;
	}

	/**
	 * Traverse nodes using a custom evaluator and return the results as a string
	 * @param node - the starting node of the traversal
	 */
	public String getInterTraversalResultsAsString(Node node)
	{
		String output = "";
		Traverser custom = getInterTraverser(node);
		for (Path position : custom)
		{
			output += position + "\n";    	
		}
		return output;
	}

	/**
	 * Method returning a traverser using a custom evaluator - specifically, inter relationships are traversed
	 * @param node - the starting node of the traversal
	 */
	public Traverser getInterTraverser(Node node) 
	{
		TraversalDescription td = embedded.getDb().traversalDescription()
				.breadthFirst()
				.relationships(RelTypes.INTER_REL)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(new CustomEvaluator());
		return td.traverse(node);
	}

	/**
	 * Method returning a traverser using a custom evaluator - specifically, INTRA relationships are traversed
	 * @param node - the starting node of the traversal
	 */
	public Traverser getIntraRelTraverser(Node node)
	{
		TraversalDescription td = embedded.getDb().traversalDescription()
				.breadthFirst()
				.relationships(RelTypes._default)
				.evaluator(Evaluators.excludeStartPosition());
		//.evaluator(new Evaluator());
		return td.traverse(node);
	}

	/**
	 * Method returning a traverser using a custom evaluator - specifically, INTRA relationships which
	 * are labelled with specific parent-child property value are returned
	 * @param node - the starting node of the traversal
	 */
	public Traverser getHierarchyIntraRelTraverser(Node node)
	{
		TraversalDescription td = embedded.getDb().traversalDescription()
				.breadthFirst()
				.relationships(RelTypes._default)
				.evaluator(Evaluators.excludeStartPosition())
				.evaluator(Evaluators.toDepth(1))
				.evaluator(new CustomParentChildEvaluator());
		return td.traverse(node);
	}
}
