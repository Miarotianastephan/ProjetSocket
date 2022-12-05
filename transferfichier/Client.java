package client;

import java.net.Socket;
import java.io.*;
import java.io.FileInputStream;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Font;
import java.awt.Dimension;
import javafx.event.ActionEvent;
import java.awt.event.ActionListener;
import action.*;

public class Client extends JFrame{
    Socket socket = null;
    String serverName;
    int portNumber;
    InputStream is = null;
    OutputStream os = null;

    JPanel jpInfoFichier;
    JPanel jpBtn;
    JLabel labelFileSelected;
    JButton btnSend;
    JButton btnChoice;
    JProgressBar progress;
    String path = null;

    public JProgressBar getProgressBar(){
        return progress;
    }
    public String getPath(){
        return  path;
    }
    public JLabel getLabelFileSelected(){
        return labelFileSelected;
    }
    public String getServerName(){
        return serverName;
    }
    public int getPortNumber(){
        return portNumber;
    }
    public void setServerName(String srvnm){
        serverName= srvnm;
    }
    public void setPort(int pt){
        portNumber= pt;
    }
    public void setLabelFileSelected(String text){
        labelFileSelected.setText(text);
    }
    public void setPath(String pth){path = pth;}
    
    public Client(String serverHostName, int port)  throws Exception{
        super("Client post");
        setLayout(null);
        setSize(new Dimension(800, 650));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        initLabel();
        initPushButton();
        setVisible(true);
        btnSend.addActionListener(new MyAction(this));
        btnChoice.addActionListener(new MyAction(this));
    }
    
    // sendFile function define here
    public void sendFile(String serverHostName,int port,String path)throws Exception
    {
        this.socket = new Socket(serverHostName, port);
        System.out.println("Connected to the server...");
        int bytes = 0;
        // Open the File where he located in My PC
        File file = new File(path);
        FileInputStream fileInputStream =null;
        if(file.isFile()){//if the path is a file 
            fileInputStream = new FileInputStream(file);
        }else{throw (new Exception("Not file found"));}
 
        // Here we send the File to Server
        DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
        dataOutputStream.writeLong(file.length());
        byte[] buffer = new byte[4 * 1024];
        while ( (bytes = fileInputStream.read(buffer)) != -1 ) 
        {
            // Send the file to Server Socket 
            System.out.println("Bytes- "+bytes);
            dataOutputStream.write(buffer, 0, bytes);
            dataOutputStream.flush();

        }
        // close the file here
        fileInputStream.close();
        dataOutputStream.close();
    }

    public void initLabel(){
        jpInfoFichier = new JPanel();
        jpInfoFichier.setLayout(null);
        jpInfoFichier.setBackground(new Color(181, 186, 187));
        jpInfoFichier.setBounds(100, 150, 600, 100);
        labelFileSelected = new JLabel("Choisir un element...");
        labelFileSelected.setBounds(10,5,580,100);
        labelFileSelected.setFont(new Font(("Tahoma"),Font.BOLD, 25));
        jpInfoFichier.add(labelFileSelected);

        JLabel labelprogress = new JLabel();
        labelprogress.setText("Suivez l'envoie de votre fichier...");
        labelprogress.setBounds(100, 475, 200, 30);
        labelprogress.setFont(new Font(("Arial"),Font.BOLD,12));
        labelprogress.setForeground(new Color(4, 205, 151));
        //bar de progression du transfert
        progress = new JProgressBar(0,100);
        progress.setStringPainted(true);
        progress.setBounds(100, 500, 200, 20);
        this.add(labelprogress);
        this.add(progress);
        this.add(jpInfoFichier);
    }

    public void initPushButton(){
        jpBtn = new JPanel();
        jpBtn.setLayout(null);
        jpBtn.setBackground(new Color(181, 186, 187));
        jpBtn.setBounds(200,350,400,75);
        jpBtn.setBorder(new EmptyBorder(8, 8, 8, 8));

        btnSend = new JButton("Send");
        btnSend.setName("Send");
        btnSend.setBounds(40,10,150,50);  
        btnSend.setFont(new Font(("Arial"),Font.BOLD, 20));
        btnSend.setBackground(new Color(4, 205, 151));

        btnChoice = new JButton("Choice");
        btnChoice.setName("Choice");
        btnChoice.setBounds(220,10,150,50);
        btnChoice.setFont(new Font(("Arial"),Font.BOLD, 20));
        btnChoice.setBackground(new Color(255, 161, 90));

        jpBtn.add(btnSend);
        jpBtn.add(btnChoice);
        this.add(jpBtn);
    }

    public String getExtension(String fileName) {
        if (fileName == null) {
           return null;
        }
        int lastDotIndex = fileName.lastIndexOf('.');
        return (lastDotIndex == -1) ? "" : fileName.substring(lastDotIndex + 1).toLowerCase();
    }

    public static void main(String args[]) 
    {
        try {
            Client client = new Client("localhost", 1234);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
