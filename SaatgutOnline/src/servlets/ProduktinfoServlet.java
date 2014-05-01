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







import view.ProduktinfoView;


/**
 * Servlet implementation class Anzeige
 */
@WebServlet("/Produktinfo")
public class ProduktinfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ProduktinfoView produktinfoView;
       
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
		
		this.produktinfoView = new ProduktinfoView(request);
		int produktID = Integer.parseInt(request.getParameter("produkt"));
		response.setCharacterEncoding("ISO-8859-15"); // Sonst wird das Euro-Symbol nicht angezeigt
		
		// Kopfbereich (und damit auch Navigationsbereich) einbinden
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
				
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.print(this.produktinfoView.anzeigenProduktinfo(produktID));

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
