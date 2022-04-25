package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;



/**
 * Utility class for connecting to the database
 * 
 * Uses the HikariCP library for managing a connection pool
 * @see <a href="https://brettwooldridge.github.io/HikariCP/">HikariCP</a>
 */
public class ConnectDB {
	
	private static final String jdbcURL = "jdbc:mysql://localhost/poweroutages";
	private static HikariDataSource ds;
	
	/* pool di connessioni. Ogni qualvolta si necessita di interrogare il database si chiede in prestito
	 	una connessione tra quelle gia' esistenti e, una volta effettuata l'operazione, questa viene rila-
	 	sciata. conn.close() serve per rilasciare la connessione, non per chiuderla */
	public static Connection getConnection() {
		if(ds == null) {
			HikariConfig config = new HikariConfig();
			config.setJdbcUrl(jdbcURL);
			config.setUsername("root");
			config.setPassword("TdP_2022");
			
			config.addDataSourceProperty("cachePrepStmts", true);
			config.addDataSourceProperty("prepStmtChacheSize", 250);
			config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
			
			ds = new HikariDataSource(config);
		}
		
		try {
			return ds.getConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("Errore di connessione ad db");
			throw new RuntimeException(e);
		}
	}

	

}
