package com.example.alexis.parkmycar.models.controlleur;


import com.example.alexis.parkmycar.models.metier.EtatTicket;
import com.example.alexis.parkmycar.models.metier.Ticket;
import com.example.alexis.parkmycar.models.metier.Usager;
import com.example.alexis.parkmycar.models.metier.Voiture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CtrlVoiture {
	public static void requestVoitures()
	{
		ApiConnectionService api = new ApiConnectionService() {
			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				CtrlZone.requestZones();
			}
		};
		api.execute(Voiture.URL);
	}
	
	public static void initialisationVoitures(StringBuilder json)
	{
		System.out.println(json.toString());
		try {
			JSONObject obj = new JSONObject(json.toString());
			JSONArray array = obj.getJSONObject("voiture").getJSONArray("records");

			for(int i = 0; i < array.length(); i++)
			{
				JSONArray t = array.optJSONArray(i);
				System.out.println(t.getString(0));

				new Voiture(
					t.getString(0),
					t.getString(1),
					t.getString(2),
					t.getString(3),
					t.getString(4));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Voiture> getVoituresByUsager(Usager usager) { return usager.getListeVoituresUsager(); }

	public static Voiture getVoitureByImmat(String immat) {
		for (Voiture v : getVoitures())
			if (v.getImmatriculation().equals(immat))
				return v;

		return null;
	}
	
	public static List<Voiture> getVoitures() { return Voiture.getListeVoitures(); }
}
