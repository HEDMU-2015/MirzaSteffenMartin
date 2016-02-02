package kartotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class Systemkartotek_redigering {

	Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();

	public void redigerKompetence(String kompetence, String Afdeling) throws SQLException {
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement(
				"UPDATE Kompetencer SET Afdeling ='" + Afdeling + "' Where Kompetence ='" + kompetence + "';");

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}

	}

	public void setKompetence(String kompetence) throws SQLException {
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement(
				"UPDATE Redigeringsvariabel SET Kompetence ='" + kompetence + "' WHERE type = 'Kompetence';");

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}
	}

	public String getKompetence() throws SQLException {
		String kompetence = null;
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT Kompetence FROM Redigeringsvariabel WHERE type = 'Kompetence';");
		ResultSet resultset = statement.executeQuery();

		try {
			while (resultset.next()) {
				kompetence = resultset.getString("Kompetence");
				statement.execute();

			}
		} catch (SQLIntegrityConstraintViolationException s) {

		}
		return kompetence;
	}

	public void setAfdeling(String afdeling) throws SQLException {
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement(
				"UPDATE Redigeringsvariabel SET Kompetence ='" + afdeling + "' WHERE type = 'Afdeling';");

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}
	}

	public String getAfdeling() throws SQLException {
		String afdeling = null;
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT Kompetence FROM Redigeringsvariabel WHERE type = 'Afdeling';");
		ResultSet resultset = statement.executeQuery();

		try {
			while (resultset.next()) {
				afdeling = resultset.getString("Kompetence");
				statement.execute();

			}
		} catch (SQLIntegrityConstraintViolationException s) {

		}

		return afdeling;
	}

	public String getMedarbejder() throws SQLException {
		String ID = null;
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT Kompetence FROM Redigeringsvariabel WHERE type = 'Medarbejder';");
		ResultSet resultset = statement.executeQuery();

		try {
			while (resultset.next()) {
				ID = resultset.getString("Kompetence");
				statement.execute();
				System.out.println("Du vil gerne redigere" + ID);
			}
		} catch (SQLIntegrityConstraintViolationException s) {

		}

		return ID;
	}

	public void setMedarbejder(String ID) throws SQLException {
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement(
				"UPDATE Redigeringsvariabel SET Kompetence ='" + ID + "' WHERE type = 'Medarbejder';");

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}
	}

	public void redigerAfdeling(String afdelingny, String afdelinggammel) throws SQLException {
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("Insert INTO Afdelinger (Afdeling) VALUES('" + afdelingny + "')");
		PreparedStatement statement2 = connection.prepareStatement(
				"UPDATE Kompetencer SET Afdeling ='" + afdelingny + "' WHERE Afdeling ='" + afdelinggammel + "';");
		PreparedStatement statement3 = connection
				.prepareStatement("DELETE FROM Afdelinger WHERE Afdeling = '" + afdelinggammel + "';");

		try {

			statement.execute();
			statement2.execute();
			statement3.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

			s.printStackTrace();
		}

	}

	public void redigerMedarbejder(String navn, String email, List kompetencer) throws SQLException {
		Connection connection = sk.connectTilDB();
		Systemkartotek_redigering sysoRe = new Systemkartotek_redigering();
		PreparedStatement statement = connection.prepareStatement(
				"Update Medarbejdere SET NAVN ='" + navn + "'WHERE ID ='" + sysoRe.getMedarbejder() + "';");
		PreparedStatement statement2 = connection.prepareStatement(
				"Update Medarbejdere SET EMAIL ='" + email + "'WHERE ID ='" + sysoRe.getMedarbejder() + "';");
		PreparedStatement statement4 = connection
				.prepareStatement("DELETE FROM kompmed WHERE medarbejderID ='" + sysoRe.getMedarbejder() + "';");

		try {

			statement.execute();
			statement2.execute();
			statement4.execute();

			for (int i = 0; i < kompetencer.size(); i++) {
				PreparedStatement statement3 = connection
						.prepareStatement("INSERT INTO KOMPMED(KompetenceID,MedarbejderID) VALUES('"
								+ kompetencer.get(i) + "','" + sysoRe.getMedarbejder() + "');");
				statement3.execute();
			}

		} catch (SQLIntegrityConstraintViolationException s) {

			s.printStackTrace();
		}

	}

	public String kompetencerUdFraID(String kompID) throws SQLException {

		Connection connection = sk.connectTilDB();
		String kompetence = null;
		PreparedStatement statement = connection
				.prepareStatement("SELECT Kompetence FROM Kompetencer WHERE ID ='" + kompID + "';");
		ResultSet resultset = statement.executeQuery();

		while (resultset.next()) {
			kompetence = resultset.getString("Kompetence");
			statement.execute();

		}
		return kompetence;
	}
}
