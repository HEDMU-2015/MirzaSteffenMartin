package logik;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import domæne.KompetentMedarbejder;
import kartotek.Systemkartotek_opslag;

public class Søgning {

	public List oversætKompetence(Map<String, List<Integer>> harkomp) throws SQLException{
		Systemkartotek_opslag sysoOp = new Systemkartotek_opslag();
		ArrayList kompetenteMedarbejdere = new ArrayList();
		for (Entry<String, List<Integer>> entry : harkomp.entrySet()) {
			String navn = null;
			String email = null;
			try{
			navn = (String) sysoOp.findMedarbejderID(entry.getKey()).get(0);
			
			email =  (String) sysoOp.findMedarbejderID(entry.getKey()).get(1);
	
			} catch(Exception e){
				
			}
			String ID = entry.getKey();
		
			List<Integer> kompetencer = entry.getValue();
			List nyKompetencer = new ArrayList();
			nyKompetencer = sysoOp.findKompetenceViaID(kompetencer);
			
			KompetentMedarbejder komMed = new KompetentMedarbejder(navn, email, nyKompetencer, ID);
			
			kompetenteMedarbejdere.add(komMed);
			
			
		}
		
		return kompetenteMedarbejdere;
	} 
}
