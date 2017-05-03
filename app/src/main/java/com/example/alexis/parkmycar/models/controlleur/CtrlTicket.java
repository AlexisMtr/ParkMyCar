package com.example.alexis.parkmycar.models.controlleur;


import com.example.alexis.parkmycar.models.metier.EtatTicket;
import com.example.alexis.parkmycar.models.metier.Ticket;
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.models.metier.Zone;
import com.example.alexis.parkmycar.utils.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CtrlTicket {

	public static void requestTickets()
	{
		ApiConnectionService api = new ApiConnectionService() {
			@Override
			protected void onPostExecute(Void aVoid) {
				utils.finishImport = true;
			}
		};
		api.execute(Ticket.URL);
	}
	
	public static void initialisationTickets(StringBuilder json)
	{
		System.out.println(json.toString());
		try {
			JSONObject obj = new JSONObject(json.toString());
			JSONArray array = obj.getJSONObject("ticket").getJSONArray("records");

			for(int i = 0; i < array.length(); i++)
			{
				JSONArray t = array.optJSONArray(i);
				System.out.println(t.getString(0));

				EtatTicket etat = t.getString(8).equals("En cours") ? EtatTicket.ENCOURS : EtatTicket.TERMINE;

				// on ne recupere pas les ticket en cours
				// ça evite de devoir chercher parmis tous les ticket ceux qui appartiennent à l'utilisateur
				if(etat == EtatTicket.ENCOURS)
					continue;

				new Ticket(
					t.getString(1),
					t.getString(2),
					t.getString(3),
					t.getString(4),
					t.getInt(5),
					t.getDouble(6),
					t.getInt(7),
					etat);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Ticket> getTicketsByVoiture(Voiture voiture) { return voiture.getListeTicketsVoiture(); }
	
	public static List<Ticket> getTickets() { return Ticket.getListeTickets(); }

	public static Ticket addTicket(String dateDebut, String dateFin, Voiture voiture, String coord, Zone zone)
	{
		return new Ticket(dateDebut, dateFin, voiture.getImmatriculation(), coord, zone.getId(), 0, 0, EtatTicket.ENCOURS);
	}

	public static Ticket getCurrent()
	{
		for(Ticket t : getTickets())
			if(t.getEtat() == EtatTicket.ENCOURS)
				return t;
		return null;
	}

	public static void addMinutes(int minutes)
	{
		CtrlTicket.getCurrent().setDuree(CtrlTicket.getCurrent().getDuree() + minutes);
	}

	public static void cancelCurrent()
	{
		if(CtrlTicket.getCurrent() == null)
			return;

		CtrlVoiture.getVoitureByImmat(CtrlTicket.getCurrent().getImmat()).getListeTicketsVoiture().remove(CtrlTicket.getCurrent());
		CtrlTicket.getTickets().remove(CtrlTicket.getCurrent());
	}

	public static void finishTicket(int dureeEffectiveSeconde)
	{
		Calendar c = Calendar.getInstance(Locale.FRANCE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD HH:mm:ss", Locale.FRANCE);
		CtrlTicket.getCurrent().setDateFin(df.format(c.getTime()));
		CtrlTicket.getCurrent().setDureePayanteMinute((dureeEffectiveSeconde / 60));

		Ticket.getListeTickets().add(CtrlTicket.getCurrent());

		// a la fin obligatoirement
		CtrlTicket.getCurrent().setEtat(EtatTicket.TERMINE);
	}

	public static double calculPrix(long duree)
	{
		double tarif = CtrlTicket.getCurrent().getZone().getTarif();

		return duree * (tarif/3600);
	}
}
