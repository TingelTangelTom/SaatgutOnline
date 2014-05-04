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
import javax.websocket.Session;

/**
 * Servlet Filter implementation class SessionVerwaltung
 */
@WebFilter(description = "Universal-Filter für die Session-Verwaltung", urlPatterns = { "/*" })
public class SessionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public SessionFilter() {
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
		// damit die Session-Funktionen zur Verfuegung stehen
		
		
		// Alternative Methode
		
//		HttpSession session = request.getSession();
//		if (session.isNew()) {
//		    // Session wurde soeben erstellt
//		} else {
//		    // Wurde bereits frueher erstellt
//		}
		
		// pr�fen, ob eine gueltige Session vorhanden ist
		
		if (request instanceof HttpServletRequest) {
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			if (session == null) {
			    // Keine gueltige Session vorhanden. Neue erstellen:
			    System.out.println("Session abgelaufen, neue erstellt");
				session = ((HttpServletRequest) request).getSession();
			    
			    // neue Sesison, daher den Standard "nicht angemeldet" zuweisen
			    session.setAttribute( "angemeldet", false);
				session.setAttribute("sortierung_reihenfolge", "ASC");
				session.setAttribute("sortierung_produktanzahl", 7);
				session.setAttribute("sortierung_limit_von", 0);
				session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
	      	    session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
			    
			} else {
			    // Gueltige Session gefunden
			}
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
