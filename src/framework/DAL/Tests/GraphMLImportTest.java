package framework.DAL.Tests;

import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.DAL.graphmlInput.GraphMLImporter;
import framework.general.PropertyReader;
import framework.utilities.FileUtilities;

/** Test GraphML import
*
*/
public class GraphMLImportTest 
{
	/** Read db path from config file
	 *
	 */
	private static PropertyReader propReader = new PropertyReader();
	private static final String DB_PATH = propReader.getProperties()[1];
	static GraphMLImporter importer;
	
	static final String path1 = "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\JBBPBitInputStream.graphml";
	static final String path4 = "C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\JBBPParser.graphml";
	static final String path2 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\graphmltest\\JBBPBitInputStream.graphml";
	static final String path3 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\graphmltest\\JBBPBitOutputStream.graphml";
	static final String path5="C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\VertexList.graphml";
	static final String path6="C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\VertexLabel.graphml";
	
	static final String path7="C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\UMLClass\\UML.graphml";
	/**
	 *
	 */
	@BeforeClass
	public static void setup() 
	{
		importer = new GraphMLImporter(DB_PATH);
	}
	
	/**
	 *
	 */
	@AfterClass
	public static void tearDown() 
	{
		importer.closeDb();
	}
	
	/**
	 *
	 */
	@Test
	public void importSingleFile() 
	{
		importer.importSingleFile(path7);
	}
	
	/**
	 *
	 */
	
	public void importMultipleFiles() 
	{
		List<String> files = FileUtilities.getGraphMLFilesFullPathFromFolder
				("C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\");
		String[] listOfInputFilePaths = new String[files.size()];
		files.toArray(listOfInputFilePaths); 
		//String[] listOfInputFilePaths = new String[] {"C:\\Users\\Ildi\\Dropbox\\PhD\\ACMF\\SourceCode\\AbstractSuperGene.graphml"};
		importer.importInputFiles(listOfInputFilePaths);
	}
}
