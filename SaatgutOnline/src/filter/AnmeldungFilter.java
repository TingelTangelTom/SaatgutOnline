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
 * <p>Die Klasse <code>AnmeldungFilter</code> pruef bei jedem Seitenaufruf,
 * ob Anmeldedaten im request als Parameter uebergeben wurden und leitet
 * diese gegebenenfalls an den AnmeldungController weiter.</p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
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
		if(request.getParameter("anmelden") != null)
		{
			// Controller starten und Anmeldedaten im request uegbergeben
			AnmeldungController anmeldungController = new AnmeldungController(
					request, response);
		}
		chain.doFilter(request, response);
	}

}
