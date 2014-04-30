package servlets;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.FussbereichView;

/**
 * Servlet implementation class FussbereichController
 */
@WebServlet("/Fussbereich")
public class FussbereichServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FussbereichServlet()
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
		
		// DEBUG-Ausgabe für request-Parameter (GET)
		//TODO remove
		{
		System.out.println("\n----(GET) request-Parameter----");		
		Enumeration<String> paramaters = request.getParameterNames();
		while (paramaters.hasMoreElements())
		{
			String name = paramaters.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " = " + value);			
		}		
		System.out.println("----------------------------------");
		}	
		System.out.println("\n---Session-Inhalte---");
		Enumeration<String> sessionParamaters = request.getSession().getAttributeNames();
		while (sessionParamaters.hasMoreElements())
		{
			String name = sessionParamaters.nextElement();
			System.out.println(name + " = " + request.getSession().getAttribute(name));			
		}
		System.out.println("---------------------");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{	
		// Fussbereichview schliessen
		FussbereichView fussbereichView = new FussbereichView(response);
		
		// Fussbereich oeffnen
		fussbereichView.outFussbereichAnfang();
		
		// Inhalte des Fussbereiches ausgeben
		fussbereichView.outFussbereichInhalt();
		
		// Fussbereich schliessen
		fussbereichView.outFussbereichEnde();
		
		
		
		// DEBUG-Ausgabe für request-Parameter (POST)
		//TODO remove
		{
		System.out.println("\n---(POST)  request-Parameter---");		
		Enumeration<String> paramaters = request.getParameterNames();
		while (paramaters.hasMoreElements())
		{
			String name = paramaters.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " = " + value);			
		}		
		System.out.println("----------------------------------");
		}		
		System.out.println("\n---Session-Inhalte---");
		Enumeration<String> sessionParamaters = request.getSession().getAttributeNames();
		while (sessionParamaters.hasMoreElements())
		{
			String name = sessionParamaters.nextElement();
			System.out.println(name + " = " + request.getSession().getAttribute(name));			
		}
		System.out.println("---------------------");
	
		
	}
}
