package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.KasseController;
import controller.UrlController;

/**
 * Servlet implementation class KasseServlet
 */
@WebServlet("/Kasse")
public class KasseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KasseServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		
		UrlController urlController = new UrlController(request);
		urlController.urlInSessionLegen();
		
		if((boolean) request.getSession().getAttribute("angemeldet"))
		{
			KasseController kasseController = new KasseController(request, response);
			kasseController.outKasseAnzeigen();
		}
		else
		{
			rd = getServletContext().getRequestDispatcher("/Registrierung");
			rd.forward(request, response);	
		}
		
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
