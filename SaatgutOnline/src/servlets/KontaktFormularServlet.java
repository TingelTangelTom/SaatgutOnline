package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.KontaktFormularController;
import controller.UrlController;

/**
 * <p>
 * Inkludiert Kopfbereich, Inhalt und Fussbereich.
 * </p>
 * <p>
 * Ruft den <code>KontaktformularController</code> auf.
 * </p>
 */
@WebServlet(description = "Kontakt Formular", urlPatterns = { "/Kontakt" })
public class KontaktFormularServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KontaktFormularServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		UrlController urlController = new UrlController(request);
		urlController.urlInSessionLegen();
		// Inhalte ausgeben (per view!)
		KontaktFormularController kontaktFormularController = new KontaktFormularController(request, response);
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		doGet(request, response);
	}
}