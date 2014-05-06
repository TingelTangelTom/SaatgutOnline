package controller;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * <p>
 * Die Klasse UrlController stellt die Methoden
 * <code>zwischenzuspeicherndeUrlInSessionLegen()</code> und
 * <code>zwischengespeicherteUrlAusSessionHolen()</code> zur Verfügung.
 * </p>
 * <p>
 * Mittels dieser Methoden ist es moeglich, dynamisch auf die zuletzt
 * abgespeicherte Seite, oder auch speziell auf die zuletzt abgelegte
 * Produktseite (<i>Produktliste</i> bzw. <i>Produktinfo</i>) zu verlinken.
 * </p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class UrlController {
	private HttpSession session;
	private HttpServletRequest request;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>UrlController</code>.
	 * </p>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 */
	public UrlController(HttpServletRequest request) {
		this.request = request;
		this.session = request.getSession();
	}

	/**
	 * <p>
	 * Legt die zwischenzuspeichernde URL inklusive aller Paramater in der
	 * Session ab.
	 * </p>
	 * <p>
	 * Es wird automatisch zwischen <b>Produktseite</b> und <b>LetzteSeite</b>
	 * unterschieden: </br>enthaelt die URL den Teilstring "<b>/Produkt</b>", so
	 * wird die URL sowohl unter dem key <i>urlProduktseite</i> als auch
	 * <i>urlLetzteSeite</i> abgelegt. </br>Ist der Teilstring nicht enthalten,
	 * wird die URL nur unter <i>urlLetzteSeite</i> abgelegt.
	 * </p>
	 */
	public void urlInSessionLegen() {
		String zwischenzuspeicherndeUrl = this.request.getRequestURL().toString() + "?";

		Enumeration<String> paramaternamen = this.request.getParameterNames();
		while (paramaternamen.hasMoreElements()) {
			String parametername = paramaternamen.nextElement();
			zwischenzuspeicherndeUrl += parametername + "=" + this.request.getParameter(parametername) + "&";
		}

		if (zwischenzuspeicherndeUrl.lastIndexOf("&") == -1) {
			zwischenzuspeicherndeUrl = zwischenzuspeicherndeUrl
					.substring(0, zwischenzuspeicherndeUrl.indexOf("?"));
		} else {
			zwischenzuspeicherndeUrl = zwischenzuspeicherndeUrl.substring(0,
					zwischenzuspeicherndeUrl.lastIndexOf("&"));
		}

		if (this.request.getServletPath().contains("/Produkt")) {
			this.session.setAttribute("urlLetzteSeite", zwischenzuspeicherndeUrl);
			this.session.setAttribute("urlProduktseite", zwischenzuspeicherndeUrl);
		} else {
			this.session.setAttribute("urlLetzteSeite", this.request.getRequestURL().toString());
		}
	}

	/**
	 * <p>
	 * Holt die zwischengespeicherte URL inklusive aller Parameter unter dem Key
	 * <b>'url'+seite</b> aus der Session und gibt diese als
	 * <code>String<code> zurück.
	 * </p>
	 * 
	 * @param seite
	 *            - Seite, deren URL ausgegeben werden soll (<b>LetzteSeite</b>
	 *            oder <b>Produktseite</b>) als <code>String</code>
	 * @return Die URL mit allen Parametern als <code>String<code>
	 */
	public String urlAusSessionHolen(String seite) {
		return (String) this.session.getAttribute("url" + seite);
	}
}
