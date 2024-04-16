package BDD;

import java.io.Serializable;
import java.sql.Date;

public class reservation implements Serializable {

private String entreprise ;
private String avion;
private String pilote;
private Date jour ;
private int heure;
private int min;
private String arrive;
public String getArrive() {
	return arrive;
}
public void setArrive(String arrive) {
	this.arrive = arrive;
}
public reservation(String entreprise, String avion, String pilote, Date jour, int heure, int min,String arrive) {
	super();
	this.entreprise = entreprise;
	this.avion = avion;
	this.pilote = pilote;
	this.jour = jour;
	this.heure = heure;
	this.min = min;
	this.arrive=arrive;
}
@Override
public String toString() {
	return "reservation [entreprise=" + entreprise + ", avion=" + avion + ", pilote=" + pilote + ", jour=" + jour
			+ ", heure=" + heure + ", min=" + min + "]";
}
public String getEntreprise() {
	return entreprise;
}
public void setEntreprise(String entreprise) {
	this.entreprise = entreprise;
}
public String getAvion() {
	return avion;
}
public void setAvion(String avion) {
	this.avion = avion;
}
public String getPilote() {
	return pilote;
}
public void setPilote(String pilote) {
	this.pilote = pilote;
}
public Date getJour() {
	return jour;
}
public void setJour(Date jour) {
	this.jour = jour;
}
public int getHeure() {
	return heure;
}
public void setHeure(int heure) {
	this.heure = heure;
}
public int getMin() {
	return min;
}
public void setMin(int min) {
	this.min = min;
}


	
	
}
