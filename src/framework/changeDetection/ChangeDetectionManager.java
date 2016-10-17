package framework.changeDetection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import framework.DAL.GraphML.GraphMLProcessor;
import framework.DAL.Transformation.Extractor;
import framework.DAL.Transformation.IDGenerator;
import framework.DAL.Transformation.JavaExtractor;
import framework.DAL.Transformation.Transformer;
import framework.DAL.graphmlInput.GraphMLImporter;
import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.ArtefactType;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ChangeExtractor;
import framework.changeDetection.ChangeIdentifier;
import framework.changeDetection.FileLevelChangeType;
import framework.changeDetection.GraphDbUpdater;
import framework.general.PropertyReader;
import framework.general.SequentialExecutionManager;
import framework.utilities.FileUtilities;

/** Manage the change detection process
 * 
 * input: change in repository - user initiates change extraction
 * output: list of deleted ChangeData, list of edited ChangeData, list of added ChangeData 
 * + updated GraphML files in framework folder and updated graph database
 *
 */
public class ChangeDetectionManager 
{
	/**
	 * 
	 */
	PropertyReader prop = new PropertyReader();

	/**
	 * 
	 */
	private SequentialExecutionManager manager = new SequentialExecutionManager();
	/**
	 * 
	 */
	private ChangeExtractor changeExtractor = new ChangeExtractor(manager);
	/**
	 * 
	 */
	private ChangeIdentifier identifier = new ChangeIdentifier(manager);
	/**
	 * 
	 */
	private GraphDbUpdater dbUpdater;

	/**
	 * 
	 */
	private Transformer transformer;

	/**
	 * 
	 */
	private Extractor ext = new JavaExtractor(manager);

	/**
	 * 
	 */
	private String DB_PATH = prop.getProperties()[1];

	/**
	 * 
	 */
	GraphMLImporter importer;

	/** The output of change detection
	 * 
	 */
	List<ChangeData> changeDetectionOutput = new ArrayList<ChangeData>();

	/** Return the output of change detection
	 * 
	 * @return
	 */
	public List<ChangeData> getChangeDetectionOutput() 
	{
		return changeDetectionOutput;
	}

	/**
	 * 
	 * @param changeDetectionOutput
	 */
	public void setChangeDetectionOutput(List<ChangeData> changeDetectionOutput) 
	{
		this.changeDetectionOutput.addAll(changeDetectionOutput);
	}

	/** Execute change extraction before change detection takes place
	 * 
	 */
	public ChangeDetectionManager() 
	{
		changeExtractor.Execute();
	}

	/** Manage the change detection output and return a list of ChangeData objects
	 * 
	 * @return
	 */
	public List<ChangeData> manageChangeDetection() 
	{
		// The output of deletes, adds and edits
		List<ChangeData> deleteChanges = new ArrayList<ChangeData>();
		List<ChangeData> addChanges = new ArrayList<ChangeData>();
		List<ChangeData> editChanges = new ArrayList<ChangeData>();

		// If there are deleted items
		if(!changeExtractor.getDeleted().isEmpty()) 
		{
			deleteChanges = doDeleteChangeDetection();
			setChangeDetectionOutput(deleteChanges);
		}
		// If there are added items
		if(!changeExtractor.getAdded().isEmpty()) 
		{
			addChanges = doAddChangeDetection();
			setChangeDetectionOutput(addChanges);
		}
		//If there are edited items
		if(!changeExtractor.getEdited().isEmpty()) 
		{
			editChanges = doEditChangeDetection();
			setChangeDetectionOutput(editChanges);
		}
		return getChangeDetectionOutput();
	}

	/** Perform change detection when there are deleted items
	 * 
	 * @return a list of ChangeData objects
	 */
	public List<ChangeData> doDeleteChangeDetection() 
	{
		List<String> uniqueIds = new ArrayList<String>();
		dbUpdater = new GraphDbUpdater(manager);
		for(int i = 0; i < changeExtractor.getDeleted().size(); i++) 
		{
			System.out.println(changeExtractor.getDeleted().get(i));
			// Find graphml files in framework folder with the same name as the deleted files
			// TODO: currently this assumes that the original files are source code artefacts
			// Extend it to non-source code artefacts
			String filePath = prop.getProperties()[0] + "SourceCode\\" +
					FileUtilities.getFileNameWithoutExtension(changeExtractor.getDeleted().get(i)) + FileUtilities.GRAPHML_EXTENSION;
			System.out.println("PATH:"  + filePath);
			GraphMLProcessor processor = new GraphMLProcessor(filePath);
			uniqueIds = processor.getUniqueIds();
			dbUpdater.labelToBeRemovedNodes(uniqueIds);
			FileUtilities.deleteFile(filePath);
			FileUtilities.deleteFile(FileUtilities.getPathWithoutExtension(filePath)+FileUtilities.XML_EXTENSION);
		}
		dbUpdater.getGraphStore().closeDb();
		return generateDeleteChangeData(uniqueIds);
	}

