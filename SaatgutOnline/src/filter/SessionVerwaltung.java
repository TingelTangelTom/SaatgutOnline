package filter;
/**
 * @author Christof Weigandt
 * @version 0.1
 * Dieser Filter kontrolliert und verwaltet die Sessions
 * 
 */
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class SessionVerwaltung
 */
@WebFilter(description = "Universal-Filter f�r die Session-Verwaltung", urlPatterns = { "/*" })
public class SessionVerwaltung implements Filter {

    /**
     * Default constructor. 
     */
    public SessionVerwaltung() {
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
		
	
		// den ankommenden ServletRequest in einen HttpServletRequest casten,
		// damit die Session-Funktionen zur Verf�gung stehen
		
		
		// Alternative Methode
		
//		HttpSession session = request.getSession();
//		if (session.isNew()) {
//		    // Session wurde soeben erstellt
//		} else {
//		    // Wurde bereits fr�her erstellt
//		}
		
		// pr�fen, ob eine g�ltige Session vorhanden ist
		
		HttpSession session = ((HttpServletRequest) request).getSession(false);
		if (session == null) {
		    // Keine g�ltige Session vorhanden. Neue erstellen:
		    session = ((HttpServletRequest) request).getSession();
		} else {
		    // G�ltige Session gefunden
		}
		
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
