package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.AnmeldungErfolgreichController;
import controller.RegistrierungController;

/**
 * <p>Die Klasse <code>AnmeldungErfolgreichServlet</code>
 * nimmt den request entgegen und erzeugt einen  <code>AnmeldungErfolgreichController</code>.
 * </p>
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see AnmeldungErfolgreichController
 */

@WebServlet("/AnmeldungErfolgreich")
public class AnmeldungErfolgreichServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AnmeldungErfolgreichServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = getServletContext().getRequestDispatcher("/Kopfbereich");
		rd.include(request, response);
		AnmeldungErfolgreichController anmeldungErfolgreichController = new AnmeldungErfolgreichController(request, response);
		rd = getServletContext().getRequestDispatcher("/Fussbereich");
		rd.include(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
