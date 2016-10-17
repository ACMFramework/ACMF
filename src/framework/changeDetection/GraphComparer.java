package framework.changeDetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.w3c.dom.NodeList;
import com.google.common.collect.MapDifference;
import com.google.common.collect.MapDifference.ValueDifference;
import com.google.common.collect.Maps;
import framework.DAL.GraphML.GraphMLProcessor;
import framework.DAL.Transformation.IDGenerator;

/** Compare two subsequent versions of an artefact expressed as graphml files
 *
 */
public class GraphComparer 
{
	/**
	 * GraphMLProcessor handling the previous version of the graph
	 */
	private GraphMLProcessor beforeGraphProc;

	/**
	 * 
	 * @return
	 */
	public GraphMLProcessor getBeforeGraphProc() 
	{
		return beforeGraphProc;
	}

	/**
	 * GraphMLProcessor handling the new version of the graph
	 */
	private GraphMLProcessor afterGraphProc;

	/**
	 * 
	 * @return
	 */
	public GraphMLProcessor getAfterGraphProc() 
	{
		return afterGraphProc;
	}

	/**
	 * LinkedHashmap representing the previous version of the graph
	 */
	private LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap;

	/**
	 * 
	 * @param beforeMap
	 */
	public void setBeforeMap(LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap) 
	{
		this.beforeMap = beforeMap;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedHashMap<String, LinkedHashMap<String, String>> getBeforeMap() 
	{
		return beforeMap;
	}

	/**
	 * LinkedHashmap representing the new version of the graph
	 */
	private LinkedHashMap<String, LinkedHashMap<String, String>> afterMap;

	/**
	 * 
	 * @param afterMap
	 */
	public void setAfterMap(LinkedHashMap<String, LinkedHashMap<String, String>> afterMap) 
	{
		this.afterMap = afterMap;
	}

	/**
	 * 
	 * @return
	 */
	public LinkedHashMap<String, LinkedHashMap<String, String>> getAfterMap() 
	{
		return afterMap;
	}

	/**
	 * Utility hashmap capturing old and new keys that have been matched
	 */
	public HashMap<String, String> visited = new HashMap<String, String>();

	/**Map representation of edited nodes
	 * 
	 */
	public LinkedHashMap<String, LinkedHashMap<String, String>> edited = new LinkedHashMap<String, LinkedHashMap<String, String>>();

	/**Set representation of removed nodes
	 * 
	 */
	public Set<String> removed = null;

	/**Set representation of added nodes
	 * 
	 */
	public Set<String> added = null;

	/**
	 * @param The paths of the two graphs to be compared
	 */
	public GraphComparer(String beforeGraphPath, String afterGraphPath)
	{
		beforeGraphProc = new GraphMLProcessor(beforeGraphPath);
		afterGraphProc = new GraphMLProcessor(afterGraphPath);
	}

	/** Create a LinkedHashMap representation of graph nodes and their properties
	 * @param graphmlProcessor - represents a GraphMLProcessor instance providing
	 * access to the contents of the graphML files
	 */
	public LinkedHashMap<String, LinkedHashMap<String, String>> createMapRepresentation(GraphMLProcessor graphmlProcessor)
	{
		LinkedHashMap<String, LinkedHashMap<String, String>> outerMap = 
				new LinkedHashMap<String, LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> innerMap = null;
		NodeList nodes = graphmlProcessor.returnNodeNodeList();

		for (int j = 0; j < nodes.getLength(); j++) 
		{
			innerMap = new LinkedHashMap<String, String>();
			// Value for the outer hashmap, an inner hashmap
			NodeList children = nodes.item(j).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) 
			{	// Eliminate whitespace
				if(children.item(i).hasAttributes()) 
				{	// Add keys and values to an inner map
					innerMap.put(children.item(i).getAttributes().item(0).getNodeValue(), 
							children.item(i).getTextContent());
				}
			}
			outerMap.put("Key" + j, innerMap);
		}
		setUniqueIdValuesEmpty(outerMap);
		return outerMap;
	}

