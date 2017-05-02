package com.example.alexis.parkmycar.models.controlleur;

import android.os.AsyncTask;

import com.example.alexis.parkmycar.models.metier.Ticket;
import com.example.alexis.parkmycar.models.metier.Voiture;
import com.example.alexis.parkmycar.models.metier.Zone;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.alexis.parkmycar.models.metier.*;



public class ApiConnectionService extends AsyncTask<String, Void, Void>
{

    public StringBuilder inializeConnection(String url)
    {
        StringBuilder result = new StringBuilder();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setDoOutput(false);
            con.setRequestMethod("GET");
            con.connect();

            int st = con.getResponseCode();
            InputStream is = con.getInputStream();
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

        }catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected Void doInBackground(String... url) {
    	StringBuilder json = inializeConnection(url[0]);
    	switch (url[0])
    	{
	    	case Ticket.URL:
	    		CtrlTicket.initialisationTickets(json);
	    		break;
	    	case Zone.URL:
	    		CtrlZone.initialisationZones(json);
	    		break;
	    	case Voiture.URL:
	    		CtrlVoiture.initialisationVoitures(json);
	    		break;
	    	case Usager.URL:
	    		CtrlUsager.initialisationUsagers(json);
	    		break;
    	}

    	return null;
    }
}