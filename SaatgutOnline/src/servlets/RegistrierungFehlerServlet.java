package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.RegistrierungFehlerController;

/**
 * <p>
 * Die Klasse <code>RegistrierungFehlerServlet</code> nimmt den request entgegen und erzeugt einen
 * <code>RegistrierungFehlerController</code>.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see RegistrierungFehlerController
 */
@WebServlet("/RegistrierungFehler")
public class RegistrierungFehlerServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegistrierungFehlerServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		RegistrierungFehlerController registrierungFehlerController = new RegistrierungFehlerController(request,
				response);
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
	}
}
