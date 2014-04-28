package servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import controller.DatenbankController;
import view.ProduktinfoView;


/**
 * Servlet implementation class Anzeige
 */
@WebServlet("/ProduktinfoServlet")
public class ProduktinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProduktinfoView produktView;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProduktinfoServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		this.produktView = new ProduktinfoView(request);
		
		response.setCharacterEncoding("ISO-8859-15"); // Sonst wird das Euro-Symbol nicht angezeigt
		DatenbankController.getVerbindung();
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/KopfbereichController");
		rd.include(request, response);
				
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(this.produktView.anzeigenProduktinfo());

		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/FussbereichController");
		rd.include(request, response);		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
