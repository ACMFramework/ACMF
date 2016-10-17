package framework.general.Tests;

import static org.junit.Assert.*;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.general.PropertyReader;

/**
 * @author Ildiko Pete
 */
public class ConfigReaderWriterTest 
{
	/** PropertyReader
	 * 
	 */
	private static PropertyReader propReader = new PropertyReader();
	
	/**
	 *
	 */
	@BeforeClass
	public static void setup()
	{		
		
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testReadOne() 
	{
		String first = propReader.getProperties()[0];
		assertEquals(first, "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testReadTwo() 
	{
		String second = propReader.getProperties()[1];
		assertEquals(second, "C:\\Users\\Ildi\\Documents\\Neo4j\\default.graphdb");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testReadThree() 
	{
		String third = propReader.getProperties()[2];
		assertEquals(third, System.getProperty("user.dir") + "\\resources\\xslt\\scTransformer.xslt");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testReadFour() 
	{
		String fourth = propReader.getProperties()[3];
		assertEquals(fourth, System.getProperty("user.dir") + "\\resources\\xslt\\umlTransformer.xslt");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead5() 
	{
		String fifth = propReader.getProperties()[4];
		assertEquals(fifth, System.getProperty("user.dir") + "\\resources\\xslt\\scTransformer.xslt");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead6() 
	{
		String sixth = propReader.getProperties()[5];
		assertEquals(sixth, System.getProperty("user.dir") + "\\resources\\xslt\\moduleViewArchitectureTransformer.xslt");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead7() 
	{
		String seventh = propReader.getProperties()[6];
		assertEquals(seventh, System.getProperty("user.dir") + "\\resources\\xslt\\useCaseTransformer.xslt");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead8() 
	{
		String eighth = propReader.getProperties()[7];
		assertEquals(eighth, System.getProperty("user.dir") + "\\resources\\test\\JBBPBitInputStream.graphml");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead9() 
	{
		String ninth = propReader.getProperties()[8];
		assertEquals(ninth, System.getProperty("user.dir") + "\\resources\\test\\JBBPBitOutputStream.graphml");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead10() 
	{
		String tenth = propReader.getProperties()[9];
		assertEquals(tenth, System.getProperty("user.dir") + "\\resources\\test\\BitIOCommonTest.graphml");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead11() 
	{
		String eleventh = propReader.getProperties()[10];
		assertEquals(eleventh, System.getProperty("user.dir") + "\\resources\\test\\RelationsFile.xml");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead12() 
	{
		String twelweth = propReader.getProperties()[11];
		assertEquals(twelweth, "http://Ildi-PC:8000");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead13() 
	{
		String thirteenth = propReader.getProperties()[12];
		assertEquals(thirteenth, "D:\\CloneOfLocalRepo\\cloned");
	}
	
	/** Test reading from property file
	 * 
	 */
	@Test
	public void testRead14() 
	{
		String fourteenth = propReader.getProperties()[13];
		assertEquals(fourteenth, System.getProperty("user.dir") + "\\resources\\scripts\\SsetupCentralRepoScript.bat");
	}
}
