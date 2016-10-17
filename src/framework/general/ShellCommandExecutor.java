package framework.general;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Functionality to execute shell commands
 */
public class ShellCommandExecutor 
{
	/** Execute command with arguments
	 * @param arguments - the arguments of the shell command represented by a string array
	 */
	public static void execute(String[] command) throws IOException 
	{
		Process proc = Runtime.getRuntime().exec(command);
		BufferedReader error = new BufferedReader(
				new InputStreamReader(proc.getErrorStream()));
		String line = null;
		while ((line = error.readLine()) != null)
		{
			System.out.println(line);
		}
	}

	/** Execute command
	 * @param command
	 */
	public static void execute(String command) throws IOException 
	{
		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String outputString = null;
		List<String> outputs = new ArrayList<String>();
		while ((outputString = stdInput.readLine()) != null) 
		{
			outputs.add(outputString);
			System.out.println(outputString);
		}
		
		BufferedReader error = new BufferedReader(
				new InputStreamReader(proc.getErrorStream()));
		String line = null;
		while ((line = error.readLine()) != null)
		{
			System.out.println(line);
		}
	}
	
	/** Execute command and return the output lines
	 * @param command
	 */
	public static List<String> executeAndReturn(String command) throws IOException 
	{
		List<String> outputs = new ArrayList<String>();
		Process proc = Runtime.getRuntime().exec(command);

		BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
		String outputString = null;
		
		while ((outputString = stdInput.readLine()) != null) 
		{
			outputs.add(outputString);
			System.out.println(outputString);
		}
		
		BufferedReader error = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
		String line = null;
		while ((line = error.readLine()) != null)
		{
			System.out.println(line);
		}
		
		return outputs;
	}

	/** Execute command passed in
	 * @param command
	 */
	public static String executeShellCommand(String command) throws IOException 
	{
		StringBuffer output = new StringBuffer();

		Process process;
		try 
		{
			process = Runtime.getRuntime().exec(command);
			process.waitFor();

			BufferedReader reader = 
					new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line = "";			
			while ((line = reader.readLine())!= null) 
			{
				output.append(line + "\n");
				System.out.println(">" + line);
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return output.toString();
	}

	/** Execute command passed in
	 * @param command
	 * @return 
	 */
	public static void executeShellCommandUsingBuilder(String [] command) throws IOException 
	{

		ProcessBuilder probuilder = new ProcessBuilder(command);
		//You can set up your work directory
		probuilder.directory(new File("C:\\Users\\Ildi\\workspace\\Refactored"));
		Process process = probuilder.start();

		StringBuilder str = new StringBuilder();
		BufferedReader err = new BufferedReader((new InputStreamReader(process.getErrorStream())));
		String line2;
		System.out.printf("ERROR: ",
				Arrays.toString(command));

		while ((line2 = err.readLine()) != null) 
		{
			System.out.println(line2);
		}


		//Read out dir output
		InputStream is = process.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		String line;
		System.out.printf("Output of running %s is:\n",
				Arrays.toString(command));
		while ((line = br.readLine()) != null) {
			System.out.println(line);
		}

		//Wait to get exit value
		try {
			int exitValue = process.waitFor();
			System.out.println("\n\nExit Value is " + exitValue);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
