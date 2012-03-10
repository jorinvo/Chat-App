package models;

import java.util.ArrayList;
import utils.Observer;


public class AppModel implements Model{

	private String name;
	private String host;
	private Integer port;
	private ArrayList<String> messages = new ArrayList<String>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();

	
	public void subscribe(Observer o){
		observers.add(o);
	}
	
	public void unsubscribe(Observer o){
		int i = observers.indexOf(o);
		if (i >= 0) observers.remove(i);
	}
	
	private void publishMessage(String data){
		for (int i = 0; i < observers.size(); i++) {
			Observer observer = (Observer)observers.get(i);
			observer.newMessage(data);
		}
	}
	
	public String getName() {
		return name;
	}
	
	public String getHost() {
		return host;
	}
	
	public Integer getPort(){
		return port;
	}
	
	public void setName( String n){
		name = n;
	}
	
	public void setHost(String h) {
		host = h;
	}
	
	public void setPort(Integer p) {
		port = p;
	}

	public void addMessage(String msg) {
		messages.add(msg);
		publishMessage(msg);
	}
	
	public void clear() {
		messages.clear();
	}
}
