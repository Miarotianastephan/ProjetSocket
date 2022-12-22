package client;

import java.net.Socket;
import java.io.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.net.UnknownHostException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import action.*;
import data.Data;

public class Client extends JFrame{
    Socket socket = null;
    String serverName;
    int portNumber;
    InputStream is = null;
    OutputStream os = null;

    JPanel jpInfoFichier;
    JPanel jpBtn;
    JPanel jpInsert;

    JLabel labelFileSelected;
    JButton btnSend;
    JButton btnChoice;
    JButton btnLocal;
    JButton btnHosting;
    JProgressBar progress;
    JTextField text1;
    JTextField text2;
    //setting port and host ip
    public String getText1() {
        return text1.getText();
    }
    public int getText2() {
        return Integer.parseInt(text2.getText());
    }
    public void setText2(JTextField text2) {
        this.text2 = text2;
    }

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
    
    public JPanel getJpInsert() {
        return jpInsert;
    }
    public void setJpInsert(JPanel jpInsert) {
        this.jpInsert = jpInsert;
    }
    //setting configuration for connnection
    public void setConfigToLocal(){
        this.setServerName("localhost");
        this.setPort(this.getText2());
        System.out.println("ip_port "+getServerName()+"::"+getPortNumber());
    }
    public void setConfigForHosting(){
        this.setServerName(this.getText1());
        this.setPort(this.getText2());
        System.out.println("ip_port "+getServerName()+"::"+getPortNumber());
    }

    //constructors
    public Client()throws Exception{
        super("Client post");
        setLayout(null);
        setSize(new Dimension(800, 650));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        initLabel();
        initPushButton();
        setVisible(true);
        btnLocal.addActionListener(new MyAction(this));
        btnHosting.addActionListener(new MyAction(this));
        btnSend.addActionListener(new MyAction(this));
        btnChoice.addActionListener(new MyAction(this));
    }
    
    //FONCTION SEND
    public void sendFile(String path) throws Exception
    {
        System.out.println("Tonga send");
        
        String serverHostName=getServerName();
        int port=getPortNumber();
        
        this.socket = new Socket(serverHostName, port);

        System.out.println("Connected to the server...");
        //Selection file on my PC
        File file = new File(path);
        FileInputStream input =null;

        if(file.isFile()){//if only a file 
            input = new FileInputStream(file);
        }else{throw (new Exception("Not file found"));}
 
        //Sending file object DATA
        byte b[] = new byte[input.available()];
        int sizeOfByte = input.read(b);
        ObjectOutputStream sortie = new ObjectOutputStream(socket.getOutputStream());
        Data packData = new Data();
        packData.setNameData(file.getName());
        packData.setExtension(getExtension(file.getAbsolutePath()));
        packData.setData(b);
        sortie.writeObject(packData);
        sortie.flush();
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
        
        jpInsert = new JPanel();
        jpInsert.setLayout(null);
        jpInsert.setBackground(new Color(181, 186, 187));
        jpInsert.setBounds(100,50,400,75);
        jpInsert.setBorder(new EmptyBorder(8, 8, 8, 8));

        //choix du machine a hoster
        text1 = new JTextField("localhost");
        text1.setColumns(20);
        text1.setBounds(0, 15, 100, 25);
        text1.setFont(new Font(("Tahoma"),Font.BOLD, 16));
        //choix du port de connexion avec le serveur 
        text2 = new JTextField("port");
        text2.setColumns(20);
        text2.setBounds(110, 15, 105, 25);
        text2.setFont(new Font(("Tahoma"),Font.BOLD, 16));

         
        btnLocal = new JButton("Local");
        btnLocal.setName("Localhost");                              ///SUR PC LOCAL
        btnLocal.setBounds(215,15,90,20);
        btnLocal.setFont(new Font(("Sans-serif"),Font.BOLD, 16));
        btnLocal.setBackground(new Color(218,220,224));
        btnHosting = new JButton("Hosting");
        btnHosting.setName("reseau");                               ///SUR PC EN RESEAU
        btnHosting.setBounds(300,15,80,20);
        btnHosting.setFont(new Font(("Arial"),Font.BOLD, 16));
        btnHosting.setBackground(new Color(218,220,224));

        btnSend = new JButton("Send");
        btnSend.setName("Send");
        btnSend.setBounds(40,10,150,50);
        btnSend.setFont(new Font(("Arial"),Font.BOLD, 20));
        btnSend.setBackground(new Color(4, 205, 151));
        btnChoice = new JButton("Choose");
        btnChoice.setName("Choose");
        btnChoice.setBounds(220,10,150,50);
        btnChoice.setFont(new Font(("Arial"),Font.BOLD, 20));
        btnChoice.setBackground(new Color(255, 161, 90));

        jpBtn.add(btnSend);
        jpBtn.add(btnChoice);
        jpInsert.add(text1);
        jpInsert.add(text2);
        jpInsert.add(btnLocal);
        jpInsert.add(btnHosting);
        this.add(jpBtn);
        this.add(jpInsert);
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
            // Client client = new Client("192.168.10.229", 1234);
            Client client = new Client();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
