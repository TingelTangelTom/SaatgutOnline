package view;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;
//import java.util.PropertyResourceBundle;
//import java.util.ResourceBundle;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* Servlet implementation class SimonKopfbereich
*/
@WebServlet("/SimonKopfbereich")
public class SimonKopfbereich extends HttpServlet {
private static final long serialVersionUID = 1L;
       
    /**
* @see HttpServlet#HttpServlet()
*/
    public SimonKopfbereich() {
        super();
        // TODO Auto-generated constructor stub
    }

/**
* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
*/
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// Browsersprache
Locale sprache = request.getLocale();

// Sprachwahl des Kunden
Locale flaggenwahl = Locale.ENGLISH;

if(!sprache.equals(flaggenwahl)) {
sprache = flaggenwahl;
}

// Länderliste für Flaggen
// Locale[] locale = { Locale.GERMAN, Locale.ENGLISH };

//ResourceBundle textbundle = PropertyResourceBundle.getBundle("I18N." + sprache.getLanguage() + "." + getClass().getSimpleName(), sprache);

//final String WILLKOMMEN = textbundle.getString("WILLKOMMEN");

// Einbindung der Kopfzeile
RequestDispatcher rd;
rd = getServletContext().getRequestDispatcher("/SimonKopfbereich");
rd.include(request, response);

// Inhalt des eigentlichen Servlets
response.setContentType("text/html");
PrintWriter out = response.getWriter();
out.println("Inhalt");
// Inhalt der Fusszeile
rd = getServletContext().getRequestDispatcher("/SimonFussbereich");	
rd.include(request, response);





}

/**
* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
*/
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
// TODO Auto-generated method stub
}

}

