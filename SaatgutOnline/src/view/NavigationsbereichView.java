package view;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.KategorieModel;

public class NavigationsbereichView
{
	private PrintWriter out;
	
	public NavigationsbereichView(HttpServletRequest request, HttpServletResponse response)
	{
		response.setContentType("text/html");
		try
		{
			this.out = response.getWriter();
		} catch (IOException e)
		{
			System.out.println("PrintWriter nicht erstellt!");
			e.printStackTrace();
		}		
	}

	public void outNavigationsabereichAnfang()
	{
		this.out.println("<tr>\n<td class=\"navigation\">"); // oeffnet Navigationsbereich		
	}
	
	public void outNavigationsbereichEnde()
	{
		this.out.println("</td>\n<td class=\"inhalt\">");
	}
	
	public void outKategorienListeAnfang()
	{
		this.out.println("<table>");
	}
	
	public void outKategorienListeEnde()
	{
		this.out.println("</table>");
	}
	
	public void outHauptKategorieAnzeigen(KategorieModel kategorieModel)
	{		
		this.out.println("<tr>\n<td colspan=\"2\">");
		
		this.out.println("<a href=\"/SaatgutOnline/ProduktinfoServlet?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		
		
//		this.out.println("<form action=\"\" method=\"POST\">");
//		this.out.println("<input type=\"submit\" name=\"hauptkategorie_name\" value=\""+ kategorieModel.getKategorieName() + "\">");
//		this.out.println("<input type=\"hidden\" name=\"hauptkategorie_id\" value=\"" + kategorieModel.getKategorieId() + "\">");
//		this.out.println("<input type=\"hidden\" name=\"elternkategorie_id\" value=\"" + kategorieModel.getElternKategorieId() + "\">");
//		this.out.println("</form>");
		
		this.out.println("</td>\n</tr>");
	}
	
	public void outUnterKategorieAnzeigen(KategorieModel kategorieModel)
	{			
		this.out.println("<tr>\n<td>");
		
		//TODO remove next line - format properly in CSS!
		this.out.println("&nbsp;");
		
		this.out.println("</td>\n<td>");
		this.out.println("<a href=\"/SaatgutOnline/ProduktinfoServlet?kategorie="
				+ kategorieModel.getKategorieId()				
				+ "\">\n"
				+ kategorieModel.getKategorieName()
				+ "\n</a>");
		
//		this.out.println("<form action=\"\" method=\"POST\">");
//		this.out.println("<input type=\"submit\" name=\"unterkategorie_name\" value=\""+ kategorieModel.getKategorieName() + "\">");
//		this.out.println("<input type=\"hidden\" name=\"unterkategorie_id\" value=\"" + kategorieModel.getKategorieId() + "\">");
//		this.out.println("<input type=\"hidden\" name=\"elternkategorie_id\" value=\"" + kategorieModel.getElternKategorieId() + "\">");
//		this.out.println("</form>");
		
		
		this.out.println("</td>\n</tr>");
	}
}
