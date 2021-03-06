package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.VersandInfoController;

/**
 * <p>
 * Inkludiert Kopfbereich, Inhalt und Fussbereich.
 * </p>
 * <p>
 * Ruft den <code>VersandInfoController</code> auf.
 * </p>
 */
@WebServlet(description = "Versand & Zahlung", urlPatterns = { "/VersandInfo" })
public class VersandInfoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public VersandInfoServlet()
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
		// Inhalte ausgeben (per view!)
		new VersandInfoController(request, response);
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