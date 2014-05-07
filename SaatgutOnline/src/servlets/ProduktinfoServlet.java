package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import controller.UrlController;
import view.ProduktinfoView;

/**
 * <p>
 * Das Servlet <code>ProduktinfoServlet</code> ist für die Zusammenstellung des Frameworks zust&uuml;ndig.
 * </p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 */
@WebServlet("/Produktinfo")
public class ProduktinfoServlet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	private ProduktinfoView produktinfoView;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProduktinfoServlet()
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException
	{
		// TODO
		this.produktinfoView = new ProduktinfoView(request);
		int produktID;
		if (request.getParameter("produkt") != null)
		{
			produktID = Integer.parseInt(request.getParameter("produkt"));
		}
		else
		{
			produktID = 1;
		}
		response.setContentType("text/html");
		response.setCharacterEncoding("ISO-8859-15"); // Sonst wird das Euro-Symbol nicht angezeigt
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		/*
		 * Die nächsten beiden Quellcode-Zeilen stammen von Tom Weigelt
		 */
		UrlController urlController = new UrlController(request);
		urlController.urlInSessionLegen();
		PrintWriter out = response.getWriter();
		out.print(this.produktinfoView.anzeigenProduktinfo(produktID));
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