package framework.repoAccessor;

import java.util.List;

/** For the purposes of the prototype, the repo accessor is required to provide a list of edited, added and deleted items
 * 
 */
public interface IRepoQuery 
{	
	/**
	 *
	 */
	public List<String> getListOfAddedItems();
	
	/**
	 *
	 */
	public List<String> getListOfEditedItems();
	
	/**
	 *
	 */
	public List<String> getListOfDeletedItems();
}
