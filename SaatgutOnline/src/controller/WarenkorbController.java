package controller;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import view.HtmlAusgabe;
import view.WarenkorbView;

/**
 * <p>Die Klasse <code>WarenkorbController</code> stellt Kontrollstrukturen zur
 * Darstellung und Organisation des Warenkorbs zur Verfuegung.</p>
 * 
 * @author Tom Weigelt
 * @version 1.0
 * @since 1.7.0_51
 */
public class WarenkorbController
{
	private HttpSession session;
	private Hashtable<ProduktModel, Integer> warenkorb;
	private WarenkorbView warenkorbView;
	private HttpServletRequest request;

	/**
	 * <p>
	 * Konstruktor der Klasse <code>WarenkorbController</code>.
	 * </p>
	 * <p>Ruft die Methode <code>warenkorbAusSessionHolen()</code> auf.
	 * </br>Die Methode holt einen bestehenden Warenkorb aus der <code>HttpSession</code>
	 * und legt ihn in der Variable <i>warenkorb</i> ab. Falls kein Warenkorb in der Session
	 * liegt, wird er erzeugt.</p>
	 * <p>Ruft die Methode <code>warenkorbAktualiseren()</code> auf.
	 * </br>Die Methode stellt die Funktionalitaet fuer den Warenkorb zur Verfuegung:
	 * </br>- Produkt hinzufuegen
	 * </br>- Produkt entfernen
	 * </br>- Menge aendern</p>
	 * <p>Der aktuelle Warenkorb wird in die Session geschrieben.
	 * </p>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
	 * @see javax.servlet.http.HttpSession
	 * @see javax.servlet.http.HttpServletRequest
	 * @see javax.servlet.http.HttpServletResponse
	 */
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response)
	{
		this.request = request;
		this.session = request.getSession();
		this.warenkorbView = new WarenkorbView(request, response);
		this.warenkorbAusSessionHolen();
		this.warenkorbAktualisieren();
		this.session.setAttribute("warenkorb", warenkorb);
	}

	/**
	 * <p>Formatiert durch den situationsbedingten Aufruf von Methoden der Klasse
	 * <code>WarenkorbView</code> die Darstellung des Warenkorbs und gibt diese aus.</p>
	 * @see view.WarenkorbView
	 */
	public void warenkorbAnzeigen()
	{
		double zwischensumme = 0;
		HtmlAusgabe htmlAusgabe = new HtmlAusgabe(this.request);

		this.warenkorbView.outWarenkorbAnfang();

		if (!this.warenkorb.isEmpty())
		{
			Enumeration<ProduktModel> produkte = this.warenkorb.keys();
			while (produkte.hasMoreElements())
			{
				ProduktModel anzuzeigendesProduktModel = produkte.nextElement();
				int menge = this.warenkorb.get(anzuzeigendesProduktModel);

				if (menge > anzuzeigendesProduktModel.getBestand())
				{
					menge = anzuzeigendesProduktModel.getBestand();
					this.warenkorb.put(anzuzeigendesProduktModel, menge);
					this.warenkorbView.outMengeNichtImBestand();
				}

				double gesamtpreisPosition = menge * anzuzeigendesProduktModel.getPreisBrutto();
				String einzelpreisFormatiert = htmlAusgabe.outPreisformat(anzuzeigendesProduktModel.getPreisBrutto());
				String gesamtpreisPositionFormatiert = htmlAusgabe.outPreisformat(gesamtpreisPosition);
				this.warenkorbView.outWarenkorbInhalt(anzuzeigendesProduktModel, menge, einzelpreisFormatiert, gesamtpreisPositionFormatiert);

				zwischensumme += gesamtpreisPosition;
			}
		}
		else
		{
			this.warenkorbView.outLeererWarenkorb();
		}

		String zwischensummeFormatiert = htmlAusgabe.outPreisformat(zwischensumme);
		this.warenkorbView.outWarenkorbEnde(zwischensummeFormatiert);
	}

	@SuppressWarnings("unchecked")
	private void warenkorbAusSessionHolen()
	{
		if (this.session.getAttribute("warenkorb") != null)
		{
			this.warenkorb = (Hashtable<ProduktModel, Integer>) this.session.getAttribute("warenkorb");
		} else
		{
			this.warenkorb = new Hashtable<ProduktModel, Integer>();
		}
	}

	private void warenkorbAktualisieren()
	{
		Enumeration<ProduktModel> produktModelsImWarenkorb;
		ProduktModel produktModelImWarenkorb;

		if (this.request.getParameter("produkt") != null)
		{
			int id = Integer.parseInt(this.request.getParameter("produkt"));
			
			// die drei nachfolgenden Zeilen nutzen Code von Simon Ankele
			ProduktController produktController = new ProduktController(this.request);
			ProduktModel produktModelAusDatenbank = new ProduktModel();
			produktModelAusDatenbank = produktController.getProdukt(id);

			produktModelsImWarenkorb = this.warenkorb.keys();
			if (produktModelsImWarenkorb.hasMoreElements())
			{
				boolean produktNichtImWarenkorb = true;
				int hinzugefuegteMenge = Integer.parseInt(this.request.getParameter("menge"));

				while (produktModelsImWarenkorb.hasMoreElements())
				{
					produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
					if (produktModelAusDatenbank.getId() == produktModelImWarenkorb.getId())
					{
						produktNichtImWarenkorb = false;
						int mengeImWarenkorb = this.warenkorb.get(produktModelImWarenkorb);
						int neueMenge = mengeImWarenkorb + hinzugefuegteMenge;
						this.warenkorb.put(produktModelImWarenkorb, neueMenge);
					}
				}

				if (produktNichtImWarenkorb)
				{
					this.warenkorb.put(produktModelAusDatenbank, hinzugefuegteMenge);
				}

			} else
			{
				this.warenkorb.put(produktModelAusDatenbank, Integer.parseInt(this.request.getParameter("menge")));
			}
		}
		else
		{
			if (this.request.getParameter("aktualisieren") != null)
			{				
				/*
				 * Da pro Aufruf die Menge mehrere Produkte aktualisiert werden kann,
				 * gibt es moeglicherweise mehrere Parameter des Namens "menge". Um die Menge dennoch
				 * eindeutig einem Produkt zuordenen zu koennen, wird bei der Erzeugung des Parameters (per
				 * Unterstrich getrennt) die Produkt-ID angehaengt.
				 * 
				 * Der Ubergabeparameter "menge" ist wie folgt aufgebaut:
				 * menge_<produktId>=<neueMenge>
				 * 
				 * Um nun die Produkt-ID als Wert zu bekommen, mit dem weiter gearbeitet
				 * werden kann, wird der Parameter am Unterstrich gesplittet und die
				 * Bestandteile "menge" und <produktId> im StringArray 'splittedParameter'
				 * abgelegt.
				 * 
				 * Die Produkt-ID ist dann als Integer verfuegbar per:
				 * Integer.parseInt(splittedParameter[1]) 
				 */
				Enumeration<String> parameters;
				String[] splittedParameter;
				String parameter;

				parameters = this.request.getParameterNames();
				while (parameters.hasMoreElements())
				{
					parameter = parameters.nextElement();

					if (parameter.startsWith("menge"))
					{
						splittedParameter = parameter.split("[_]");

						produktModelsImWarenkorb = this.warenkorb.keys();
						while (produktModelsImWarenkorb.hasMoreElements())
						{
							produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
							if (produktModelImWarenkorb.getId() == Integer.parseInt(splittedParameter[1]))
							{
								int value = 0;

								try
								{
									value = Integer.parseInt(this.request.getParameter(parameter));
								} catch (NumberFormatException e)
								{
									value = this.warenkorb.get(produktModelImWarenkorb);									
								}

								if (value >= 0)
								{
									if (value == 0)
									{
										this.warenkorb.remove(produktModelImWarenkorb);
									} else
									{
										this.warenkorb.put(produktModelImWarenkorb, value);
									}
								}
							}
						}
					}
				}

				
				parameters = this.request.getParameterNames();
				
				while (parameters.hasMoreElements())
				{
					parameter = parameters.nextElement();

					/*
					 * Auch hier wird wieder der Parameter bei der Erzeugung um die Produkt-ID
					 * erweitert und dann am Unterstrich gesplittet.
					 * 
					 * siehe oben
					 */
					if (parameter.startsWith("entfernen"))
					{
						splittedParameter = parameter.split("[_]");

						produktModelsImWarenkorb = this.warenkorb.keys();
						while (produktModelsImWarenkorb.hasMoreElements())
						{
							produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
							if (produktModelImWarenkorb.getId() == Integer.parseInt(splittedParameter[1]))
							{
								this.warenkorb.remove(produktModelImWarenkorb);
							}
						}
					}
				}
			} else
			{
				if (this.request.getParameter("leeren") != null)
				{
					this.warenkorb.clear();
				}
			}
		}
	}
}
