package framework.general;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/** Write to config file
 * 
 *
 */
public class PropertyWriter 
{
	/** Property file name
	 * 
	 */
	String propertyFileName = System.getProperty("user.dir") +"\\resources\\config\\config.properties";
	
	/**
	 * 
	 */
	Properties prop = new Properties();

	/**
	 * 
	 * @param key
	 * @param value
	 * @throws IOException
	 */
	public void createPropertyFile(String frameworkPath, String dbPath, String remoteRepo, String localRepo) throws IOException 
	{
	    
		//FileWriter writer = new FileWriter(propertyFileName);
	   // prop.store(writer, value);
	   // writer.close();
	    
	    try 
		{
			FileOutputStream outputStream = new FileOutputStream(propertyFileName);
			prop.setProperty("frameworkPath", frameworkPath);
		    prop.setProperty("dbPath", dbPath);
		    prop.setProperty("sourceCodeXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\scTransformer.xslt");
		    prop.setProperty("classDiagramXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\umlTransformer.xslt");
		    prop.setProperty("junitTestCaseXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\scTransformer.xslt");
		    prop.setProperty("moduleViewArchitectureXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\moduleViewArchitectureTransformer.xslt");
		    prop.setProperty("useCaseXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\useCaseTransformer.xslt");
		    prop.setProperty("reqXSLTPath", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\xslt\\reqTransformer.xslt");
		    prop.setProperty("empty1", "");
		    prop.setProperty("empty2", "");
		    prop.setProperty("testRelationsFile", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\test\\RelationsFile.xml");
		    prop.setProperty("remoteRepo", remoteRepo);
		    prop.setProperty("localRepo", localRepo);
		    prop.setProperty("repoSetupBatch", "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\scripts\\setupCentralRepoScript.bat");
		    prop.store(outputStream, "");
			outputStream.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	/** Write to the property file
	 * 
	 * @param propKey
	 * @param propValue
	 */
	public void writeProperty(String propKey, String propValue) 
	{
		try 
		{
			FileOutputStream outputStream = new FileOutputStream(propertyFileName);
			prop.setProperty(propKey, propValue);
			outputStream.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
}
