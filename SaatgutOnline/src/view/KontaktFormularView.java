package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class KontaktFormularView {
	
	public KontaktFormularView(HttpServletRequest request, HttpServletResponse response) {
		
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println();
	}
	
	public void outKontaktFormular(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>Kontakt</h1><p><label>SaatgutOnline GmbH<br>Am Waldrand 325<br>"
			    + "12325 Palmenhausen<br>E-Mail kontakt@saatgutonline.de<br>Tel 049-098-764512-0<br>"
			    + "Fax 049-098-764512-99 </label></p>"
			    + "<form action=/SaatgutOnline/KontaktFormular>"
			    + "<table width=\"200\" border=\"0\" cellpadding=\"1\" cellspacing=\"1\">"
			    + "<tr><td>Anrede:</td><td><select name=\"Anrede\" id=\"Anrede\" title=\"Anrede\">"
			    + "<option>Frau</option><option>Herr</option></select></td></tr>"
			    + "<tr><td><label for=\"Vorname\">Vorname:</label></td>"
			    + "<td><input name=\"Vorname\" type=\"text\" id=\"Vorname\" size=\"35\" maxlength=\"60\"></td>"
			    + "</tr><tr><td>Nachname:</td>"
			    + "<td><input name=\"Nachname\" type=\"text\" id=\"Nachname\" size=\"35\" maxlength=\"60\"></td></tr>"
			    + "<tr><td><label for=\"E-Mail\">E-Mail:</label></td>"
			    + "<td><input name=\"E-Mail\" type=\"text\" id=\"E-Mail\" size=\"35\" maxlength=\"60\"></td></tr>"
			    + "<tr><td>Betreff:</td><td><input name=\"Betreff\" type=\"text\" id=\"Betreff\" size=\"35\" maxlength=\"60\"></td></tr>"
			    + "<tr><td valign=\"top\">Nachricht:</td>"
			    + "<td><textarea name=\"Nachricht\" cols=\"30\" rows=\"10\" maxlength=\"900\" id=\"Nachricht\"></textarea></td></tr>"
			    + "<tr><td valign=\"top\">&nbsp;</td><td><div align=\"left\">"
			    + "<input name=\"submit\" type=\"submit\" id=\"submit\" formmethod=\"POST\" value=\"Senden\"></div></td></tr></table></form>");
	}
	
	
	
}