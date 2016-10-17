package framework.DAL.Transformation;

import java.util.List;
import framework.general.IExecutable;
import framework.general.IExecutionManager;

/** Executable class responsible for extractions
 * 
 *
 */
public abstract class Extractor implements IExecutable
{	
	/**
	 * 
	 */
	private List<String> outputs;
	
	/** Outputs of extraction - list of .xml files
	 * 
	 * @return
	 */
	public List<String> getOutputs() 
	{
		return outputs;
	}

	/** Set the outputs of extraction
	 * 
	 * @param outputs
	 */
	public void setOutputs(List<String> outputs) 
	{
		this.outputs = outputs;
	}
	
	/** Return error message
	 * 
	 * @param message
	 */
	public String returnErrorMessage(String message) 
	{
		return message;
	}
	
	/**
	 * 
	 * @param manager
	 */
	public Extractor(IExecutionManager manager) 
	{
		manager.register(this);
	}
	
	/** Execute extraction
	 * 
	 */
	@Override
	public abstract void Execute();
	
	/** Extract specific files
	 * 
	 */
	public abstract void ExtractSpecificFiles(List<String> files);
}
