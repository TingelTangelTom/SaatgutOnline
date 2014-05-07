package servlets;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import controller.KonfigurationController;

/**
 * <p>
 * Dieses <code>KonfigurationServlet</code> wird einmalig beim Serverstart ausgeführt. Die
 * Konfigurationseinstellungen aus WebContent\resources\xml\Konfiguration.xml werden geparst
 * und den entsprechenden Variablen zugewiesen.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see anmeldungVerarbeitungController
 */
@WebServlet("/Konfiguration")
public class KonfigurationServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public KonfigurationServlet()
	{
		super();
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig konfiguration) throws ServletException
	{
		super.init(konfiguration);
		KonfigurationController.initialisiereKonfiguration(konfiguration);
	}
}