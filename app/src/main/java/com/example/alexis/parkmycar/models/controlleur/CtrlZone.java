package com.example.alexis.parkmycar.models.controlleur;


import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.models.metier.Zone;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CtrlZone {
	public static void requestZones()
	{
		ApiConnectionService api = new ApiConnectionService() {
			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				CtrlTicket.requestTickets();
			}
		};
		api.execute(Zone.URL);
	}
	
	public static void initialisationZones(StringBuilder json)
	{
		System.out.println(json.toString());
		try {
			JSONObject obj = new JSONObject(json.toString());
			JSONArray array = obj.getJSONObject("zone").getJSONArray("records");

			for(int i = 0; i < array.length(); i++)
			{
				JSONArray t = array.optJSONArray(i);
				System.out.println(t.getString(0));

				new Zone(
					t.getInt(0),
					t.getString(1),
					t.getDouble(2),
					t.getString(3),
					t.getString(4));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Zone> getZones() { return Zone.getListeZones(); }

	public static Zone getZoneById(int id) {
		for (Zone v : getZones())
			if (v.getId() == id)
				return v;

		return null;
	}
}
