package framework.repoAccessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import framework.general.PropertyReader;
import framework.general.ShellCommandExecutor;

/** Access to Mercurial repository
 * 
 */
public class MercurialRepoQuery implements IRepoQuery 
{
	/** Read paths from config file
	 *
	 */
	private static PropertyReader propReader = new PropertyReader();
	/**
	 * 
	 */
	private static final String remoteRepository = propReader.getProperties()[11];
	/**
	 * 
	 */
	private static final String localRepositoryPath = propReader.getProperties()[12];
	
	/**
	 * 
	 */
	private static final String batchFilePath = propReader.getProperties()[14];

	/** Command to execute batch file
	 * 
	 */
	private final String command = "cmd /c " + batchFilePath;
	
	private final String modified = "M";
	private final String removed = "R";
	private final String added = "A";
	
	/**
	 * 
	 */
	private List<String> addedFiles = new ArrayList<String>();

	/**
	 * 
	 */
	private List<String> deletedFiles = new ArrayList<String>();

	/**
	 * 
	 */
	private List<String> editedFiles = new ArrayList<String>();

	/**
	 * 
	 * @param addedFiles
	 */
	public void setAddedFiles(List<String> addedFiles) 
	{
		this.addedFiles = addedFiles;
	}

	/**
	 * 
	 * @param deletedFiles
	 */
	public void setDeletedFiles(List<String> deletedFiles) 
	{
		this.deletedFiles = deletedFiles;
	}

	/**
	 * 
	 * @param editedFiles
	 */
	public void setEditedFiles(List<String> editedFiles) 
	{
		this.editedFiles = editedFiles;
	}

	/** A script is run to return changes from Mercurial using hgstatus
	 * 
	 * @throws IOException
	 */
	public MercurialRepoQuery() throws IOException 
	{
		// Get changes from remote repository
		Thread myThread = new Thread(new Runnable()
		{
			@Override
			public void run() 
			{
				try 
				{
					//String[] command = {"cmd.exe", "start", "", "/C" , "d: && cd LocalRepo && hg serve"};
					String command = "cmd /c  " + "D:\\LocalRepo\\getChangesFromRepo.bat";
					//ShellCommandExecutor.executeShellCommandUsingBuilder(killcmd);
					ShellCommandExecutor.executeShellCommand(command);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
			}
		}, "cmdThread");
		//myThread.start();

		getChanges();
	}

	/** Get changes from repository using hg status
	 * 
	 * @return
	 */
	public List<String> getChanges()
	{
		List<String> changes = new ArrayList<String>();
		List<String> edits = new ArrayList<String>();
		List<String> adds = new ArrayList<String>();
		List<String> deletes = new ArrayList<String>();
		try 
		{
			changes = ShellCommandExecutor.executeAndReturn(command);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		for(int i = 0; i < changes.size(); i++) 
		{
			if(changes.get(i).substring(0, 1).equals(modified))
			{
				edits.add(localRepositoryPath + "\\" + splitString(changes.get(i)));
			}
			else if(changes.get(i).substring(0, 1).equals(added))
			{
				adds.add(localRepositoryPath + "\\" +splitString(changes.get(i)));
			}
			else if(changes.get(i).substring(0, 1).equals(removed))
			{
				deletes.add(localRepositoryPath + "\\" + splitString(changes.get(i)));
			}
		}
		setEditedFiles(edits);
		setAddedFiles(adds);
		setDeletedFiles(deletes);
		return changes;
	}

	/** Split string on space and return second part
	 * 
	 * @param string
	 * @return
	 */
	private String splitString(String string) 
	{
		String[] stringArray = string.split(" ");
		return stringArray[1];
	}

	@Override
	public List<String> getListOfAddedItems() 
	{
		return addedFiles;
	}

	@Override
	public List<String> getListOfEditedItems() 
	{
		return editedFiles;
	}

	@Override
	public List<String> getListOfDeletedItems() 
	{
		return deletedFiles;
	}
}
