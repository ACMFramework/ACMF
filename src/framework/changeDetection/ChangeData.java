package framework.changeDetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.common.collect.MapDifference.ValueDifference;

/**
 * Class representing artefact changes 
 *
 */
public class ChangeData 
{
	/**
	 * 
	 */
	private ArtefactElementChangeType typeOfChange;
	
	/**
	 * 
	 */
	private FileLevelChangeType fileLevelChange;
	
	/**
	 * 
	 */
	private ArtefactType typeOfArtefact;
	
	/**
	 * 
	 */
	private ElementType typeOfElement_previous;
	
	/**
	 * 
	 */
	private ElementType typeOfElement_current;
	
	/**
	 * 
	 */
	private Boolean signatureChange;
	
	/**
	 * 
	 */
	private String currentName;
	
	/**
	 * 
	 */
	private String previousName;
	
	/**
	 * 
	 */
	private String uniqueId;
	
	/**
	 * 
	 */
	private List<Map<String, ValueDifference<String>>> edits;
	
	/**
	 * 
	 */
	private List<HashMap<String, String>> added;

	/**
	 * 
	 */
	public ChangeData() 
	{
	}

	/**
	 * @param previousName - the name of the element in the previous version 
	 * @param previousType - the type of the element in the previous version 
	 * @param currentName - the name of the element in the current (updated) version 
	 * @param currentType - the type of the element in the current (updated) version 
	 * @param typeOfChange - change type (either add, delete or edit)
	 * @param edits - a list of key-value pairs of edited properties (property name = key, property value = value)
	 */
	public ChangeData(String previousName, ElementType previousType, String currentName, 
			ElementType currentType, Boolean signatureChange, ArtefactType artefactType, ArtefactElementChangeType typeOfChange, 
			List<Map<String, ValueDifference<String>>> edits, String id, FileLevelChangeType fileChange) 
	{
		this.previousName = previousName;
		this.typeOfElement_previous = previousType;
		this.currentName = currentName;
		this.typeOfElement_current = currentType;
		this.signatureChange = signatureChange;
		this.typeOfArtefact = artefactType;
		this.typeOfChange = typeOfChange;
		this.edits = edits;
		this.uniqueId = id;
		this.fileLevelChange = fileChange;
	}

	/**
	 * @param previousName - the name of the element in the previous version 
	 * @param previousType - the type of the element in the previous version 
	 * @param currentName - the name of the element in the current (updated) version 
	 * @param currentType - the type of the element in the current (updated) version 
	 * @param typeOfChange - change type (either add, delete or edit)
	 */
	public ChangeData(String previousName, ElementType previousType, String currentName, 
			ElementType currentType, Boolean signatureChange, ArtefactType artefactType, 
			ArtefactElementChangeType typeOfChange) 
	{
		this.previousName = previousName;
		this.typeOfElement_previous = previousType;
		this.currentName = currentName;
		this.typeOfElement_current = currentType;
		this.signatureChange = signatureChange;
		this.typeOfArtefact = artefactType;
		this.typeOfChange = typeOfChange;
	}

	/**
	 * @param previousName - the name of the element in the previous version 
	 * @param previousType - the type of the element in the previous version 
	 * @param currentName - the name of the element in the current (updated) version 
	 * @param currentType - the type of the element in the current (updated) version 
	 * @param typeOfChange - change type (either add, delete or edit)
	 */
	public ChangeData(String previousName, ElementType previousType, String currentName, 
			ElementType currentType, ArtefactType artefactType, ArtefactElementChangeType typeOfChange) 
	{
		this.previousName = previousName;
		this.typeOfElement_previous = previousType;
		this.currentName = currentName;
		this.typeOfElement_current = currentType;
		this.typeOfArtefact = artefactType;
		this.typeOfChange = typeOfChange;
	}

