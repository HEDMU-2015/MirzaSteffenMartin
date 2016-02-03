
public class CascadeThread extends Thread {
Boolean måStart = false;
CascadeThread næste;
String navn;

	public CascadeThread(String navn){
		this.navn = navn;
		
	}
	
	public void run(){
		for(;;){
			if(måStart == true){
				try {
					Thread.sleep((long)(Math.random() * 2000));
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(navn+"er væltet");
				try{
				næste.start();
				næste.vælt();
				} catch(Exception e){
					System.out.println("færdig!");
				}
				
				break;
			}
		}
	}
	
	public void vælt(){
		måStart = true;
	}
	
	public void setNæste(CascadeThread næste){
		this.næste = næste;
	}
}
