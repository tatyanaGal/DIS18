package de.dis2011;

import de.dis2011.data.Makler;
import de.dis2011.data.Mietvertrag;
import de.dis2011.data.Person;
import java.sql.Date;
import java.util.ArrayList;
import de.dis2011.data.Kaufvertrag;
import de.dis2011.data.Haus;
import de.dis2011.data.Wohnung;

/**
 * Hauptklasse
 */
public class Main {
	/**
	 * Startet die Anwendung
	 */
	public static void main(String[] args) {
		showMainMenu();
	}

	/**
	 * Zeigt das Hauptmenü
	 */
	public static void showMainMenu() {
		// Menüoptionen
		final int MENU_MAKLER = 0;
		final int QUIT = 1;

		// Erzeuge Menü
		Menu mainMenu = new Menu("Hauptmenü");
		mainMenu.addEntry("Makler-Verwaltung", MENU_MAKLER);
		mainMenu.addEntry("Beenden", QUIT);

		// Verarbeite Eingabe
		while (true) {
			int response = mainMenu.show();

			switch (response) {
			case MENU_MAKLER:
				showMaklerMenu();
				break;
			case QUIT:
				return;
			}
		}
	}

	/**
	 * Zeigt die Maklerverwaltung
	 */
	public static void showMaklerMenu() {
		// Menüoptionen
		final int NEW_MAKLER = 0;
		final int LOGIN_MAKLER = 1;
		final int BACK = 2;

		// Maklerverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verwaltung");
		maklerMenu.addEntry("Neuer Makler", NEW_MAKLER);
		maklerMenu.addEntry("Login", LOGIN_MAKLER);
		maklerMenu.addEntry("Zurück zum Hauptmenü", BACK);

		// Verarbeite Eingabe
		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case NEW_MAKLER:
				newMakler();
				break;
			case LOGIN_MAKLER:
				loginMakler();
				break;
			case BACK:
				return;

			}
		}
	}

	/**
	 * Zeigt die Makleraccount-Verwaltungsmenu.
	 * 
	 * @param m
	 *            Makler-Account, dessen Daten verwaltet werden.
	 */
	public static void showMaklerVerwaltungMenu(Makler m) {
		// Menüoptionen
		final int ACCOUNT_MAKLER = 0;
		final int ESTATES = 1;
		final int CONTRACTS = 2;
		final int BACK = 3;

		Makler makler = m;

		// Makleraccountverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Account");
		maklerMenu.addEntry("Persönliche Daten verwalten", ACCOUNT_MAKLER);
		maklerMenu.addEntry("Immobillien vewalten", ESTATES);
		maklerMenu.addEntry("Verträge verwalten", CONTRACTS);
		maklerMenu.addEntry("Zurück zum Login", BACK);

		// Verarbeite Eingabe
		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case ACCOUNT_MAKLER:
				showAccountVerwaltungMenu(makler);
				break;
			case ESTATES:
				showEstatesMenu(makler);
				break;
			case CONTRACTS:
				showContractsMenu(makler);
				break;
			case BACK:
				return;
			}
		}

	}

	/**
	 * Zeigt Accountsverwaltungsmenu, um persönliche Daten zu verwalten.
	 */
	public static void showAccountVerwaltungMenu(Makler m) {
		// Menüoptionen
		final int DATA = 0;
		final int DELETE = 1;
		final int BACK = 2;

		Makler makler = m;

		System.out.println("Hier sind Ihre persönliche Daten aufgelistet: \n Name: " + makler.getName() + "\n Adresse: "
				+ makler.getAddress() + "\n Login: " + makler.getLogin() + "\n Passwort: " + makler.getPassword()
				+ "\n\n");

		// Makleraccountverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Account/Persönliche Daten");
		maklerMenu.addEntry("Persönliche Daten ändern", DATA);
		maklerMenu.addEntry("Account löschen", DELETE);
		maklerMenu.addEntry("Zurück zur Account-Übersicht", BACK);

		// Verarbeite Eingabe
		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case DATA:
				dataChange(makler);
				break;

			case DELETE:
				deleteAccount(makler);
				break;

			case BACK:
				return;
			}
		}
	}

	/**
	 * Zeigt das Immobilienverwaltungsmenu.
	 * 
	 * @param m
	 *            Makler-Account, dessen Immobilien verwaltet werden.
	 */
	public static void showEstatesMenu(Makler m) {
		// Menüoptionen
		final int SHOW_ESTATES = 0;
		final int CREATE = 1;
		final int UPDATE = 2;
		final int DELETE = 3;
		final int BACK = 4;

		Makler makler = m;

		// Makleraccountverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Immobilien");
		maklerMenu.addEntry("Meine Immobilien anzeigen", SHOW_ESTATES);
		maklerMenu.addEntry("Neue Immobilie hinzufügen", CREATE);
		maklerMenu.addEntry("Bestehende Immobilie ändern", UPDATE);
		maklerMenu.addEntry("Bestehende Immobilie löschen", DELETE);
		maklerMenu.addEntry("Zurück zur Account-Übersicht", BACK);

		// Verarbeite Eingabe
		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case SHOW_ESTATES:
				loadEstates(makler);
				break;
			case CREATE:
				newEstate(makler);
				break;
			case UPDATE:
				esateUpdate(makler);
				break;
			case DELETE:
				estateDelete(makler);
				break;
			case BACK:
				return;
			}
		}

	}

	/**
	 * Zeigt das Verträgeverwaltungsmenu.
	 * 
	 * @param m
	 *            Makler-Account, dessen für dessen Immobilie Verträge verwaltet
	 *            werden.
	 */
	public static void showContractsMenu(Makler m) {
		// Menüoptionen
		final int SHOW_CONTRACTS = 0;
		final int CREATE = 1;
		final int INSERT = 2;
		final int UPDATE = 3;
		final int BACK = 4;

		Makler makler = m;

		// Makleraccountverwaltungsmenü
		Menu maklerMenu = new Menu("Makler-Verträge");
		maklerMenu.addEntry("Meine Verträge anzeigen", SHOW_CONTRACTS);
		maklerMenu.addEntry("Vertrag erstellen", CREATE);
		maklerMenu.addEntry("Person erstellen", INSERT);
		maklerMenu.addEntry("Person-Daten ändern", UPDATE);
		maklerMenu.addEntry("Zurück zur Account-Übersicht", BACK);

		// Verarbeite Eingabe
		while (true) {
			int response = maklerMenu.show();

			switch (response) {
			case SHOW_CONTRACTS:
				loadContracts(makler);
				break;
			case CREATE:
				newContract(makler);
				break;
			case INSERT:
				newPerson();
				break;
			case UPDATE:
				personUpdate();
				break;
			case BACK:
				return;
			}
		}

	}

	/**
	 * Ändert persönliche Daten von einem Makler.
	 * 
	 * @param makler
	 */
	public static void personUpdate() {

		Person person = new Person();
		String answer = "";
		int answerInt = 0;
		int i;

		System.out.println("Personen: \n | ID | Firstname | Lastname | Address | \n");
		// Lade alle Personen aus der Datenbank
		ArrayList<Person> persons = new ArrayList<Person>();
		persons = Person.load();

		for (i = 0; i < persons.size(); i++) {

			System.out.println(persons.get(i).getId() + " " + persons.get(i).getFirstname() + " "
					+ persons.get(i).getLastname() + " " + persons.get(i).getAddress());
		}

		answerInt = FormUtil
				.readInt("Welche Personendaten möchten Sie ändern? Geben Sie bitte eine ID  von der Person ein");

		try {
			person = Person.load(answerInt);
			if (person != null) {
				System.out.println(
						"Um Die Daten zu ändern, fügen Sie die notwendigen Daten in folgenden Feldern ein. Wenn Sie ein Feld leer lassen, werden die Daten nicht geändert");

				answer = FormUtil.readString("Firstname");
				if (!answer.isEmpty()) {
					person.setFirstname(answer);
				}

				answer = FormUtil.readString("Lastname");
				if (!answer.isEmpty()) {
					person.setLastname(answer);
				}

				answer = FormUtil.readString("Address");
				if (!answer.isEmpty()) {
					person.setAddress(answer);
				}

				person.save();

				System.out.println("\n Die Daten wurden erfolgreich geändert! \n Firstname: " + person.getFirstname()
						+ "\n Lastname: " + person.getLastname() + "\n Address: " + person.getAddress() + "\n\n");

			} else {
				System.out.println("Die von Ihnen eingegebene ID ist ungültig!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Die eingegebene ID existiert nicht! Bitte versuchen Sie es erneuet!");
		}
	}

	/**
	 * Legt eine neue Person an, nachdem der Benutzer die entprechenden Daten
	 * eingegeben hat.
	 */
	public static void newPerson() {

		Person person = new Person();
		String answer;

		answer = FormUtil.readString("Firstname");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
			answer = FormUtil.readString("Firstname");
		}
		person.setFirstname(answer);

		answer = FormUtil.readString("Lastname");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
			answer = FormUtil.readString("Lastname");
		}
		person.setLastname(answer);

		answer = FormUtil.readString("Adresse");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
			answer = FormUtil.readString("Adresse");
		}
		person.setAddress(answer);

		person.save();

		System.out.println("Eine Person mit der ID " + person.getId() + " wurde erfolgreich erstellt!");
	}

	/**
	 * Legt einen neuen Vertrag an, nachdem der Benutzer die entprechenden Daten
	 * eingegeben hat.
	 */
	public static void newContract(Makler makler) {

		String str1 = "Mietvertrag";
		String str2 = "Kaufvertrag";
		String answer = "";
		int id;
		int i;
		Date datumContract;
		Date datumStart;
		Mietvertrag tenancy = new Mietvertrag();

		System.out.println("Hier sind Ihre Immobilien aufgelistet:");
		loadEstates(makler);

		ArrayList<Person> persons = new ArrayList<Person>();
		persons = Person.load();

		System.out.println("Personen: \n | ID | Firstname | Lastname | Adresse | \n");
		for (i = 0; i < persons.size(); i++) {

			System.out.println(persons.get(i).getId() + " " + persons.get(i).getFirstname() + " "
					+ persons.get(i).getLastname() + " " + persons.get(i).getAddress() + "  \n");
		}

		try {
			answer = FormUtil.readString("Mietvertrag/Kaufvertrag erstellen?");

			if (answer.toLowerCase().equals(str1.toLowerCase())) {

				// Check, ob die Wohnung dem Makler gehört
				id = FormUtil
						.readInt("Für welche Wohnung möchten Sie einen Vertrag erstellen? Geben Sie bitte eine ID ein");

				while (Wohnung.loadOneApartment(id).getAgent() == makler.getId()) {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen Sie es erneut! \n");
					id = FormUtil.readInt(
							"Für welche Wohnung möchten Sie einen Vertrag erstellen? Geben Sie bitte eine ID ein");
				}
				tenancy.setApartment_ID(id);

				datumContract = Date.valueOf(FormUtil.readString("Date (YYYY-MM-DD)"));
				tenancy.setDate(datumContract);
				tenancy.setPlace(FormUtil.readString("Place"));

				// Check, ob das Vertragsdatum später als Startdatum ist
				datumStart = Date.valueOf(FormUtil.readString("Start date (YYYY-MM-DD)"));

				while (datumContract.after(datumStart)) {
					System.out.println(
							"Das von Ihnen eingegebene Datum ist inkorrekt! Das Startdatum kann nicht vor dem Vertragsdatum liegen. Versuchen Sie es erneut! \n");
					datumStart = Date.valueOf(FormUtil.readString("Start date (YYYY-MM-DD)"));
				}
				tenancy.setStartdate(datumStart);
				tenancy.setDuration(FormUtil.readString("Duration"));
				tenancy.setAddcosts(FormUtil.readString("Addcosts"));

				// Check, ob die Person existiert
				id = FormUtil.readInt("Person ID");
				while (Person.load(id) == null) {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen SIe es erneut! \n");
					id = FormUtil.readInt("Person ID");
				}

				tenancy.setPerson_ID(id);
				tenancy.save();

				System.out.println(
						"Ein Mietvertrag mit der Nummer " + tenancy.getContractnr() + " wurde erfolgreich erstellt!");
				// showMainMenu();
				// TODO
			} else if (answer.toLowerCase().equals(str2.toLowerCase())) {

				id = FormUtil
						.readInt("Für welches Haus möchten Sie einen Vertrag erstellen? Geben Sie bitte eine ID ein");
				Kaufvertrag purchase = new Kaufvertrag();

				// Check, ob das Haus dem Makler gehört

				id = FormUtil
						.readInt("Für welches Haus möchten Sie einen Vertrag erstellen? Geben Sie bitte eine ID ein");

				while (Haus.loadOneHouse(id).getAgent() == makler.getId()) {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen Sie es erneut! \n");
					id = FormUtil.readInt(
							"Für welches Haus möchten Sie einen Vertrag erstellen? Geben Sie bitte eine ID ein");
				}
				purchase.setHouse_ID(id);

				purchase.setDate(Date.valueOf(FormUtil.readString("Date (YYYY-MM-DD)")));
				purchase.setPlace(FormUtil.readString("Place"));
				purchase.setInstallmentsnr(FormUtil.readInt("Installments number"));
				purchase.setRate(Float.valueOf(FormUtil.readString("Rate (XX,XX)")));

				purchase.setHouse_ID(id);

				// Check, ob die Person existiert
				id = FormUtil.readInt("Person ID");
				while (Person.load(id) == null) {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen SIe es erneut! \n");
					id = FormUtil.readInt("Person ID");
				}
				purchase.setPerson_ID(id);
				purchase.save();

				System.out.println(
						"Ein Kaufvertrag mit der Nummer " + purchase.getContractnr() + " wurde erfolgreich erstellt!");
				// showMainMenu();
				// TODO

			} else {
				System.out.println("Ihre Eingabe war falsch! Versuchen Sie es erneut!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Die eingegebene ID existiert nicht! Bitte versuchen Sie es erneuet!");
		}
	}

	/**
	 * Lädt Contracts, die der Makler abgeschlossen hat.
	 * 
	 * @param makler
	 */
	public static void loadContracts(Makler m) {
		Makler makler = m;
		int i;

		ArrayList<Mietvertrag> tenancies = new ArrayList<Mietvertrag>();
		tenancies = Mietvertrag.load(makler.getId());

		System.out.println(
				"Mietverträge: \n | Contract number | Date | Place | Start | Duration | Addcosts | Apartment_ID | Person_ID | \n");
		for (i = 0; i < tenancies.size(); i++) {

			System.out.println(tenancies.get(i).getContractnr() + " " + tenancies.get(i).getDate() + " "
					+ tenancies.get(i).getPlace() + " " + tenancies.get(i).getStartdate() + " "
					+ tenancies.get(i).getDuration() + " " + tenancies.get(i).getAddcosts() + " "
					+ tenancies.get(i).getApartment_ID() + " " + tenancies.get(i).getPerson_ID());
		}

		ArrayList<Kaufvertrag> purchases = new ArrayList<Kaufvertrag>();
		purchases = Kaufvertrag.load(makler.getId());

		System.out.println(
				"Kaufverträge: \n | Contract number | Date | Place | Installments number | Rate | House_ID | Person_ID | \n");
		for (i = 0; i < purchases.size(); i++) {

			System.out.println(purchases.get(i).getContractnr() + " " + purchases.get(i).getDate() + " "
					+ purchases.get(i).getPlace() + " " + purchases.get(i).getInstallmentsnr() + " "
					+ purchases.get(i).getRate() + " " + purchases.get(i).getHouse_ID() + " "
					+ purchases.get(i).getPerson_ID());
		}

	}

	/**
	 * Löscht eine Immobilie aus der DB.
	 * 
	 * @param makler
	 */
	public static void estateDelete(Makler m) {
		Makler makler = m;
		String str1 = "Wohnung";
		String str2 = "Haus";
		int id;
		String answer = "";

		System.out.println("Hier sind Ihre Immobilien aufgelistet\n");
		loadEstates(makler);

		try {
			answer = FormUtil.readString("Was möchten Sie löschen? (Wohnung/Haus)");
			// Check, ob ein richtiges Wort eingegeben wurde
			if (answer.toLowerCase().equals(str1.toLowerCase())) {
				id = FormUtil.readInt("Welche Wohnung möchten Sie löschen? Geben Sie bitte eine ID ein");

				// Check, ob die WOhnung dem Makler gehört
				if (Wohnung.loadOneApartment(id).getAgent() == makler.getId()) {
					Wohnung apartment = new Wohnung();
					apartment = Wohnung.loadOneApartment(id);
					apartment.delete();
					System.out.println("Die Wohnung wurde erfolgreich gelöscht!");
				} else {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen Sie es erneut!");
				}

			} else if (answer.toLowerCase().equals(str2.toLowerCase())) {
				id = FormUtil.readInt("Bei welchem Haus möchten Sie Änderungen vornehmen? Geben Sie bitte eine ID ein");

				// Check, ob die WOhnung dem Makler gehört
				if (Wohnung.loadOneApartment(id).getAgent() == makler.getId()) {
					Haus house = new Haus();
					house = Haus.loadOneHouse(id);
					house.delete();
					System.out.println("Das Haus wurde erfolgreich gelöscht!");
				} else {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen Sie es erneut!");
				}
			} else {
				System.out.println("Bitte geben Sie entweder 'Haus' oder 'Wohnung' ein!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Die eingegebene ID existiert nicht! Bitte versuchen Sie es erneuet!");
		}
	}

	/**
	 * Ändert persönliche Daten von einem Makler.
	 * 
	 * @param makler
	 */
	public static void esateUpdate(Makler m) {

		Makler makler = m;
		String answer = "";
		int answerInt = 0;
		String str1 = "Wohnung";
		String str2 = "Haus";
		int id;

		System.out.println("Hier sind Ihre Immobilien aufgelistet\n");
		loadEstates(makler);

		try {
			answer = FormUtil.readString("Wo möchten Sie die Änderungen vornehmen? (Wohnung/Haus)");
			if (answer.toLowerCase().equals(str1.toLowerCase())) {

				id = FormUtil
						.readInt("Bei welcher Wohnung möchten Sie Änderungen vornehmen? Geben Sie bitte eine ID ein");

				// TODO Check, ob die WOhnung dem Makler gehört
				if (Wohnung.loadOneApartment(id).getAgent() == makler.getId()) {
					Wohnung apartment = new Wohnung();
					apartment = Wohnung.loadOneApartment(id);
					System.out.println(
							"Um Ihre Daten zu dieser Wohnung zu ändern, fügen Sie die notwendigen Daten in folgenden Feldern ein. Wenn Sie ein Feld leer lassen, werden die Daten nicht geändert");

					answer = FormUtil.readString("City");
					if (!answer.isEmpty()) {
						apartment.setCity(answer);
					}

					answer = FormUtil.readString("Postcode");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						apartment.setPostcode(answerInt);
					}

					answer = FormUtil.readString("Street");
					if (!answer.isEmpty()) {
						apartment.setStreet(answer);
					}

					answer = FormUtil.readString("Street number");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						apartment.setStreetnr(answerInt);
					}

					answer = FormUtil.readString("Square");
					if (!answer.isEmpty()) {
						apartment.setSquare(answer);
					}

					answer = FormUtil.readString("Floor");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						apartment.setFloor(answerInt);
					}

					answer = FormUtil.readString("Rent");
					if (!answer.isEmpty()) {
						apartment.setRent(answer);
					}

					answer = FormUtil.readString("Rooms");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						apartment.setRooms(answerInt);
					}

					answer = FormUtil.readString("Balcony (1/0)");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);

						// Check, ob 1 oder 0 eingegeben wurd
						while (answerInt != 0 && answerInt != 1) {
							System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
							answer = FormUtil.readString("Balcony (1/0)");
						}
						apartment.setBalcony(answerInt);
					}

					answer = FormUtil.readString("Kitchen (1/0)");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);

						// Check, ob 1 oder 0 eingegeben wurd
						while (answerInt != 0 && answerInt != 1) {
							System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
							answer = FormUtil.readString("Kitchen (1/0)");
						}
						apartment.setKitchen(answerInt);
					}
					apartment.save();

					System.out.println("\n Die Daten wurden erfolgreich geändert! \n City: " + apartment.getCity()
							+ "\n Postcode: " + apartment.getPostcode() + "\n Street: " + apartment.getStreet()
							+ "\n Street number: " + apartment.getStreetnr() + "\n Square: " + apartment.getSquare()
							+ "\n Floor number: " + apartment.getFloor() + "\n Rent: " + apartment.getRent()
							+ "\n Rooms: " + apartment.getRooms() + "\n Balcony: " + toString(apartment.getBalcony())
							+ "\n Kitchen: " + toString(apartment.getKitchen()) + "\n\n");
				} else {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen SIe es erneut!");
				}

			} else if (answer.toLowerCase().equals(str2.toLowerCase())) {
				id = FormUtil.readInt("Bei welchem Haus möchten Sie Änderungen vornehmen? Geben Sie bitte eine ID ein");

				// Check, ob die Haus dem Makler gehört
				if (Haus.loadOneHouse(id).getAgent() == makler.getId()) {
					Haus house = new Haus();
					house = Haus.loadOneHouse(id);

					System.out.println(
							"Um Ihre Daten zu diesem Haus zu ändern, fügen Sie die notwendigen Daten in folgenden Feldern ein. Wenn Sie ein Feld leer lassen, werden die Daten nicht geändert");

					answer = FormUtil.readString("City");
					if (!answer.isEmpty()) {
						house.setCity(answer);
					}

					answer = FormUtil.readString("Postcode");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						house.setPostcode(answerInt);
					}

					answer = FormUtil.readString("Street");
					if (!answer.isEmpty()) {
						house.setStreet(answer);
					}

					answer = FormUtil.readString("Street number");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						house.setStreetnr(answerInt);
					}

					answer = FormUtil.readString("Square");
					if (!answer.isEmpty()) {
						house.setSquare(answer);
					}

					answer = FormUtil.readString("Floors");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);
						house.setFloors(answerInt);
					}

					answer = FormUtil.readString("Price");
					if (!answer.isEmpty()) {
						house.setPrice(answer);
					}

					answer = FormUtil.readString("Garden (1/0)");
					if (!answer.isEmpty()) {
						answerInt = Integer.parseInt(answer);

						// Check, ob 1 oder 0 eingegeben wurd
						while (answerInt != 0 && answerInt != 1) {
							System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
							answer = FormUtil.readString("Garden (1/0)");
						}
						house.setGarden(answerInt);
					}
					house.save();

					System.out.println("\n Die Daten wurden erfolgreich geändert! \n City: " + house.getCity()
							+ "\n Postcode: " + house.getPostcode() + "\n Street: " + house.getStreet()
							+ "\n Street number: " + house.getStreetnr() + "\n Square: " + house.getSquare()
							+ "\n Floors: " + house.getFloors() + "\n Price: " + house.getPrice() + "\n Garden: "
							+ toString(house.getGarden()) + "\n\n");
				} else {
					System.out.println("Die von Ihnen eingegebene ID ist ungültig! Versuchen Sie es erneut!");
				}
			} else {
				System.out.println("Ihre Eingabe ist falsch! Versuchen SIe es erneut!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Die eingegebene ID existiert nicht! Bitte versuchen Sie es erneuet!");
		}

	}

	/**
	 * Legt eine neue Immobilie an, nachdem der Benutzer die entprechenden Daten
	 * eingegeben hat.
	 */
	public static void newEstate(Makler makler) {

		String str1 = "Wohnung";
		String str2 = "Haus";
		String answer = "";
		int answerInt = 0;
		try {
			answer = FormUtil.readString("Wohnung/Haus?");
			if (answer.toLowerCase().equals(str1.toLowerCase())) {

				Wohnung apartment = new Wohnung();

				apartment.setAgent(makler.getId());

				answer = FormUtil.readString("City");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("City");
				}
				apartment.setCity(answer);

				apartment.setPostcode(FormUtil.readInt("Postcode"));

				answer = FormUtil.readString("Street");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Street");
				}
				apartment.setStreet(answer);

				apartment.setStreetnr(FormUtil.readInt("Street Number"));

				answer = FormUtil.readString("Square");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Square");
				}
				apartment.setSquare(answer);

				apartment.setFloor(FormUtil.readInt("Floor number"));

				answer = FormUtil.readString("Rent");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Rent");
				}
				apartment.setRent(answer);

				apartment.setRooms(FormUtil.readInt("Rooms"));

				answer = FormUtil
						.readString("Balcony (1/0). Wenn Sie nichts eingeben, wird als Default eine 0 übernommen");
				if (!answer.isEmpty()) {
					answerInt = Integer.parseInt(answer);

					// Check, ob 1 oder 0 eingegeben wurd
					while (answerInt != 0 && answerInt != 1) {
						System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
						answer = FormUtil.readString("Balcony (1/0)");
					}
					apartment.setBalcony(answerInt);
				}

				answer = FormUtil
						.readString("Kitchen (1/0). Wenn Sie nichts eingeben, wird als Default eine 0 übernommen");
				if (!answer.isEmpty()) {
					answerInt = Integer.parseInt(answer);

					// Check, ob 1 oder 0 eingegeben wurd
					while (answerInt != 0 && answerInt != 1) {
						System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
						answer = FormUtil.readString("Kitchen (1/0)");
					}
					apartment.setKitchen(answerInt);
				}
				apartment.save();

				System.out.println("Eine Wohnung mit der ID " + apartment.getId() + " wurde erfolgreich erstellt!");

			} else if (answer.toLowerCase().equals(str2.toLowerCase())) {

				Haus house = new Haus();

				house.setAgent(makler.getId());

				answer = FormUtil.readString("City");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("City");
				}
				house.setCity(answer);

				house.setPostcode(FormUtil.readInt("Postcode"));

				answer = FormUtil.readString("Street");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Street");
				}
				house.setStreet(answer);

				house.setStreetnr(FormUtil.readInt("Street Number"));

				answer = FormUtil.readString("Square");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Square");
				}
				house.setSquare(answer);

				house.setFloors(FormUtil.readInt("Floors"));

				answer = FormUtil.readString("Price");
				// Check, ob die Eingabe leer ist
				while (answer.isEmpty()) {
					System.out.println("Die Eingabe kann nicht leer sein! Bitte vresuchen Sie es erneut!");
					answer = FormUtil.readString("Price");
				}
				house.setPrice(answer);

				answer = FormUtil
						.readString("Garden (1/0). Wenn Sie nichts eingeben, wird als Default eine 0 übernommen");
				if (!answer.isEmpty()) {
					answerInt = Integer.parseInt(answer);

					// Check, ob 1 oder 0 eingegeben wurd
					while (answerInt != 0 && answerInt != 1) {
						System.out.println("Die Eingabe kann entweder 0 oder 1 sein! Versuchen Sie es erneut!");
						answer = FormUtil.readString("Garden (1/0)");
					}
					house.setGarden(answerInt);
				}
				house.save();

				System.out.println("Ein Haus mit der ID " + house.getId() + " wurde erfolgreich erstellt!");
			} else {
				System.out.println("Die Eingabe war falsch! Bitte versuchen Sie es erneut!");
			}
		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Die eingegebene ID existiert nicht! Bitte versuchen Sie es erneuet!");
		}
	}

	/**
	 * Lädt Häuser und Wohnungen, die der Makler verwaltet.
	 * 
	 * @param makler
	 */
	public static void loadEstates(Makler m) {
		Makler makler = m;
		int i;

		ArrayList<Haus> houses = new ArrayList<Haus>();
		houses = Haus.load(makler.getId());

		System.out.println(
				"Häuser: \n | ID | City | Postcode | Street | Streetnr. | Square | Floors | Price | Garden | \n");
		for (i = 0; i < houses.size(); i++) {

			System.out.println(houses.get(i).getId() + " " + houses.get(i).getCity() + " " + houses.get(i).getPostcode()
					+ " " + houses.get(i).getStreet() + " " + houses.get(i).getStreetnr() + " "
					+ houses.get(i).getSquare() + " " + houses.get(i).getFloors() + " " + houses.get(i).getPrice() + " "
					+ toString(houses.get(i).getGarden()) + "\n");
		}

		ArrayList<Wohnung> apartments = new ArrayList<Wohnung>();
		apartments = Wohnung.load(makler.getId());

		System.out.println(
				"Wohnungen: \n | ID | City | Postcode | Street | Streetnr. | Square | Floor | Rent | Balcony | Kitchen | \n");
		for (i = 0; i < apartments.size(); i++) {

			System.out.println(apartments.get(i).getId() + " " + apartments.get(i).getCity() + " "
					+ apartments.get(i).getPostcode() + " " + apartments.get(i).getStreet() + " "
					+ apartments.get(i).getStreetnr() + " " + apartments.get(i).getSquare() + " "
					+ apartments.get(i).getFloor() + " " + apartments.get(i).getRent() + " "
					+ toString(apartments.get(i).getBalcony()) + " " + toString(apartments.get(i).getKitchen()) + "\n");
		}

	}

	/**
	 * Kleine Hilfsmethode, um 1 und 0 zu Boolean zu konvertieren.
	 * 
	 * @param int1
	 *            Integer
	 * @return boolean
	 */
	private static String toString(int int1) {
		if (int1 == 1) {
			return "Yes";
		} else {
			return "No";
		}
	}

	/**
	 * Löscht einen Makler-Account aus der DB.
	 * 
	 * @param makler
	 */
	public static void deleteAccount(Makler m) {
		Makler makler = m;
		String answer;
		System.out.println(
				"Sind Sie sicher, dass Ihr Account gelöscht werden soll? Diesen Vorgang kann man nicht rückgängig machen!");
		answer = FormUtil.readString("Ja/Nein");
		if (answer.toLowerCase().equals("ja")) {
			makler.delete();
			System.out.println("Ihr Account wurde erfolgreich gelöscht!");
			showMainMenu();
		} else if (answer.toLowerCase().equals("nein")) {
			System.out.println("Ihr Account mit der ID " + makler.getId() + " bleibt bestehen!");
		} else {
			System.out.println("Ihre Eingabe war falsch! Versuchen Sie es erneut!");
		}
	}

	/**
	 * Ändert persönliche Daten von einem Makler.
	 * 
	 * @param makler
	 */
	public static void dataChange(Makler m) {
		Makler makler = m;
		String answer = "";

		System.out.println(
				"Um Ihre Daten zu ändern, fügen Sie die notwendigen Daten in folgenden Feldern ein. Wenn Sie ein Feld leer lassen, werden die Daten nicht geändert");

		answer = FormUtil.readString("Name");
		if (!answer.isEmpty()) {
			makler.setName(answer);
		}

		answer = FormUtil.readString("Adresse");
		if (!answer.isEmpty()) {
			makler.setAddress(answer);
		}

		answer = FormUtil.readString("Login");
		if (!answer.isEmpty()) {
			makler.setLogin(answer);
		}

		answer = FormUtil.readString("Passwort");
		if (!answer.isEmpty()) {
			makler.setPassword(answer);
		}
		makler.save();

		System.out.println("\n Ihre persönliche Daten wurden erfolgreich geändert! \n Name: " + makler.getName()
				+ "\n Adresse: " + makler.getAddress() + "\n Login: " + makler.getLogin() + "\n Passwort: "
				+ makler.getPassword() + "\n\n");
	}

	/**
	 * Login für ein Makler, nachdem der Benutzer die entprechenden Daten eingegeben
	 * hat, wird ein Maklerverwaltungsmenu angezeigt.
	 */
	public static void loginMakler() {
		Makler makler = new Makler();
		try {
			makler = Makler.loadWithLogin(FormUtil.readString("Login"), FormUtil.readString("Passwort"));

			System.out.println("Willkommen zurück, " + makler.getName()
					+ "! Nun können Sie Ihren Account und/oder Ihre Immobilien verwalten!\n");
			showMaklerVerwaltungMenu(makler);

		} catch (NullPointerException e) {
			e.printStackTrace();
			System.out.println("Das Passwort und das Login sind nicht korrekt! Versuchen Sie erneut!");
		}
	}

	/**
	 * Legt einen neuen Makler an, nachdem der Benutzer die entprechenden Daten
	 * eingegeben hat.
	 */
	public static void newMakler() {
		Makler m = new Makler();
		String answer;

		answer = FormUtil.readString("Name");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte versuchen Sie es erneut!");
			answer = FormUtil.readString("Name");
		}
		m.setName(answer);

		answer = FormUtil.readString("Adresse");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte versuchen Sie es erneut!");
			answer = FormUtil.readString("Adresse");
		}
		m.setAddress(answer);

		answer = FormUtil.readString("Login");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte versuchen Sie es erneut!");
			answer = FormUtil.readString("Login");
		}
		m.setLogin(answer);

		answer = FormUtil.readString("Passwort");
		// Check, ob die Eingabe leer ist
		while (answer.isEmpty()) {
			System.out.println("Die Eingabe kann nicht leer sein! Bitte versuchen Sie es erneut!");
			answer = FormUtil.readString("Passwort");
		}
		m.setPassword(answer);

		m.save();

		System.out.println("Makler mit der ID " + m.getId() + " wurde erzeugt. \n");
	}
}
