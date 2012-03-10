package controllers;
import models.Model;
import views.AppView;

public class AppController implements Controller{
	
	AppView view;
	Model model;	
	SocketController socket;
	
	public AppController(Model m){
		
		model = m;
		view = new AppView(this, model);
		socket = new SocketController(this, model);
		
	}

	public void connect() {
		String name = view.getName();
		String host = view.getHost(); 
		if (name.length() >= 1 && host.length() >= 7) {
			try{
				Integer port = Integer.parseInt( view.getPort() );
	//			which ports to use ??
				if( port >= 0 &&  port <= 65535 ) {
					model.setName(name);
					model.setHost(host); 
					model.setPort(port);
					socket.connect();
				}
				else view.warning("Ungültiger Port");
			} catch(NumberFormatException e){
				view.warning("Ungültiger Port");
			}
		} else {
			warning("Nicht alles ausgefüllt...");
		}
	}
	
	public void disconnect() {
		socket.disconnect();
		view.toggleCard("login");
		view.clearHistory();
		model.clear();
	}

	public void send() {
		String msg = view.getMessage();
		if (msg.length() >= 1){
			socket.send(msg);
			view.resetInputfield();
		}
	}
	
	public void exit() {
		socket.disconnect();
	}

	public void success(String data){
		view.success(data);
	}
	
	public void warning( String data ) {
		view.warning(data);
	}
}
