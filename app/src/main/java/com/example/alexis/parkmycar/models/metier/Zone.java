package com.example.alexis.parkmycar.models.metier;

import java.util.ArrayList;
import java.util.List;

public class Zone {
	
	private static List<Zone> listeZones = new ArrayList<Zone>();
	public static final String URL = "http://miage-grenoble-android.16mb.com/api.php/zone";

	//["id","nom","tarif","debut","fin"]
	private int id = 0;
	private String nom = "";
	private double tarif = 0.0f;
	private String heureDebut = "";
	private String heureFin = "";
	
	
	public Zone(int id, String nom, double tarif, String heurDebut, String heurFin)
	{
		this.id = id;
		this.nom = nom;
		this.tarif = tarif;
		this.heureDebut = heurDebut;
		this.heureFin = heurFin;
		
		listeZones.add(this);
	}
	
	
	/**
	 * GET / SET
	 */
	
	public static List<Zone> getListeZones() { return listeZones; }
	public static void setListeZones(List<Zone> listeZones) { Zone.listeZones = listeZones; }
	public int getId() { return id; }
	public void setId(int id) { this.id = id; }
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }
	public double getTarif() { return tarif; }
	public void setTarif(double tarif) { this.tarif = tarif; }
	public String getHeureDebut() { return heureDebut; }
	public void setHeureDebut(String heureDebut) { this.heureDebut = heureDebut; }
	public String getHeureFin() { return heureFin; }
	public void setHeureFin(String heureFin) { this.heureFin = heureFin; }
	
}
