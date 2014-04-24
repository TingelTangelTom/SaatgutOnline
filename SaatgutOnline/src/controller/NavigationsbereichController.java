package controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.NavigationsbereichView;

/**
 * Servlet implementation class NavigationsbereichController
 */
@WebServlet("/NavigationsbereichController")
public class NavigationsbereichController extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NavigationsbereichController()
	{
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// NavigationsbereichView erzeugen
		NavigationsbereichView navigationsbereichView = new NavigationsbereichView(response);
		
		// Navigationsbereich oeffnen
		navigationsbereichView.outNavigationsabereichAnfang();
		
		// Inhalte des Navigationsbereiches ausgeben
		navigationsbereichView.outNavigationsbereichInhalt();
		
		//Navigationsbereich schliessen
		navigationsbereichView.outNavigationsbereichEnde();
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
