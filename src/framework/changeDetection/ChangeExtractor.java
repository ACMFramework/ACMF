package framework.changeDetection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import framework.general.IExecutable;
import framework.general.IExecutionManager;
import framework.repoAccessor.IRepoQuery;
import framework.repoAccessor.RepoFactory;

/** Executable class to carry out change extraction from external repository
 * 
 *
 */
public class ChangeExtractor implements IExecutable
{
	/**
	 * 
	 */
	private IRepoQuery repo; 
	
	/** List of added items
	 * 
	 */
	private List<String> added = new ArrayList<String>();
	
	/** List of edited items
	 * 
	 */
	private List<String> edited = new ArrayList<String>();
	
	/** List of deleted items
	 * 
	 */
	private List<String> deleted = new ArrayList<String>();
	
	/**
	 * 
	 * @return
	 */
	public List<String> getAdded() 
	{
		return added;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getEdited() 
	{
		return edited;
	}

	/**
	 * 
	 * @return
	 */
	public List<String> getDeleted() 
	{
		return deleted;
	}

	/**
	 * 
	 * @param manager
	 */
	public ChangeExtractor(IExecutionManager manager) 
	{
		manager.register(this);
	}
	
	/**
	 * 
	 */
	@Override
	public void Execute() 
	{
		try 
		{
			repo = RepoFactory.getRepo(RepoFactory.MERCURIAL);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		added = repo.getListOfAddedItems();
		edited = repo.getListOfEditedItems();
		deleted = repo.getListOfDeletedItems();
	}
}
