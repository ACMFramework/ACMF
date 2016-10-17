/**
 * 
 */
package framework.DAL.GraphDb;

/**
 *
 */
public abstract class DaoFactory 
{
	/**
	 *
	 */
	public static final int NEO4J = 1;
	
	/**
	 *
	 */
	public static final int TITAN = 2; // etc.
	
	/**
	 *
	 */
	public abstract IGraphStoreDao getGraphStoreDAO();
	
	/**
	 *
	 */
	public static IGraphStoreDao getDataStore(int dataStore) 
	{ 
		switch (dataStore) {
		case NEO4J: 
           return new Neo4jEmbeddedDao();
		default : 
			return null;
		}
    }
}
