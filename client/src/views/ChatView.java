package views;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import controllers.*;

public class ChatView extends JPanel {

	Controller controller;
	
	JTextArea history = new JTextArea(10, 20);
	JButton disconnectButton = new JButton("trennen");
	JLabel username = new JLabel();
	JTextField input = new JTextField();
	JButton sendButton = new JButton("senden");
	
	public ChatView( Controller controller){
		
		this.controller = controller;
		
		setLayout( new BorderLayout(10, 10) );

		JPanel inputPane = new JPanel( new BorderLayout(10, 10) );
		inputPane.add(disconnectButton, BorderLayout.WEST);
		inputPane.add(username, BorderLayout.WEST);
		inputPane.add(input, BorderLayout.CENTER);
		inputPane.add(sendButton, BorderLayout.EAST);
		
		JPanel disconnectPane = new JPanel( new BorderLayout() );
		disconnectPane.add(disconnectButton, BorderLayout.EAST);
		
		
		history.setLineWrap(true);
		history.setEditable(false);
	    JScrollPane historyPane = new JScrollPane(
	    	history,
	        JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
	        JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );
		
		add(disconnectPane, BorderLayout.PAGE_START);
		add(historyPane, BorderLayout.CENTER);
		add(inputPane, BorderLayout.PAGE_END);
		
		username.setText("default: ");
		
		bindEvents();
	}
	
	private void bindEvents(){
        disconnectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.disconnect();
            }
        });
        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.send();
            }
        });
        input.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.send();
            }
        });
	}
}
