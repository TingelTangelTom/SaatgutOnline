package filter;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
public class SprachversionFilter implements Filter
{
	/**
	 * @see Filter#destroy()
	 */
	public void destroy()
	{
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException
	{
System.out.println("\n---SprachversionsFilter---");
		DatenbankController.getVerbindung();
		HttpSession session = ((HttpServletRequest) request).getSession(); 
		Locale locale;
		
		/*
		 * wenn noch keine Sprache in der Session steht,
		 * Browsersprache auslesen. Wenn diese NICHT Deutsch ist,
		 * default auf english setzen.
		 */
		if(session.getAttribute("sprache") == null)
		{	
			
System.out.println("Keine Sprache in der Session hinterlegt.");

			locale = request.getLocale();
			
			if(! locale.equals(Locale.GERMAN))
			{
				locale = Locale.ENGLISH;
			}
			session.setAttribute("sprache", locale);
			spracheIdInSessionLegen(session, locale);			
		}
		
		/*
		 * falls Sprachwahl erfolgt, entsprechende locale in die Session schreiben
		 */
		if (((HttpServletRequest) request).getParameter("sprache") != null)
		{
System.out.println("Sprachauswahl per SprachversionFilter erfolgt.");			
			
			String sprachwahl = ((HttpServletRequest) request).getParameter("sprache"); // ParameterValue aus POST lesen
			switch (sprachwahl)
			{
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
System.out.println("---pre-processing  ende---");


		chain.doFilter(request, response);
	
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException
	{
	}
	
	private void spracheIdInSessionLegen(HttpSession session, Locale locale)
	{
		try {
			String query = "SELECT sprache_id FROM sprache WHERE name = \"" + locale.getLanguage() + "\"";
			
			Statement statement = DatenbankController.verbindung.createStatement();
			ResultSet resultset = statement.executeQuery(query);
			if(resultset.next()){
				session.setAttribute("spracheId", resultset.getInt(1));
System.out.println("sprache = " + locale.getLanguage());
System.out.println("spracheId = " + session.getAttribute("spracheId"));
			}
		} catch (SQLException e) {
			System.out.println("SELECT-Anweisung nicht ausgeführt!");
			e.printStackTrace();
		}
	}

}
