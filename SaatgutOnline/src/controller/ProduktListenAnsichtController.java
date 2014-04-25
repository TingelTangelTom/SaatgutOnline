package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import view.ProduktListenAnsichtView;
import model.ProduktModel;



public class ProduktListenAnsichtController {
	
	ProduktListenAnsichtView pv;
	ProduktModel produktModel;
	
	public ProduktListenAnsichtController() {
		super();
		this.produktModel = new ProduktModel();
	}

	// Testabfrage
	public ProduktModel getProdukt(int id) {

		DatenbankController.getVerbindung();
		
		String query = "SELECT produkt_id, kategorie_id FROM produkt WHERE produkt_id=" + id;
			
		try {
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			while(resultset.next()){
			produktModel.setNummer(resultset.getInt(1));
			produktModel.setKategorieId(resultset.getInt(2));
			}	
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return produktModel;
	}
}