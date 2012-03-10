package models;

import utils.Observer;

public interface Model{

	
	public void subscribe(Observer o);
	public void unsubscribe(Observer o);

	public String getName();
	public String getHost();
	public Integer getPort();
	public void setName(String n);
	public void setHost(String h);
	public void setPort(Integer p);
	public void addMessage(String msg);
	public void clear();

}
