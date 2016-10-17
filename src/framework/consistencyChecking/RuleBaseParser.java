package framework.consistencyChecking;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import framework.DAL.Transformation.XmlParser;
import framework.changeDetection.ArtefactElementChangeType;
import framework.changeDetection.ArtefactType;
import framework.changeDetection.ChangeData;
import framework.changeDetection.ElementType;
import framework.general.PropertyReader;
import framework.impactAnalysis.CustomNodeRepresentation;

/** Parse rule base and return corresponding rules
 *
 */
public class RuleBaseParser 
{
	/**
	 * 
	 */
	private XmlParser parser = new XmlParser();

	/**
	 * 
	 */
	private String container = "Container";

	/**
	 * 
	 */
	private String member = "Member";

	/**
	 * 
	 */
	private String deleteNodeName = "DeleteFileLevelChangeRule";

	/**
	 * 
	 */
	private String deleteArtefactNodeName = "DeleteArtefactElementLevelChangeRule";

	/**
	 * 
	 */
	private String editArtefactNodeName = "EditArtefactElementLevelChangeRule";

	/**
	 * 
	 */
	private String addArtefactNodeName= "AddArtefactElementLevelChangeRule";

	/**
	 * 
	 */
	private String deleteIntraNodeName = "DeleteIntraRule";

	/**
	 * 
	 */
	private String addIntraNodeName = "AddIntraRule";

	/**
	 * 
	 */
	private String editIntraNodeName = "EditIntraRule";

	/**
	 * 
	 */
	private String signatureValue = "Signature";

	/**
	 * 
	 */
	private static final String naValue = "NA";

	/**
	 * 
	 */
	private PropertyReader prop = new PropertyReader();

	/**
	 * 
	 */
	private String deleteXmlPath = prop.getProperties()[15];

	/**
	 * 
	 */
	private String editXmlPath = prop.getProperties()[16];

	/**
	 * 
	 */
	private String intraXmlPath = prop.getProperties()[17];

	/**
	 * 
	 * @param typeFromXml
	 * @param change
	 * @return
	 */
	private Boolean artefactTypesMatch(String typeFromXml, ArtefactType type) 
	{
		if (type.toString().equalsIgnoreCase(typeFromXml))
			return true;
		else
			return false;
	}

	/**
	 * 
	 * @param node
	 * @return
	 */
	private ArtefactType getArtefactType(String id) 
	{
		String artefactPrefix = id.substring(0, 2);
		// Get artefact type enum value based on artefact prefix
		ArtefactType artefact = ArtefactType.returnArtefactType(artefactPrefix);
		return artefact;
	}

	/** Return literal value "Container" if artefact element is container
	 * 
	 * @param type
	 * @return
	 */
	private String container(ElementType type) 
	{
		String cont = null;
		if (ElementType.isContainer(type)) 
			cont = container;
		else 
			cont = member;
		return cont;
	}

	/** Return literal value "Container" if artefact element is container
	 * 
	 * @param node
	 * @return
	 */
	private String container(String elementType) 
	{
		String cont = null;
		if (ElementType.isContainer(elementType))
			cont = container;
		else
			cont = member;
		return cont;
	}

	/**
	 * 
	 * @param value
	 * @return
	 */
	private Boolean isSignature(String value) 
	{
		if(value.equalsIgnoreCase(signatureValue))
			return true;
		else
			return false;
	}

