package controller;

import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.ProduktModel;
import view.HtmlAusgabe;
import view.WarenkorbView;

public class WarenkorbController
{
	private HttpSession session;
	private Hashtable<ProduktModel, Integer> warenkorb;
	private WarenkorbView warenkorbView;
	private HttpServletRequest request;

	// GET-Kostruktor
	public WarenkorbController(HttpServletRequest request, HttpServletResponse response)
	{
		// TODO remove
		System.out.println("---WarenkorbController---");

		this.request = request;
		this.session = request.getSession();
		this.warenkorbView = new WarenkorbView(request, response);
		this.warenkorbAusSessionHolen();
		this.warenkorbAktualisieren();
		this.session.setAttribute("warenkorb", warenkorb);

		// TODO remove
		System.out.println("-------------------------");
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
		// Wurde Produkt an WK übergeben?
		if (this.request.getParameter("produkt") != null)
		{
			// Produkt aus DB holen
			int id = Integer.parseInt(this.request.getParameter("produkt"));
			ProduktController produktController = new ProduktController(this.request);
			ProduktModel produktModelAusDatenbank = new ProduktModel();
			produktModelAusDatenbank = produktController.getProdukt(id);

			// Liegt Produkt noch nicht im WK?
			Enumeration<ProduktModel> warenkorbInhalt = this.warenkorb.keys();
			if (warenkorbInhalt.hasMoreElements())
			{
				boolean produktNichtImWarenkorb = true;

				// WK-Inhalte mit Produkt vergleichen
				while (warenkorbInhalt.hasMoreElements())
				{
					ProduktModel produktImWarenkorb = warenkorbInhalt.nextElement();
					if (produktModelAusDatenbank.getId() == produktImWarenkorb.getId())
					{
						//TODO MENGE AUSLAGERN!!
						produktNichtImWarenkorb = false;
						int mengeImWarenkorb = this.warenkorb.get(produktImWarenkorb);
						int hinzugefuegteMenge = Integer.parseInt(this.request.getParameter("menge"));
						int neueMenge = mengeImWarenkorb + hinzugefuegteMenge;
						this.warenkorb.put(produktImWarenkorb, neueMenge);
					}
				}

				if (produktNichtImWarenkorb)
				{
					this.warenkorb.put(produktModelAusDatenbank, Integer.parseInt(this.request.getParameter("menge")));
				}

			} else
			{
				// in WK legen wenn WK leer
				this.warenkorb.put(produktModelAusDatenbank, Integer.parseInt(this.request.getParameter("menge")));
			}
		} else
		{
			// Wurde 'aktualisieren' gewaehlt?
			if (this.request.getParameter("aktualisieren") != null)
			{
				// TODO remove
				System.out.println("Warenkorb wird aktualisert...");

				ProduktModel produktModelImWarenkorb;
				Enumeration<ProduktModel> produktModelsImWarenkorb;
				Enumeration<String> parameters;
				String[] splittedParameter;
				String parameter;

				// Menge aktualisieren
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

				// Bestellpostitionen entfernen
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
				// Wurde 'leeren' gewaehlt?
				if (this.request.getParameter("leeren") != null)
				{
					// TODO remove
					System.out.println("Warenkorb wird geleert");

					this.warenkorb.clear();
				}
			}
		}
	}
}
