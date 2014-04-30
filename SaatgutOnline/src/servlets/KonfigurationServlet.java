package servlets;

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

import java.io.File;

/**
 *  Konfigurations-Servlet, wird bei Serverstart geladen
 *  und parst Daten aus der Konfiguration.xml
 */
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
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document dokument = null;
		try {
			dokument = dBuilder.parse(xmlDatei);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		//optional, but recommended
		//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
//		dokument.getDocumentElement().normalize();
	 
		System.out.println("Root element :" + dokument.getDocumentElement().getNodeName());
		System.out.println(dokument.getElementsByTagName("dbHost"));
		System.out.println(dokument.getElementsByTagName("dbHost"));
		
//		dokument.getDocumentElement().
		
		
		//		NodeList nList = dokument.getElementsByTagName("staff");
		
		getServletContext().setAttribute("dbHost", konfiguration.getInitParameter("dbHost"));
		getServletContext().setAttribute("dbPort", konfiguration.getInitParameter("dbPort"));
		getServletContext().setAttribute("datenbank", konfiguration.getInitParameter("datenbank"));
		getServletContext().setAttribute("dbBenutzer", konfiguration.getInitParameter("dbBenutzer"));
		getServletContext().setAttribute("dbPasswort", konfiguration.getInitParameter("dbPasswort"));
		getServletContext().setAttribute("BenutzernameRegel", konfiguration.getInitParameter("BenutzernameRegel"));
		getServletContext().setAttribute("PasswortRegel", konfiguration.getInitParameter("PasswortRegel"));
	}
}