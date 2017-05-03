package com.example.alexis.parkmycar.models.metier;

import com.example.alexis.parkmycar.models.controlleur.CtrlVoiture;
import com.example.alexis.parkmycar.models.controlleur.CtrlZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Ticket {
	
	private static List<Ticket> listeTickets = new ArrayList<Ticket>();
	public static final String URL = "http://miage-grenoble-android.16mb.com/api.php/ticket";
	
	//"datetime_debut","datetime_fin","immatriculation","coordonnees","zone","cout","duree_payante_minute","etat"
	private String dateDebut = "";
	private String dateFin = "";
	private String coordonnees = null;
	private Zone zone = null;
	private double cout = 0.0f;
	private int dureePayanteMinute = 0;
	private EtatTicket etat = null;

	private int duree = 0;
	private int rappel = 0;
	private String immat;
	
	public Ticket(String dateDebut, String dateFin, String voiture, String coord, int zone, double cout, int dureePayanteMinute, EtatTicket etat)
	{
		this.dateDebut = dateDebut;
		this.dateFin = dateFin;
		this.coordonnees = coord;
		this.zone = CtrlZone.getZoneById(zone);
		this.cout = cout;
		this.dureePayanteMinute = dureePayanteMinute;
		this.etat = etat;

		this.immat = voiture;
		
		listeTickets.add(this);
		
		Voiture v = CtrlVoiture.getVoitureByImmat(voiture);
		if(v != null)
			v.getListeTicketsVoiture().add(this);
	}
	
	/**
	 * GET / SET
	 */
	
	public static List<Ticket> getListeTickets() { return listeTickets; }
	public EtatTicket getEtat() { return etat; }
	public void setEtat(EtatTicket etat) {	this.etat = etat; }
	public String getDateDebut() { return dateDebut; }
	public void setDateDebut(String dateDebut) { this.dateDebut = dateDebut; }
	public String getDateFin() { return dateFin; }
	public void setDateFin(String dateFin) { this.dateFin = dateFin; }
	public String getCoordonnees() { return coordonnees; }
	public void setCoordonnees(String coordonnees) { this.coordonnees = coordonnees; }
	public Zone getZone() { return zone; }
	public void setZone(Zone zone) { this.zone = zone; }
	public double getCout() { return cout; }
	public void setCout(double cout) { this.cout = cout; }
	public int getDureePayanteMinute() { return dureePayanteMinute; }
	public void setDureePayanteMinute(int dureePayanteMinute) { this.dureePayanteMinute = dureePayanteMinute; }

	public void setDuree(int duree){ this.duree = duree; }
	public int getDuree() { return this.duree; }
	public void setRappel(int rappel) { this.rappel = rappel; }
	public int getRappel() { return this.rappel; }

	public String getImmat() { return this.immat; }

	public double getDureeTicket()
	{
		Calendar c_debut = Calendar.getInstance();
		Calendar c_fin = Calendar.getInstance();
		c_debut.set(0,0,0,0,0,0);
		c_fin.set(0,0,0,0,0,0);

		return 0.0;
	}
	
}
