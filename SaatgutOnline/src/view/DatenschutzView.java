package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DatenschutzView {
	
	public DatenschutzView(HttpServletRequest request, HttpServletResponse response, String datenschutzText) {
		
		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println(datenschutzText);
	}
}
