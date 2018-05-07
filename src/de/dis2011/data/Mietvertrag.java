package de.dis2011.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Mietvertrag-Bean
 * 
 */
public class Mietvertrag {

	private int contractnr = -1;
	private String place;
	private Date date;
	private Date startdate;
	private String duration;
	private String addcosts;
	private int apartment_ID;
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

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getAddcosts() {
		return addcosts;
	}

	public void setAddcosts(String addcosts) {
		this.addcosts = addcosts;
	}

	/**
	 * Lädt ein Vertrag aus der Datenbank anhand der Wohnung ID.
	 * 
	 * @param id
	 *            Wohnung_ID
	 * @return eine Wohnung
	 */
	public static Mietvertrag loadOneContract(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Tenancy WHERE APARTMENT_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Iteriere über die SQL-Ergebnisse
			if (rs.next()) {
				Mietvertrag ts = new Mietvertrag();

				ts.setContractnr(rs.getInt("Tenancy_Contractnr"));
				ts.setDate(rs.getDate("Tenancy_Date"));
				ts.setPlace(rs.getString("Tenancy_Place"));
				ts.setStartdate(rs.getDate("Start"));
				ts.setDuration(rs.getString("Duration"));
				ts.setAddcosts(rs.getString("Addcosts"));
				ts.setApartment_ID(id);
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
	 * Lädt eine Liste mit allen Mietverträgen aus der Datenbank, die ein bestimmter
	 * Makler abgeschlossen hat.
	 * 
	 * @param agent
	 *            ID des Maklers
	 * @return Liste mit Mietverträgen
	 */
	public static ArrayList<Mietvertrag> load(int agent) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Tenancy T, Apartment A  WHERE A.Agent_ID = ? AND A.Apartment_ID = T.Apartment_ID";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, agent);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Erzeuge eine Liste von Häusern
			ArrayList<Mietvertrag> tenancies = new ArrayList<Mietvertrag>();

			// Iteriere über die SQL-Ergebnisse
			while (rs.next()) {
				Mietvertrag ts = new Mietvertrag();

				ts.setContractnr(rs.getInt("Tenancy_Contractnr"));
				ts.setDate(rs.getDate("Tenancy_Date"));
				ts.setPlace(rs.getString("Tenancy_Place"));
				ts.setStartdate(rs.getDate("Start"));
				ts.setDuration(rs.getString("Duration"));
				ts.setAddcosts(rs.getString("Addcosts"));
				ts.setApartment_ID(rs.getInt("Apartment_ID"));
				ts.setPerson_ID(rs.getInt("Person_ID"));

				tenancies.add(ts);
			}
			rs.close();
			pstmt.close();
			return tenancies;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public int getApartment_ID() {
		return apartment_ID;
	}

	public void setApartment_ID(int apartment_ID) {
		this.apartment_ID = apartment_ID;
	}

	public int getPerson_ID() {
		return person_ID;
	}

	public void setPerson_ID(int person_ID) {
		this.person_ID = person_ID;
	}

	/**
	 * Speichert den Mietvertrag in der Datenbank. Ist noch keine ID vergeben
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
				String insertSQL = "INSERT INTO Tenancy(Tenancy_Date, Tenancy_Place, Start, Duration, Addcosts, Apartment_ID, Person_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fuehre Anfrage aus
				pstmt.setDate(1, getDate());
				pstmt.setString(2, getPlace());
				pstmt.setDate(3, getStartdate());
				pstmt.setString(4, getDuration());
				pstmt.setString(5, getAddcosts());
				pstmt.setInt(6, getApartment_ID());
				pstmt.setInt(7, getPerson_ID());
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
				String updateSQL = "UPDATE Tenancy SET Tenancy_Date = ?, Tenancy_Place = ?, Start = ?, Duration = ?, Addcosts = ?, Apartment_ID = ?, Person_ID = ? WHERE Tenancy_Contractnr = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setDate(1, getDate());
				pstmt.setString(2, getPlace());
				pstmt.setDate(3, getStartdate());
				pstmt.setString(4, getDuration());
				pstmt.setString(5, getAddcosts());
				pstmt.setInt(6, getApartment_ID());
				pstmt.setInt(7, getPerson_ID());
				pstmt.setInt(8, getContractnr());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
