package framework.DAL.Transformation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.xml.sax.SAXException;
import framework.DAL.GraphML.GraphMLProcessor;
import framework.changeDetection.ArtefactType;
import framework.general.IExecutable;
import framework.general.IExecutionManager;
import framework.general.PropertyReader;
import framework.utilities.FileUtilities;

/** IExecutable class representing the Transformation component
 * 
 *
 */
public class Transformer implements IExecutable
{
	/**
	 * 
	 */
	private PropertyReader prop = new PropertyReader();
	/**
	 * 
	 */
	private String sourceCodeXSLT = prop.getProperties()[2];
	/**
	 * 
	 */
	private String unitTestXSLT = prop.getProperties()[2];
	/**
	 * 
	 */
	private String reqXSLT = prop.getProperties()[14];
	/**
	 * 
	 */
	private String classDiagramXSLT = prop.getProperties()[3];
	/**
	 * 
	 */
	private String moduleViewArchitectureXSLT = prop.getProperties()[5];

	/**
	 * 
	 */
	private String useCaseXSLT = prop.getProperties()[6];

	/** The name of the subfolder that contains conceptual architecture artefacts
	 * 
	 */
	private final String archConceptualSub = "ArchitectureConceptual";

	/** The name of the subfolder that contains module view architecture artefacts
	 * 
	 */
	private final String arcModuleSub = "ArchitectureModuleView";

	/** The name of the subfolder that contains source code artefacts
	 * 
	 */
	private final String scSub = "SourceCode";

	/** The name of the subfolder that contains UML class diagram artefacts
	 * 
	 */
	private final String umlClassSub = "UMLClass";

	/** The name of the subfolder that contains UML use case diagram artefacts
	 * 
	 */
	private final String umlUseCaseSub = "UMLUseCase";

	/** The name of the subfolder that contains UML sequence diagram artefacts
	 * 
	 */
	private final String umlSequenceSub = "UMLSequence";

	/** The name of the subfolder that contains unit test artefacts
	 * 
	 */
	private final String unitTestSub = "UnitTests";

	/** The name of the subfolder that contains requirement artefacts
	 * 
	 */
	private final String reqSub = "Requirement";

	/**
	 * 
	 */
	private XmlTransformer transform;

	/**
	 * 
	 */
	private IDGenerator gen;

	/** List of graphml outputs
	 * 
	 */
	private List<String> outputPaths = new ArrayList<String>();

	/** Get output of transformation - list of graphml files
	 * 
	 * @return
	 */
	public List<String> getOutputPaths() 
	{
		return outputPaths;
	}

	/** The list of graphml files - output of transformation
	 * 
	 * @param paths
	 */
	public void setOutputPaths(List<String> paths) 
	{
		this.outputPaths.addAll(paths);
	}
	
	/**
	 * 
	 * @param path
	 */
	public void setOutputPath(String path) 
	{
		this.outputPaths.add(path);
	}

	/**
	 * 
	 * @param manager
	 */
	public Transformer(IExecutionManager manager) 
	{
		manager.register(this);
		transform = new XmlTransformer();
	}

