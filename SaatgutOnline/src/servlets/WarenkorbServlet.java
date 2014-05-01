package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProduktModel;
import controller.WarenkorbController;

/**
 * Servlet implementation class WarenkorbServlet
 */
@WebServlet("/Warenkorb")
public class WarenkorbServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WarenkorbServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{	
		// Kopfbereich ausgeben
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
	
		//TODO remove
		System.out.println("*****Warenkorb per GET");
		WarenkorbController warenkorbController = new WarenkorbController(request, response);
		
		warenkorbController.warenkorbAnzeigen();
		
		
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);			
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		// Kopfbereich ausgeben
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
				
		//Produkt-Dummy erzeugen	
		ProduktModel produktModel = new ProduktModel();
		produktModel.setId(325);
		produktModel.setKategorieId(4);
		produktModel.setBestand(69);
		produktModel.setName("Produkt-Dummy");
		produktModel.setBestellnummer("987654321");
		produktModel.setPreisNetto(123.45);
		produktModel.setPreisBrutto(234.56);
		produktModel.setGewicht(9.87);	
		int bestellmenge = 7;
		
		//TODO remove
		System.out.println("*****Warenkorb per POST");
		WarenkorbController warenkorbController = new WarenkorbController(request, response, produktModel, bestellmenge);
		
		warenkorbController.warenkorbAnzeigen();
		
		
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);	
	}

}
