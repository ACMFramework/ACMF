package framework.traceability.Tests;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import framework.artefactRepresentation.ElementTypes;
import framework.traceability.*;

/**
* @author Ildiko Pete
*/
public class EnumerationTests 
{
	/**
	 *
	 */
	MLDataGenerator data = new MLDataGenerator();
	
	/**
	 *
	 */
	ElementTypes types = new ElementTypes();
	
	/**
	 *
	 */
	String id1 = "sc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\JGAP\\SourceCodeGraphml\\Impl\\AveragingCrossoverOperator.graphml";	
	String id2 = "sc1C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\JGAP\\SourceCodeGraphml\\Impl\\AveragingCrossoverOperator.graphml";
	String id3 = "di22/Users/ildikopete/Dropbox/PhD/SharedBackup/Evaluation/MazeSolver/Evaluation Files/UML/Revision19/XML/OldVersion/revision19Old.vdx";
	String id4 = "ut0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\JGAP\\TestCasesGraphml\\Impl\\AveragingCrossoverOperatorTest.graphml";
	
	String id5 = "ar0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\Titan\\Architecture\\TitanArchitecture.graphml";
	String id6 = "sd0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\MyRobotLab\\Architecture\\registerServicesSequenceDiagram.graphml";
	String id7 = "uc0C:\\Users\\Ildi\\Dropbox\\PhD\\SharedBackup\\Evaluation\\BinaryBlockParser\\UseCases\\UseCaseDiagram.graphml";
	
	/**
	 *
	 */
	@Test
	public void testAbstractionLevelSeparation() 
	{
		assertEquals(0.0, data.enumerateAbstractionLevelSeparation(id1, id2), 0.01);
		assertEquals(1.0, data.enumerateAbstractionLevelSeparation(id1, id3), 0.01);
		assertEquals(0.0, data.enumerateAbstractionLevelSeparation(id1, id4), 0.01);
		assertEquals(1.0, data.enumerateAbstractionLevelSeparation(id3, id4), 0.01);
		
		assertEquals(2.0, data.enumerateAbstractionLevelSeparation(id5, id4), 0.01);
		assertEquals(1.0, data.enumerateAbstractionLevelSeparation(id5, id3), 0.01);
		assertEquals(0.0, data.enumerateAbstractionLevelSeparation(id7, id5), 0.01);
		assertEquals(1.0, data.enumerateAbstractionLevelSeparation(id7, id6), 0.01);
		assertEquals(2.0, data.enumerateAbstractionLevelSeparation(id7, id2), 0.01);
		assertEquals(0.0, data.enumerateAbstractionLevelSeparation(id6, id3), 0.01);
		assertEquals(1.0, data.enumerateAbstractionLevelSeparation(id6, id1), 0.01);
	}
	
	/**
	 *
	 */
	@Test
	public void testIsContainer() 
	{
		assertEquals(1.0, data.isContainer(types.getCL()), 0.01);
		assertEquals(0.0, data.isContainer(types.getATT()), 0.01);
		assertEquals(1.0, data.isContainer(types.getENUMS()), 0.01);
		assertEquals(0.0, data.isContainer(types.getFIELD()), 0.01);
		assertEquals(1.0, data.isContainer(types.getINTF()), 0.01);
		assertEquals(0.0, data.isContainer(types.getMETHOD()), 0.01);
		assertEquals(0.0, data.isContainer(types.getUMLOP()), 0.01);
		assertEquals(0.0, data.isContainer(types.getREQ()), 0.01);
		
		assertEquals(1.0, data.isContainer(types.getARCHITECTURE_COMPONENT()), 0.01);
		assertEquals(1.0, data.isContainer(types.getARCHITECTURE_MODULE()), 0.01);
		assertEquals(0.0, data.isContainer(types.getSEQUENCE_DIAGRAM_MESSAGE()), 0.01);
		assertEquals(0.0, data.isContainer(types.getSEQUENCE_DIAGRAM_USECASE()), 0.01);
		assertEquals(0.0, data.isContainer(types.getUSE_CASE_UC()), 0.01);
	}
	
	/**
	 *
	 */
	@Test
	public void testTypeExtraction() 
	{
		int prefixLength = 2;
		assertEquals(prefixLength, data.returnArtefactPrefixFromUniqueId(id1).length());
		assertEquals(prefixLength, data.returnArtefactPrefixFromUniqueId(id2).length());
		assertEquals(prefixLength, data.returnArtefactPrefixFromUniqueId(id3).length());
	}
	
	/**
	 *
	 */
	@Test
	public void testTypes() 
	{
		assertEquals(0.0, data.isRequirement(id1), 0.01);
		assertEquals(0.0, data.isRequirement(id2), 0.01);
		assertEquals(0.0, data.isRequirement(id3), 0.01);
		assertEquals(0.0, data.isRequirement(id4), 0.01);
		
		assertEquals(0.0, data.isDesign(id1), 0.01);
		assertEquals(0.0, data.isDesign(id2), 0.01);
		assertEquals(1.0, data.isDesign(id3), 0.01);
		assertEquals(0.0, data.isDesign(id4), 0.01);
		
		assertEquals(1.0, data.isSourceCode(id1), 0.01);
		assertEquals(1.0, data.isSourceCode(id2), 0.01);
		assertEquals(0.0, data.isSourceCode(id3), 0.01);
		assertEquals(0.0, data.isSourceCode(id4), 0.01);
		
		assertEquals(0.0, data.isUnitTest(id1), 0.01);
		assertEquals(0.0, data.isUnitTest(id2), 0.01);
		assertEquals(0.0, data.isUnitTest(id3), 0.01);
		assertEquals(1.0, data.isUnitTest(id4), 0.01);
	}
}
