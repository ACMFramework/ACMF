package framework.changeDetection;

/**
 * Enum storing element type values
 *
 */
public enum ElementType 
{
	// Requirements are split into functional and non-functional subtypes, in this version
	// the type Functional is used
	Functional,
	Class,
	Method,
	Field, 
	Enum,
	Interface,
	UseCase,
	UseCase_Object_Class,
	Component,
	UMLOperation,
	UMLAttribute,
	Module;
	
	/** Return true if element is child/member element
	 * 
	 */
	 public static boolean isChild(String type) 
	 {
		 boolean isChild = type.equalsIgnoreCase("Functional") || type.equalsIgnoreCase("Method") 
				 || type.equalsIgnoreCase("UMLOperation") || type.equalsIgnoreCase("UMLAttribute") 
				 || type.equalsIgnoreCase("Field") || type.equalsIgnoreCase("UseCase_Object_Class")
				 || type.equalsIgnoreCase("UseCase") ? true : false;
		 return isChild;
	  }
	 
	 /** Return true if element is a container element
	  * 
	  * @param type
	  * @return
	  */
	 public static boolean isContainer(String type) 
	 {
		 boolean isContainer = type.equalsIgnoreCase("Class") || type.equalsIgnoreCase("Interface")
				 || type.equalsIgnoreCase("Enum") || type.equalsIgnoreCase("Component")
				 || type.equalsIgnoreCase("Module") ? true : false;
		 return isContainer;
	 }
	 
	 /**
	  * 
	  * @param type
	  * @return
	  */
	 public static boolean isContainer(ElementType type) 
	 {
		 boolean isContainer = type.toString().equalsIgnoreCase("Class") || type.toString().equalsIgnoreCase("Interface")
				 || type.toString().equalsIgnoreCase("Enum") || type.toString().equalsIgnoreCase("Component")
				 || type.toString().equalsIgnoreCase("Module") ? true : false;
		 return isContainer;
	 }
}
