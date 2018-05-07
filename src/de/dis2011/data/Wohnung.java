package de.dis2011.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Haus-Bean
 * 
 * Beispiel-Tabelle: CREATE TABLE Apartment(Apartment_ID int NOT NULL GENERATED
 * ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY,
 * Apartment_City varchar(255) NOT NULL, Apartment_Postcode int NOT NULL,
 * Apartment_Street varchar(255) NOT NULL, Apartment_Streetnr int NOT NULL,
 * Apartment_Square varchar(255) NOT NULL, Floor int NOT NULL, Rent varchar(255)
 * NOT NULL, Rooms decimal(3,1) NOT NULL, Balcony int DEFAULT 0, Kitchen int
 * DEFAULT 0, Agent_ID int, CONSTRAINT FK_Agent FOREIGN KEY (Agent_ID)
 * REFERENCES Estate_Agent(Agent_ID), CONSTRAINT CHK_Balcony CHECK (Balcony=1 OR
 * Balcony=0), CONSTRAINT CHK_Kitchen CHECK (Kitchen=1 OR Kitchen=0));
 * 
 */
public class Wohnung {

	private int id = -1;
	private String city;
	private int postcode;
	private String street;
	private int streetnr;
	private String square;
	private int floor;
	private String rent;
	private int rooms;
	private int balcony;
	private int kitchen;
	private int agent;

	/**
	 * getId von der Immobilie
	 * 
	 * @return ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * Set ID von der Immobilie
	 * 
	 * @param id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * get CIty von der Immobilie
	 * 
	 * @return stadt
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Set City von der Immobilie
	 * 
	 * @param city
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Get Postleitzahl von der Immobilie
	 * 
	 * @return postleitzahl
	 */
	public int getPostcode() {
		return postcode;
	}

	/**
	 * Set Postleitzahl von der Immobilie
	 * 
	 * @param postcode
	 */
	public void setPostcode(int postcode) {
		this.postcode = postcode;
	}

	/**
	 * Get Strasse von der Immoblie
	 * 
	 * @return strasse
	 */
	public String getStreet() {
		return street;
	}

	/**
	 * Set Strasse von der Immobilie
	 * 
	 * @param street
	 */
	public void setStreet(String street) {
		this.street = street;
	}

	/**
	 * Get Strassennummer
	 * 
	 * @return strassennummer
	 */
	public int getStreetnr() {
		return streetnr;
	}

	/**
	 * Set Strassennummer
	 * 
	 * @param streetnr
	 */
	public void setStreetnr(int streetnr) {
		this.streetnr = streetnr;
	}

	/**
	 * Get Gesamtfläche von der Immobilie
	 * 
	 * @return gesamtfläche
	 */
	public String getSquare() {
		return square;
	}

	/**
	 * Set Gesamtfläche von der Immobilie
	 * 
	 * @param square
	 */
	public void setSquare(String square) {
		this.square = square;
	}

	/**
	 * Get Makler, der diese Immobilie verwaltet
	 * 
	 * @return Makler ID
	 */
	public int getAgent() {
		return agent;
	}

	/**
	 * Set Makler, der diese Immobilie verwaltet
	 * 
	 * @param agent
	 */
	public void setAgent(int agent) {
		this.agent = agent;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getRent() {
		return rent;
	}

	public void setRent(String rent) {
		this.rent = rent;
	}

	public int getRooms() {
		return rooms;
	}

	public void setRooms(int rooms) {
		this.rooms = rooms;
	}

	public int getBalcony() {
		return balcony;
	}

	public void setBalcony(int balcony) {
		this.balcony = balcony;
	}

	public int getKitchen() {
		return kitchen;
	}

	public void setKitchen(int kitchen) {
		this.kitchen = kitchen;
	}

	/**
	 * Lädt eine Liste mit allen WOhnungen aus der Datenbank, die ein bestimmter
	 * Makler verwaltet.
	 * 
	 * @param agent
	 *            ID des Maklers
	 * @return Liste mit Wohnung
	 */
	public static ArrayList<Wohnung> load(int agent) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM APARTMENT WHERE AGENT_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, agent);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Erzeuge eine Liste von Häusern
			ArrayList<Wohnung> apartments = new ArrayList<Wohnung>();

