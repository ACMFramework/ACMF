package framework.general;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/** Read property file 
 * 
 */
public class PropertyReader 
{
	/** Property file name
	 * 
	 */
	String propertyFileName = System.getProperty("user.dir") + "\\resources\\config\\config.properties";
	
	/** InputStream
	 * 
	 */
	InputStream inputStream;

	/** FileOutputStream
	 * 
	 */
	FileOutputStream outputStream;

	/** Properties
	 * 
	 */
	Properties prop = new Properties();

	/** Properties
	 * 
	 */
	private String[] properties = new String [100];

	/** Properties
	 * 
	 */
	public String[] getProperties() 
	{
		return this.properties;
	}

	/** Fill list of properties
	 * 
	 */
	public PropertyReader() 
	{
		try 
		{
			returnProperties();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	/** Return required property value as a string
	 * 
	 */
	public void returnProperties() throws IOException 
	{
		try 
		{
			inputStream = new FileInputStream(propertyFileName);
			if (inputStream != null) 
			{
				prop.load(inputStream);
			} 
			else 
			{
				throw new FileNotFoundException("Property file '" + propertyFileName + "' not found in the classpath");
			}
			properties[0] = prop.getProperty("frameworkPath");
			properties[1] = prop.getProperty("dbPath");
			properties[2] = prop.getProperty("sourceCodeXSLTPath");
			properties[3] = prop.getProperty("classDiagramXSLTPath");
			properties[4] = prop.getProperty("junitTestCaseXSLTPath");
			properties[5] = prop.getProperty("moduleViewArchitectureXSLTPath");
			properties[6] = prop.getProperty("useCaseXSLTPath");
			properties[7] = prop.getProperty("reqXSLTPath");
			properties[8] = prop.getProperty("");
			properties[9] = prop.getProperty("");
			properties[10] = prop.getProperty("testRelationsFile");
			properties[11] = prop.getProperty("remoteRepo");
			properties[12] = prop.getProperty("localRepo");
			properties[13] = prop.getProperty("repoSetupBatch");
			properties[14] = prop.getProperty("getChangesFromRepoBatch");
			properties[15] = prop.getProperty("deleteRulePath");
			properties[16] = prop.getProperty("editRulePath");
			properties[17] = prop.getProperty("intraRulePath");
			properties[18] = prop.getProperty("relationsXSLTPath");
			properties[19] = prop.getProperty("arffTrainingDataNominal");
		} 
		catch (Exception e) 
		{
			System.out.println("Exception: " + e);
			System.out.println("Check property array size");
		} 
	
		finally 
		{
			inputStream.close();
		}
	}
}
