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
 * Die Klasse <code>WarenkorbController</code> stellt Kontrollstrukturen zur Darstellung
 * und Organisation des Warenkorbs zur Verfuegung
 * @author Tom Weigelt
 *
 */
public class WarenkorbController
{
	/**
	 * aktuelle <code>HttpSession</code>
	 * @see javax.servlet.http.HttpSession
	 */
	private HttpSession session;
	
	/**
	 * Eine <code>Hashtable</code> der im Warenkorb abgelegten Produkte vom Datentyp <code>ProduktModel</code> als Schluessel
	 * und deren Bestellmenge vom Datentyp <code>int</code> als Wert
	 */
	private Hashtable<ProduktModel, Integer> warenkorb;
	
	/**
	 * Objekt der Klasse <code>WarenkorbView</code>
	 * @see view.WarenkorbView
	 */
	
	private WarenkorbView warenkorbView;
	
	/**
	 * Objekt der Klasse <code>HttpServletRequest</code>
	 * @see javax.servlet.http.HttpServletRequest
	 */
	private HttpServletRequest request;

	/**
	 * Konstruktor der Klasse <code>WarenkorbController</code>
	 * @param request - der aktuelle <code>HttpServletRequest</code>
	 * @param response - die aktuelle <code>HttpServletResponse</code>
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
				
				if(menge > anzuzeigendesProduktModel.getBestand())
				{
					menge = anzuzeigendesProduktModel.getBestand();
					this.warenkorb.put(anzuzeigendesProduktModel, menge);
					this.warenkorbView.outMengeNichtImBestand();
					
				}
				
				double gesamtpreisPosition = menge * anzuzeigendesProduktModel.getPreisBrutto();
				String einzelpreisFormatiert = htmlAusgabe.outPreisformat(anzuzeigendesProduktModel.getPreisBrutto());
				String gesamtpreisPositionFormatiert = htmlAusgabe.outPreisformat(gesamtpreisPosition);
				this.warenkorbView.outWarenkorbInhalt(anzuzeigendesProduktModel, menge, einzelpreisFormatiert,
						gesamtpreisPositionFormatiert);

				zwischensumme += gesamtpreisPosition;
			}
		} else
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
		if (this.request.getParameter("produkt") != null)
		{
			int id = Integer.parseInt(this.request.getParameter("produkt"));
			ProduktController produktController = new ProduktController(this.request);
			ProduktModel produktModelAusDatenbank = new ProduktModel();
			produktModelAusDatenbank = produktController.getProdukt(id);

			Enumeration<ProduktModel> warenkorbInhalt = this.warenkorb.keys();
			if (warenkorbInhalt.hasMoreElements())
			{
				boolean produktNichtImWarenkorb = true;
				int hinzugefuegteMenge = Integer.parseInt(this.request.getParameter("menge"));

				while (warenkorbInhalt.hasMoreElements())
				{
					ProduktModel produktModelImWarenkorb = warenkorbInhalt.nextElement();
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
		} else
		{
			if (this.request.getParameter("aktualisieren") != null)
			{
				ProduktModel produktModelImWarenkorb;
				Enumeration<ProduktModel> produktModelsImWarenkorb;
				Enumeration<String> parameters;
				String[] splittedParameter;
				String parameter;

				parameters = this.request.getParameterNames();
				while (parameters.hasMoreElements())
				{
					parameter = parameters.nextElement();
					String value = this.request.getParameter(parameter);

					if (parameter.startsWith("menge"))
					{
						splittedParameter = parameter.split("[_]");

						produktModelsImWarenkorb = this.warenkorb.keys();
						while (produktModelsImWarenkorb.hasMoreElements())
						{
							produktModelImWarenkorb = produktModelsImWarenkorb.nextElement();
							if (produktModelImWarenkorb.getId() == Integer.parseInt(splittedParameter[1]))
							{
								if(Integer.parseInt(value) >= 0)
								{
									if (Integer.parseInt(value) == 0)
									{
										this.warenkorb.remove(produktModelImWarenkorb);
									} else
									{
										this.warenkorb.put(produktModelImWarenkorb, Integer.parseInt(value));
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
