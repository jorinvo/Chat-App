package utils;


public interface Observer {
	
	public void newMessage(String data);
	public void success(String data); 
	public void warning(String data);

}
