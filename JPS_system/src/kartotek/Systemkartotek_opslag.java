package kartotek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domæne.KompetentMedarbejder;
import kartotek.Systemkartotek_forbindelser;
import logik.Søgning;

public class Systemkartotek_opslag {

	public String findAfdeling(String kompetence) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		String afdeling = null;
		PreparedStatement statement = connection
				.prepareStatement("SELECT Afdeling FROM Kompetencer WHERE Kompetence ='" + kompetence + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			afdeling = resultset.getString("Afdeling");
		}
		return afdeling;

	}

	public List findMedarbejderID(String ID) throws SQLException {

		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();
		ArrayList navnEmail = new ArrayList<String>();

		PreparedStatement statement = connection
				.prepareStatement("SELECT navn,email FROM Medarbejdere WHERE ID ='" + ID + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {

			String navn = resultset.getString("navn");
			String email = resultset.getString("email");

			navnEmail.add(navn);
			navnEmail.add(email);
		}

		return navnEmail;
	}

	public List findKompetenceViaID(List kompetencer) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();
		ArrayList kompetenceListe = new ArrayList<>();

		for (Object i : kompetencer) {

			PreparedStatement statement = connection
					.prepareStatement("SELECT kompetence FROM Kompetencer WHERE ID ='" + i + "';");
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				String kompetence = resultset.getString("kompetence");
				kompetenceListe.add(kompetence);

			}

		}

		return kompetenceListe;
	}

	public int findMedarbID(String navn) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		int ID = 0;
		PreparedStatement statement = connection
				.prepareStatement("SELECT ID FROM Medarbejdere WHERE navn ='" + navn + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			ID = resultset.getInt("ID");
		}
		return ID;

	}

	public int findKompID(String kompetence) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		int ID = 0;
		PreparedStatement statement = connection
				.prepareStatement("SELECT ID FROM Kompetencer WHERE kompetence ='" + kompetence + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			ID = resultset.getInt("ID");
		}
		return ID;

	}

	public List getKompetencer() throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		ArrayList kompetencer = new ArrayList();
		PreparedStatement statement = connection.prepareStatement("SELECT Kompetence FROM Kompetencer");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			String kompetence = resultset.getString("Kompetence");
			kompetencer.add(resultset.getString("Kompetence"));
		}
		return kompetencer;
	}

	public List findKompViaMedarbID(String ID) throws SQLException {

		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT Kompetenceid FROM kompmed WHERE Medarbejderid ='" + ID + "';");
		ResultSet resultset = statement.executeQuery();

		ArrayList temp = new ArrayList();

		while (resultset.next()) {
			int kompetence = resultset.getInt("Kompetenceid");
			// dette dur fint
			temp.add(kompetence);

		} // her har vi vores hashmap som har medarbejdere samt deres
			// kompetencer.

		List nytemp = new ArrayList();
		nytemp = findKompetenceViaID(temp);

		resultset.close();
		statement.close();
		connection.close();
		return nytemp;

	}

	public Map getKompetencerAfdeling() throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		HashMap<String, List> Afdeling = new HashMap();

		PreparedStatement statement = connection.prepareStatement("SELECT Kompetence,Afdeling FROM Kompetencer");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			ArrayList kompetencer = new ArrayList();
			String kompetence = resultset.getString("Kompetence");
			String afdeling = resultset.getString("afdeling");

			if (!Afdeling.containsKey(afdeling)) {
				kompetencer.add(kompetence);
				Afdeling.put(afdeling, kompetencer);
			} else if (Afdeling.containsKey(afdeling)) {
				Afdeling.get(afdeling).add(kompetence);
			}
		}
		
		return Afdeling;
	}

	public String getKompetenceID(String kompetence) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		String kompetenceID = null;
		PreparedStatement statement = connection
				.prepareStatement("SELECT ID FROM Kompetencer WHERE Kompetence ='" + kompetence + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			kompetenceID = resultset.getString("ID");
		}
		return kompetenceID;

	}

	public List findAfdelinger() throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		ArrayList afdelinger = new ArrayList<>();
		String afdeling = null;
		PreparedStatement statement = connection.prepareStatement("SELECT Afdeling FROM Kompetencer");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			afdeling = resultset.getString("Afdeling");
			if (!afdelinger.contains(afdeling)) {
				afdelinger.add(afdeling);
			}
		}
		return afdelinger;

	}

	public boolean erIAfdeling(String afdeling) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("SELECT Afdeling FROM Kompetencer;");
		ResultSet resultset = statement.executeQuery();
		boolean findes = false;
		while (resultset.next()) {
			if (afdeling == resultset.getString("Afdeling")) {
				findes = true;

				break;
			} else
				findes = false;

		}
		return findes;

	}

	public boolean erMedarbejder(String ID) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("SELECT ID FROM Medarbejdere;");
		ResultSet resultset = statement.executeQuery();
		boolean findes = false;
		while (resultset.next()) {
			String newID = resultset.getString("ID");
			if (ID == newID) {
				findes = true;

				break;
			} else
				findes = false;

		}
		return findes;

	}

	public boolean erIKompetence(String kompetence) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("SELECT Kompetence FROM Kompetencer;");
		ResultSet resultset = statement.executeQuery();
		boolean findes = false;
		while (resultset.next()) {
			if (kompetence == resultset.getString("Kompetence")) {
				findes = true;

				break;
			} else
				findes = false;

		}
		return findes;

	}

	public List redigeringsKompetencer(String ID) throws SQLException {
		Systemkartotek_forbindelser sysofo = new Systemkartotek_forbindelser();
		sysofo.connectTilDB();
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		List kompetencerOversat = new ArrayList();
		PreparedStatement statement = connection
				.prepareStatement("SELECT KompetenceID FROM KompMed WHERE MedarbejderID ='" + ID + "';");
		ResultSet resultset = statement.executeQuery();
		List<String> kompetencer = new ArrayList();
		while (resultset.next()) {
			kompetencer.add(resultset.getString("KompetenceID"));

		}
		Systemkartotek_redigering sysoRe = new Systemkartotek_redigering();
		for (String i : kompetencer) {

			kompetencerOversat.add(sysoRe.kompetencerUdFraID(i));
		}
		return kompetencerOversat;

	}
}
