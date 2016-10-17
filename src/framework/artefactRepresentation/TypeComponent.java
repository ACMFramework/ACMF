package framework.artefactRepresentation;

/**
 * 
 *
 */
public abstract class TypeComponent extends ObjectOrientedElement implements ITypeComponent 
{
	private String variableType;
	
	public String getVariableType() 
	{
		return variableType;
	}
	public void setVariableType(String variableType) 
	{
		this.variableType = variableType;
	}
	
	public TypeComponent()
	{
		super();
	}
	
	public TypeComponent(String id)
	{
		super(id);
	}
	
	public TypeComponent(String name, String id)
	{
		super(name, id);
	}
	
	public TypeComponent(String name, String id, String type)
	{
		super(name, id, type);
	}
	
	public TypeComponent(String name, String id, String type, String visibility)
	{
		super(name, id, type, visibility);
	}
	
	public TypeComponent(String name, String id, String type, String visibility, String returnType)
	{
		super(name, id, type, visibility);
		this.variableType = returnType;
	}
}
