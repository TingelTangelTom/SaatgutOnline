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
import view.ProduktListenAnsichtView;


/**
 * Servlet implementation class Anzeige
 */
@WebServlet("/Anzeige")
public class ProduktListenAnsichtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProduktListenAnsichtView produktView;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProduktListenAnsichtServlet() {
        super();
        this.produktView = new ProduktListenAnsichtView();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		DatenbankController.getVerbindung();
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/KopfbereichController");
		rd.include(request, response);
				
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(this.produktView.anzeigenProduktAuflistung());

		// Fussbereich einbinden
		rd = getServletContext().getRequestDispatcher("/FussbereichController");
		rd.include(request, response);		
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
