
public class Controller {

	public static void main(String[] args) {
		TennisSpiller t1 = new TennisSpiller("T1");
		TennisSpiller t2 = new TennisSpiller("T2");
		t1.setModstander(t2);
		t2.setModstander(t1);
		
		t1.start();
		t2.start();
		t1.modtagBold();
		

	}

}