	/** Return corresponding unique id prefix for each folder / artefact type
	 * 
	 * @param inputFolder
	 * @return
	 */
	public String getCorrespondingUniqueIdPrefix(String inputFolder) 
	{
		String prefix = null;

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + archConceptualSub)) 
		{
			prefix = GraphMLProcessor.ARCHITECTURE_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + arcModuleSub)) 
		{
			prefix = GraphMLProcessor.ARCHITECTURE_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + scSub)) 
		{
			prefix = GraphMLProcessor.SC_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + umlClassSub)) 
		{
			prefix = GraphMLProcessor.DESIGN_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + umlUseCaseSub)) 
		{
			prefix = GraphMLProcessor.USE_CASE_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + umlSequenceSub)) 
		{
			prefix = GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + unitTestSub)) 
		{
			prefix = GraphMLProcessor.UNIT_TEST_ID_PREFIX;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + reqSub)) 
		{
			prefix = GraphMLProcessor.REQ_ID_PREFIX;
		}

		return prefix;
	}

	/** Return corresponding xslt file for each folder / artefact type
	 * 
	 * @param inputFolder
	 * @return
	 */
	public String getCorrespondingXSLT(String inputFolder) 
	{
		String xslt = null;

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + archConceptualSub)) 
		{
			xslt = "";
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] +arcModuleSub)) 
		{
			xslt = moduleViewArchitectureXSLT;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] +scSub)) 
		{
			xslt = sourceCodeXSLT;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] +umlClassSub)) 
		{
			xslt = classDiagramXSLT;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0]+ umlUseCaseSub)) 
		{
			xslt = useCaseXSLT;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0]+ umlSequenceSub)) 
		{
			xslt = "";
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + unitTestSub)) 
		{
			xslt = unitTestXSLT;
		}

		if(inputFolder.equalsIgnoreCase(prop.getProperties()[0] + reqSub)) 
		{
			xslt = reqXSLT;
		}

		return xslt;
	}

	/** Return corresponding xslt file for each artefact type
	 * 
	 * @param inputFolder
	 * @return
	 */
	public String getXSLTBasedOnArtefact(ArtefactType artefact) 
	{
		String xslt = null;
		if(artefact == ArtefactType.ARCHITECTURE) 
		{
			xslt = "";
		}

		if(artefact == ArtefactType.JAVA_SOURCE_CODE) 
		{
			xslt = sourceCodeXSLT;
		}

		if(artefact == ArtefactType.UML_CLASS_DIAGRAM) 
		{
			xslt = classDiagramXSLT;
		}

		if(artefact == ArtefactType.USE_CASE) 
		{
			xslt = useCaseXSLT;
		}

		if(artefact == ArtefactType.SEQUENCE_DIAGRAM) 
		{
			xslt = "";
		}

		if(artefact == ArtefactType.UNIT_TEST) 
		{
			xslt = unitTestXSLT;
		}

		if(artefact == ArtefactType.REQUIREMENT_SPECIFICATION) 
		{
			xslt = reqXSLT;
		}

		return xslt;
	}

	/** Return corresponding unique id prefix for each artefact type
	 * 
	 * @param inputFolder
	 * @return
	 */
	public String getUniqueIdPrefixBasedOnArtefact(ArtefactType artefact) 
	{
		String prefix = null;
		
		if(artefact == ArtefactType.ARCHITECTURE) 
		{
			prefix = GraphMLProcessor.ARCHITECTURE_ID_PREFIX;
		}

		if(artefact == ArtefactType.JAVA_SOURCE_CODE) 
		{
			prefix = GraphMLProcessor.SC_ID_PREFIX;
		}

		if(artefact == ArtefactType.UML_CLASS_DIAGRAM) 
		{
			prefix = GraphMLProcessor.DESIGN_ID_PREFIX;
		}
		
		if(artefact == ArtefactType.USE_CASE) 
		{
			prefix = GraphMLProcessor.USE_CASE_ID_PREFIX;
		}

		if(artefact == ArtefactType.SEQUENCE_DIAGRAM) 
		{
			prefix = GraphMLProcessor.SEQUENC_DIAGRAM_ID_PREFIX;
		}
		
		if(artefact == ArtefactType.UNIT_TEST) 
		{
			prefix = GraphMLProcessor.UNIT_TEST_ID_PREFIX;
		}
		
		if(artefact == ArtefactType.REQUIREMENT_SPECIFICATION) 
		{
			prefix = GraphMLProcessor.REQ_ID_PREFIX;
		}

		return prefix;
	}
	
	/** Generic function to perform transformation of all .xml and .java.xml files
	 * 
	 */
	@Override
	public void Execute() 
	{
		// Do transformation
		String[] inputFolders = FileUtilities.getSubFolders(prop.getProperties()[0]);

		for (int k = 0; k < inputFolders.length; k++) 
		{
			if(!FileUtilities.isFolderEmpty(prop.getProperties()[0] + inputFolders[k]))
			{
				try 
				{
					transform.transformMultipleXmlFiles(prop.getProperties()[0] + inputFolders[k], getCorrespondingXSLT(prop.getProperties()[0] + inputFolders[k]));
				} 
				catch (ParserConfigurationException | SAXException | IOException
						| TransformerException e) 
				{
					e.printStackTrace();
				}
			}
		}

		// Generate ids
		List<String> files = null;
		for(String folder : inputFolders) 
		{
			files = FileUtilities.getGraphMLFilesFullPathFromFolder(prop.getProperties()[0] + folder);
			for(int j = 0; j < files.size(); j++) 
			{
				gen = new IDGenerator(files.get(j));
				gen.generateUniqueIdsForGraphmlFile(getCorrespondingUniqueIdPrefix(prop.getProperties()[0] + folder));
			}
			setOutputPaths(files);
		}
	}


	/** Transform a list of specific files
	 * 
	 */
	public void transformSpecificFiles(List<String> files, ArtefactType artefact) 
	{
		List<String> outputs = new ArrayList<String>();
		// Do transformation
		for (int k = 0; k < files.size(); k++) 
		{
			try 
			{
				transform.transformXmlFile(files.get(k), getXSLTBasedOnArtefact(artefact));
			} 
			catch (ParserConfigurationException | SAXException | IOException
					| TransformerException e) 
			{
				e.printStackTrace();
			}
			outputs.add(FileUtilities.getPathWithoutExtension(files.get(k))+FileUtilities.GRAPHML_EXTENSION);
			gen = new IDGenerator(FileUtilities.getPathWithoutExtension(files.get(k))+FileUtilities.GRAPHML_EXTENSION);
			gen.generateUniqueIdsForGraphmlFile(getUniqueIdPrefixBasedOnArtefact(artefact));
		}
		setOutputPaths(outputs);
	}
	

	/** Re-generate graphml files logic
	 * 
	 */
	public void reGenerate(List<String> editedFiles, ArtefactType artefact) 
	{	
		List<String> outputs = new ArrayList<String>();
		// Do transformation
		for (int k = 0; k < editedFiles.size(); k++) 
		{
			try 
			{
				// If renamed file already exists, delete it - that version can be discarded and rename current file
				if(FileUtilities.fileExists(FileUtilities.getPathWithoutExtension(editedFiles.get(k)) + FileUtilities.GRAPHML_EXTENSION + FileUtilities.SUFFIX + FileUtilities.GRAPHML_EXTENSION))
				{
					FileUtilities.deleteFile(FileUtilities.getPathWithoutExtension(editedFiles.get(k)) + FileUtilities.GRAPHML_EXTENSION + FileUtilities.SUFFIX + FileUtilities.GRAPHML_EXTENSION);
					FileUtilities.renameFile(FileUtilities.getPathWithoutExtension(editedFiles.get(k)) + FileUtilities.GRAPHML_EXTENSION, FileUtilities.SUFFIX);
				}
				else 
				{
					// Rename previous
					FileUtilities.renameFile(FileUtilities.getPathWithoutExtension(editedFiles.get(k)) + FileUtilities.GRAPHML_EXTENSION, FileUtilities.SUFFIX);
				}
				
				transform.transformXmlFile(editedFiles.get(k), getXSLTBasedOnArtefact(artefact));
			} 
			catch (ParserConfigurationException | SAXException | IOException
					| TransformerException e) 
			{
				e.printStackTrace();
			}
			outputs.add(FileUtilities.getPathWithoutExtension(editedFiles.get(k))+FileUtilities.GRAPHML_EXTENSION);
			// Note: generation of unique and node ids is managed by the ChangeDetectionManager class
		}
		setOutputPaths(outputs);
	}	
}
