package com.example.alexis.parkmycar.models.metier;

import java.util.ArrayList;
import java.util.List;

public class Usager {
	private static List<Usager> listeUsagers = new ArrayList<Usager>();
	public static final String URL = "http://miage-grenoble-android.16mb.com/api.php/usager";
	
	//["nom","prenom","tel","mail","mdp"]
	private List<Voiture> listeVoituresUsager = new ArrayList<Voiture>();
	private String nom = "";
	private String prenom = "";
	private String tel = "";
	private String mail = "";
	private String mdp = "";
	
	
	public Usager(String nom, String prenom, String tel, String mail, String mdp)
	{
		this.setNom(nom);
		this.setPrenom(prenom);
		this.setTel(tel);
		this.setMail(mail);
		this.setMdp(mdp);

		Usager.getListeUsagers().add(this);
	}
	
	
	/**
	 * GET / SET
	 */
	
	public static List<Usager> getListeUsagers() { return listeUsagers; }
	public static void setListeUsagers(List<Usager> listeUsagers) { Usager.listeUsagers = listeUsagers; }
	public List<Voiture> getListeVoituresUsager() { return listeVoituresUsager; }
	public void setListeVoituresUsager(List<Voiture> listeVoituresUsager) { this.listeVoituresUsager = listeVoituresUsager; }
	public String getNom() { return nom; }
	public void setNom(String nom) { this.nom = nom; }
	public String getPrenom() { return prenom; }
	public void setPrenom(String prenom) { this.prenom = prenom; }
	public String getTel() { return tel; }
	public void setTel(String tel) { this.tel = tel; }
	public String getMail() { return mail; }
	public void setMail(String mail) { this.mail = mail; }
	public String getMdp() { return mdp; }
	public void setMdp(String mdp) { this.mdp = mdp; }
	
}
