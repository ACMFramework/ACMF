package framework.artefactRepresentation;

/**
 * 
 *
 */
public class Field extends TypeComponent implements IField
{
	public Field()
	{
		super();
	}
	
	public Field(String id)
	{
		super(id);
	}
	
	public Field(String name, String id)
	{
		super(name, id);
	}
	
	public Field(String name, String id, String type)
	{
		super(name, id, type);
	}
	
	public Field(String name, String id, String type, String visibility)
	{
		super(name, id, type, visibility);
	}
	
	public Field(String name, String id, String type, String visibility, String returnType)
	{
		super(name, id, type, visibility, returnType);
	}
}
