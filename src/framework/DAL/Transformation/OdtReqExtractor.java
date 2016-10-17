package framework.DAL.Transformation;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.odftoolkit.odfdom.doc.OdfDocument;
import org.odftoolkit.odfdom.doc.OdfTextDocument;
import org.odftoolkit.odfdom.doc.office.OdfOfficeText;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 
 *
 */
public class OdtReqExtractor 
{

	// Method to convert an ODT input file to an XML file format that can be transformed using XSLT
	// https://incubator.apache.org/odftoolkit/odfdom/Layers.html
	// http://www.langintro.com/odfdom_tutorials/create_odt.html
	public Document convertODTInput(String odtPath) throws Exception
	{
		// Return a nodelist of the content required
		OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(odtPath);
		OdfOfficeText t = odt.getContentRoot();
		NodeList childNodes = ((Node) t).getChildNodes();

		XmlParser xmlCreator = new XmlParser();
		Document doc = xmlCreator.createNewDocument("requirement");

		for (int i = 0; i < childNodes.getLength(); i++) 
		{
			Node node = childNodes.item(i);
			Node copyNode = doc.importNode(node, true);
			doc.getDocumentElement().appendChild(copyNode);
		}
		return doc;
	}

	/** Method to extract and pre-process the text contents of an odt file 
	 * @param odtPath - the file path of the odt file
	 * @param outputPath - the file path of the output xml file
	 */
	public void produceXMLInput(String odtPath, String outputPath) throws Exception
	{
		// Return a nodelist of the content required
		OdfTextDocument odt = (OdfTextDocument) OdfDocument.loadDocument(odtPath);
		OdfOfficeText t = odt.getContentRoot();
		NodeList childNodes = ((Node) t).getChildNodes();

		// Create a new XML file and append nodes from Nodelist to it
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		Document doc = docBuilder.newDocument();
		Element rootElement = doc.createElement("requirement");
		doc.appendChild(rootElement);

		for (int i = 0; i < childNodes.getLength(); i++) 
		{
			Node node = childNodes.item(i);
			Node copyNode = doc.importNode(node, true);
			rootElement.appendChild(copyNode);
		}
		
		// Save file
		XmlParser parser = new XmlParser();	
		parser.saveFile(doc, outputPath);
	}
}
