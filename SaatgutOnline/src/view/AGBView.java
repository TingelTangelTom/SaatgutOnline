package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.UrlController;

/**
 * Diese Klasse ist für die Ausgabe der AGB zuständig.
 * 
 * @author Anja
 *
 */
public class AGBView {

	public AGBView(HttpServletRequest request, HttpServletResponse response, String agbText) {

		UrlController urlController = new UrlController(request);

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println(agbText);
		out.println("<br><br><a href=\"" + urlController.urlAusSessionHolen("LetzteSeite")
				+ "\">&#11013 Zurück</a>");
	}
}
