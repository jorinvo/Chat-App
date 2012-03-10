import controllers.AppController;
import models.AppModel;

public class Client {
	
	public static void main(String[] args) {
	
		new AppController(new AppModel());
	
	}
}
