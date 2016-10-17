package framework.general;

import java.io.IOException;
import java.io.InputStream;

/** Setup framework config file
 *
 */
public class ConfigurationHandler
{
	private static PropertyWriter propWriter = new PropertyWriter();
	
	public static void doConfiguration(String rootFolderPath, String dbPath, String remoteRepo, String localRepo) throws IOException 
	{
		/*prop.writeProperty("frameworkPath", rootFolderPath);
		prop.writeProperty("dbPath", dbPath);
		prop.writeProperty("remoteRepo", remoteRepo);
		prop.writeProperty("localRepo", localRepo);*/
		propWriter.createPropertyFile(rootFolderPath, dbPath, remoteRepo, localRepo);
	}
}