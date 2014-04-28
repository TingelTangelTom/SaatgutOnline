 package servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.KopfbereichController;

/**
 * Servlet implementation class KopfbereichController
 */
@WebServlet("/Kopfbereich")
public class KopfbereichServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KopfbereichServlet()
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
		 * Hier ein FollowMe fuer meine Vorgehensweise.
		 * Bitte klauen, was gefaellt! =D
		 */
		
		/*
		 * Schritt 1:
		 * Controller initialisieren.
		 * 
		 * Ich schicke die response mit, da ich die spaeter fuer den
		 * PrintWriter im View brauche.
		 * 
		 * --> weiter in KopfbereichController
		 */
		KopfbereichController kopfbereichController = new KopfbereichController(request, response);
		
	
		/*
		 * SCHRITT 7:
		 * 
		 * Alles komplett ausgeben.
		 * 
		 * --> ENDE! =D
		 */
		kopfbereichController.outKopfbereichAnzeigen();
		
		
		
		// Navigationsbereich einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Navigationsbereich");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		System.out.println("\nPOST-Attribut(e) angenommen:");
		
		Enumeration<String> paramaters = request.getParameterNames();
		while (paramaters.hasMoreElements())
		{
			String name = paramaters.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " = " + value);			
		}
		
		System.out.println("");
		
		
		doGet(request, response);
	}

}
