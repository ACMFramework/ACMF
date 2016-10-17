package framework.DAL.Transformation;

import java.util.List;
import framework.general.IExecutionManager;
import framework.general.PropertyReader;

/**
 * 
 *
 */
public class UMLClassExtractor extends Extractor
{

	/**
	 * 
	 */
	private PropertyReader prop = new PropertyReader();
	
	/** Framework root folder
	 * 
	 */
	private final String ROOT_FOLDER = prop.getProperties()[0];
	
	/** Framework UML class folder - is called UMLClass by convention
	 * 
	 */
	private final String UMLCLASS_FOLDER = "UMLClass";
	
	/** No .dia files found error message
	 * 
	 */
	private final String NO_FILES = "No .dia files found in local repository";
	
	/** File does not exist message
	 * 
	 */
	private final String FILE_DOES_NOT_EXIST = "File does not exist";
	
	/** Outputs of extraction - list of .xml files
	 * 
	 * @return
	 */
	public List<String> getOutputs() 
	{
		return super.getOutputs();
	}

	/** Set the outputs of extraction
	 * 
	 * @param outputs
	 */
	public void setOutputs(List<String> outputs) 
	{
		super.setOutputs(outputs);
	}
	
	/**
	 * 
	 * @return
	 */
	public String getRepoFolder() 
	{
		return prop.getProperties()[12];
	}
	
	/**
	 * 
	 * @param manager
	 */
	public UMLClassExtractor(IExecutionManager manager) 
	{
		super(manager);
	}

	@Override
	public void Execute() 
	{
	
	}

	@Override
	public void ExtractSpecificFiles(List<String> files) 
	{
		
	}
}
