package old.files;

import javafx.beans.property.SimpleStringProperty;

public class Kompetence {
	private SimpleStringProperty kompetence = new SimpleStringProperty("");
public Kompetence(){
	
}

public String getKompetence(){
	return kompetence.get();
}

public void setKompetence(String value){
	kompetence.set(value);
}
//top kek
@Override
public String toString(){
	return getKompetence();
}
}
