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
            System.out.println("Client: "+socket.getInetAddress()+" connectee sur le port "+socket.getPort());
            receiveFile();
  
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
    public void receiveFile()throws Exception
    {
      ObjectInputStream entrer = new ObjectInputStream(socket.getInputStream());

      Data packData = (Data) entrer.readObject();

      String path = readFileParameter("param.ini")+packData.getNameData();
      FileOutputStream output = new FileOutputStream(path);
      System.out.println(".......");
      System.out.println("Path de sauvegarde: "+packData.getNameData());
      System.out.println("Nom du pack recue: "+packData.getNameData());
      System.out.println("Taille du pack recue: "+packData.getData().length);
      System.out.println(".......");
      
      output.write(packData.getData());
      output.close();
    }

    public String readFileParameter(String pathParam)throws FileNotFoundException,IOException{
      File file = new File(pathParam);
      BufferedReader reader= new BufferedReader(new FileReader(file)); 
      String toread = reader.readLine().trim();
      String[] firstParam = toread.split("\\:::");
      System.out.println("..................avy aty am pc an miaro..................");      
      System.out.println("information du parametre "+firstParam[1]);
      System.out.println("information du parametre "+firstParam[1]);
      reader.close();
      return firstParam[1];      
    }
}