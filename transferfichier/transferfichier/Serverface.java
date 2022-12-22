package server;

import javax.swing.*;
import java.awt.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Serverface extends JFrame{
    Server server;

    public Serverface() {
        super("Select port number");
        setLayout(null);
        setSize(new Dimension(300, 200));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        // this.server = server;

        JPanel paneSetters = new JPanel();
        paneSetters.setLayout(null);
        paneSetters.setBackground(new Color(181, 186, 187));
        paneSetters.setBounds(50, 20, 200, 50);
        JTextField txt = new JTextField("Port de connexion");
        txt.setColumns(20);
        txt.setBounds(0, 0, 200, 45);
        txt.setFont(new Font(("Tahoma"),Font.BOLD, 16));
        paneSetters.add(txt);
        JButton btnOk = new JButton("Submit");
        btnOk.setBounds(150,80,100,30);
        btnOk.setFont(new Font(("Arial"),Font.BOLD, 16));
        add(paneSetters);
        add(btnOk);
        setVisible(true);
        
        btnOk.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                if(txt.getText().equalsIgnoreCase("Port de connexion")==false){
                    try {
                    if(Integer.parseInt(txt.getText()) > 1000){
                        int port = Integer.parseInt(txt.getText());
                        txt.setText("Run on port "+port);
                        // setVisible(false);
                        System.out.println("OPENING A POINT");
                        Server mainserver = new Server(port);
                    }
                } catch (Exception ex) {
                    // TODO: handle exception
                    JOptionPane.showMessageDialog(paneSetters,"invalid input");
                }
            }JOptionPane.showMessageDialog(paneSetters,"Select a port");
            }
        });
    }

    public static void main(String args[]) 
    {
      try {
        Serverface server = new Serverface();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    
}
