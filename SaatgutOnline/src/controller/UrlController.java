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
 * @author Tom Weigelt
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
	public void urlInSessionLegen()
	{
		String zwischenzuspeicherndeUrl = this.request.getRequestURL().toString()+"?";
		
		Enumeration<String> paramaternamen = this.request.getParameterNames();
		while(paramaternamen.hasMoreElements())
		{
			String parametername = paramaternamen.nextElement();
			zwischenzuspeicherndeUrl += parametername + "=" + this.request.getParameter(parametername) + "&";
		}		
		
		
		if(zwischenzuspeicherndeUrl.lastIndexOf("&") == -1)
		{
			zwischenzuspeicherndeUrl = zwischenzuspeicherndeUrl.substring(0, zwischenzuspeicherndeUrl.indexOf("?"));
		}
		else
		{
			zwischenzuspeicherndeUrl = zwischenzuspeicherndeUrl.substring(0, zwischenzuspeicherndeUrl.lastIndexOf("&"));
		}
				
		if(this.request.getServletPath().contains("/Produktliste")
				|| this.request.getServletPath().contains("/Produktinfo"))
		{
			this.session.setAttribute("urlLetzteSeite", zwischenzuspeicherndeUrl);
			this.session.setAttribute("urlProduktseite", zwischenzuspeicherndeUrl);
		}
		else
		{
			this.session.setAttribute("urlLetzteSeite", request.getRequestURL().toString());			
		}
	}
	
	
	/**
	 * Holt die zwischengespeicherte URL inklusive aller Parameter unter dem Key <b>'url'+bereich</b> aus der
	 * HttpSession und gibt diese als <code>String<code> zurück
	 * 
	 * @param Der Bereich, von dem aus zur URL zurückgekehrt werden soll (zB. Fussbereich) als <code>String</code>
	 * @return Die URL mit allen Parametern als <code>String<code>
	 */
	public String urlAusSessionHolen(String bereich)
	{
		return (String) this.session.getAttribute("url"+bereich);		
	}
}
