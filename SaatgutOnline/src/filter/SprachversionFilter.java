package filter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import controller.DatenbankController;

/**
 * Servlet Filter implementation class SprachversionFilter
 */
@WebFilter(filterName = "SprachversionFilter", urlPatterns = { "/*" })
public class SprachversionFilter implements Filter {
	
	
	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			Locale locale;
			if (session.getAttribute("sprache") == null) {
				locale = request.getLocale();
				if (!locale.equals(Locale.GERMAN)) {
					locale = Locale.ENGLISH;
				}
				session.setAttribute("sprache", locale);
				spracheIdInSessionLegen(session, locale);
			}
			if (((HttpServletRequest) request).getParameter("sprache") != null) {
				String sprachwahl = ((HttpServletRequest) request).getParameter("sprache");
				switch (sprachwahl) {
				case "de":
					locale = Locale.GERMAN;
					break;
				case "en":
					locale = Locale.ENGLISH;
					break;
				default:
					locale = Locale.ENGLISH;
					break;
				}
				session.setAttribute("sprache", locale);
				spracheIdInSessionLegen(session, locale);
			}
		}
		chain.doFilter(request, response);
	}

	
	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

	
	private void spracheIdInSessionLegen(HttpSession session, Locale locale) {
		String query = "SELECT sprache_id FROM sprache WHERE name = \"" + locale.getLanguage() + "\"";
		// Die nachfolgende Zeile nutzt Code von Christof Weigand
		ResultSet resultSet = DatenbankController.sendeSqlRequest(query);
		try {
			if (resultSet.next()) {
				session.setAttribute("spracheId", resultSet.getInt("sprache_id"));
			}
		} catch (SQLException e) {
		}
	}
}