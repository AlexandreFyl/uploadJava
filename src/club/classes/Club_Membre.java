package club.classes;

import java.io.Serializable;

public class Club_Membre implements Serializable{
	private int idMembre;
	private int idClub;
	
	public int getIdMembre() {
		return idMembre;
	}
	public void setIdMembre(int idMembre) {
		this.idMembre = idMembre;
	}
	public int getIdClub() {
		return idClub;
	}
	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}
}
