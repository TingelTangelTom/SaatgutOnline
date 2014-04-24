package filter;

import java.io.IOException;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class SprachversionFilter
 */
@WebFilter(filterName = "SprachversionFilter", urlPatterns = {"/test*"})
public class SprachversionFilter implements Filter
{

	/**
	 * Default constructor.
	 */
	public SprachversionFilter()
	{
	}

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
		if(request.getAttribute("lang") == null)
		{
			Locale sprache = request.getLocale();
			System.out.println(sprache);
						
			String originalUrl = ((HttpServletRequest) request).getRequestURL().toString();
			RequestDispatcher rd = ((HttpServletRequest) request).getRequestDispatcher("/"+originalUrl + "&lang=" + sprache);
			rd.forward(request, response);
			
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
