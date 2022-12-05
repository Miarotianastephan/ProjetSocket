package server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

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
 
    public void receiveFile(String fileName)throws Exception
    {
        int bytes = 0;
        FileOutputStream fileOutputStream = new FileOutputStream("C:/Miaro/"+fileName);
        DataInputStream dataInputStream= new DataInputStream(socket.getInputStream()); 
        long size = dataInputStream.readLong(); // read file size
        byte[] buffer = new byte[4 * 1024];
        while ( (size > 0) && (bytes = dataInputStream.read(buffer, 0, (int)Math.min(buffer.length, size)))!= -1) 
        {
            fileOutputStream.write(buffer, 0, bytes);
            size -= bytes;
        }
        // Here we received file
        System.out.println("File is Received");
        fileOutputStream.close();
        dataInputStream.close();
    }

    public static void main(String args[]) 
    {
      try {
        Server server = new Server(1234);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
}