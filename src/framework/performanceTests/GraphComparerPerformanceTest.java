package framework.performanceTests;

import static org.junit.Assert.assertTrue;
import java.util.LinkedHashMap;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.GraphComparer;

/** Test graph comparison functionality.
 */
public class GraphComparerPerformanceTest 
{
	/**
	 * Paths for original and modified versions of test graphml file
	 */
	static String beforePath = System.getProperty("user.dir") + "\\resources\\changeDetectionTest\\BitIOCommonTest.graphml";
	static String afterPath1 = System.getProperty("user.dir") + "\\resources\\changeDetectionTest\\BitIOCommonTestAfter1.graphml";
	
	
	
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
		comp.setBeforeMap(comp.createMapRepresentation(comp.getBeforeGraphProc()));
	    comp.setAfterMap(comp.createMapRepresentation(comp.getAfterGraphProc()));
	    comp.compareMaps(comp.getBeforeMap(), comp.getAfterMap());
	    List<ChangeData> changes = comp.getChangeData();
	    System.out.println("Changes size************: " + changes.size());
	    return changes;
	}
	
	/**
	 *
	 */
	@Test
	public void testRemoveOneNode() 
	{
		List<ChangeData> changes = getChanges(afterPath1);
		assertTrue(changes.size() == 1);
		assertTrue(changes.get(0).getTypeOfChange() == ArtefactElementChangeType.DELETE);
		assertTrue(changes.get(0).getCurrentName().equalsIgnoreCase("testWriteRead"));
	}
}

