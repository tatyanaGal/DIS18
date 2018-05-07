package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Person-Bean
 * 
 */
public class Person {
	private int id = -1;
	private String firstname;
	private String lastname;
	private String address;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	/**
	 * Lädt eine Person aus der Datenbank
	 * 
	 * @param id
	 *            ID des zu ladender Person
	 * @return Person-Instanz
	 */
	public static Person load(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM PERSON WHERE PERSON_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Person ts = new Person();
				ts.setId(id);
				ts.setFirstname(rs.getString("FIRSTNAME"));
				ts.setLastname(rs.getString("LASTNAME"));
				ts.setAddress(rs.getString("PERSON_ADDRESS"));

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
	 * Speichert die Person in der Datenbank. Ist noch keine ID vergeben worden,
	 * wird die generierte Id von DB2 geholt und dem Model übergeben.
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Fuege neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spaeter generierte IDs zurueckgeliefert werden!
				String insertSQL = "INSERT INTO PERSON(Firstname, Lastname, Person_Address) VALUES (?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fC<hre Anfrage aus
				pstmt.setString(1, getFirstname());
				pstmt.setString(2, getLastname());
				pstmt.setString(3, getAddress());
				pstmt.executeUpdate();

				// Hole die Id des engefC<gten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE PERSON SET Firstname = ?, Lastname = ?, Person_Address = ? WHERE Person_ID = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getFirstname());
				pstmt.setString(2, getLastname());
				pstmt.setString(3, getAddress());
				pstmt.setInt(4, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht den Makler in der Datenbank.
	 * 
	 */
	public void delete() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {

			//
			String deleteSQL = "DELETE FROM PERSON WHERE Firstname = ? AND Lastname = ? AND Person_Address = ? AND Person_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(deleteSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getFirstname());
			pstmt.setString(2, getLastname());
			pstmt.setString(3, getAddress());
			pstmt.setInt(4, getId());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Lädt eine Liste mit allen Personen aus der Datenbank, die ein bestimmter
	 * Makler abgeschlossen hat.
	 * 
	 * @return Liste mit Personen
	 */
	public static ArrayList<Person> load() {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM Person";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Erzeuge eine Liste von Häusern
			ArrayList<Person> persons = new ArrayList<Person>();

			// Iteriere über die SQL-Ergebnisse
			while (rs.next()) {
				Person ts = new Person();

				ts.setId(rs.getInt("Person_ID"));
				ts.setFirstname(rs.getString("Firstname"));
				ts.setLastname(rs.getString("Lastname"));
				ts.setAddress(rs.getString("Person_Address"));
				
				persons.add(ts);
			}
			rs.close();
			pstmt.close();
			return persons;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
