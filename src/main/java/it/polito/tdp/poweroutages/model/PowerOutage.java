package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class PowerOutage {

	/* ATTRIBUTI CLASSE POWEROUTAGE */
	private int id;		// id del blackout
	private Nerc nerc;	// NERC interessato
	private int customersAffected;	// numero di clienti coinvolti
	private LocalDateTime dateEventBegan;	// inizio del blackout
	private LocalDateTime dateEventFinished;	// fine del blackout
	
	private int annoPO;
	private int durataInOre;
	
	/* 	COSTRUTTORE PARAMETRIZZATO */
	public PowerOutage(int id, Nerc nerc, int customersAffected, LocalDateTime dateEventBegan,
			LocalDateTime dateEventFinished) {
		this.id = id;
		this.nerc = nerc;
		this.customersAffected = customersAffected;
		this.dateEventBegan = dateEventBegan;
		this.dateEventFinished = dateEventFinished;
		
		this.annoPO = dateEventBegan.getYear();
		this.durataInOre = (int)ChronoUnit.HOURS.between(dateEventBegan, dateEventFinished);
			
	}

	/* METODI GETTER/SETTER */
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Nerc getNerc() {
		return nerc;
	}

	public void setNerc(Nerc nerc) {
		this.nerc = nerc;
	}

	public int getCustomersAffected() {
		return customersAffected;
	}

	public void setCustomersAffected(int customersAffected) {
		this.customersAffected = customersAffected;
	}

	public LocalDateTime getDateEventBegan() {
		return dateEventBegan;
	}

	public void setDateEventBegan(LocalDateTime dateEventBegan) {
		this.dateEventBegan = dateEventBegan;
	}

	public LocalDateTime getDateEventFinished() {
		return dateEventFinished;
	}

	public void setDateEventFinished(LocalDateTime dateEventFinished) {
		this.dateEventFinished = dateEventFinished;
	}

	/* ****************** */
	public int getAnnoPO() {
		return annoPO;
	}

	public int getDurataInOre() {
		return durataInOre;
	}
	/* ****************** */
	
	/* METODO TOSTRING() */
	public String toString() {
		
		return annoPO + "\t" + dateEventBegan + "\t" + dateEventFinished 
				+ "\t" + durataInOre + "\t" + customersAffected;
	}
}
