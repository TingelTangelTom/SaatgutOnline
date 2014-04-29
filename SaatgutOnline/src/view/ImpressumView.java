package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImpressumView {

	public ImpressumView(HttpServletRequest request, HttpServletResponse response,
			String unternehmen_adresse, String unternehmen_telefon, String unternehmen_fax,
			String unternehmen_email, String unternehmen_geschaeftsfuehrung, String register_nr,
			String umsatzsteuer_id, String wirtschafts_id, String impressum_copyright) {

		response.setContentType("text/html");
		PrintWriter out;
		try {
			out = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		out.println(unternehmen_adresse + unternehmen_telefon + unternehmen_fax + unternehmen_email
				+ unternehmen_geschaeftsfuehrung + register_nr + register_nr + umsatzsteuer_id
				+ wirtschafts_id + impressum_copyright);

		// out.println(unternehmen_adresse);
		// out.println(unternehmen_telefon);
		// out.println(unternehmen_fax);
		// out.println(unternehmen_email);
		// out.println(unternehmen_geschaeftsfuehrung);
		// out.println(register_nr);
		// out.println(register_nr);
		// out.println(umsatzsteuer_id);
		// out.println(wirtschafts_id);
		// out.println(impressum_copyright);
	}
}
