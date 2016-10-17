package framework.repoAccessor.Tests;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import framework.general.PropertyReader;
import framework.repoAccessor.IRepoQuery;
import framework.repoAccessor.RepoFactory;

/**
 *
 */
public class RepoTest 
{
	/**
	 *
	 */
	private static IRepoQuery repo;

	/**
	 * @throws IOException 
	 *
	 */
	@BeforeClass
	public static void setup() throws IOException 
	{		
		repo = RepoFactory.getRepo(RepoFactory.MERCURIAL);
		System.out.println(repo.getClass());
	}

	/**
	 *
	 */
	@AfterClass
	public static void tearDown() 
	{
		// stop hg serve
	}

	/**
	 *
	 */
	@Test
	public void testGetAddedFileName() 
	{	
		for(int i = 0; i < repo.getListOfAddedItems().size(); i++) 
		{
			System.out.println("Added files " + repo.getListOfAddedItems().get(i));
		}
	}

	/**
	 *
	 */
	@Test
	public void testGetDeletedFileName() 
	{	
		for(int i = 0; i < repo.getListOfDeletedItems().size(); i++) 
		{
			System.out.println("Deleted files " + repo.getListOfDeletedItems().get(i));
		}
	}

	/**
	 *
	 */
	@Test
	public void testGetEditedFileName() 
	{	
		for(int i = 0; i < repo.getListOfEditedItems().size(); i++) 
		{
			System.out.println("Edited files " + repo.getListOfEditedItems().get(i));
		}
	}
}
