package domæne;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kartotek.Systemkartotek_opslag;
import kartotek.Systemkartotek_søgning;

public class KompetentMedarbejder {
	String navn;
	String email;
	List kompetencer;
	String ID;
	Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();
	Systemkartotek_søgning sysoSø = new Systemkartotek_søgning();
	HashMap medarbejder = new HashMap();

	public KompetentMedarbejder(String navn, String email, List kompetencer, String ID) {
		this.navn = navn;
		this.email = email;
		this.kompetencer = kompetencer;
		this.ID = ID;
	}

	public String getNavn() throws SQLException {
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();

		this.navn = navn;
		return this.navn;
	}

	public void setNavn(String navn) throws SQLException {
		this.navn = navn;

		this.navn = navn;

	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) throws SQLException {
		this.email = email;
	}

	public List getKompetencer() {
		return kompetencer;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = ID;
	}

}
