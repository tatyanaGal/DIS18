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
 * Test Mario Push
 * 
 * Beispiel-Tabelle: CREATE TABLE House(House_ID int NOT NULL GENERATED ALWAYS
 * AS IDENTITY (START WITH 1, INCREMENT BY 1, NO CACHE) PRIMARY KEY, House_City
 * varchar(255) NOT NULL, House_Postcode int NOT NULL, House_Street varchar(255)
 * NOT NULL, House_Streetnr int NOT NULL, House_Square varchar(255) NOT NULL,
 * Floors int NOT NULL, Price varchar(255) NOT NULL, Garden int DEFAULT 0,
 * Agent_ID int, CONSTRAINT FK_Agent2 FOREIGN KEY (Agent_ID) REFERENCES
 * Estate_Agent(Agent_ID), CONSTRAINT CHK_Garden CHECK (Garden=1 OR Garden=0));
 */
public class Haus {
	private int id = -1;
	private String city;
	private int postcode;
	private String street;
	private int streetnr;
	private String square;
	private int floors;
	private String price;
	private int garden;
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

	public int getFloors() {
		return floors;
	}

	public void setFloors(int floors) {
		this.floors = floors;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public int getGarden() {
		return garden;
	}

	public void setGarden(int garden) {
		this.garden = garden;
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

	/**
	 * Lädt eine Liste mit allen Häusern aus der Datenbank, die ein bestimmter
	 * Makler verwaltet.
	 * 
	 * @param agent
	 *            ID des Maklers
	 * @return Liste mit Häusern
	 */
	public static ArrayList<Haus> load(int agent) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM HOUSE WHERE AGENT_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, agent);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Erzeuge eine Liste von Häusern
			ArrayList<Haus> houses = new ArrayList<Haus>();

			// Iteriere über die SQL-Ergebnisse
			while (rs.next()) {
				Haus ts = new Haus();

				ts.setId(rs.getInt("HOUSE_ID"));
				ts.setCity(rs.getString("HOUSE_CITY"));
				ts.setPostcode(rs.getInt("HOUSE_POSTCODE"));
				ts.setStreet(rs.getString("HOUSE_STREET"));
				ts.setStreetnr(rs.getInt("HOUSE_STREETNR"));
				ts.setSquare(rs.getString("HOUSE_SQUARE"));
				ts.setFloors(rs.getInt("FLOORS"));
				ts.setPrice(rs.getString("PRICE"));
				ts.setGarden(rs.getInt("GARDEN"));
				ts.setAgent(agent);

				houses.add(ts);
			}
			rs.close();
			pstmt.close();
			return houses;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Speichert das Haus in der Datenbank. Ist noch keine ID vergeben worden, wird
	 * die generierte Id von DB2 geholt und dem Model übergeben.
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
				String insertSQL = "INSERT INTO HOUSE(House_City, House_Postcode, House_Street, House_Streetnr, House_Square, Floors, Price, Garden, Agent_ID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

				PreparedStatement pstmt = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);

				// Setze Anfrageparameter und fuehre Anfrage aus
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostcode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetnr());
				pstmt.setString(5, getSquare());
				pstmt.setInt(6, getFloors());
				pstmt.setString(7, getPrice());
				pstmt.setInt(8, getGarden());
				pstmt.setInt(9, getAgent());
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
				String updateSQL = "UPDATE HOUSE SET House_City = ?, House_Postcode = ?, House_Street = ?, House_Streetnr = ?, House_Square = ?, Floors = ?, Price = ?, Garden = ?, Agent_ID = ? WHERE HOUSE_ID = ?";
				PreparedStatement pstmt = con.prepareStatement(updateSQL);

				// Setze Anfrage Parameter
				pstmt.setString(1, getCity());
				pstmt.setInt(2, getPostcode());
				pstmt.setString(3, getStreet());
				pstmt.setInt(4, getStreetnr());
				pstmt.setString(5, getSquare());
				pstmt.setInt(6, getFloors());
				pstmt.setString(7, getPrice());
				pstmt.setInt(8, getGarden());
				pstmt.setInt(9, getAgent());
				pstmt.setInt(10, getId());
				pstmt.executeUpdate();

				pstmt.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Löscht das Haus in der Datenbank.
	 * 
	 */
	public void delete() {
		// Hole Verbindung
		Connection con = DB2ConnectionManager.getInstance().getConnection();

		try {

			//
			String deleteSQL = "DELETE FROM HOUSE WHERE House_City = ? AND House_Postcode = ? AND House_Street = ? AND House_Streetnr = ? AND House_Square = ? AND Floors = ? AND Price = ? AND Garden = ? AND Agent_ID = ? AND HOUSE_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(deleteSQL);

			// Setze Anfrage Parameter
			pstmt.setString(1, getCity());
			pstmt.setInt(2, getPostcode());
			pstmt.setString(3, getStreet());
			pstmt.setInt(4, getStreetnr());
			pstmt.setString(5, getSquare());
			pstmt.setInt(6, getFloors());
			pstmt.setString(7, getPrice());
			pstmt.setInt(8, getGarden());
			pstmt.setInt(9, getAgent());
			pstmt.setInt(10, getId());
			pstmt.executeUpdate();

			pstmt.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Lädt ein Haus aus der Datenbank anhand der ID.
	 * 
	 * @param agent
	 *            ID des Maklers
	 * @return ein Haus
	 */
	public static Haus loadOneHouse(int id) {
		try {
			// Hole Verbindung
			Connection con = DB2ConnectionManager.getInstance().getConnection();

			// Erzeuge Anfrage
			String selectSQL = "SELECT * FROM HOUSE WHERE HOUSE_ID = ?";
			PreparedStatement pstmt = con.prepareStatement(selectSQL);
			pstmt.setInt(1, id);

			// Führe Anfrage aus
			ResultSet rs = pstmt.executeQuery();

			// Iteriere über die SQL-Ergebnisse
			if (rs.next()) {
				Haus ts = new Haus();

				ts.setId(id);
				ts.setCity(rs.getString("HOUSE_CITY"));
				ts.setPostcode(rs.getInt("HOUSE_POSTCODE"));
				ts.setStreet(rs.getString("HOUSE_STREET"));
				ts.setStreetnr(rs.getInt("HOUSE_STREETNR"));
				ts.setSquare(rs.getString("HOUSE_SQUARE"));
				ts.setFloors(rs.getInt("FLOORS"));
				ts.setPrice(rs.getString("PRICE"));
				ts.setGarden(rs.getInt("GARDEN"));
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

}
