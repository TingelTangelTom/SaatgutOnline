package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import view.ProduktView;
import model.ProduktModel;



public class ProduktController {
	
	ProduktView pv;
	ProduktModel produktModel;

	// Testabfrage
	public ProduktModel getProdukt(int id) {

		DatenbankController.getVerbindung();
		
		String query = "SELECT id, kategorie_id FROM produkt WHERE id=" + id;
			
		try {
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			
			produktModel.setNummer(resultset.getInt(1));
			produktModel.setKategorieId(resultset.getInt(2));
			
				
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return produktModel;
	}
}
