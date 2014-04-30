package filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.xml.ws.Response;

import controller.AnmeldungController;

/**
 * Servlet Filter implementation class AnmeldungFilter
 */
@WebFilter(description = "Anmeldedaten verarbeiten", urlPatterns = { "/*" })
public class AnmeldungFilter implements Filter {

	FilterConfig config;

    /**
     * Default constructor. 
     */
    public AnmeldungFilter() {
    }
	
	public void setFilterConfig(FilterConfig config) {
	this.config = config;
	}

	public FilterConfig getFilterConfig() {
	return config;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig config) throws ServletException {
	setFilterConfig(config);
	}	
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 ServletContext context = getFilterConfig().getServletContext();
		// Wurden Formulardaten uebertragen?
		if(request.getParameter("login") != null)
		{
			System.out.println("LOGIN erfolgt!");
			System.out.println("Benutzername: " + request.getParameter("benutzername"));
			System.out.println("Passwort: " + request.getParameter("passwort"));

			// Controller starten und Anmeldedaten uegbergeben
			AnmeldungController anmeldungController = new AnmeldungController(
					request, response, context);

		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}



}
