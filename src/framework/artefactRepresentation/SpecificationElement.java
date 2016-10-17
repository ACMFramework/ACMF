package framework.artefactRepresentation;

/**
 * 
 *
 */
public class SpecificationElement extends Element implements ISpecificationElement
{
	public String title = "";
	public String content = "";
	public String priority = "";
	public String specType = "";
	
	public String getTitle() 
	{
		return title;
	}

	public void setTitle(String title) 
	{
		this.title = title;
	}

	public String getContent() 
	{
		return content;
	}

	public void setContent(String content) 
	{
		this.content = content;
	}

	public String getPriority() 
	{
		return priority;
	}

	public void setPriority(String priority) 
	{
		this.priority = priority;
	}

	public String getSpecType() 
	{
		return specType;
	}

	public void setSpecType(String specType) 
	{
		this.specType = specType;
	}

	public SpecificationElement()
	{
		super();
	}

	public SpecificationElement(String id) 
	{
		super(id);
	}

	public SpecificationElement(String id, String name) 
	{
		super(id, name);
	}
	
	public SpecificationElement(String id, String name, String title) 
	{
		super(id, name);
		this.title = title;
	}
	
	public SpecificationElement(String id, String name, String title, String content) 
	{
		super(id, name);
		this.title = title;
		this.content = content;
	}
	
	public SpecificationElement(String id, String name, String title, String content, String priority) 
	{
		super(id, name);
		this.title = title;
		this.content = content;
		this.priority = priority;
	}
	
	public SpecificationElement(String id, String name, String title, String content, String priority, String specType) 
	{
		super(id, name);
		this.title = title;
		this.content = content;
		this.priority = priority;
		this.specType = specType;
	}
}
