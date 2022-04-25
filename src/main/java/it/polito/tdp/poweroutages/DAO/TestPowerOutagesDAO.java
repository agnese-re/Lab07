package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class TestPowerOutagesDAO {

	public static void main(String[] args) {
		
		try {
			Connection connection = ConnectDB.getConnection();
			connection.close();
			System.out.println("Connection Test PASSED");
			
			PowerOutageDAO dao = new PowerOutageDAO() ;
			
			System.out.println(dao.getNercList()) ;
			
			System.out.println("\n*** POWEROUTAGES OF MAAC ***");
			System.out.println(dao.allPowerOutageNerc(new Nerc(8,"MAAC")).size() + " blackout");
			for(PowerOutage po: dao.allPowerOutageNerc(new Nerc(8,"MAAC")))
				System.out.println(po.toString());

		} catch (Exception e) {
			System.err.println("Test FAILED");
		}

	}

}
