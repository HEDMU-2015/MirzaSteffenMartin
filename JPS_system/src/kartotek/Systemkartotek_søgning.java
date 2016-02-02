package kartotek;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domæne.KompetentMedarbejder;
import logik.Søgning;
import old.files.Kompetence;

public class Systemkartotek_søgning {

	public List opslagPåKompetence(List<Integer> brugfor) throws SQLException {

		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection.prepareStatement("SELECT Medarbejderid, Kompetenceid FROM kompmed");
		ResultSet resultset = statement.executeQuery();

		Map<String, List<Integer>> medarbkomp = new HashMap<String, List<Integer>>();
		Map<String, List<Integer>> harkomp = new HashMap<String, List<Integer>>();

		while (resultset.next()) {
			String navn = resultset.getString("Medarbejderid");

			int kompetence = resultset.getInt("Kompetenceid");

			ArrayList temp = new ArrayList();

			if (!medarbkomp.containsKey(navn)) {
				temp.add(kompetence);
				medarbkomp.put(navn.toString(), temp);
			} else {
				medarbkomp.get(navn).add(kompetence);

			}

		}

		for (Entry<String, List<Integer>> entry : medarbkomp.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();

			for (int i = 0; i < brugfor.size(); i++) {
				ArrayList kompetencer = new ArrayList();

				if (medarbkomp.get(key).contains(brugfor.get(i))) {
					kompetencer.add(brugfor.get(i));

					if (!harkomp.containsKey(key)) {
						harkomp.put(key, kompetencer);

					} else {
						harkomp.get(key).addAll(kompetencer);

					}

				}

			}
		}
		Søgning søg = new Søgning();
		List<KompetentMedarbejder> kompetenteMedarbejdere = new ArrayList();

		//kompetenteMedarbejdere = søg.oversætKompetence(harkomp);

		resultset.close();
		statement.close();
		connection.close();
		return kompetenteMedarbejdere;

	}

	public ArrayList søgAfdeling(String søgeord) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Afdelinger WHERE Afdeling LIKE'%" + søgeord + "%';");
		ResultSet resultset = statement.executeQuery();

		ArrayList afdeling = new ArrayList();
		while (resultset.next()) {

			afdeling.add(resultset.getString("Afdeling"));

		}
		return afdeling;

	}

	public ArrayList søgMedarbejderNavn(String søgeord) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Medarbejdere WHERE navn LIKE'%" + søgeord + "%';");
		ResultSet resultset = statement.executeQuery();

		ArrayList afdeling = new ArrayList();
		while (resultset.next()) {

			afdeling.add(resultset.getString("navn"));

		}
		return afdeling;

	}

	public ArrayList søgMedarbejderEmail(String søgeord) throws SQLException {
		Systemkartotek_forbindelser sk = new Systemkartotek_forbindelser();
		Connection connection = sk.connectTilDB();

		PreparedStatement statement = connection
				.prepareStatement("SELECT * FROM Medarbejdere WHERE email LIKE'%" + søgeord + "%';");
		ResultSet resultset = statement.executeQuery();

		ArrayList afdeling = new ArrayList();
		while (resultset.next()) {

			afdeling.add(resultset.getString("email"));

		}
		return afdeling;

	}
}
