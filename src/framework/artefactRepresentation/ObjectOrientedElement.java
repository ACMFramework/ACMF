package framework.artefactRepresentation;

/**
 *
 */
public class ObjectOrientedElement extends Element implements IObjectOrientedElement
{
	private String visibility;
	private String type;
	
	public String getVisibility() 
	{
		return visibility;
	}
	public void setVisibility(String visibility) 
	{
		this.visibility = visibility;
	}
	public String getType() 
	{
		return type;
	}
	public void setType(String type) 
	{
		this.type = type;
	}
	
	public ObjectOrientedElement()
	{
		super();
	}
	
	public ObjectOrientedElement(String id)
	{
		super(id);
	}
	
	public ObjectOrientedElement(String name, String id)
	{
		super(name, id);
	}
	
	public ObjectOrientedElement(String name, String id, String type)
	{
		super(name, id);
		this.type = type;
	}
	
	public ObjectOrientedElement(String name, String id, String type, String visibility)
	{
		super(name, id);
		this.type = type;
		this.visibility = visibility;
	}
}
