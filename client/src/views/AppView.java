package views;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import utils.Observer;

import controllers.Controller;
import models.Model;

public class AppView implements Observer {

	Controller controller;
	
	JFrame frame = new JFrame();
	JPanel panel = new JPanel(new CardLayout());
	
	ChatView chat;
	LoginView login;
		
	public AppView( Controller c, Model m){

		controller = c; 
		m.subscribe(this);

		chat = new ChatView(controller);
		login = new LoginView(controller);
		
		initPanel();
		
		initFrame();
	}
	
	private void initPanel(){
		panel.setBorder( new LineBorder(new Color(0xEEEEEE), 6));
		panel.add(login, "login");
		panel.add(chat, "chat");
	}

	private void initFrame() {
		frame.setTitle("Chat App");
	    frame.setContentPane( panel );
		frame.pack();
		frame.setVisible(true);
		
		locateFrame();
		
		frame.addWindowListener(new WindowListener() {
			public void windowClosing(WindowEvent e){
				controller.exit();
			} 
			public void windowClosed(WindowEvent e) {} 
			public void windowOpened(WindowEvent e) {} 
			public void windowIconified(WindowEvent e) {} 
			public void windowDeiconified(WindowEvent e) {} 
			public void windowActivated(WindowEvent e) {} 
			public void windowDeactivated(WindowEvent e) {}
		});
	}
	
	
	private void locateFrame(){
		Toolkit tk = Toolkit.getDefaultToolkit();
	    Dimension screenSize = tk.getScreenSize();
	    Dimension frameSize = frame.getSize();
	    frame.setLocation( 
	    	screenSize.width / 2 - frameSize.width / 2, 
	    	screenSize.height / 2 - frameSize.height / 2
		);
	}
	
	
	public String getName(){
		return login.nameInput.getText();
	}
	
	public String getHost(){
		return login.hostInput.getText();
	}
	
	public String getPort(){
		return login.portInput.getText();
	}
	
	public String getMessage(){
		return chat.input.getText();
	}
	
	public void setName(String name){
		chat.username.setText(name + ": ");
	}
	
	public void resetInputfield(){
		chat.input.setText("");
	}
	
	public void focusInputfield(){
		chat.input.requestFocus();
	}
	
	public void clearHistory(){
		chat.history.setText("");
	}
	
	public void newMessage(String msg){
		chat.history.append(msg);
		chat.history.getCaret().setDot( chat.history.getDocument().getLength() );
		frame.repaint();
	}
	
	public void toggleCard(String newCard){
		CardLayout cards = (CardLayout)( panel.getLayout() );
		cards.show(panel, newCard);
	}
	
	public void success(String name){
		setName(name);
		toggleCard("chat");
		newMessage("System: WIlkommen!\n");
		focusInputfield();
	}
	
	public void warning(String data){
		JOptionPane.showMessageDialog(
			frame,
            data,
            "Hey Du!",
            JOptionPane.WARNING_MESSAGE
        );
	}
	
}
