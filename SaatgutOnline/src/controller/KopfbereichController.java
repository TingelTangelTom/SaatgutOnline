package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.KopfbereichView;

/**
 * Servlet implementation class KopfbereichController
 */
@WebServlet("/KopfbereichController")
public class KopfbereichController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KopfbereichController()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// KopfbereichView erzeugen
		KopfbereichView kopfbereichView = new KopfbereichView(response);
		
		// Kopfbereich oeffnen
		kopfbereichView.outKopfbereichAnfang();
		
		// Inhalt des Kopfbereiches ausgeben
		kopfbereichView.outKopfbereichInhalt();
		
		// Kopfbereich schließen, Navigationsbereich oeffnen
		kopfbereichView.outKopfbereichMitte();
		
		// Navigationsbereich einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/NavigationsbereichController");
		rd.include(request, response);
		
		// Navigationsbereich schließen
		kopfbereichView.outKopfbereichEnde();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		super.doGet(request, response);
	}

}
