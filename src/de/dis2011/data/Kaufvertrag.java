package de.dis2011.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Kaufvertrag-Bean
 * 
 */
public class Kaufvertrag {

	private int contractnr = -1;
	private String place;
	private Date date;
	private int installmentsnr;
	private float rate;
	private int house_ID;
	private int person_ID;

	/**
	 * getVertragnr von dem Vertrag
	 * 
	 * @return Vertrag number
	 */
	public int getContractnr() {
		return contractnr;
	}

	/**
	 * Set ID von der Vertrag
	 * 
	 * @param id
	 */
	public void setContractnr(int number) {
		this.contractnr = number;
	}

	/**
	 * get Datum, wann der Vertrag unterschrieben wurde
	 * 
	 * @return datum
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * Set Datum, wann der Vertrag unterschrieben wurde
	 * 
	 * @param datum
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * Get Stadt, wo der Vertrag unterschrieben wurde
	 * 
	 * @return stadt
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * Set Stadt, wo der Vertrag unterschrieben wurde
	 * 
	 * @param stadt
	 */
	public void setPlace(String place) {
		this.place = place;
	}

	public int getInstallmentsnr() {
		return installmentsnr;
	}

	public void setInstallmentsnr(int installmentsnr) {
		this.installmentsnr = installmentsnr;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public int getHouse_ID() {
		return house_ID;
	}

	public void setHouse_ID(int house_ID) {
		this.house_ID = house_ID;
	}

	public int getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(int person_ID) {
		this.person_ID = person_ID;
	}
	
	/**
	 * Lädt ein Vertrag aus der Datenbank anhand der Wohnung ID.
	 * 
	 * @param id
	 *            Wohnung_ID
	 * @return eine Wohnung
	 */
	public static Kaufvertrag loadOneContract(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Purchase WHERE House_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Iteriere über die SQL-Ergebnisse
			if (rs.next()) {
				Kaufvertrag ts = new Kaufvertrag();

				ts.setContractnr(rs.getInt("Purchase_Contractnr"));
				ts.setDate(rs.getDate("Purchase_Date"));
				ts.setPlace(rs.getString("Purchase_Place"));
				ts.setInstallmentsnr(rs.getInt("Installmentsnr"));
				ts.setRate(rs.getFloat("Rate"));
				ts.setHouse_ID(id);
				ts.setPerson_ID(rs.getInt("Person_ID"));

				rs.close();
				pstmt.close();
				return ts;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Lädt eine Liste mit allen Kaufverträgen aus der Datenbank, die ein bestimmter
	 * Makler abgeschlossen hat.
	 * 
	 * @param agent
	 *            ID des Maklers
	 * @return Liste mit Kaufverträgen
	 */
	public static ArrayList<Kaufvertrag> load(int agent) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Purchase P, House H  WHERE H.Agent_ID = ? AND H.House_ID = P.House_ID";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, agent);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Erzeuge eine Liste von Häusern
			ArrayList<Kaufvertrag> purchases = new ArrayList<Kaufvertrag>();

			// Iteriere über die SQL-Ergebnisse
			while (rs.next()) {
				Kaufvertrag ts = new Kaufvertrag();

				ts.setContractnr(rs.getInt("Purchase_Contractnr"));
				ts.setDate(rs.getDate("Purchase_Date"));
				ts.setPlace(rs.getString("Purchase_Place"));
				ts.setInstallmentsnr(rs.getInt("Installmentsnr"));
				ts.setRate(rs.getFloat("Rate"));
				ts.setHouse_ID(rs.getInt("House_ID"));
				ts.setPerson_ID(rs.getInt("Person_ID"));

				purchases.add(ts);
			}
			rs.close();
			pstmt.close();
			return purchases;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Speichert den Kaufvertrag in der Datenbank. Ist noch keine ID vergeben
	 * worden, wird die generierte Id von DB2 geholt und dem Model übergeben.
	 * 
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Fuege neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getContractnr() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spaeter generierte IDs zurueckgeliefert werden!
				String insertSQL = "INSERT INTO Purchase(Purchase_Date, Purchase_Place, Installmentsnr, Rate, House_ID, Person_ID) VALUES (?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fuehre Anfrage aus
				pstmt.setDate(1, getDate());
				pstmt.setString(2, getPlace());
				pstmt.setInt(3, getInstallmentsnr());
				pstmt.setFloat(4, getRate());
				pstmt.setInt(5, getHouse_ID());
				pstmt.setInt(6, getPerson_ID());
				pstmt.executeUpdate();

				// Hole die Id des engefuegten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setContractnr(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE Purchase SET Purchase_Date = ?, Purchase_Place = ?, Installmentsnr = ?, Rate = ?, House_ID = ?, Person_ID = ? WHERE Purchase_Contractnr = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setDate(1, getDate());
				pstmt.setString(2, getPlace());
				pstmt.setInt(3, getInstallmentsnr());
				pstmt.setFloat(4, getRate());
				pstmt.setInt(5, getHouse_ID());
				pstmt.setInt(6, getPerson_ID());
				pstmt.setInt(7, getContractnr());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
