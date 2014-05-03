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

import controller.ConnectionPoolController;
import controller.DatenbankController;
import controller.EmailController;
import controller.KonfigurationController;

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
		KonfigurationController.initialisiereKonfiguration(konfiguration);
	}
}