	/** Compare hashmaps representing two versions of graphml files to detect changes
	 * @param beforeMap - represents version 1 of the graphml file
	 * @param afterMap - represents version 2 of the graphml file
	 */
	public void compareMaps(LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap, LinkedHashMap<String, 
			LinkedHashMap<String, String>> afterMap)
	{
		LinkedHashMap<String, String> beforeValue = null;
		LinkedHashMap<String, String> afterValue = null;

		Iterator<String> iterator = beforeMap.keySet().iterator();
		while (iterator.hasNext()) 
		{
			String key = iterator.next().toString();
			beforeValue = beforeMap.get(key);
			System.out.println("1_" + key + ", " + "contents: " + beforeValue);

			Iterator<String> iterator2 = afterMap.keySet().iterator();
			while (iterator2.hasNext()) 
			{
				String key2 = iterator2.next().toString();
				afterValue = afterMap.get(key2);
				System.out.println("2_" + key2 + " " + afterValue);

				// Find matches - if compared hashmaps have same number of keys, if D0 key values are the same 
				// then the two hashmaps represent the same graphML node
				if(beforeValue.keySet().size() == afterValue.keySet().size()) 
				{	
					if(beforeValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT).toString().equals(afterValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT).toString()) 
							&& beforeValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT).toString().equals(afterValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT).toString())) 
					{
						// If it is a source code, unit test or UML method, handle it separately
						if(beforeValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT).toString().equals(GraphMLProcessor.TYPE_METHOD)
								|| beforeValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT).toString().equals(GraphMLProcessor.TYPE_UML_OP)) 
						{
							if(beforeValue.get(GraphMLProcessor.PARAM_DATA_KEY_VALUE).toString().equals(afterValue.get(GraphMLProcessor.PARAM_DATA_KEY_VALUE).toString()))
									{
										
									}
						}
						System.out.println("--------" + beforeValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT).toString() + " MATCHES " 
								+ afterValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT).toString() + " at old key " + key + " and new key " + key2);
						// Identical graphML nodes, no change
						if (beforeValue.equals(afterValue)) 
						{
							visited.put(key, key2);
							System.out.println("--------Identical nodes, these have not been edited at old key " + key + " and new key " + key2);
							System.out.println("Added to Visited: old key:  " + key + " new key: " + key2);
							System.out.println("----------------");
							break;
						}
						// Identical graphML nodes, which have been edited
						else
						{
							System.out.println("--------Matching nodes, which have been edited at old key " + key + " and new key " + key2);
							System.out.println("----------------");
							visited.put(key, key2);
							System.out.println("Added to Visited: old key:  " + key + " new key: " + key2);
							// Identify entries by "old" key and value is "new" value
							edited.put(key, afterValue);
							break;
						}
					}
					// Although the number of keys in the two hashmaps are equal, the D0 values differ,
					// there is no way of telling if the two graphML nodes represent the same entity.
					// Hence renames are also regarded as additions.
					else 
					{
						System.out.println("--------Same number of keys but NO MATCH");
					}
				}
				// Number of keys in the two hashmaps differ, the entities are different
				else 
				{
					System.out.println("------Different number of keys and NO MATCH" + key);
				}
			}
		}
		System.out.println("Beforemap keyset: " + beforeMap.keySet());
		System.out.println("Aftermap keyset: " + afterMap.keySet());
		System.out.println("Visited keys: " + visited.keySet());

		// Output - edited, removed and added nodes
		removed = new HashSet<String>(beforeMap.keySet());
		removed.removeAll(visited.keySet());
		added = new HashSet<String>(afterMap.keySet());
		added.removeAll(visited.values());
	}

	/** Utility method - also creating a map representation of graphml files but unique ids values are kept for
	 * further processing
	 * @param graphmlProcessor
	 */
	private LinkedHashMap<String, LinkedHashMap<String, String>> createMapIncludingIds(GraphMLProcessor g)
	{
		LinkedHashMap<String, LinkedHashMap<String, String>> outerMap = 
				new LinkedHashMap<String, LinkedHashMap<String, String>>();
		LinkedHashMap<String, String> innerMap = null;
		NodeList nodes = g.returnNodeNodeList();

		for (int j = 0; j < nodes.getLength(); j++) 
		{
			innerMap = new LinkedHashMap<String, String>();
			// Value for the outer hashmap, an inner hashmap
			NodeList children = nodes.item(j).getChildNodes();
			for (int i = 0; i < children.getLength(); i++) 
			{	// Eliminate whitespace
				if(children.item(i).hasAttributes()) 
				{	// Add keys and values to an inner map
					innerMap.put(children.item(i).getAttributes().item(0).getNodeValue(), 
							children.item(i).getTextContent());
				}
			}
			outerMap.put("Key" + j, innerMap);
		}
		return outerMap;
	}
	
	/** Return list of ChangeData objects
	 * @param beforeMap - represents version 1 of the graphml file
	 * @param afterMap - represents version 2 of the graphml file
	 */
	public List<ChangeData> getChangeData()
			{
		List<ChangeData> changeDataList = new ArrayList<ChangeData>();
		List<HashMap<String, String>> removedItems = new ArrayList<HashMap<String, String>>();
		List<HashMap<String, String>> addedItems = new ArrayList<HashMap<String, String>>();
		// Reset beforeMap to contain the d8 values - by default d8 values are taken out
		setBeforeMap(createMapIncludingIds(getBeforeGraphProc()));
		
		for (String entry : removed) 
		{
			removedItems.add(beforeMap.get(entry));
		}

		for(int i = 0; i < removedItems.size(); i++) 
		{
			ChangeData changeData = new ChangeData();
			changeData.setUniqueId(removedItems.get(i).get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT));
			changeData.setCurrentName(removedItems.get(i).get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT));
			changeData.setPreviousName(changeData.getCurrentName());
			changeData.setTypeOfArtefact(getArtefactType());
			changeData.setSignatureChange(false);
			changeData.setFileLevelChange(FileLevelChangeType.EDIT);
			changeData.setTypeOfChange(ArtefactElementChangeType.DELETE);
			changeData.setTypeOfElement_current(getElementType(removedItems.get(i)));
			System.out.println("Element type in removed items: " + changeData.getTypeOfElement_current());
			changeDataList.add(changeData);
		}

		setUniqueIdValuesInEditedMap(beforeMap, edited);
		for(String keys : edited.keySet()) 
		{
			ChangeData changeData = new ChangeData();
			changeData.setUniqueId(edited.get(keys).get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT));
			changeData.setCurrentName(edited.get(keys).get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT));
			changeData.setPreviousName(changeData.getCurrentName());
			changeData.setTypeOfArtefact(getArtefactType());
			changeData.setFileLevelChange(FileLevelChangeType.EDIT);
			changeData.setTypeOfElement_current(getElementType(edited.get(keys)));
			changeData.setTypeOfChange(ArtefactElementChangeType.EDIT);
			MapDifference<String, String> mapDifference = Maps.difference(beforeMap.get(keys), edited.get(keys));
			Map<String, ValueDifference<String>> sameKeyDifferentValue = mapDifference.entriesDiffering();
			changeData.setEdits(sameKeyDifferentValue);
			if(changeData.getTypeOfArtefact().equals(ArtefactType.JAVA_SOURCE_CODE) 
					|| changeData.getTypeOfArtefact().equals(ArtefactType.UNIT_TEST)) 
			{
				changeData.setSignatureChange(isSignatureChange(beforeMap));
			}
			changeDataList.add(changeData);
		}

		// To return add change data, the new version of the graphml file has to be updated as all unique ids are empty
		// 1. Update non-added nodes with unique ids - assign ids to all matching nodes
		// 2. Create new ids for not matching nodes - new nodes 
		putUniqueIdsInAfterMap(beforeMap, getAfterMap());
		
		for (String entry : added) 
		{
			addedItems.add(afterMap.get(entry));
		}

		for(int i = 0; i < addedItems.size(); i++) 
		{
			ChangeData changeData = new ChangeData();
			changeData.setCurrentName(addedItems.get(i).get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT));
			changeData.setPreviousName(changeData.getCurrentName());
			changeData.setFileLevelChange(FileLevelChangeType.EDIT);
			changeData.setSignatureChange(false);
			changeData.setTypeOfArtefact(getArtefactType());
			changeData.setTypeOfChange(ArtefactElementChangeType.ADD);
			changeData.setTypeOfElement_current(getElementType(addedItems.get(i)));
			changeData.setUniqueId(addedItems.get(i).get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT));
			changeData.setAdded(addedItems.get(i));
			changeDataList.add(changeData);
			System.out.println(changeData.getAdded().get(0));
		}
		return changeDataList;
	}
	
	/** Update unique id values in latest graphml file
	 * 
	 * @param afterMap
	 */
	public void setIdValuesInAfterGraphML(LinkedHashMap<String, LinkedHashMap<String, String>> afterMap) 
	{
		IDGenerator gen = new IDGenerator(getAfterGraphProc());
		LinkedHashMap<String, String> afterValue = null;
		Iterator<String> iterator_ = afterMap.keySet().iterator();
		while (iterator_.hasNext()) 
		{
			String key_ = iterator_.next().toString();
			afterValue = afterMap.get(key_);
			String name = afterValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT);
			String type = afterValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT);
			String uniqueId = afterValue.get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT);
			
			// TODO: optionalParameterValue
			getAfterGraphProc().setUniqueIdBasedOnNameAndType(name, type, null, uniqueId);
		}
		// Generate new ids for newly added nodes with empty unique ids
		// Do this per empty unique - check, generate, save, then do the same for each 
		int numberOfEmptyIds = getAfterGraphProc().getNoOfNodesWithEmptyId();
	
		for(int i =0; i < numberOfEmptyIds; i++) 
		{
			String uniqueId = gen.generateSingleUniqueId();
			getAfterGraphProc().setEmptyUniqueIdToNewValue(uniqueId);
		}
		
		// re-generate node ids
		ArrayList<String> uniqueIds = getAfterGraphProc().getUniqueIds();
		getAfterGraphProc().updateNodeIds(uniqueIds);
		gen.generateParentChildIntraLink();
	}
	
	/** Return element type enum based on D6 hashmap key
	 * @param map - hashmap containing the values for the D6 key
	 */
	public ElementType getElementType(HashMap<String, String> map) 
	{	
		ElementType elType = null;
		String type = map.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT);

		if (type.equalsIgnoreCase(GraphMLProcessor.TYPE_CLASS)) 
		{
			elType = ElementType.Class;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_FIELD)) 
		{
			elType = ElementType.Field;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_METHOD)) 
		{
			elType = ElementType.Method;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_INTERFACE)) 
		{
			elType = ElementType.Interface;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_REQUIREMENT)) 
		{
			elType = ElementType.Functional;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_USE_CASE)) 
		{
			elType = ElementType.UseCase;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_USE_CASE_OBJECT_CLASS)) 
		{
			elType = ElementType.UseCase_Object_Class;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_COMPONENT)) 
		{
			elType = ElementType.Component;
		}
		else if(type.equalsIgnoreCase(GraphMLProcessor.TYPE_MODULE)) 
		{
			elType = ElementType.Module;
		}
		else if (type.equalsIgnoreCase(GraphMLProcessor.TYPE_UML_ATT)) 
		{
			elType = ElementType.UMLAttribute;
		}
		else if (type.equalsIgnoreCase(GraphMLProcessor.TYPE_UML_OP)) 
		{
			elType = ElementType.UMLOperation;
		}
		return elType;
	}

	/** Return artefact type based on the original graphML representation
	 * 
	 */
	public ArtefactType getArtefactType() 
	{
		ArtefactType artefactType = null;
		if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.SC_ID_PREFIX))
		{
			artefactType = ArtefactType.JAVA_SOURCE_CODE;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.REQ_ID_PREFIX))
		{
			artefactType = ArtefactType.REQUIREMENT_SPECIFICATION;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.DESIGN_ID_PREFIX))
		{
			artefactType = ArtefactType.UML_CLASS_DIAGRAM;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.UNIT_TEST_ID_PREFIX))
		{
			artefactType = ArtefactType.UNIT_TEST;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.ARCHITECTURE_ID_PREFIX))
		{
			artefactType = ArtefactType.ARCHITECTURE;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.USE_CASE_ID_PREFIX))
		{
			artefactType = ArtefactType.USE_CASE;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX))
		{
			artefactType = ArtefactType.SEQUENCE_DIAGRAM;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.DOCUMENTATION_ID_PREFIX))
		{
			artefactType = ArtefactType.DOCUMENTATION;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.API_ID_PREFIX))
		{
			artefactType = ArtefactType.API;
		}
		else if(afterGraphProc.getGraphNodeIdAttribute().equalsIgnoreCase(GraphMLProcessor.CONFIG_FILE_ID_PREFIX))
		{
			artefactType = ArtefactType.CONFIG_FILE;
		}
		return artefactType;
	}

	/** Utility method to check if change was a signature change (affecting any property values 
	 * other than d8, d6, d3 by comparing the edited nested hashmap with beforemap
	 * @param map - LinkedHashMap representation of graphs 
	 */
	public boolean isSignatureChange(LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap) 
	{
		boolean signatureChange = true;
		for(String keys : edited.keySet()) 
		{
			// Get differing values and their corresponding keys from inner hashmap
			MapDifference<String, String> mapDifference = Maps.difference(beforeMap.get(keys), edited.get(keys));
			Map<String, ValueDifference<String>> sameKeyDifferentValue = mapDifference.entriesDiffering();

			if(sameKeyDifferentValue.keySet().contains(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT) ||
					sameKeyDifferentValue.keySet().contains(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT) ||
					sameKeyDifferentValue.keySet().contains(GraphMLProcessor.CONTENT_DATA_KEY_VALUE_SHORT)) 
			{
				signatureChange = false;
			}
		}
		return signatureChange;
	}

	/** Utility method to set values of D8 keys to empty string
	 * @param map - LinkedHashMap representation of graphs 
	 */
	private void setUniqueIdValuesEmpty(LinkedHashMap<String, LinkedHashMap<String, String>> map) 
	{	
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) 
		{
			String key = iterator.next().toString();
			LinkedHashMap<String, String> value = map.get(key);
			value.put(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT, "");
		}
	}

	/** Set specific values of inner hashmap in nested hashmap from a nested hashmap input
	 * 
	 */
	public void setUniqueIdValuesInEditedMap(LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap, LinkedHashMap<String, 
			LinkedHashMap<String, String>> editedMap)
	{
		LinkedHashMap<String, String> beforeValue = null;
		LinkedHashMap<String, String> editedValue = null;

		Iterator<String> iterator = beforeMap.keySet().iterator();
		while (iterator.hasNext()) 
		{
			String key = iterator.next().toString();
			beforeValue = beforeMap.get(key);

			Iterator<String> iterator2 = editedMap.keySet().iterator();
			while (iterator2.hasNext()) 
			{
				String key2 = iterator2.next().toString();
				editedValue = editedMap.get(key2);

				if(key.equals(key2)) 
				{
					editedValue.put(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT, beforeValue.get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT).toString());
				}	
			}
		}
	}

	/** Update afterMap to contain unique ids
	 * 
	 */
	public void putUniqueIdsInAfterMap(LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap, LinkedHashMap<String, 
			LinkedHashMap<String, String>> afterMap)
	{
		LinkedHashMap<String, String> beforeValue = null;
		LinkedHashMap<String, String> afterValue = null;
		String visitedValue = null;
		String uniqueId = null;

		Iterator<String> iterator_ = visited.keySet().iterator();
		while (iterator_.hasNext()) 
		{
			String key_ = iterator_.next().toString();
			visitedValue = visited.get(key_);

			Iterator<String> iterator = beforeMap.keySet().iterator();
			while (iterator.hasNext()) 
			{
				String key = iterator.next().toString();
				beforeValue = beforeMap.get(key);

				if(key_.equals(key))
				{
					uniqueId = beforeValue.get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT).toString();
				}

				Iterator<String> iterator2 = afterMap.keySet().iterator();
				while (iterator2.hasNext()) 
				{
					String key2 = iterator2.next().toString();
					afterValue = afterMap.get(key2);

					if(key2.equals(visitedValue)) 
					{
						afterValue.put(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT, uniqueId);
					}	
				}
			}
		}
	}
}