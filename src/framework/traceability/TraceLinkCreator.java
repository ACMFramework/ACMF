package framework.traceability;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

import org.xml.sax.SAXException;

import weka.core.converters.XRFFSaver;

import framework.DAL.Transformation.XmlTransformer;
import framework.general.IExecutable;
import framework.general.IExecutionManager;
import framework.general.PropertyReader;

/** Initiate trace link creation
 *
 */
public class TraceLinkCreator implements IExecutable
{
	/**
	 * 
	 */
	private static PropertyReader propReader = new PropertyReader();
	
	/**Input path specified by user, currently value is set here
	 * 
	 */
	final String inputPath = "C:\\Users\\Ildi\\Desktop\\2804.csv";
	
	/** Output path specified by user, currently value is set here
	 * 
	 */
	final String outputPath = "C:\\Users\\Ildi\\Desktop\\finalOutput.xml";
	
	/** Read XSLT file path from properties
	 * 
	 */
	final String xsltPath = propReader.getProperties()[18];
	
	
	/** XML transformer to transform arff file to xml relations file format
	 * 
	 */
	XmlTransformer transformer = new XmlTransformer();
	
	/** CSV data generation functionality
	 * 
	 */
	MLDataGenerator gen = new MLDataGenerator();
	
	/** Classification functionality
	 * 
	 */
	Classification classification = new Classification();
	
	/** Path of the input XML file specified by the user
	 * 
	 */
	private String inputXMLPath = null;
	
	/**
	 * 
	 * @return
	 */
	public String getXMLInputPath() 
	{
		return inputXMLPath;
	}

	/** Set the path of the input XML file
	 * 
	 * @param path
	 * @return
	 */
	public void setXMLInput(String path) 
	{
		inputXMLPath = path;
	}
	
	
	/** Path to the classified data - arff file
	 * 
	 */
	private String classifiedTestDataOutput = null;
	
	/**
	 * 
	 * @return
	 */
	public String getClassifiedTestDataOutput() 
	{
		return classifiedTestDataOutput;
	}
	
	/**
	 * 
	 * @param classifiedTestDataOutput
	 */
	public void setClassifiedTestDataOutput(String classifiedTestDataOutput) 
	{
		this.classifiedTestDataOutput = classifiedTestDataOutput;
	}

	/** Path of the CSV file
	 * 
	 */
	private String csvPath = null;
	
	/**
	 * 
	 * @return
	 */
	public String getCsvPath() 
	{
		return csvPath;
	}
	/**
	 * 
	 * @param csvPath
	 */
	public void setCsvPath(String csvPath) 
	{
		this.csvPath = csvPath;
	}

	/**
	 * 
	 */
	private String finalOutputXML = null;
	
	/**
	 * 
	 * @return
	 */
	public String getFinalOutputXML() 
	{
		return finalOutputXML;
	}

	/**
	 * 
	 * @param finalOutputXML
	 */
	public void setFinalOutputXML(String finalOutputXML) 
	{
		this.finalOutputXML = finalOutputXML;
	}

	/**
	 * 
	 * @param manager
	 */
	public TraceLinkCreator(IExecutionManager manager) 
	{
		manager.register(this);
		setCsvPath(inputPath);
		setFinalOutputXML(outputPath);
	}

	/**
	 * 
	 */
	@Override
	public void Execute() 
	{
		// Generate CSV test data from specified input. The output path of the CSV file is specified in the constructor
		try 
		{
			gen.generateTestData(getXMLInputPath(), getCsvPath());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		// Create arff representation
		try 
		{
			classification.createArffFromCsv(getCsvPath());
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		try 
		{
			classification.classifyMLP();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		// Classify test data and get output path of arff file
		setClassifiedTestDataOutput(classification.getClassifiedNaiveBayes());
		
		// Generate xrff file from arff file and then xml using XSLT
		try 
		{
			classification.saveArffToXrff(getClassifiedTestDataOutput());
			transformer.genericTransformXmlFile(classification.getXrffPath(), xsltPath, getFinalOutputXML());
		} 
		catch (IOException | ParserConfigurationException | SAXException | TransformerException e) 
		{
			e.printStackTrace();
		}
		System.out.println("Suggested traceability links saved to: " + getFinalOutputXML());
	}
}
