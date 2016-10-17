package framework.repoAccessor;

import java.io.IOException;

/** 
 *
 */
public abstract class RepoFactory 
{
	/**
	 *
	 */
	public static final int MERCURIAL = 1;
	
	/**
	 *
	 */
	public static final int SVN = 2; // etc.
	
	/**
	 *
	 */
	public abstract IRepoQuery getRepoQuery();
	
	/**
	 * @throws IOException 
	 *
	 */
	public static IRepoQuery getRepo(int repo) throws IOException 
	{ 
		switch (repo) 
		{
		case MERCURIAL: 
          return new MercurialRepoQuery();
		default : 
			return null;
		}
   }
}
