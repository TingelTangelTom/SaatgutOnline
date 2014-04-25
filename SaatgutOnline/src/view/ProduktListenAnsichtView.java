package view;

import controller.ProduktListenAnsichtController;
import model.ProduktModel;

public class ProduktListenAnsichtView {
	ProduktListenAnsichtController produktController;
	ProduktModel produktModel;
	private String htmlOutput;	
	public ProduktListenAnsichtView()
	{
		this.produktController = new ProduktListenAnsichtController();
		this.produktModel = new ProduktModel();
	}
	
	public String outHtmlOutput() {	
		
		/*produktModel = this.produktController.getProdukt(1);
		htmlOutput = "<table border='0' width=100%  height=100% cellspacing='0' cellpadding='0'>"
						+ "<tr style=\"height:100;\">"
						+ "<td colspan=\"2\">"
						+ "Kopfbereich"
						+ "</td></tr><tr><td style=\"width:200;\">"
						+ "Navigationsbereich"
						+ "</td><td>";		
		htmlOutput += produktModel.getKategorieId();
				//getProdukt(1).getKategorieId());
		htmlOutput += "Hauptseite"
						+ "</td></tr><tr style=\"height:100%;\">"
						+ "<td colspan=\"2\">"
						+ "Fussbereich"
						+ "</td></tr></table>";
		*/
		produktModel = this.produktController.getProdukt(1);
		htmlOutput = String.valueOf(produktModel.getKategorieId());
		return htmlOutput;
	}
	
}
