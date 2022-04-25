package it.polito.tdp.poweroutages.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.poweroutages.model.Nerc;
import it.polito.tdp.poweroutages.model.PowerOutage;

public class PowerOutageDAO {
	
	public List<Nerc> getNercList() {

		String sql = "SELECT id, value FROM nerc";
		List<Nerc> nercList = new ArrayList<>();

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();

			while (res.next()) {
				Nerc n = new Nerc(res.getInt("id"), res.getString("value"));
				nercList.add(n);
			}

			conn.close();

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

		return nercList;
	}
	
	/* Metodo per ottenere tutti i blackout relativi ad uno specifico NERC */
	public List<PowerOutage> allPowerOutageNerc(Nerc nerc) {
		
		String sql = "SELECT P.id, P.nerc_id, YEAR(date_event_began), "
				+ "date_event_began, date_event_finished, "
				+ "customers_affected "
				+ "FROM poweroutages P, nerc N "
				+ "WHERE P.nerc_id = N.id AND N.value = ? "
				+ "ORDER BY date_event_began, date_event_finished";
		List<PowerOutage> powerOutagesList = new ArrayList<PowerOutage>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, nerc.getValue());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				powerOutagesList.add(new PowerOutage(rs.getInt("id"), nerc, rs.getInt("customers_affected"),
						rs.getTimestamp("date_event_began").toLocalDateTime(),
						rs.getTimestamp("date_event_finished").toLocalDateTime()));
			}
			
			conn.close();
			return powerOutagesList;
		} catch(SQLException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

}
