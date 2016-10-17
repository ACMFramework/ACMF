package framework.artefactRepresentation;

/**
 * 
 *
 */
public class Method extends TypeComponent implements IMethod
{
	private String content;
	private String parameters;
	
	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

	public String getParameters() 
	{
		return parameters;
	}

	public void setParameters(String parameters) 
	{
		this.parameters = parameters;
	}
	
	public Method()
	{
		super();
	}
	
	public Method(String id)
	{
		super(id);
	}
	
	public Method(String name, String id)
	{
		super(name, id);
	}
	
	public Method(String name, String id, String type)
	{
		super(name, id, type);
	}
	
	public Method(String name, String id, String type, String visibility)
	{
		super(name, id, type, visibility);
	}
	
	public Method(String name, String id, String type, String visibility, String returnType)
	{
		super(name, id, type, visibility, returnType);
	}
	
	public Method(String name, String id, String type, String visibility, String returnType, String parameters)
	{
		super(name, id, type, visibility, returnType);
		this.parameters = parameters;
	}
	
	public Method(String name, String id, String type, String visibility, String returnType, String parameters, String content)
	{
		super(name, id, type, visibility, returnType);
		this.parameters = parameters;
		this.content = content;
	}
}
