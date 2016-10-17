package framework.DAL.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import framework.general.IExecutionManager;
import framework.general.PropertyReader;
import framework.general.ShellCommandExecutor;
import framework.utilities.FileUtilities;

/** Java source code extraction
 * 
 *
 */
public class JavaExtractor extends Extractor
{
	/**
	 * 
	 */
	private PropertyReader prop = new PropertyReader();
	
	/** Src2srcml command
	 * 
	 */
	private static final String SRC2SRCML_COMMAND = "src2srcml --language=Java ";
	
	/** Framework root folder
	 * 
	 */
	private final String ROOT_FOLDER = prop.getProperties()[0];
	
	/** Framework source code folder - is called SourceCode by convention
	 * 
	 */
	private final String SC_FOLDER = "SourceCode";
	
	/** No .java files found error message
	 * 
	 */
	private final String NO_FILES = "No .java files found in local repository";
	
	/** No .java files found error message
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
	public JavaExtractor(IExecutionManager manager) 
	{
		super(manager);
	}
	
	/** Execute extraction using the src2srcml tool
	 * Example command: src2srcml --language=Java main.java -o main.java.xml
	 * 
	 */
	@Override
	public void Execute() 
	{
		List<String> outputs = new ArrayList<String>();
		List<String> javaFiles = FileUtilities.getJavaFilesFromFolder(getRepoFolder());
		List<String> umlClassFiles = FileUtilities.getDiaXmlFilesFromFolder(getRepoFolder());
		
		if(javaFiles.size()==0) 
		{
			System.out.println(returnErrorMessage(NO_FILES));
			setOutputs(null);
		}
		else 
		{
			for(int i = 0; i < javaFiles.size(); i++) 
			{
				try 
				{
					ShellCommandExecutor.executeShellCommand
					("cmd /c cd " + ROOT_FOLDER + SC_FOLDER + " && " + SRC2SRCML_COMMAND + getRepoFolder() + "\\" + javaFiles.get(i) +" -o "+ javaFiles.get(i) + FileUtilities.SIMPLE_XML_EXTENSION);
					System.out.println("Successfully extracted file " + javaFiles.get(i));
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				outputs.add(ROOT_FOLDER + SC_FOLDER + FileUtilities.getFileNameWithoutExtension(javaFiles.get(i)) + FileUtilities.XML_EXTENSION);
			}
			setOutputs(outputs);
		}
	}
	
	/** Extract specific files using the src2srcml tool
	 * Example command: src2srcml --language=Java main.java -o main.java.xml
	 * List items should provide full path of file
	 */
	@Override
	public void ExtractSpecificFiles(List<String> files) 
	{
		List<String> outputs = new ArrayList<String>();
		for(int i = 0; i < files.size(); i++) 
		{
			if(FileUtilities.fileExists(files.get(i))) 
			{
				try 
				{
					ShellCommandExecutor.executeShellCommand
					("cmd /c cd " + ROOT_FOLDER + SC_FOLDER + " && " + SRC2SRCML_COMMAND + files.get(i) +" -o "+ FileUtilities.getFileNameAndExtensionFromPath(files.get(i)) + FileUtilities.SIMPLE_XML_EXTENSION);
					System.out.println("Successfully extracted file " + files.get(i));
				}  
				catch (IOException e) 
				{
					e.printStackTrace();
					System.out.println(e.getMessage());
				}
				outputs.add(ROOT_FOLDER + SC_FOLDER + "\\" + FileUtilities.getFileNameWithoutExtension(files.get(i)) + FileUtilities.XML_EXTENSION);
			}
			else 
			{
				System.out.println(returnErrorMessage(files.get(i) + "--" + FILE_DOES_NOT_EXIST));
			}
		}
		setOutputs(outputs);
	}
}
