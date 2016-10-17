package framework.DAL.Tests;

import static org.junit.Assert.*;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;
import framework.DAL.Transformation.XmlTransformer;
import framework.general.PropertyReader;

/** Test XSLT transformation functionality
 */
public class TransformationTest 
{
	static XmlTransformer tr;
	static PropertyReader prop = new PropertyReader();
	static String sourceCodeXSLT = prop.getProperties()[2];
	static String unitTestXSLT = prop.getProperties()[2];
	
	private String folder = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\transformationTest";
	private String path1 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\transformationTest\\JBBPCustomFieldTypeProcessor.java.xml";
	private String path2 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\transformationTest\\JBBPExternalValueProvider.java.xml";
	private String path3 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\transformationTest\\JBBPNamedNumericFieldMap.java.xml";
	private String path4 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\transformationTest\\JBBPCustomFieldTypeProcessorTest.java.xml";
	
	
	@BeforeClass
	public static void setup() 
	{
		tr = new XmlTransformer();
	}
	
	@AfterClass
	public static void tearDown() 
	{
		
	}
	
	@Test
	public void testSourceCodeTransformation() throws ParserConfigurationException, SAXException, IOException, TransformerException 
	{
		tr.transformXmlFile(path1, sourceCodeXSLT);
	}
	
	@Test
	public void testSourceCodeTransformationMultipleFiles() throws ParserConfigurationException, SAXException, IOException, TransformerException 
	{
		String folder2 = "C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Change detection\\constraints\\Example graphs";
		tr.transformMultipleXmlFiles(folder2, sourceCodeXSLT);
	}
	
	@Test
	public void testClassDiagramTransformation() 
	{
		
	}
	
	@Test
	public void testRequirementsTransformation() 
	{

	}
	
	@Test
	public void testArchitectureTransformation() 
	{
		
	}
	
	@Test
	public void testUnitTestTransformation() throws ParserConfigurationException, SAXException, IOException, TransformerException 
	{
		tr.transformXmlFile(path4, sourceCodeXSLT);
	}
	
	@Test
	public void testSequenceDiagramTransformation() throws ParserConfigurationException, SAXException, IOException, TransformerException 
	{
		
	}
}
