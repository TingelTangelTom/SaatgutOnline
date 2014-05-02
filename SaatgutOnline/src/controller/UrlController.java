package controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/**
 * Die Klasse UrlController stellt die Methoden
 * <code>zwischenzuspeicherndeUrlInSessionLegen()</code> und
 * <code>zwischengespeicherteUrlAusSessionHolen()</code>
 * zur Verfügung
 * 
 * @author tomw
 *
 */
public class UrlController
{	
	private HttpSession session;
	private HttpServletRequest request;
	
	/**
	 * Konstruktor der Klasse <code>UrlController</code>
	 * 
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * 
	 */
	public UrlController(HttpServletRequest request)
	{
		this.request = request;
		this.session = request.getSession();
	}
	
	/**
	 * Legt die aktuelle URL mit allen Parametern unter dem Key <b>'url'</b> in der HttpSession ab
	 */
	public void zwischenzuspeicherndeUrlInSessionLegen()
	{
		String zwischenzuspeicherndeUrl = this.request.getRequestURL().toString()+"?";
		
		Enumeration<String> paramaternamen = this.request.getParameterNames();
		while(paramaternamen.hasMoreElements())
		{
			String parametername = paramaternamen.nextElement();
			zwischenzuspeicherndeUrl += parametername + "=" + this.request.getParameter(parametername) + "&";
			System.out.println("key="+parametername);			
		}		
		zwischenzuspeicherndeUrl = zwischenzuspeicherndeUrl.substring(0, zwischenzuspeicherndeUrl.lastIndexOf("&"));
				
		this.session.setAttribute("url", zwischenzuspeicherndeUrl);
	}
	
	/**
	 * Holt die zwischengespeicherte URL inklusive aller Parameter unter dem Key <b>'url'</b> aus der
	 * HttpSession und gibt diese als <code>String<code> zurück
	 * 
	 * @return Gibt einen String zurück, der die URL mit allen Parametern enthält
	 */
	public String zwischengespeicherteUrlAusSessionHolen()
	{
		return (String) this.session.getAttribute("url");		
	}
}
