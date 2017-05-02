package com.example.alexis.parkmycar.models.controlleur;


import com.example.alexis.parkmycar.models.metier.Usager;
import com.example.alexis.parkmycar.models.metier.Voiture;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CtrlUsager {
	public static void requestUsagers()
	{
		ApiConnectionService api = new ApiConnectionService() {
			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				CtrlVoiture.requestVoitures();
			}
		};
		api.execute(Usager.URL);
	}
	
	public static void initialisationUsagers(StringBuilder json)
	{
		System.out.println(json.toString());
		try {
			JSONObject obj = new JSONObject(json.toString());
			JSONArray array = obj.getJSONObject("usager").getJSONArray("records");

			for(int i = 0; i < array.length(); i++)
			{
				JSONArray t = array.optJSONArray(i);
				System.out.println(t.getString(0));

				new Usager(
					t.getString(1),
					t.getString(2),
					t.getString(3),
					t.getString(4),
					t.getString(5));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Usager> getUsagers() { return Usager.getListeUsagers(); }

	public static Usager getUsagerByMail(String mail) {
		for (Usager v : getUsagers())
			if (v.getMail().equals(mail))
				return v;

		return null;
	}
}
