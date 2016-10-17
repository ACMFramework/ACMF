package framework.general;

import java.util.ArrayList;
import java.util.List;

/** Orchestrate execution of individual components' functionality
 * 
 *
 */
public class SequentialExecutionManager implements IExecutionManager 
{
	/**
	 * 
	 */
	private final List<IExecutable> executableComponents = new ArrayList<>();

	/** Register component with manager
	 * 
	 */
	@Override
	public void register(IExecutable component) 
	{
		executableComponents.add(component);
	}
	
	/** Execute functionality of registered components sequentially
	 * 
	 */
	public void doProcess() 
	{
		for (IExecutable comp : executableComponents)
		{
			comp.Execute();
		}
	}
}