	/** Perform change detection for added items
	 * 
	 * @return list of ChangeData objects
	 */
	public List<ChangeData> doAddChangeDetection() 
	{
		List<String> uniqueIds = new ArrayList<String>();
		// Initiate extraction of specific added files
		ext.ExtractSpecificFiles(changeExtractor.getAdded());

		// Supply list of files extracted to the transformer and create graphml files
		List<String> extractionOutput = new ArrayList<String>();
		extractionOutput = ext.getOutputs();
		transformer = new Transformer(manager);
		transformer.transformSpecificFiles(extractionOutput, ArtefactType.JAVA_SOURCE_CODE);

		// Get list of added graphml files
		List<String> newGraphMLFiles = transformer.getOutputPaths();
		for(int i = 0; i < newGraphMLFiles.size(); i++) 
		{
			GraphMLProcessor processor = new GraphMLProcessor(newGraphMLFiles.get(i));
			uniqueIds.addAll(processor.getUniqueIds());
		}
		
		// Import them to the database
		importer = new GraphMLImporter(DB_PATH);
		importer.setInputFiles(newGraphMLFiles);
		importer.Execute();

		// Establish inter connections where applicable - based on trace maintenance rules
		// This is applicable to Java, JUnit and UML class diagram artefacts
		dbUpdater = new GraphDbUpdater(manager);
		for(int i = 0; i < newGraphMLFiles.size(); i++) 
		{
			GraphMLProcessor processor = new GraphMLProcessor(newGraphMLFiles.get(i));
			String pair = processor.getNodeNameAndImpExt(processor.getNodeBasedOnExtendsImplements());
			if(pair!=null) 
			{
				String[] parameters = pair.split("/");
				String nameProperty = parameters[0];
				String extendsProperty = parameters[1]; 
				if(!extendsProperty.isEmpty()) 
				{
					dbUpdater.createInterRelBasedOnName(nameProperty, extendsProperty);
				}
			}
		}
		dbUpdater.getGraphStore().closeDb();
		// Generate change data
		return generateAddChangeData(uniqueIds);
	}

	/** Perform change detection for edited items
	 * 1. Extract edited items
	 * 2. Transform edited items but keep previous version
	 * 3. Identify artefact element level changes (edit, add, delete) and return change data
	 * 4. For edit artefact element level changes, update the new version of the graphml file
	 * 5. Update database by adding new nodes, editing or deleting existing nodes 
	 * 
	 * @return list of ChangeData objects
	 */
	public List<ChangeData> doEditChangeDetection() 
	{
		// Identify artefact element level changes - which changes are edits, adds, deletes
		List<ChangeData> edits = new ArrayList<ChangeData>();
		List<ChangeData> adds = new ArrayList<ChangeData>();
		List<ChangeData> deletes = new ArrayList<ChangeData>();
		List<String> idsToDelete = new ArrayList<String>();

		// re-extract edited java file to produce a new .java.xml file
		List<String> extractionOutput = new ArrayList<String>();
		ext.ExtractSpecificFiles(changeExtractor.getEdited());
		extractionOutput = ext.getOutputs();
		// keep current graphml but rename it currentname_previous + re-transform
		transformer = new Transformer(manager);
		transformer.reGenerate(extractionOutput, ArtefactType.JAVA_SOURCE_CODE);
		// do change identification and return changes
		identifier.setPaths(transformer.getOutputPaths());
		identifier.Execute();

		List<ChangeData> changes = identifier.getOutput_changes();
		for(int i = 0; i < changes.size(); i++) 
		{
			if(changes.get(i).getTypeOfChange() == ArtefactElementChangeType.EDIT) 
			{
				edits.add(changes.get(i));
			}
			if(changes.get(i).getTypeOfChange() == ArtefactElementChangeType.ADD) 
			{
				adds.add(changes.get(i));
			}
			if(changes.get(i).getTypeOfChange() == ArtefactElementChangeType.DELETE) 
			{
				deletes.add(changes.get(i));
				idsToDelete.add(changes.get(i).getUniqueId());
			}
		}

		// Update database and graphml representation - Trace maintenance
		dbUpdater = new GraphDbUpdater(manager);
		if(!adds.isEmpty()) 
		{
			// Update current graphml file with unique ids, intra links and node ids, generate unique id for new nodes
			setIdValuesInAfterGraphML(identifier.getComparer().getAfterMap());
			//TODO: cater for constructors - add parameters check
			// Update unique id in changeData with generated ids from the graphml
			for(int i = 0; i < adds.size(); i++) 
			{
				if(adds.get(i).getUniqueId().toString().isEmpty()) 
				{
					// Get node based on name and type and pass in result to retrive unique id of node
					String id = identifier.getComparer().getAfterGraphProc().getUniqueIdOfNode(identifier.getComparer().
							getAfterGraphProc().findNodeBasedOnNameAndType(adds.get(i).getCurrentName(), 
									adds.get(i).getTypeOfElement_current().toString()));
					adds.get(i).setUniqueId(id);
					adds.get(i).getAdded().get(0).put(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT, id);
				}
			}
			dbUpdater.addNewNodes(adds);

			// Connect new node in the database to parent element based on unique id
			String parentId = identifier.getComparer().getAfterGraphProc()
					.getUniqueIdOfNode(identifier.getComparer().getAfterGraphProc().getFirstNodeInFile());
			dbUpdater.createRelationshipExistingNodes(adds, parentId);
			
		}
		if(!edits.isEmpty()) 
		{
			// Update current graphml file with unique ids, intra links and node ids, generate unique id for new nodes
			setIdValuesInAfterGraphML(identifier.getComparer().getAfterMap());
			dbUpdater.updateMultipleNodesProperties(edits);

		}
		if(!deletes.isEmpty()) 
		{
			setIdValuesInAfterGraphML(identifier.getComparer().getAfterMap());
			dbUpdater.labelToBeRemovedNodes(idsToDelete);
		}
		dbUpdater.getGraphStore().closeDb();
		return changes;
	}

