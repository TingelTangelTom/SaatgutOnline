package filter;

import java.io.IOException;
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

		HttpSession session = ((HttpServletRequest) request).getSession(true); 
		Locale locale;
		
		/*
		 * wenn noch keine Sprache in der Session steht,
		 * eingestellte Sprache des Browsers als Defaultwert setzen.
		 */
		if(session.getAttribute("sprache") == null)
		{			
			session.setAttribute("sprache", request.getLocale());			
		}
		
		/*
		 * falls Sprachwahl erfolgt, entsprechende locale in die Session schreiben
		 */
		if (((HttpServletRequest) request).getParameter("sprache") != null)
		{
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
				locale = request.getLocale();
				break;
			}			
			session.setAttribute("sprache", locale);			
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
