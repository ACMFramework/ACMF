package framework.changeDetection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import framework.general.IExecutable;
import framework.general.IExecutionManager;
import framework.utilities.FileUtilities;

/** Executable class to carry out change identification
 * 
 */
public class ChangeIdentifier implements IExecutable
{
	/** GraphComparer
	 * 
	 */
	GraphComparer comparer;
	
	/** Return GraphComparer
	 * 
	 * @return
	 */
	public GraphComparer getComparer() 
	{
		return comparer;
	}

	/** The paths of the previous and current version of the graphml file
	 * 
	 */
	private HashMap<String, String> inputPaths = new HashMap<String, String>();
	
	/** The output of change identification - a list of ChangeData objects
	 * 
	 */
	private List<ChangeData> output_changes = new ArrayList<ChangeData>();

	/** Get output of change identification
	 * 
	 * @return
	 */
	public List<ChangeData> getOutput_changes() 
	{
		return output_changes;
	}

	/**
	 * 
	 * @param output_changes
	 */
	public void setOutput_changes(List<ChangeData> output_changes) 
	{
		this.output_changes.addAll(output_changes);
	}

	/**
	 * 
	 * @param manager
	 */
	public ChangeIdentifier(IExecutionManager manager) 
	{
		manager.register(this);
	}

	/**
	 * 
	 * @return
	 */
	public HashMap<String, String> getPaths() 
	{
		return inputPaths;
	}

	/** Set paths for change identification
	 *
	 */
	public void setPaths(List<String> currentPaths) 
	{
		for(int i = 0; i < currentPaths.size(); i ++) 
		{
			this.inputPaths.put(currentPaths.get(i), currentPaths.get(i) + FileUtilities.SUFFIX + FileUtilities.GRAPHML_EXTENSION);
		}
	}
	
	/** Execute change identification 
	 * Output: list of change data objects
	 * 
	 */
	@Override
	public void Execute() 
	{
		Iterator it = getPaths().entrySet().iterator();
	    while (it.hasNext()) 
	    {
	        Map.Entry pair = (Map.Entry)it.next();
	        comparer = new GraphComparer(pair.getValue().toString(), pair.getKey().toString());
	        comparer.setBeforeMap(comparer.createMapRepresentation(comparer.getBeforeGraphProc()));
	        comparer.setAfterMap(comparer.createMapRepresentation(comparer.getAfterGraphProc()));
	        comparer.compareMaps(comparer.getBeforeMap(), comparer.getAfterMap());
			setOutput_changes(comparer.getChangeData());
	    }
	}	
}
