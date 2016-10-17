package framework.changeDetection;

/**
 * Enum storing artefact type values
 *
 */
public enum ArtefactType 
{
	JAVA_SOURCE_CODE,
	UML_CLASS_DIAGRAM,
	REQUIREMENT_SPECIFICATION,
	ARCHITECTURE,
	UNIT_TEST,
	USE_CASE,
	SEQUENCE_DIAGRAM,
	DOCUMENTATION,
	API,
	CONFIG_FILE,
	MODULE_VIEW_ARCHITECTURE;
	
	 /** Return value of artefact type
	  * 
	  * @param type
	  * @return
	  */
	 public static ArtefactType returnType(String type) 
	 {
		 ArtefactType artefact = null;
		 if(type.equalsIgnoreCase("API"))
			 artefact = ArtefactType.API;
		 if(type.equalsIgnoreCase("ARCHITECTURE"))
			 artefact = ArtefactType.ARCHITECTURE;
		 if(type.equalsIgnoreCase("CONFIG_FILE"))
			 artefact = ArtefactType.CONFIG_FILE;
		 if(type.equalsIgnoreCase("DOCUMENTATION"))
			 artefact = ArtefactType.DOCUMENTATION;
		 if(type.equalsIgnoreCase("JAVA_SOURCE_CODE"))
			 artefact = ArtefactType.JAVA_SOURCE_CODE;
		 if(type.equalsIgnoreCase("MODULE_VIEW_ARCHITECTURE"))
			 artefact = ArtefactType.MODULE_VIEW_ARCHITECTURE;
		 if(type.equalsIgnoreCase("REQUIREMENT_SPECIFICATION"))
			 artefact = ArtefactType.REQUIREMENT_SPECIFICATION;
		 if(type.equalsIgnoreCase("SEQUENCE_DIAGRAM"))
			 artefact = ArtefactType.SEQUENCE_DIAGRAM;
		 if(type.equalsIgnoreCase("UML_CLASS_DIAGRAM"))
			 artefact = ArtefactType.UML_CLASS_DIAGRAM;
		 if(type.equalsIgnoreCase("UNIT_TEST"))
			 artefact = ArtefactType.UNIT_TEST;
		 if(type.equalsIgnoreCase("USE_CASE"))
			 artefact = ArtefactType.USE_CASE;
		return artefact;
	 }
	 
	 /** Return value of artefact type based on artefact prefix
	  * 
	  * @param type
	  * @return
	  */
	 public static ArtefactType returnArtefactType(String type) 
	 {
		 ArtefactType artefact = null;
		 if(type.equalsIgnoreCase("AP"))
			 artefact = ArtefactType.API;
		 if(type.equalsIgnoreCase("AR"))
			 artefact = ArtefactType.ARCHITECTURE;
		 if(type.equalsIgnoreCase("CF"))
			 artefact = ArtefactType.CONFIG_FILE;
		 if(type.equalsIgnoreCase("DC"))
			 artefact = ArtefactType.DOCUMENTATION;
		 if(type.equalsIgnoreCase("SC"))
			 artefact = ArtefactType.JAVA_SOURCE_CODE;
		 if(type.equalsIgnoreCase("AR"))
			 artefact = ArtefactType.MODULE_VIEW_ARCHITECTURE;
		 if(type.equalsIgnoreCase("RQ"))
			 artefact = ArtefactType.REQUIREMENT_SPECIFICATION;
		 if(type.equalsIgnoreCase("SD"))
			 artefact = ArtefactType.SEQUENCE_DIAGRAM;
		 if(type.equalsIgnoreCase("DI"))
			 artefact = ArtefactType.UML_CLASS_DIAGRAM;
		 if(type.equalsIgnoreCase("UT"))
			 artefact = ArtefactType.UNIT_TEST;
		 if(type.equalsIgnoreCase("UC"))
			 artefact = ArtefactType.USE_CASE;
		return artefact;
	 }
}