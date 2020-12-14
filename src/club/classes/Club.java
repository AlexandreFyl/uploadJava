package club.classes;

import java.io.Serializable;

public class Club implements Serializable{
	private int idClub;
	private String nomClub;
	
	public Club(int idClub, String nomClub) {
		this.idClub = idClub;
		this.nomClub = nomClub;
	}
	
	public int getIdClub() {
		return idClub;
	}
	public void setIdClub(int idClub) {
		this.idClub = idClub;
	}
	public String getNomClub() {
		return nomClub;
	}
	public void setNomClub(String nomClub) {
		this.nomClub = nomClub;
	}
	
	public String toString() {
		return this.idClub + "- "+this.nomClub;
	}
}
