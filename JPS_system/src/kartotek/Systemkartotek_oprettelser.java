package kartotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Systemkartotek_oprettelser {

	public void opretNyKompetence(String kompetence, String afdeling) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("INSERT INTO Kompetencer (Kompetence, Afdeling) values (?,?)");
		statement.setString(1, kompetence);
		statement.setString(2, afdeling);

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}

	}

	public void opretNyAfdeling(String Afdeling, List kompetencer) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("INSERT INTO Afdelinger (Afdeling) values (?)");
		statement.setString(1, Afdeling);

		try {

			statement.execute();

			for (int i = 0; i < kompetencer.size(); i++) {
				opretNyKompetence(kompetencer.get(i).toString(), Afdeling);
			}

		} catch (SQLIntegrityConstraintViolationException s) {

		}

	}

	public void opretNyMedarbejder(String navn, String email, List<Integer> kompetencer) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Systemkartotek_opslag skopslag = new Systemkartotek_opslag();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("INSERT INTO Medarbejdere (navn,email) values (?,?)");
		statement.setString(1, navn);
		statement.setString(2, email);

		try {

			statement.execute();

			for (int i = 0; i < kompetencer.size(); i++) {
				opretNyKompMed(skopslag.findMedarbID(navn), kompetencer.get(i));
			}

		} catch (SQLIntegrityConstraintViolationException s) {

		}
	}

	public void opretNyKompMed(int medID, int kompID) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Systemkartotek_opslag skopslag = new Systemkartotek_opslag();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("INSERT INTO kompmed (medarbejderid,kompetenceid) values (?,?)");
		statement.setInt(1, medID);
		statement.setInt(2, kompID);

		try {

			statement.execute();

		} catch (SQLIntegrityConstraintViolationException s) {

		}
	}
}
