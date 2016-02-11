
public class Controller {
	public static void main(String[] args) {
	
		CascadeThread c1 = new CascadeThread("c1");
		CascadeThread c2 = new CascadeThread("c2");
		CascadeThread c3 = new CascadeThread("c3");
		CascadeThread c4 = new CascadeThread("c4");
		c1.setNæste(c2);
		c2.setNæste(c3);
		c3.setNæste(c4);
		c1.start();
		c1.vælt();
	}
}
