package com.example.alexis.parkmycar.models.metier;

import com.example.alexis.parkmycar.models.controlleur.CtrlUsager;

import java.util.ArrayList;
import java.util.List;

public class Voiture {
	
	private static List<Voiture> listeVoitures = new ArrayList<Voiture>();
	public static final String URL = "http://miage-grenoble-android.16mb.com/api.php/voiture";
	
	//["marque","modele","immatriculation","identifiant","mail_usager"]
	private String id = "";
	private List<Ticket> listeTicketsVoitures = new ArrayList<Ticket>();
	private String marque = "";
	private String modele = "";
	private String immatriculation = "";
	
	public Voiture(String marque, String modele, String immat, String id,  String usager)
	{
		this.id = id;
		this.marque = marque;
		this.modele = modele;
		this.immatriculation = immat;
		
		listeVoitures.add(this);
		
		CtrlUsager.getUsagerByMail(usager).getListeVoituresUsager().add(this);
	}
	
	/**
	 * GET / SET
	 */
	
	public static List<Voiture> getListeVoitures() { return listeVoitures; }
	public static void setListeVoitures(List<Voiture> listeVoitures) { Voiture.listeVoitures = listeVoitures; }
	public String getId() { return id; }
	public void setId(String id) { this.id = id; }
	public String getMarque() { return marque; }
	public void setMarque(String marque) { this.marque = marque; }
	public String getModele() { return modele; }
	public void setModele(String modele) { this.modele = modele; }
	public String getImmatriculation() { return immatriculation; }
	public void setImmatriculation(String immatriculation) { this.immatriculation = immatriculation; }
	public List<Ticket> getListeTicketsVoiture() { return listeTicketsVoitures; }
	public void setListeTicketsVoiture(List<Ticket> listeTicketsVoiture) { this.listeTicketsVoitures = listeTicketsVoiture; }
	
}
