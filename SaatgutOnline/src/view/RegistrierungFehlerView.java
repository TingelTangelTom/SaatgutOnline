package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrierungFehlerView {

	public RegistrierungFehlerView (HttpServletRequest request, HttpServletResponse response) {
		
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
	
public void outRegistrierungFehler(HttpServletRequest request, HttpServletResponse response) {
	response.setContentType("text/html");
	PrintWriter out;
	try {
		out = response.getWriter();
	} catch (IOException e) {
		e.printStackTrace();
		return;
	}
	out.println("<h1>Bitte korrigieren Sie Ihre Eingaben</h1>");
	// Formular, vorausgefüllt!
}	
	
	
}
