package servlets;

import java.io.IOException;
import java.io.PrintWriter;




import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.ProduktModel;
import controller.ProduktController;
import view.ProduktView;


/**
 * Servlet implementation class Anzeige
 */
@WebServlet("/Anzeige")
public class Anzeige extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Anzeige() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ProduktModel pm = new ProduktModel();
		ProduktView pv = new ProduktView();
		ProduktController pc = new ProduktController();
		
		
		
		// Inhalte ausgeben (per Servlet & view!)
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println(pv.outHtmlOutput());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
