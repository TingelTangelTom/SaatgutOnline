package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrierungErfolgreichView {

	public RegistrierungErfolgreichView (HttpServletRequest request, HttpServletResponse response) {
				
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
		
	public void outRegistrierungErfolgreich(HttpServletRequest request, HttpServletResponse response) {
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println("<h1>Registrierung erfolgreich</h1>");
		out.println("<p>Zum Abschluss Ihrer Registrierung brauchen Sie nur noch den Link"
				+ "in unserer E-Mail an Sie zu klicken.</p>");
	}		
}
