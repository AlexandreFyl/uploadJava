package club.classes;

import java.io.Serializable;

public class Membre implements Serializable{
	private int id;
	private int idClub;
	private String nom;
	private String prenom;
	private int age;
	private String licence;


	public Membre(int id, int idClub, String nom, String prenom, int age, String licence) {
		this.id = id;
		this.idClub = idClub;
		this.nom = nom;
		this.prenom = prenom;
		this.age = age;
		this.licence = licence;
	}

	public int getId() {
		return id;
	}
	
	public int getIdClub() {
		return idClub;
	}

	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}

	public String getLicence() {
		return licence;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String isLicence() {
		return licence;
	}
	public void setLicence(String licence) {
		this.licence = licence;
	}
	
	public String toString() {
		return this.idClub + " " + this.nom + " " + this.prenom + " " + this.age + " " + this.licence;
	}
	
}
