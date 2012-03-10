package views;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controllers.*;

public class LoginView extends JPanel {

	Controller controller;
	
	JTextField hostInput = new JTextField(9);
	JTextField portInput = new JTextField(9);
	JTextField nameInput = new JTextField(9);
	JButton connectButton = new JButton("Verbinden");
	
	public LoginView( Controller controller ){
		
		this.controller = controller;
		
		setLayout( new FlowLayout( FlowLayout.CENTER, 30, 10 ) );
		
		JPanel inputPane = new JPanel();
		inputPane.setLayout( new GridLayout(3, 2, 0, 20) );
		inputPane.add( new JLabel("Host:") );
		inputPane.add(hostInput);
		inputPane.add( new JLabel("Port:") );
		inputPane.add(portInput);
		inputPane.add( new JLabel("Name:") );
		inputPane.add(nameInput);
		
		add(inputPane);
		add(connectButton);

		bindEvents();
	}
	
	private void bindEvents(){
        connectButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.connect();
            }
        });
        hostInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.connect();
            }
        });
        portInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.connect();
            }
        });
        nameInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                controller.connect();
            }
        });
        
        hostInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
               hostInput.selectAll();
            }
        });
        portInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
               portInput.selectAll();
            }
        });
        nameInput.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
               nameInput.selectAll();
            }
        });
	}
}
