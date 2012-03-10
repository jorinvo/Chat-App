import server.ChatServer;

public class Server {
	   
   public static void main(String args[]){ 
      
	   if (args.length != 1){
		   System.out.println("Usage: java ChatServer port");
	   } else {
         new ChatServer( Integer.parseInt(args[0]) );
	   }
	}
}
