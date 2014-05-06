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
import view.ProduktlisteView;

/**
 * <p>Das Servlet <code>ProduktlisteServlet</code> ist für die Zusammenstellung des 
 * Frameworks zust&uuml;ndig.</p>
 * 
 * @author Simon Ankele
 * @version 1.0
 * @since 1.7.0_51
 * 
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
		
		response.setContentType("text/html");
	    response.setCharacterEncoding("ISO-8859-15");
	    	    
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
 		
		/*
		 * Die nächsten beiden Quellcode-Zeilen stammen von Tom Weigelt
		 */
		UrlController urlController = new UrlController(request);
		urlController.urlInSessionLegen();
		
		PrintWriter out = response.getWriter();
		out.print(this.produktliste.anzeigenProduktliste(request));


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