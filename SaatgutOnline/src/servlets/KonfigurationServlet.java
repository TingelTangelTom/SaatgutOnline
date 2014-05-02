package servlets;

/**
 * @author Christof Weigandt
 * @version 0.1
 * Dieses Konfigurations-Servlet wird einmalig beim Serverstart ausgeführt.
 * Die Konfigurationseinstellungen aus WebContent\resources\xml\Konfiguration.xml
 * werden geparst und den entsprechenden Variablen zugewiesen.
 * 
 */

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import controller.DatenbankController;
import controller.EMailController;

import java.io.File;


@WebServlet("/Konfiguration")
public class KonfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final boolean release = false;		// assertions enabled...
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	public KonfigurationServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig konfiguration) throws ServletException {
		
		super.init(konfiguration);
		
		File xmlDatei = new File(getServletContext().getRealPath("\\resources\\xml\\Konfiguration.xml"));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document dokument = dBuilder.parse(xmlDatei);
			dokument.getDocumentElement().normalize();	// TODO optional!!!
	 
			NodeList nodeListe = dokument.getElementsByTagName("datenbank");
		
			for (int i=0; i <nodeListe.getLength(); i++) {
				Node node = nodeListe.item(i);
				
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					DatenbankController.setDbHost(element.getElementsByTagName("dbHost").item(0).getTextContent());
					DatenbankController.setDbPort(element.getElementsByTagName("dbPort").item(0).getTextContent());
					DatenbankController.setDbName(element.getElementsByTagName("dbName").item(0).getTextContent());
					DatenbankController.setDbBenutzer(element.getElementsByTagName("dbBenutzer").item(0).getTextContent());
					DatenbankController.setDbPasswort(element.getElementsByTagName("dbPasswort").item(0).getTextContent());
				}
			}
			
//			nodeListe = dokument.getElementsByTagName("regeln");
//			
//			for (int i=0; i <nodeListe.getLength(); i++) {
//				Node node = nodeListe.item(i);
//				
//				if (node.getNodeType() == Node.ELEMENT_NODE) {
//					Element element = (Element) node;
//					
//					EMailController.setEmailRegel.(element.getElementsByTagName("emailregel").item(0).getTextContent());
//				}
//			}
//		
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}