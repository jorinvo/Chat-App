package server;
import java.io.*;
import java.net.*;

public class ChatServerThread extends Thread {  
	
	private ChatServer         server    = null;
	private Socket             socket    = null;
	private int                ID        = -1;
	private DataInputStream  streamIn  =  null;
	private DataOutputStream  streamOut = null;
	public boolean isRunning 		     = true;

	public ChatServerThread(ChatServer server, Socket socket){
		super();
		this.server = server;
		this.socket = socket;
		ID     = socket.getPort();
   }
	
   public void send(String out){ 
	   
	   try{  
		   streamOut.writeUTF(out);
		   streamOut.flush();
       } catch(IOException ioe){  
    	   System.out.println(ID + " ERROR sending: " + ioe.getMessage());
          server.remove(ID);
       }
   }
   
   
   public int getID(){ return ID; }
   
   
   public void run(){  
	   
	   System.out.println("Server Thread " + ID + " running.");
	   while (isRunning){  
		   try{
			   server.handle( ID, streamIn.readUTF() );
		   } catch(IOException ioe){
			   System.out.println(ID + " ERROR reading: " + ioe.getMessage());
			   server.remove(ID);
		   }
	   }
   }
   
   public void open() throws IOException{
	   streamIn  = new DataInputStream( new BufferedInputStream( socket.getInputStream() ) );
	   streamOut = new DataOutputStream( new BufferedOutputStream( socket.getOutputStream() ) );
   }
   
   public void close() throws IOException{  
	   if (socket != null)    socket.close();
	   if (streamIn != null)  streamIn.close();
	   if (streamOut != null) streamOut.close();
   }
}