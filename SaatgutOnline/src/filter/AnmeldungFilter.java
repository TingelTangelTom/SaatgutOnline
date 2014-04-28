package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import controller.AnmeldungController;

/**
 * Servlet Filter implementation class AnmeldungFilter
 */
@WebFilter(description = "Anmeldedaten verarbeiten", urlPatterns = { "/AnmeldungFilter" })
public class AnmeldungFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AnmeldungFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// Wurden Formulardaten uebertragen?
		if(request.getParameter("login") != null)
		{
			System.out.println("LOGIN erfolgt!");
			System.out.println("Nutzername: " + request.getParameter("nutzername"));
			System.out.println("Passwort: " + request.getParameter("passwort"));

			// Controller starten und Anmeldedaten uegbergeben
			AnmeldungController anmeldungController = new AnmeldungController(request.getParameter("nutzername"), request.getParameter("passwort"));

		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
