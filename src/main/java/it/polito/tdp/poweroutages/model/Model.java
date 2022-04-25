package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.DAO.PowerOutageDAO;

public class Model {
	
	private PowerOutageDAO podao;
	private List<PowerOutage> allPoweroutages;	// di quel NERC
	private List<PowerOutage> migliore;
	private int numClientiMigliore;
	
	public Model() {
		podao = new PowerOutageDAO();
		/* Non inizializzo allPoweroutages, migliore e numClientiMigliore nel modello.
		 	Ogni volta che l'utente seleziona un nuovo NERC allPoweroutages cambia. 
		 	Inoltre migliore e numClientiMigliore devono essere inizializzati ogni 
		 	volta e NON un unica volta all'interno del modello. Inizializzati rispet-
		 	tivamente a 0 e a lista vuota ad ogni scelta dell'utente */
	}
	
	public List<Nerc> getNercList() {
		return podao.getNercList();
	}
	
	public List<PowerOutage> allPowerOutageNerc(Nerc nerc) {
		return podao.allPowerOutageNerc(nerc);
	}
	
	/* Metodo che restituisce il migliore sottoinsieme di blackout dato un NERC, 
	 	un massimo numero di anni e un numero massimo di ore */
	public List<PowerOutage> miglioreSottoinsieme(Nerc nerc, int years, int hours) {
		
		numClientiMigliore = 0;
		migliore = new ArrayList<PowerOutage>();
		allPoweroutages = new ArrayList<PowerOutage>(podao.allPowerOutageNerc(nerc));
		
		List<PowerOutage> parziale = new ArrayList<PowerOutage>();
		ricercaMigliore(parziale,0,years,hours);
		
		return migliore;	
	}

	/* Metodo ricorsivo */
	public void ricercaMigliore(List<PowerOutage> parziale, int livello, int years, int hours) {
		// CASI TERMINALI
		if(livello == allPoweroutages.size())	// ho finito i blackout
			return;
		if(!isValida(parziale,years,hours))		// soluzione non valida
			return;
		else {	// soluzione valida
			int sommaClientiCoinvolti = this.numClientiCoinvolti(parziale);
			if(sommaClientiCoinvolti > numClientiMigliore) {
				numClientiMigliore = sommaClientiCoinvolti;
				migliore = new ArrayList<PowerOutage>(parziale);
			}
		}
		
		// livello ridotto a scegliere se aggiungere o non aggiungere (2 possibilita') 
		// provo ad aggiungere 
		parziale.add(allPoweroutages.get(livello));
		ricercaMigliore(parziale,livello+1,years,hours);
		// provo a non aggiungere
		parziale.remove(allPoweroutages.get(livello));
		ricercaMigliore(parziale,livello+1,years,hours);
		
	}
	
	/* Metodo che calcola il numero di clienti coinvolti */
	public int numClientiCoinvolti(List<PowerOutage> parziale) {

		int numClienti = 0;
		
		for(int indice = 0; indice < parziale.size(); indice++)
			numClienti += parziale.get(indice).getCustomersAffected();
		
		return numClienti;
	}
	
	/* Metodo che verifica se la soluzione parziale e' valida */
	public boolean isValida(List<PowerOutage> parziale, int years, int hours) {
		
		int sommaHoursDisservizio = 0;
		int differenzaInAnni = 0;
		
		if(parziale.size() == 0)	// prima iterazione, livello 0
			return true;
		
		// Controllo sul numero di ore di disservizio -> minore di hours inserito dall'utente
		for(int indice = 0; indice < parziale.size(); indice++)
			sommaHoursDisservizio += parziale.get(indice).getDurataInOre();
		if(sommaHoursDisservizio >= hours)
			return false;	// non valido
		
		// Controllo sul numero di anni -> minore di years inserito dall'utente
		PowerOutage ultimo = parziale.get(parziale.size()-1);
		PowerOutage primo = parziale.get(0);
		
		differenzaInAnni = ultimo.getAnnoPO() - primo.getAnnoPO();
		if(differenzaInAnni >= years)
			return false;
		
		return true;	// soluzione valida
	}
	
	/* Metodo che ritorna il numero di clienti coinvolti nel sottoinsieme migliore */
	public int numClientiMigliore() {
		
		int numClienti = 0;
		
		for(int indice = 0; indice < migliore.size(); indice++)
			numClienti += migliore.get(indice).getCustomersAffected();
		
		return numClienti;
	}
	
	/* Metodo che ritorna il numero di ore di disservizio totali nel sottoinsieme migliore */
	public int numHoursMigliore() {
		
		int numHours = 0;
		
		for(int indice = 0; indice < migliore.size(); indice++)
			numHours += migliore.get(indice).getDurataInOre();
		
		return numHours;
	}
	
	
}