	/** Find intra rules applicable for edit, delete and add artefact element level changes
	 * 
	 * @param change
	 * @param node
	 * @return
	 */
	public String findIntraRules(ChangeData change, CustomNodeRepresentation node) 
	{
		String rule = null;
		Document doc = null;
		try 
		{
			doc = parser.parseXMLFile(new File(intraXmlPath));
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
		// Parse add artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.ADD)) 
		{
			NodeList addRulesList = doc.getElementsByTagName(addIntraNodeName);

			for(int i = 0; i < addRulesList.getLength(); i ++) 
			{
				NodeList children = addRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								getArtefactType(node.getId()))
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(node.getType()))
						) 
				{
					rule = children.item(7).getTextContent();
				}
			}
		}
		// Parse delete artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.DELETE)) 
		{
			NodeList deleteRulesList = doc.getElementsByTagName(deleteIntraNodeName);
			for(int i = 0; i < deleteRulesList.getLength(); i ++) 
			{
				NodeList children = deleteRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								getArtefactType(node.getId()))
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(node.getType()))
						) 
				{
					rule = children.item(7).getTextContent();
					System.out.println(rule);
				}
			}
		}
		// Parse edit artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.EDIT)) 
		{
			NodeList editRulesList = doc.getElementsByTagName(editIntraNodeName);
			for(int i = 0; i < editRulesList.getLength(); i ++) 
			{
				NodeList children = editRulesList.item(i).getChildNodes();				
				if(change.getSignatureChange().equals(isSignature(children.item(1).getTextContent()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								change.getTypeOfArtefact())
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(change.getTypeOfElement_current()))
								&& artefactTypesMatch(children.item(5).getChildNodes().item(1).getTextContent(), 
										getArtefactType(node.getId()))
										&& children.item(5).getChildNodes().item(3).getTextContent().
										equalsIgnoreCase(container(node.getType()))
						) 
				{
					rule = children.item(9).getTextContent();
					System.out.println(rule);
				}
			}
		}
		return rule;
	}

	/** Find rule corresponding to given change for all connected elements 
	 * 
	 * @param change
	 * @param iaResult
	 */
	public String findDeleteInterRule(ChangeData change, CustomNodeRepresentation node) 
	{
		String rule = null;
		Document doc = null;
		try 
		{
			doc = parser.parseXMLFile(new File(deleteXmlPath));
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
		NodeList deleteRulesList = doc.getElementsByTagName(deleteNodeName);

		for(int i = 0; i < deleteRulesList.getLength(); i ++) 
		{
			NodeList children = deleteRulesList.item(i).getChildNodes();

			if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
					change.getTypeOfArtefact()) 
					&& children.item(1).getChildNodes().item(3).getTextContent().
					equalsIgnoreCase(container(change.getTypeOfElement_current()))
					&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
							getArtefactType(node.getId()))
							&& children.item(3).getChildNodes().item(3).getTextContent().
							equalsIgnoreCase(container(node.getType()))) 
			{
				rule = children.item(7).getTextContent();
				System.out.println(rule);
			}
		}
		return rule;
	}

	/** Find rule corresponding to given change for all connected elements 
	 * 
	 * @param change
	 * @param iaResult
	 */
	public String findEditInterRuleOriginal(ChangeData change, CustomNodeRepresentation node) 
	{
		String rule = null;
		Document doc = null;
		try 
		{
			doc = parser.parseXMLFile(new File(editXmlPath));
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
		// Parse add artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.ADD)) 
		{
			NodeList addRulesList = doc.getElementsByTagName(addArtefactNodeName);
			for(int i = 0; i < addRulesList.getLength(); i ++) 
			{
				NodeList children = addRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								getArtefactType(node.getParentId()))
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(node.getParentType()))
						) 
				{
					rule = children.item(7).getTextContent();
					System.out.println(rule);
				}
			}
		}
		// Parse delete artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.DELETE)) 
		{
			NodeList deleteRulesList = doc.getElementsByTagName(deleteArtefactNodeName);
			for(int i = 0; i < deleteRulesList.getLength(); i ++) 
			{
				NodeList children = deleteRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								getArtefactType(node.getId()))
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(node.getType()))
						) 
				{
					rule = children.item(7).getTextContent();
					System.out.println(rule);
				}
			}
		}
		// Parse edit artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.EDIT)) 
		{
			NodeList editRulesList = doc.getElementsByTagName(editArtefactNodeName);
			Boolean changedEntityMatch = false;
			Boolean connectedEntityMatch = false;
			
			for(int i = 0; i < editRulesList.getLength(); i ++) 
			{
				NodeList children = editRulesList.item(i).getChildNodes();

				if(!children.item(1).getTextContent().equalsIgnoreCase(naValue)) 
				{
					if(change.getSignatureChange().equals(isSignature(children.item(1).getTextContent()))
							&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
									change.getTypeOfArtefact())
									&& children.item(3).getChildNodes().item(3).getTextContent().
									equalsIgnoreCase(container(change.getTypeOfElement_current()))
									&& artefactTypesMatch(children.item(5).getChildNodes().item(1).getTextContent(), 
											getArtefactType(node.getId()))
											&& children.item(5).getChildNodes().item(3).getTextContent().
											equalsIgnoreCase(container(node.getType()))
							) 
					{
						rule = children.item(9).getTextContent();
						System.out.println(rule);
					}
				}
				else 
				{
					// Do not take into account signature values
					if(artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
							change.getTypeOfArtefact()) 
							&& children.item(3).getChildNodes().item(3).getTextContent().
							equalsIgnoreCase(container(change.getTypeOfElement_current()))
							&& artefactTypesMatch(children.item(5).getChildNodes().item(1).getTextContent(), 
									getArtefactType(node.getId()))
									&& children.item(5).getChildNodes().item(3).getTextContent().
									equalsIgnoreCase(container(node.getType()))
							)
					{
						rule = children.item(9).getTextContent();
						System.out.println(rule);
					}
				}
			}
		}
		return rule;
	}

	/** Find rule corresponding to given change for all connected elements 
	 * 
	 * @param change
	 * @param iaResult
	 */
	public String findEditInterRule(ChangeData change, CustomNodeRepresentation node) 
	{
		String rule = null;
		Document doc = null;
		try 
		{
			doc = parser.parseXMLFile(new File(editXmlPath));
		} 
		catch (ParserConfigurationException | SAXException | IOException e) 
		{
			e.printStackTrace();
		}
		// Parse add artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.ADD)) 
		{
			NodeList addRulesList = doc.getElementsByTagName(addArtefactNodeName);
			for(int i = 0; i < addRulesList.getLength(); i ++) 
			{
				NodeList children = addRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))
						&& artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
								getArtefactType(node.getId()))
								&& children.item(3).getChildNodes().item(3).getTextContent().
								equalsIgnoreCase(container(node.getType()))
						) 
				{
					rule = children.item(7).getTextContent();
					System.out.println(rule);
				}
			}
		}
		// Parse delete artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.DELETE)) 
		{
			NodeList deleteRulesList = doc.getElementsByTagName(deleteArtefactNodeName);
			Boolean changedEntityMatch = false;
			Boolean connectedEntityMatch = false;
			for(int i = 0; i < deleteRulesList.getLength(); i ++) 
			{
				NodeList children = deleteRulesList.item(i).getChildNodes();

				if(artefactTypesMatch(children.item(1).getChildNodes().item(1).getTextContent(), 
						change.getTypeOfArtefact()) 
						&& children.item(1).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(change.getTypeOfElement_current()))) 
				{
					changedEntityMatch = true;
				}
				if(artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
						getArtefactType(node.getId()))
						&& children.item(3).getChildNodes().item(3).getTextContent().
						equalsIgnoreCase(container(node.getType()))) 
				{
					connectedEntityMatch = true;
				}
				
				if(changedEntityMatch && connectedEntityMatch)
				{
					rule = children.item(7).getTextContent();
					System.out.println(rule);
				}
			}
		}
		// Parse edit artefact element level changes
		if(change.getTypeOfChange().equals(ArtefactElementChangeType.EDIT)) 
		{
			NodeList editRulesList = doc.getElementsByTagName(editArtefactNodeName);
			Boolean changedEntityMatch = false;
			Boolean connectedEntityMatch = false;
			Boolean signatureValuesMatch = false;
			
			for(int i = 0; i < editRulesList.getLength(); i ++) 
			{
				NodeList children = editRulesList.item(i).getChildNodes();
				if(!children.item(1).getTextContent().equalsIgnoreCase(naValue)) 
				{
					if(change.getSignatureChange().equals(isSignature(children.item(1).getTextContent())))
					{
						signatureValuesMatch = true;
					}
					if(artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
							change.getTypeOfArtefact())
							&& children.item(3).getChildNodes().item(3).getTextContent().
							equalsIgnoreCase(container(change.getTypeOfElement_current()))) 
					{
						changedEntityMatch = true;
					}
					
					if(artefactTypesMatch(children.item(5).getChildNodes().item(1).getTextContent(), 
							getArtefactType(node.getId()))
							&& children.item(5).getChildNodes().item(3).getTextContent().
							equalsIgnoreCase(container(node.getType()))) 
					{
						connectedEntityMatch = true;
					}
					if(signatureValuesMatch && changedEntityMatch && connectedEntityMatch) 
					{
						rule = children.item(9).getTextContent();
						System.out.println(rule);
					}
				}
				
				else 
				{
					// Do not take into account signature values
					if(artefactTypesMatch(children.item(3).getChildNodes().item(1).getTextContent(), 
							change.getTypeOfArtefact()) 
							&& children.item(3).getChildNodes().item(3).getTextContent().
							equalsIgnoreCase(container(change.getTypeOfElement_current()))
							&& artefactTypesMatch(children.item(5).getChildNodes().item(1).getTextContent(), 
									getArtefactType(node.getId()))
									&& children.item(5).getChildNodes().item(3).getTextContent().
									equalsIgnoreCase(container(node.getType()))
							)
					{
						rule = children.item(9).getTextContent();
						System.out.println(rule);
					}
				}
			}
		}
		return rule;
	}
}
