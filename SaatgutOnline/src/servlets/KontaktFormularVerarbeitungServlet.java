package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.KontaktFormularVerarbeitungController;

/**
 * Servlet implementation class KontaktFormularVerarbeitungServlet
 * <p>
 * Inkludiert Kopfbereich, Inhalt und Fussbereich.
 * </p>
 * <p>
 * Ruft den <code>KontaktformularVerarbeitungController</code> auf.
 * </p>
 */
@WebServlet("/KontaktFormularVerarbeitung")
public class KontaktFormularVerarbeitungServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	// Konstruktor
	public KontaktFormularVerarbeitungServlet()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		// Inhalte ausgeben (per view!)
		KontaktFormularVerarbeitungController kontaktFormularVerarbeitungController = new KontaktFormularVerarbeitungController(
				request, response);
		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		doGet(request, response);
	}
}