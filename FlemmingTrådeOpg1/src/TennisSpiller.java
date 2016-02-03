import java.util.Random;

public class TennisSpiller extends Thread {
String navn;
TennisSpiller mod;
String modstandernavn;

	public TennisSpiller(String navn){
		this.navn = navn;
		
	}
	
	@Override
	public void run(){
		
	
	}
	
	public void setModstander(TennisSpiller mod){
		this.mod=mod;
		System.out.println("hej mit navn er: "+navn);
		
	}
	
	public void modtagBold(){
		double d = Math.random();
		
		
		try {
			Thread.sleep((long)(Math.random() * 2000));

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (d < 0.8){
		System.out.println(navn+" siger "+" bold modtaget");
		System.out.println(navn+" siger jeg server nu!");
		mod.modtagBold();
		} else System.out.println(navn+" siger jeg har fucket op :(");
		
		
		
	}
	
	
	
	
}
