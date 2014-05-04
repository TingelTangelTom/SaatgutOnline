package controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class KonfigurationController {
	
	private static int dbMaximalePoolgroesse = 0;
	private static String dbHost = null;
	private static String dbPort = null;
	private static String dbName = null;
	private static String dbBenutzer = null;
	private static String dbPasswort = null;
	private static String regelBenutzername = null;
	private static String regelPasswort = null;
	
	public KonfigurationController() {
	}
	
	public static void initialisiereKonfiguration(ServletConfig konfiguration) {
		
		System.out.println("KonfigurationController gestartet");
		File xmlDatei =	new File(konfiguration.getServletContext().
				getRealPath("\\resources\\xml\\Konfiguration.xml"));
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		
		try {
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document dokument = dBuilder.parse(xmlDatei);
			dokument.getDocumentElement().normalize();	// TODO optional!!!
	 
			NodeList nodeListe = dokument.getElementsByTagName("konfiguration");
		
			Node node = nodeListe.item(0);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				KonfigurationController.dbHost =
					element.getElementsByTagName("dbHost").item(0).getTextContent();
				KonfigurationController.dbPort =
					element.getElementsByTagName("dbPort").item(0).getTextContent();
				KonfigurationController.dbName =
					element.getElementsByTagName("dbName").item(0).getTextContent();
				KonfigurationController.dbBenutzer =
					element.getElementsByTagName("dbBenutzer").item(0).getTextContent();
				KonfigurationController.dbPasswort =
					element.getElementsByTagName("dbPasswort").item(0).getTextContent();
				KonfigurationController.dbMaximalePoolgroesse =
					Integer.parseInt(element.getElementsByTagName("dbMaximalePoolgroesse").item(0).getTextContent());
				KonfigurationController.regelBenutzername  =
						element.getElementsByTagName("regelBenutzername").item(0).getTextContent();
				KonfigurationController.regelPasswort =
						element.getElementsByTagName("regelPasswort").item(0).getTextContent();
			}
			
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
		ConnectionPoolController.getInstance();	// Initialisierung
	}

	public static int getDbMaximalePoolgroesse() {
		return dbMaximalePoolgroesse;
	}

	public static String getDbHost() {
		return dbHost;
	}

	public static String getDbPort() {
		return dbPort;
	}

	public static String getDbName() {
		return dbName;
	}

	public static String getDbBenutzer() {
		return dbBenutzer;
	}

	public static String getDbPasswort() {
		return dbPasswort;
	}
	public static String getRegelBenutzername() {
		return regelBenutzername;
	}
	public static String getRegelPasswort() {
		return regelPasswort;
	}
}
