package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Hashtable;

public class ChatServer implements Runnable {  
	
	private ChatServerThread clients[] = new ChatServerThread[50];
	private Hashtable<Integer, String> names = new Hashtable<Integer, String>();
	private ServerSocket server;
	private Thread       thread;
	private int clientCount = 0;


   public ChatServer(int port){
	   
	   try{
		   System.out.println("Binding to port " + port + ", please wait  ...");
		   server = new ServerSocket(port);  
		   System.out.println("Server started!");
		   try {
			    InetAddress addr = InetAddress.getLocalHost();
			    System.out.println( "Connect to: " + addr + " at port " + port);
			} catch (UnknownHostException e) {}
		   start(); 
	   } catch(IOException ioe){ 
			   System.out.println("Can not bind to port " + port + ": " + ioe.getMessage()); 
	   }
   }
   
   public void run(){
	   while (thread != null){  
		   try{  
			   System.out.println("Waiting for a client ..."); 
			   addThread(server.accept()); 
		   } catch(IOException ioe){  
			   System.out.println("Server accept error: " + ioe); stop(); 
		   }
	   }
   }
   
   public void start(){  
	   
	   if (thread == null){  
		   thread = new Thread(this); 
		   thread.start();
	   }
   }
   
   public void stop(){  
	   
	   if (thread != null){ 
		   thread = null;
	   }
   }
   
   private int findClient(int ID){  
	
	   for (int i = 0; i < clientCount; i++){
		   if (clients[i].getID() == ID){
			   return i;
		   }
	   	}
	   	return -1;
   }
   
   public synchronized void handle(int ID, String data){  
	   
	   String key = data.substring(0, 3);
	   String val = data.substring(3);
	   String out = "ERROR";
	   
	   if ( key.equals("msg") ) {
		   out = names.get(ID) + ": " + val + "\n";
	   } else if ( key.equals("con") ){  
		   if ( names.containsValue(val) ) {
			out = "nameUsed";   
			clients[findClient(ID)].send(out);
			remove(ID);
			return;
		   }
		   else { 
			   names.put( ID, val );
			   out = "System: " + val + " is online.\n";
		   }
	   } else if (key.equals("dis")){
		   out = "System: " + names.get(ID) + " is offline.\n";
		   remove(ID);
	   }
	   
	   System.out.print("(" + ID + ") " + out);
	   
	   for (int i = 0; i < clientCount; i++){
		   clients[i].send(out);
	   }
   }
   
   public synchronized void remove(int ID){  
	   
	   names.remove(ID);
	   
	   int pos = findClient(ID);
	   if (pos >= 0){  
		   ChatServerThread toTerminate = clients[pos];
		   System.out.println("Removing client thread " + ID + " at " + pos);
		   if (pos < clientCount-1){
			   for (int i = pos+1; i < clientCount; i++){
				   clients[i-1] = clients[i];
			   }
		   }
		   clientCount--;
		   try{ 
			   toTerminate.close(); 
		   } catch(IOException ioe){  
			   System.out.println("Error closing thread: " + ioe); 
		   }
		   toTerminate.isRunning = false; 
	   }
   }
   
   private void addThread(Socket socket){  
	
	   if (clientCount < clients.length){  
		   System.out.println("Client accepted: " + socket);
		   clients[clientCount] = new ChatServerThread(this, socket);
		   try{  
			   clients[clientCount].open(); 
			   clients[clientCount].start();  
			   clientCount++; 
		   } catch(IOException ioe){  
			   System.out.println("Error opening thread: " + ioe); 
		   } 
	   } else {
		   System.out.println("Client refused: maximum " + clients.length + " reached.");
	   }
   }

   public static void main(String args[]){ 
      
	   if (args.length != 1){
		   System.out.println("Usage: java ChatServer port");
	   } else {
         new ChatServer( Integer.parseInt(args[0]) );
	   }
   }
}