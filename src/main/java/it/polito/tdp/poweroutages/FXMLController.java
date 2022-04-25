/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.poweroutages;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class FXMLController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="cmbNerc"
    private ComboBox<Nerc> cmbNerc; // Value injected by FXMLLoader

    @FXML // fx:id="txtYears"
    private TextField txtYears; // Value injected by FXMLLoader

    @FXML // fx:id="txtHours"
    private TextField txtHours; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    
    /* *** VISUALIZATION WITH TABLEVIEW *** */
    @FXML
    private TableView<PowerOutage> tblPowerOutages;		// tableView ospita PowerOutage
    
    @FXML
    private TableColumn<PowerOutage, Integer> colCustomers;

    @FXML
    private TableColumn<PowerOutage, LocalDateTime> colDateEventBegan;

    @FXML
    private TableColumn<PowerOutage, LocalDateTime> colDateEventFinished;

    @FXML
    private TableColumn<PowerOutage, Integer> colDurationHours;

    @FXML
    private TableColumn<PowerOutage, Integer> colYear;
    
    /* ******************************** *** */
    
    
    private Model model;
    
    @FXML
    void doRun(ActionEvent event) {
    	txtResult.clear();
    	int numYears = 0;
    	int numHours = 0;
    	/* CAMPI COMPILATI DALL'UTENTE IN INTERFACCIA GRAFICA */
    	Nerc nercSelected = cmbNerc.getValue();
    	String years = txtYears.getText();
    	String hours = txtHours.getText();
    	
    	/* 1) Controllo che l'utente abbia selezionato un NERC */
    	if(nercSelected == null)
    		txtResult.setText("Per favore, seleziona un NERC dal menu' a tendina");
    	/* 2) Controllo che nei campi max years e max hours abbia inserito dei numeri */
    	try {
    		numYears = Integer.parseInt(years);
    		numHours = Integer.parseInt(hours);
    	} catch(NumberFormatException nfe) {
    		txtResult.setText("Inserisci valori validi nei campi Max Years e Max Hours (ONLY NUMBERS)");
    	}
    	
    	List<PowerOutage> powerOutagesMigliore = 
    			model.miglioreSottoinsieme(nercSelected, numYears, numHours);
    	txtResult.appendText("Tot people affected: " + model.numClientiMigliore() + "\n");
    	txtResult.appendText("Tot hours of outage: " + model.numHoursMigliore() + "\n");
    	for(PowerOutage po: powerOutagesMigliore)
    		txtResult.appendText(po.toString() + "\n");
    	
    	/*	SOLO PER TESTARE COLLEGAMENTO DAO - MODEL - CONTROLLER
    	if(nercSelected != null) {	// l'utente potrebbe cliccare il bottone, senza aver selezionato alcun NERC
	    	List<PowerOutage> powerOutages = model.allPowerOutageNerc(nercSelected);
	    	for(PowerOutage po: powerOutages)
	    		txtResult.appendText(po.toString()+"\n");
    	} else
    		txtResult.setText("Per favore, scegli un NERC"); 
    	*/
    	
    	tblPowerOutages.setItems(
    			FXCollections.observableArrayList(model.miglioreSottoinsieme(nercSelected, numYears, numHours)));
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert cmbNerc != null : "fx:id=\"cmbNerc\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtYears != null : "fx:id=\"txtYears\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtHours != null : "fx:id=\"txtHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        
        assert colCustomers != null : "fx:id=\"colCustomers\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colDateEventBegan != null : "fx:id=\"colDateEventBegan\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colDateEventFinished != null : "fx:id=\"colDateEventFinished\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colDurationHours != null : "fx:id=\"colDurationHours\" was not injected: check your FXML file 'Scene.fxml'.";
        assert colYear != null : "fx:id=\"colYear\" was not injected: check your FXML file 'Scene.fxml'.";
        assert tblPowerOutages != null : "fx:id=\"tblPowerOutages\" was not injected: check your FXML file 'Scene.fxml'.";
        
        /* *** PER RAPPRESENTARE CELLE TABLE VIEW */
        this.colYear.setCellValueFactory(new PropertyValueFactory<PowerOutage,Integer>("annoPO"));
        this.colDateEventBegan.setCellValueFactory(new PropertyValueFactory<PowerOutage,LocalDateTime>("dateEventBegan"));
        this.colDateEventFinished.setCellValueFactory(new PropertyValueFactory<PowerOutage,LocalDateTime>("dateEventFinished"));
        this.colDurationHours.setCellValueFactory(new PropertyValueFactory<PowerOutage,Integer>("durataInOre"));
        this.colCustomers.setCellValueFactory(new PropertyValueFactory<PowerOutage,Integer>("customersAffected"));
        
        // Utilizzare questo font per incolonnare correttamente i dati;
        txtResult.setStyle("-fx-font-family: monospace");
    }
    
    public void setModel(Model model) {
    	this.model = model;		// setto il modello
    	setComboBox();	// una volta settato il modello, popolo la comboBox
    }
    
    public void setComboBox() {
    	for(Nerc nerc: model.getNercList())
    	cmbNerc.getItems().add(nerc);
    }
}
