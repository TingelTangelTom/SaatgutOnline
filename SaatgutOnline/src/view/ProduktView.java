package view;

import model.ProduktModel;
import controller.ProduktController;

public class ProduktView {
	private String htmlOutput;
	private ProduktModel pm;
	
	public ProduktView()
	{
		
	}
	
	public String outHtmlOutput() {
	
	
	
	htmlOutput = "<table border='0' width=100%  height=100% cellspacing='0' cellpadding='0'>"
					+ "<tr style=\"height:100;\">"
					+ "<td colspan=\"2\">"
					+ "Kopfbereich"
					+ "</td></tr><tr><td style=\"width:200;\">"
					+ "Navigationsbereich"
					+ "</td><td>";
	htmlOutput += pm.getName();
	htmlOutput += "Hauptseite"
					+ "</td></tr><tr style=\"height:100%;\">"
					+ "<td colspan=\"2\">"
					+ "Fussbereich"
					+ "</td></tr></table>";
	return htmlOutput;
	}
}
