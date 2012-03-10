package controllers;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import models.Model;

public class SocketController implements Runnable {
	
	Model model;
	AppController mainController;
	Socket socket;
	Thread thread;
	DataOutputStream streamOut;
	DataInputStream streamIn;
	boolean isRunning;
	boolean nameValid;

	SocketController (AppController c, Model m) {
		mainController = c;
		model = m;
	}
	
	public void connect(){

			try {
				socket = new Socket(model.getHost(), model.getPort());
				start();
			} catch(UnknownHostException uhe) { 
				mainController.warning("Verbindung zu Server nicht möglich"); 
			} catch(IOException ioe) {
				mainController.warning("Verbindung zu Server nicht möglich"); 
			}

	}
	
	public void start() throws IOException {
		streamOut = new DataOutputStream(socket.getOutputStream());
		if (thread == null){
			thread = new Thread(this);     
			try{
				//hanging here!!
				streamIn  = new DataInputStream( socket.getInputStream() );
			} catch(IOException ioe){ stop(); }
			isRunning = true;
			thread.start();
		}
		nameValid = false;
		sendToServer( "con" + model.getName() );
	}

	public void stop(){
		
		if (thread != null) thread = null;
		try{  
			if (streamOut != null)  streamOut.close();
			if (socket    != null)  socket.close();
			if (streamIn  != null)  streamIn.close();
		} catch(IOException ioe){}
		isRunning = false;
	}
	
	private void sendToServer(String msg){
		try{  
			streamOut.writeUTF(msg);
			streamOut.flush();
		} catch(IOException ioe){ stop(); }
	}

	public void disconnect(){
		sendToServer("dis");
		stop();
	}
	
	public void run(){  
		while (isRunning){  
			try{  
				newMessage( streamIn.readUTF() );
			} catch(IOException ioe){ stop(); }
		}
	}
	
	private void newMessage ( String msg ) {
		if (nameValid) {
			model.addMessage(msg);
		} else {
			if ( msg.equals("nameUsed") ) {
				mainController.warning("Name ist schon verwendet!");
				stop();
			} else {
				nameValid = true;
				mainController.success( model.getName() );
			}
		}
	}
	
	public void send(String msg) {
		sendToServer("msg" + msg);
	}

}
