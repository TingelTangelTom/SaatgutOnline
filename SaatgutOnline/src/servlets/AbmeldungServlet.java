package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AbmeldungController;

/**
 * <p>
 * Die Klasse <code>AbmeldungServlet</code> erzeugt einen <code>AbmeldungController</code>, der die weitere
 * Verarbeitung uebernimmt. <code>AbmeldungView</code>
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AbmeldungController
 */
@WebServlet("/Abmeldung")
public class AbmeldungServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AbmeldungServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		AbmeldungController abmeldungController = new AbmeldungController(request, response);
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
	}
}
