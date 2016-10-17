package framework.changeDetection.Tests;

import static org.junit.Assert.assertTrue;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.GraphComparer;

/** Test graph comparison functionality.
 *
 */
public class GraphComparerTest 
{
	/**
	 * Paths for original and modified versions of test graphml file
	 */
	static String beforePath = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTest.graphml";
	static String afterPath1 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter1.graphml";
	static String afterPath2 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter2.graphml";
	static String afterPath3 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter3.graphml";
	static String afterPath4 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter4.graphml";
	static String afterPath5 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter5.graphml";
	static String afterPath6 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter6.graphml";
	static String afterPath7 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter7.graphml";
	static String afterPath8 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter8.graphml";
	static String afterPath9 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter9.graphml";
	static String afterPath10 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter10.graphml";
	static String afterPath11 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter11.graphml";
	static String afterPath12 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter12.graphml";
	static String afterPath13 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter13.graphml";
	static String afterPath14 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter14.graphml";
	static String afterPath15 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter15.graphml";
	static String afterPath16 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter16.graphml";
	static String afterPath17 = "C:\\Users\\Ildi\\workspace\\Refactored\\resources\\changeDetectionTest\\BitIOCommonTestAfter17.graphml";
	
	
	/**
	 * LinkedHashmap representing the previous version of the graph
	 */
	private LinkedHashMap<String, LinkedHashMap<String, String>> beforeMap;

	/**
	 * LinkedHashmap representing the previous version of the graph
	 */
	private LinkedHashMap<String, LinkedHashMap<String, String>> afterMap;

	
	@BeforeClass
	public static void setup() 
	{
	
	}

	/** Return ChangeData after comparison
	 * 
	 * @param afterPathName
	 * @return
	 */
	private List<ChangeData> getChanges(String afterPathName) 
	{
		GraphComparer comp = new GraphComparer(beforePath, afterPathName);
		beforeMap  = comp.createMapRepresentation(comp.getBeforeGraphProc());
		afterMap = comp.createMapRepresentation(comp.getAfterGraphProc());
		comp.compareMaps(beforeMap, afterMap);
		// TODO: refactor - add new method call
		//comp.getRemovedAndAddedChangeData(beforeMap, afterMap);
		//comp.getEditedChangeData(beforeMap);
		List<ChangeData> changes = null;//comp.returnChangeData(beforeMap, afterMap);
		return changes;
	}
	
	/**
	 *
	 */
	
	public void testRemoveOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath1);
		assertTrue(changes.size() == 1);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
	}

	/**
	 *
	 */
	
	public void testRemoveTwoNodes() 
	{
		List<ChangeData> changes = getChanges(afterPath2);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("rnd"));
	}

	/**
	 *
	 */
	
	public void testEditOneNodeSignature() 
	{
		List<ChangeData> changes = getChanges(afterPath6);
		assertTrue(changes.size() == 1);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(0).getSignatureChange() == true);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
	}

	/**
	 *
	 */
	
	public void testEditOneNodeNonSignature() 
	{
		List<ChangeData> changes = getChanges(afterPath7);
		assertTrue(changes.size() == 1);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(0).getSignatureChange() == false);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
	}

	/**
	 *
	 */
	
	public void testEditMultipleNodesMultipleProperties() 
	{
		List<ChangeData> changes = getChanges(afterPath11);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.EDIT);
	}

	/** This should return a delete and an add as a result of a rename
	 *
	 */
	
	public void testEditOneNodeRename() 
	{
		List<ChangeData> changes = getChanges(afterPath8);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getSignatureChange() == false);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("BitIOCommonTest"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getSignatureChange() == false);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("BitIOCommonTestRENAMED"));
	}

	/**
	 *
	 */
	
	public void testAddOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath3);
		assertTrue(changes.size() == 1);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("NEWNODE"));
	}

	/**
	 *
	 */
	
	public void testAddTwoNodes() 
	{
		List<ChangeData> changes = getChanges(afterPath4);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("ANOTHERNEWNODE"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("NEWNODE"));
	}

	/**
	 *
	 */
	
	public void testAddOneNodeRemoveOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath5);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("NEW"));
	}

	/**
	 *
	 */
	
	public void testAddOneNodeEditOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath9);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("NEW"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("BitIOCommonTest"));
	}

	/**
	 *
	 */
	
	public void testEditOneNodeRemoveOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath10);
		assertTrue(changes.size() == 2);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("rnd"));
	}
	
	/** The changes take place in this order
	 *
	 */
	
	public void testEditOneNodeRemoveOneNodeAddOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath12);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("rnd"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("BitIOCommonTest"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("NEWNODE"));
	}

	/** The changes take place in this order
	 *
	 */
	
	public void testRemoveOneNodeEditOneNodeAddOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath13);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("rnd"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("NEW"));
	}
	
	/** The changes take place in this order
	 *
	 */
	
	public void testEditOneNodeAddOneNodeRemoveOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath14);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("BitIOCommonTest"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("NEW"));
	}
	
	/** 
	 *
	 */
	
	public void testRemoveTwoNodesAddOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath15);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("rnd"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.ADD);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("NEW"));
	}
	
	/** 
	 *
	 */
	@Test
	public void testRemoveTwoNodesEditOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath16);
		assertTrue(changes.size() == 3);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("rnd"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.EDIT);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("BitIOCommonTest"));
	}
	
	/** 
	 *
	 */
	
	public void testEditAllNodes() 
	{
		List<ChangeData> changes = getChanges(afterPath17);
		assertTrue(changes.size() == 6);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getPreviousName().equalsIgnoreCase("BitIOCommonTest"));
		assertTrue(changes.get(1).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(1).getCurrentName().equalsIgnoreCase("testWriteRead"));
		assertTrue(changes.get(2).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(2).getCurrentName().equalsIgnoreCase("rnd"));
	}
}

