package controller;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import view.ProduktView;
import model.ProduktModel;



public class ProduktController {
	
	
	
	

	// Testabfrage
	public void outProdukt(int id) {
		ProduktView pv;
		ProduktModel produktModel;
		DatenbankController.getVerbindung();
		
		String query = "SELECT id, name FROM produkt WHERE id='1'";
			
		try {
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			produktModel = new ProduktModel(resultset.getInt(1), resultset.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
