package controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImpressumController
{

	private PrintWriter out;
		
	public ImpressumController(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated constructor stub
	}

	public void outImpressumInhalt()
	{
		out.println("<b>Impressum</b><p> Saatgutonline GmbH <br />"
				+ "Am Waldrand 325<br />12325 Palmenhausen</p>"
				+ "<p>Tel: 049-098-764512-0<br />Fax: 049-098-764512-99<br />"
				+ "E-Mail: saatgutonline@samen.de<br /></p><p>Geschäftsführer<br />        "
				+ "Laura Palmer<br /></p>        <p>        "
				+ "Registergericht: Amtsgericht Bremen,        "
				+ "HR 987654325    </p>                <p>        "
				+ "Umsatzsteuer-IdNr.: DE113254990        <br />"
				+ "Wirtschafts-IdNr.: DE 22325455          </p>                <br />"
				+ "<p><i>erstellt mit dem <a href=\"http://www.agb.de\">Impressum-Generator</a> von agb.de</i></p>");
	}
}
