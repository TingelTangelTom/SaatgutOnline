package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.FussbereichView;

/**
 * Servlet implementation class FussbereichController
 */
@WebServlet("/FussbereichController")
public class FussbereichController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FussbereichController()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// Fussbereichview schliessen
		FussbereichView fussbereichView = new FussbereichView(response);
		
		// Fussbereich oeffnen
		fussbereichView.outFussbereichAnfang();
		
		// Inhalte des Fussbereiches ausgeben
		fussbereichView.outFussbereichInhalt();
		
		// Fussbereich schliessen
		fussbereichView.outFussbereichEnde();
		
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
