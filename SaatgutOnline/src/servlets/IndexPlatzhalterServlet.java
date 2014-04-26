
package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.DatenbankController;

/**
 * Servlet implementation class IndexPlatzhalter
 */
@WebServlet("/IndexPlatzhalter")
public class IndexPlatzhalterServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IndexPlatzhalterServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		
		/*
		 * DIES IST NUR EIN PLATZHALTER UND SIMULIERT DIE SPAETERE INDEX-DATEI!
		 * 
		 * Jedes Servlet fuer den Hauptbereich MUSS Kopfbereich & Fussbereich einbinden!! 
		 */
		DatenbankController.getVerbindung();
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/KopfbereichController");
		rd.include(request, response);
				
		
		
		// Inhalte ausgeben (per view!)
		/*
		 * Ich habe hier nur den PrintWriter genommen, weil ich nicht extra
		 * einen Controller und einen View bauen wollte...
		 * Normal so, wie auch z.B. in Kopf oder Navi!
		 */
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("Hauptseite");
		
		
		
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/FussbereichController");
		rd.include(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		doGet(request, response);	
	}

}
