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
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class SprachversionFilter
 */
@WebFilter(filterName = "SprachversionFilter", urlPatterns = {"/nofunction*"})
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
			
			
			
			String originalUrl = ((HttpServletRequest) request).getRequestURI().toString();
			System.out.println(originalUrl);
			
			String[] arr = originalUrl.split("/");
			for (int i = 0; i < arr.length; i++)
			{
				System.out.println("arr" + i + ": " + arr[i]);
			}
			
			String neueUrl = arr[arr.length-1];
			System.out.println("/" + neueUrl + "?lang=" + sprache);
			
			
			
			RequestDispatcher rd = ((HttpServletRequest) request).getRequestDispatcher("/" + neueUrl + "?lang=" + sprache);
			rd.forward(request, response);
			
			((HttpServletResponse)response).sendRedirect(neueUrl);
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
