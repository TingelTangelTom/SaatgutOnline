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
		System.out.println("\n"
				+ "*****Warenkorb per GET");
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
				

	
		
		
		ProduktModel produktModel2 = new ProduktModel();
		produktModel2.setId(4);
		produktModel2.setKategorieId(9);
		produktModel2.setBestand(69);
		produktModel2.setName("Produkt-Dummy2");
		produktModel2.setBestellnummer("987654321");
		produktModel2.setPreisNetto(123.45);
		produktModel2.setPreisBrutto(234.56);
		produktModel2.setGewicht(9.87);
		
		int bestellmenge2 = 13;		
		
		
		
		
		//TODO remove
		System.out.println("\n*****Warenkorb per POST");
		WarenkorbController warenkorbController = new WarenkorbController(request, response, produktModel2, bestellmenge2);
		
		warenkorbController.warenkorbAnzeigen();
		
		
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);	
	}

}
