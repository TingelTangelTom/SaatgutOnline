package filter;

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
 * <p>
 * Die Klasse <code>SessionFilter</code> prueft auf eine gueltige Session und erzeugt ggfs. eine neue.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 * @see
 */
@WebFilter(description = "Universal-Filter für die Session-Verwaltung", urlPatterns = { "/*" })
public class SessionFilter implements Filter
{
	/**
	 * Konstruktor
	 */
	public SessionFilter()
	{
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * Der ankommende ServletRequest wird in einen HttpServletRequest gecastet, damit die Session-Funktionen zur
	 * Verfuegung stehen
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
		if (request instanceof HttpServletRequest)
		{
			HttpSession session = ((HttpServletRequest) request).getSession(false);
			if (session == null)
			{
				// Keine gueltige Session vorhanden. Neue erstellen:
				System.out.println("Session abgelaufen, neue erstellt");
				session = ((HttpServletRequest) request).getSession();
				// neue Sesison, daher den Standard "nicht angemeldet" zuweisen
				session.setAttribute("angemeldet", false);
				session.setAttribute("sortierung_reihenfolge", "ASC");
				session.setAttribute("erweitertesuche", "false");
				session.setAttribute("suchbegriff", "");
				session.setAttribute("suchen", "false");
				session.setAttribute("sortierung_produktanzahl", 7);
				session.setAttribute("sortierung_limit_von", 0);
				session.setAttribute("sortierung_sortierspalte", "pb.produkt_name");
				session.setAttribute("sortierung_sortierspalte_kuerzel", "pn");
			}
			else
			{
				// Gueltige Session gefunden
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
	}
}