			// Iteriere über die SQL-Ergebnisse
			while (rs.next()) {
				Wohnung ts = new Wohnung();

				ts.setId(rs.getInt("APARTMENT_ID"));
				ts.setCity(rs.getString("APARTMENT_CITY"));
				ts.setPostcode(rs.getInt("APARTMENT_POSTCODE"));
				ts.setStreet(rs.getString("APARTMENT_STREET"));
				ts.setStreetnr(rs.getInt("APARTMENT_STREETNR"));
				ts.setSquare(rs.getString("APARTMENT_SQUARE"));
				ts.setFloor(rs.getInt("FLOOR"));
				ts.setRent(rs.getString("RENT"));
				ts.setRooms(rs.getInt("ROOMS"));
				ts.setBalcony(rs.getInt("BALCONY"));
				ts.setKitchen(rs.getInt("KITCHEN"));
				ts.setAgent(agent);

				apartments.add(ts);
			}
			rs.close();
			pstmt.close();
			return apartments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Lädt eine Wohnung aus der Datenbank anhand der ID.
	 * 
	 * @param id
	 *            Wohnung_ID
	 * @return eine Wohnung
	 */
	public static Wohnung loadOneApartment(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM APARTMENT WHERE APARTMENT_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Iteriere über die SQL-Ergebnisse
			if (rs.next()) {
				Wohnung ts = new Wohnung();

				ts.setId(id);
				ts.setCity(rs.getString("APARTMENT_CITY"));
				ts.setPostcode(rs.getInt("APARTMENT_POSTCODE"));
				ts.setStreet(rs.getString("APARTMENT_STREET"));
				ts.setStreetnr(rs.getInt("APARTMENT_STREETNR"));
				ts.setSquare(rs.getString("APARTMENT_SQUARE"));
				ts.setFloor(rs.getInt("FLOOR"));
				ts.setRent(rs.getString("RENT"));
				ts.setRooms(rs.getInt("ROOMS"));
				ts.setBalcony(rs.getInt("BALCONY"));
				ts.setKitchen(rs.getInt("KITCHEN"));
				ts.setAgent(rs.getInt("AGENT_ID"));

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
	 * Speichert die Wohnung in der Datenbank. Ist noch keine ID vergeben worden,
	 * wird die generierte Id von DB2 geholt und dem Model übergeben.
	 * 
	 */
	public void save() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {
			// Fuege neues Element hinzu, wenn das Objekt noch keine ID hat.
			if (getId() == -1) {
				// Achtung, hier wird noch ein Parameter mitgegeben,
				// damit spaeter generierte IDs zurueckgeliefert werden!
				String insertSQL = "INSERT INTO APARTMENT(Apartment_City, Apartment_Postcode, Apartment_Street, Apartment_Streetnr, Apartment_Square, Floor, Rent, Rooms, Balcony, Kitchen, Agent_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fuehre Anfrage aus
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostcode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetnr());
				pstmt.setString(5, getSquare());
				pstmt.setInt(6, getFloor());
				pstmt.setString(7, getRent());
				pstmt.setDouble(8, getRooms());
				pstmt.setInt(9, getBalcony());
				pstmt.setInt(10, getKitchen());
				pstmt.setInt(11, getAgent());
				pstmt.executeUpdate();

				// Hole die Id des engefuegten Datensatzes
				ResultSet rs = pstmt.getGeneratedKeys();
				if (rs.next()) {
					setId(rs.getInt(1));
				}

				rs.close();
				pstmt.close();
			} else {
				// Falls schon eine ID vorhanden ist, mache ein Update...
				String updateSQL = "UPDATE HOUSE SET Apartment_City = ?, Apartment_Postcode = ?, Apartment_Street = ?, Apartment_Streetnr = ?, Apartment_Square = ?, Floor = ?, Rent = ?, Rooms = ?, Balcony = ?, Kitchen = ?, Agent_ID = ? WHERE APARTMENT_ID = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostcode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetnr());
				pstmt.setString(5, getSquare());
				pstmt.setInt(6, getFloor());
				pstmt.setString(7, getRent());
				pstmt.setDouble(8, getRooms());
				pstmt.setInt(9, getBalcony());
				pstmt.setInt(10, getKitchen());
				pstmt.setInt(11, getAgent());
				pstmt.setInt(12, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht die Wohnung in der Datenbank.
	 * 
	 */
	public void delete() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {

			//
			String deleteSQL = "DELETE FROM HOUSE WHERE Apartment_City = ? AND Apartment_Postcode = ? AND Apartment_Street = ? AND Apartment_Streetnr = ? AND Apartment_Square = ? AND Floor = ? AND Rent = ? AND Rooms = ? AND Balcony = ? AND Kitchen = ? AND Agent_ID = ? AND APARTMENT_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(deleteSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getCity());
			pstmt.setInt(2, getPostcode());
			pstmt.setString(3, getStreet());
			pstmt.setInt(4, getStreetnr());
			pstmt.setString(5, getSquare());
			pstmt.setInt(6, getFloor());
			pstmt.setString(7, getRent());
			pstmt.setDouble(8, getRooms());
			pstmt.setInt(9, getBalcony());
			pstmt.setInt(10, getKitchen());
			pstmt.setInt(11, getAgent());
			pstmt.setInt(12, getId());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Kleine Hilfsmethode, um Boolean zu 1 oder 0 zu konvertieren.
	 * 
	 * @param bool
	 *            Boolean
	 * @return int
	 */
	public static int toInt(boolean bool) {
		if (bool) {
			return 1;
		} else {
			return 0;
		}
	}

}
