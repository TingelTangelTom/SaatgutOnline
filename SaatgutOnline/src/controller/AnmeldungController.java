package controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.KundeModel;

/**
 * <p>
 * Die Klasse <code>Anmeldungcontroller</code> pruef die Anmeldedaten und leitet je nach Ergebnis an entsprechende
 * Servlets weiter.
 * </p>
 * 
 * @author Christof Weigandt
 * @version 1.0
 * @since 1.7.0_51
 */
public class AnmeldungController
{
	private String benutzername;
	private String passwort;
	private String regelBenutzername;
	private String regelPasswort;
	private KundeModel kunde;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>Anmeldungcontroller</code>.
	 * </p>
	 * </br>Prueft die Anmeldedaten mit der Methode <code>pruefeAnmeldedaten()</code>. Die Pruefung erfolgt
	 * mehrstufig: Zunaechst wird auf formelle Gueltigkeit von Passwort und Benutzername gemaess Konfiguration.xml
	 * geprueft, erst danach wird ggfs. die Datenbank abgefragt, zunaechst Benutzername, dann ggfs. zum Schluss das
	 * Passwort. Eine erfolgreiche Anmeldung setzt die Sessionvariable "angemeldet" auf true. <br />
	 * Je nach Ergebnis der Pruefung wird auf die entsprechenden Servlets weitergeleitet: </br>- AnmeldungFehler
	 * (Benutzername / Passwort falsch) </br>- AnmeldungRegistrierungFehler (Benutzer nicht mit Bestaetigungslink
	 * freigeschaltet) </br>- AnmeldungErfolgreich (Anmeldung erfolgt) </br>
	 * 
	 * @param request
	 *            - der aktuelle <code>HttpServletRequest</code>
	 * @param response
	 *            - die aktuelle <code>HttpServletResponse</code>
	 * @see AnmeldungFehler
	 * @see AnmeldungRegistrierungFehler
	 * @see AnmeldungErfolgreich
	 * @see KonfigurationController
	 * @see PasswortHashController
	 */
	public AnmeldungController(ServletRequest request, ServletResponse response)
	{
		this.benutzername = request.getParameter("benutzername");
		this.passwort = request.getParameter("passwort");
		switch (pruefeAnmeldedaten())
		{
		case 0:
			try
			{
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response)
						.encodeRedirectURL("AnmeldungFehler"));
			}
			catch (IOException e)
			{
			}
			break;
		case 1:
			try
			{
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response)
						.encodeRedirectURL("AnmeldungRegistrierungFehler")); // TODO erstellen!
			}
			catch (IOException e)
			{
			}
			break;
		case 2:
			HttpSession session = ((HttpServletRequest) request).getSession();
			session.setAttribute("angemeldet", true);
			session.setAttribute("benutzername", this.kunde.getBenutzername());
			try
			{
				((HttpServletResponse) response).sendRedirect(((HttpServletResponse) response)
						.encodeRedirectURL("AnmeldungErfolgreich"));
				return;
			}
			catch (IOException e)
			{
			}
		}
	}

	private int pruefeAnmeldedaten()
	{
		this.regelBenutzername = KonfigurationController.getRegelBenutzername();
		this.regelPasswort = KonfigurationController.getRegelPasswort();
		if (!(Pattern.matches(regelBenutzername, benutzername)) || !(Pattern.matches(regelPasswort, passwort)))
		{
			return 0;
		}
		this.kunde = KundeModel.ladeKundeAusDb(this.benutzername);
		if (this.kunde.getBenutzername() == null)
		{
			return 0;
		}
		String passwortHash = PasswortController.ladePasswortHashAusDb(this.kunde.getId());
		if (!(PasswortHashController.validierePasswort(this.passwort, passwortHash)))
		{
			return 0;
		}
		if (this.kunde.getFreigeschaltet() != 1)
		{
			return 1;
		}
		return 2;
	}
}