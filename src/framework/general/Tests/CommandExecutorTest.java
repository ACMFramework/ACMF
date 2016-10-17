package framework.general.Tests;

import java.io.IOException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import framework.general.ShellCommandExecutor;

/**
 */
public class CommandExecutorTest 
{

	/**
	 *
	 */
	private static ShellCommandExecutor cmde;
	
	/**
	 *
	 */
	@BeforeClass
	public static void setup() 
	{
		cmde = new ShellCommandExecutor();
		
	}

	/**
	 *
	 */
	@AfterClass
	public static void tearDown() 
	{

	}

	/**
	 * @throws IOException 
	 *
	 */
	
	public void testCommandExecution() throws IOException 
	{
		String[] args = new String[]{ "java", "-version" };
		cmde.execute(args);
	}
	
	@Test
	public void testBuilder() throws IOException 
	{
		String[] command = {"cmd.exe", "start", "/k", "D:\\delme\\1.bat"};
		ShellCommandExecutor.executeShellCommandUsingBuilder(command);
		
		System.out.println("Yes");
	}

}
