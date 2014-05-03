package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import view.HtmlAusgabe;
import view.ProduktlisteView;
import view.SuchergebnisseView;
import controller.ProduktController;
import controller.UrlController;

/**
 * Servlet implementation class SuchergebnisseServlet
 */
@WebServlet("/SuchergebnisseServlet")
public class SuchergebnisseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private SuchergebnisseView suchergebnisseView;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SuchergebnisseServlet(HttpServletRequest request) {
        super();

    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.suchergebnisseView = new SuchergebnisseView(request);
		//TODO Funktioniert nicht
		response.setContentType("text/html");
	    response.setCharacterEncoding("UTF-8");
	    	    
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
 		
		// UrlController
		UrlController urlController = new UrlController(request);
		urlController.urlInSessionLegen();
		
		
		PrintWriter out = response.getWriter();

		out.print(this.suchergebnisseView.anzeigenSuchergebnisse());

		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
		/*
		Enumeration<String> paramaters = request.getParameterNames();
		while (paramaters.hasMoreElements())
		{
			String name = paramaters.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " = " + value);			
		}
		*/

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