	/**
	 * 
	 * @param currentName
	 * @param currentType
	 * @param type
	 * @param signatureChange
	 * @param typeOfChange
	 * @param id
	 */
	public ChangeData(String currentName, ElementType type, Boolean signatureChange, 
			ArtefactElementChangeType typeOfChange, String id) 
	{
		this.currentName = currentName;
		this.typeOfElement_current = type;
		this.signatureChange = signatureChange;
		this.typeOfChange = typeOfChange;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @param currentName
	 * @param previousName
	 * @param type
	 * @param typeOfChange
	 * @param id
	 * @param edits
	 * @param artefactType
	 */
	public ChangeData(String currentName, String previousName, ElementType type, ArtefactElementChangeType typeOfChange, 
			String id, List<Map<String, ValueDifference<String>>> edits, ArtefactType artefactType) 
	{
		this.uniqueId = id;
		this.currentName = currentName;
		this.previousName = previousName;
		this.typeOfArtefact = artefactType;
		this.typeOfChange = typeOfChange;
		this.edits = edits;
	}
	
	/**
	 * 
	 * @param currentName
	 * @param previousName
	 * @param type
	 * @param typeOfChange
	 * @param id
	 * @param artefactType
	 */
	public ChangeData(String currentName, String previousName, ElementType type, ArtefactElementChangeType typeOfChange, 
			String id, ArtefactType artefactType) 
	{
		this.uniqueId = id;
		this.currentName = currentName;
		this.previousName = previousName;
		this.typeOfArtefact = artefactType;
		this.typeOfChange = typeOfChange;
	}

	/**
	 * 
	 * @param type
	 * @param typeOfChange
	 * @param id
	 */
	public ChangeData(ElementType type, ArtefactElementChangeType typeOfChange, String id) 
	{
		this.uniqueId = id;
		this.typeOfElement_current = type;
		this.typeOfChange = typeOfChange;
	}
	
	/**
	 * 
	 * @param element
	 * @param artefactType
	 * @param change
	 * @param id
	 */
	public ChangeData(ElementType element, ArtefactType artefactType,
			ArtefactElementChangeType change, String id) 
	{
		this.uniqueId = id;
		this.typeOfElement_current = element;
		this.typeOfChange = change;
		this.typeOfArtefact = artefactType;
	}
	
	/**
	 * 
	 * @param signatureChange
	 * @param typeOfChange
	 * @param id
	 */
	public ChangeData(Boolean signatureChange, ArtefactElementChangeType typeOfChange, String id) 
	{
		this.signatureChange = signatureChange;
		this.typeOfChange = typeOfChange;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @param element
	 * @param artefact
	 * @param change
	 * @param id
	 */
	public ChangeData(ElementType element, ArtefactType artefact,
			FileLevelChangeType change, String id) 
	{
		this.typeOfElement_current = element;
		this.typeOfArtefact = artefact;
		this.fileLevelChange = change;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @param element
	 * @param artefact
	 * @param fileChange
	 * @param artefactChange
	 * @param id
	 */
	public ChangeData(ElementType element, ArtefactType artefact,
			FileLevelChangeType fileChange, ArtefactElementChangeType artefactChange,
			String id) 
	{
		this.typeOfElement_current = element;
		this.typeOfArtefact = artefact;
		this.fileLevelChange = fileChange;
		this.typeOfChange = artefactChange;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @param signatureChange
	 * @param element
	 * @param artefact
	 * @param fileLevelChange
	 * @param artefactLevelChange
	 * @param id
	 */
	public ChangeData(boolean signatureChange, ElementType element,
			ArtefactType artefact, FileLevelChangeType fileLevelChange,
			ArtefactElementChangeType artefactLevelChange, String id) 
	{
		this.signatureChange = signatureChange;
		this.typeOfElement_current = element;
		this.typeOfArtefact = artefact;
		this.fileLevelChange = fileLevelChange;
		this.typeOfChange = artefactLevelChange;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @param name
	 * @param signatureChange
	 * @param element
	 * @param artefact
	 * @param fileLevelChange
	 * @param artefactLevelChange
	 * @param id
	 */
	public ChangeData(String name, boolean signatureChange, ElementType element,
			ArtefactType artefact, FileLevelChangeType fileLevelChange,
			ArtefactElementChangeType artefactLevelChange, String id) 
	{
		this.currentName = name;
		this.signatureChange = signatureChange;
		this.typeOfElement_current = element;
		this.typeOfArtefact = artefact;
		this.fileLevelChange = fileLevelChange;
		this.typeOfChange = artefactLevelChange;
		this.uniqueId = id;
	}

	/**
	 * 
	 * @return
	 */
	public ArtefactElementChangeType getTypeOfChange() 
	{
		return typeOfChange;
	}

	/**
	 * 
	 * @param typeOfChange
	 */
	public void setTypeOfChange(ArtefactElementChangeType typeOfChange) 
	{
		this.typeOfChange = typeOfChange;
	}
	
	/**
	 * 
	 * @return
	 */
	public FileLevelChangeType getTypeOfFileLevelChange() 
	{
		return fileLevelChange;
	}

	/**
	 * 
	 * @param fileLevelChange
	 */
	public void setFileLevelChange(FileLevelChangeType fileLevelChange) 
	{
		this.fileLevelChange = fileLevelChange;
	}

	/**
	 * 
	 * @return
	 */
	public String getCurrentName() 
	{
		return currentName;
	}

	/**
	 * 
	 * @param currentName
	 */
	public void setCurrentName(String currentName) 
	{
		this.currentName = currentName;
	}

	/**
	 * 
	 * @return
	 */
	public String getPreviousName() 
	{
		return previousName;
	}

	/**
	 * 
	 * @param previousName
	 */
	public void setPreviousName(String previousName) 
	{
		this.previousName = previousName;
	}

	/**
	 * 
	 * @return
	 */
	public ElementType getTypeOfElement_previous() 
	{
		return typeOfElement_previous;
	}

	/**
	 * 
	 * @param typeOfElement_previous
	 */
	public void setTypeOfElement_previous(ElementType typeOfElement_previous) 
	{
		this.typeOfElement_previous = typeOfElement_previous;
	}

	/**
	 * 
	 * @return
	 */
	public ElementType getTypeOfElement_current() 
	{
		return typeOfElement_current;
	}

	/**
	 * 
	 * @param typeOfElement_current
	 */
	public void setTypeOfElement_current(ElementType typeOfElement_current) 
	{
		this.typeOfElement_current = typeOfElement_current;
	}

	/**
	 * 
	 * @return
	 */
	public Boolean getSignatureChange() 
	{
		return signatureChange;
	}

	/**
	 * 
	 * @param signatureChange
	 */
	public void setSignatureChange(Boolean signatureChange) 
	{
		this.signatureChange = signatureChange;
	}

	/**
	 * 
	 * @return
	 */
	public ArtefactType getTypeOfArtefact() 
	{
		return typeOfArtefact;
	}

	/**
	 * 
	 * @param typeOfArtefact
	 */
	public void setTypeOfArtefact(ArtefactType typeOfArtefact) 
	{
		this.typeOfArtefact = typeOfArtefact;
	}

	/**
	 * 
	 * @return
	 */
	public String getUniqueId() 
	{
		return uniqueId;
	}

	/**
	 * 
	 * @param uniqueId
	 */
	public void setUniqueId(String uniqueId) 
	{
		this.uniqueId = uniqueId;
	}

	/**
	 * 
	 * @return
	 */
	public List<Map<String, ValueDifference<String>>> getEdits() 
	{
		return edits;
	}

	/**
	 * 
	 * @param edit
	 */
	public void setEdits(Map<String, ValueDifference<String>> edit) 
	{
		edits = new ArrayList<Map<String, ValueDifference<String>>>();
		edits.add(edit);
	}
	
	/**
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> getAdded() 
	{
		return added;
	}

	/**
	 * 
	 * @param newItem
	 */
	public void setAdded(HashMap<String, String> newItem) 
	{
		added = new ArrayList<HashMap<String, String>>();
		added.add(newItem);
	}

	/**
	 *Overriden toString() method to supply required details
	 */
	public String toString() 
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Changed element current name:" + getCurrentName());
		sb.append(", ");
		sb.append("Changed element type:" + getTypeOfElement_current());
		sb.append(", ");
		sb.append("Artefact type:" + getTypeOfArtefact());
		sb.append(", ");
		sb.append("Change type:" + getTypeOfChange());
		sb.append(", ");
		sb.append("Signature change:" + getSignatureChange());
		if(getUniqueId() != null) 
		{
			sb.append(", ");
			sb.append("Unique id:" + getUniqueId());
		}
		if(getEdits() != null) 
		{
			sb.append(", ");
			sb.append("Edit:" + getEdits().get(0).toString());
		}
		if(getAdded() != null) 
		{
			sb.append(", ");
			sb.append("Addition:" + getAdded().get(0).toString());
		}
		// nullpointer exception if list is empty
		return sb.toString();
	}
}
