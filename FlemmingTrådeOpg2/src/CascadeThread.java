
public class CascadeThread extends Thread {
Boolean m�Start = false;
CascadeThread n�ste;
String navn;

	public CascadeThread(String navn){
		this.navn = navn;
		
	}
	
	public void run(){
		for(;;){
			if(m�Start == true){
				try {
					Thread.sleep((long)(Math.random() * 2000));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(navn+"er v�ltet");
				try{
				n�ste.start();
				n�ste.v�lt();
				} catch(Exception e){
					System.out.println("f�rdig!");
				}
				
				break;
			}
		}
	}
	
	public void v�lt(){
		m�Start = true;
	}
	
	public void setN�ste(CascadeThread n�ste){
		this.n�ste = n�ste;
	}
}
