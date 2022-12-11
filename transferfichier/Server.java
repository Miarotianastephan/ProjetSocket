package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import data.Data;

public class Server{
    ServerSocket serverSocket = null;  
    Socket socket = null;   //establishes connection and waits for the client 

    public Server(int port) throws Exception{
        serverSocket = new ServerSocket(port);
        System.out.println("Serveur demarrer ::");
        System.out.println("En attente de connection........");
          while (true) {
            try {
              socket = serverSocket.accept();
              System.out.println("Client: "+socket.getInetAddress()+" connectee");
              receiveFile("test");
    
            } 
            catch (EOFException ex1) {
              System.out.println("EOEXCEPTION AND WILL BREAK THE TRANSFER");
              ex1.printStackTrace();
              break;
            }
            catch (IOException i) {
              System.out.println("Error was occured "+i);
              i.printStackTrace();
            }
        }
        System.out.println("Closing connection");
        socket.close();
    }
    

    public String getFileExtension(String filename){
        int i = filename.lastIndexOf(".");
        if( i > 0 ){
            return filename.substring(i+1);
        }else{
            return "No extension found";
        }
    }
 
    //downloading the file to the mention path
    public void receiveFile(String fileName)throws Exception
    {
      ObjectInputStream entrer = new ObjectInputStream(socket.getInputStream());
      Data packData = (Data) entrer.readObject();

      System.out.println(" ....... ");
      System.out.println(packData.getNameData()+" nom du pack recue.");
      System.out.println(" ....... ");
      /*Chemin de download. "C:/Dossiertest/" */
      FileOutputStream output = new FileOutputStream("C:/Dossiertest/"+packData.getNameData());
      output.write(packData.getData());
      output.close();
    }
}