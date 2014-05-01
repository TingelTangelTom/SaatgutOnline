package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import view.ProduktlisteView;

/**
 * Servlet implementation class ProduktlisteServlet
 */
@WebServlet("/Produktliste")
public class ProduktlisteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProduktlisteView produktliste;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProduktlisteServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.produktliste = new ProduktlisteView(request);
		//TODO Funktioniert nicht
		response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("ISO-8859-15");
	    	    
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
 		
		PrintWriter out = response.getWriter();
		
		response.setContentType("text/html; charset=UTF-8");
	    response.setCharacterEncoding("ISO-8859-15");
		out.print(this.produktliste.anzeigenProduktliste(request));

		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);
		
		Enumeration<String> paramaters = request.getParameterNames();
		while (paramaters.hasMoreElements())
		{
			String name = paramaters.nextElement();
			String value = request.getParameter(name);
			System.out.println(name + " = " + value);			
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}