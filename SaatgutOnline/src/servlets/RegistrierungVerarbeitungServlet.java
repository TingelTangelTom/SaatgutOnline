package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RegistrierungVerarbeitungController;

/**
 * <p>
 * Die Klasse <code>RegistrierungVerarbeitungServlet</code> nimmt den request entgegen und erzeugt einen
 * <code>RegistrierungVerarbeitungController</code>, der die weitere Datenverarbeitung uebernimmt.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungVerarbeitungController
 */
@WebServlet(name = "RegistrierungVerarbeitungServlet", urlPatterns = { "/RegistrierungVerarbeitung" })
public class RegistrierungVerarbeitungServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrierungVerarbeitungServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		RegistrierungVerarbeitungController registrierungVerarbeitungController = new RegistrierungVerarbeitungController(
				request, response);
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
	}
}