	/** Update unique id values in latest graphml file
	 * 
	 * @param afterMap
	 */
	public void setIdValuesInAfterGraphML(LinkedHashMap<String, LinkedHashMap<String, String>> afterMap) 
	{
		IDGenerator gen = new IDGenerator(identifier.getComparer().getAfterGraphProc());
		LinkedHashMap<String, String> afterValue = null;
		Iterator<String> iterator_ = afterMap.keySet().iterator();
		while (iterator_.hasNext()) 
		{
			String key_ = iterator_.next().toString();
			afterValue = afterMap.get(key_);
			String name = afterValue.get(GraphMLProcessor.NAME_DATA_KEY_VALUE_SHORT);
			String type = afterValue.get(GraphMLProcessor.TYPE_DATA_KEY_VALUE_SHORT);
			String uniqueId = afterValue.get(GraphMLProcessor.ID_DATA_KEY_VALUE_SHORT);
			String parameter = afterValue.get(GraphMLProcessor.PARAM_DATA_KEY_VALUE_SHORT);

			// TODO: optionalParameterValue is not yet implemented in GraphMLProcessor: setUniqueIdOfSpecificNode method, null is passed in instead of param value
			identifier.getComparer().getAfterGraphProc().setUniqueIdBasedOnNameAndType(name, type, null, uniqueId);
		}
		// Generate new ids for newly added nodes with empty unique ids
		// Do this per empty unique - check, generate, save, then do the same for each 
		int numberOfEmptyIds = identifier.getComparer().getAfterGraphProc().getNoOfNodesWithEmptyId();

		for(int i =0; i < numberOfEmptyIds; i++) 
		{
			String uniqueId = gen.generateSingleUniqueId();
			identifier.getComparer().getAfterGraphProc().setEmptyUniqueIdToNewValue(uniqueId);
		}

		// re-generate node ids
		ArrayList<String> uniqueIds = identifier.getComparer().getAfterGraphProc().getUniqueIds();
		identifier.getComparer().getAfterGraphProc().updateNodeIds(uniqueIds);
		gen.generateParentChildIntraLink();
	}

	/** Generate a list of ChangeData objects after deletes
	 * 
	 * @param uniqueId
	 * @return
	 */
	public List<ChangeData> generateDeleteChangeData(List<String> uniqueIds)
	{
		List<ChangeData> changeDataList = new ArrayList<ChangeData>();

		for(int i = 0; i < uniqueIds.size(); i++) 
		{
			ChangeData changeData = new ChangeData();
			changeData.setSignatureChange(false); 
			changeData.setTypeOfChange(ArtefactElementChangeType.DELETE);
			changeData.setFileLevelChange(FileLevelChangeType.DELETE);
			changeData.setUniqueId(uniqueIds.get(i));
			changeDataList.add(changeData);
			System.out.println(changeData.toString());
		}
		return changeDataList;
	}

	/** Generate a list of ChangeData objects after add changes
	 * 
	 * @param uniqueId
	 * @return
	 */
	public List<ChangeData> generateAddChangeData(List<String> uniqueIds)
	{
		List<ChangeData> changeDataList = new ArrayList<ChangeData>();

		for(int i = 0; i < uniqueIds.size(); i++) 
		{
			ChangeData changeData = new ChangeData();
			changeData.setSignatureChange(false); 
			changeData.setTypeOfChange(ArtefactElementChangeType.ADD);
			changeData.setFileLevelChange(FileLevelChangeType.ADD);
			changeData.setUniqueId(uniqueIds.get(i));
			changeDataList.add(changeData);
			System.out.println(changeData.toString());
		}
		return changeDataList;
	}
}
