package servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

/**
 * Servlet implementation class Konfiguration
 */
@WebServlet("/Konfiguration")
public class KonfigurationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public KonfigurationServlet() {
        super();
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig konfiguration) throws ServletException {
		
		super.init(konfiguration);
		getServletContext().setAttribute("dbHost", konfiguration.getInitParameter("dbHost"));
		getServletContext().setAttribute("dbPort", konfiguration.getInitParameter("dbPort"));
		getServletContext().setAttribute("datenbank", konfiguration.getInitParameter("datenbank"));
		getServletContext().setAttribute("dbBenutzer", konfiguration.getInitParameter("dbBenutzer"));
		getServletContext().setAttribute("dbPasswort", konfiguration.getInitParameter("dbPasswort"));
		getServletContext().setAttribute("BenutzernameRegel", konfiguration.getInitParameter("BenutzernameRegel"));
		getServletContext().setAttribute("PasswortRegel", konfiguration.getInitParameter("PasswortRegel"));
	}
}