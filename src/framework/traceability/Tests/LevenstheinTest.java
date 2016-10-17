package framework.traceability.Tests;
import static org.junit.Assert.*;
import org.junit.Test;
import framework.traceability.Levenshtein;

/**
 * @author Ildiko Pete
 */
public class LevenstheinTest 
{
	/**
	 * 
	 */
	String firstString = "getName";	
	
	/**
	 *
	 */
	String secondString = "getNames";

	/**
	 *
	 */
	@Test
	public void testDistance() 
	{
		int manualDistance = 1;
		assertEquals(manualDistance,Levenshtein.distance(firstString, secondString));
	}
	
	/**
	 *
	 */
	@SuppressWarnings("deprecation")
	@Test
	public void testDistanceInPercentage() 
	{
		double manualDistanceInPercentage = 0.875;
		assertEquals(manualDistanceInPercentage,Levenshtein.returnDistanceInPercentage(firstString, secondString));
	}
}
