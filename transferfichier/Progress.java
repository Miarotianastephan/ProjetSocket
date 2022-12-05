package action;
import client.*;

public class Progress  extends Thread{

    Client client;
    String server;
    int pr;
    public Progress(){}

    public Progress(Client cli){client = cli;}
    public Progress(Client cli,String serverHostName, int port){
        client = cli;
        server = serverHostName;
        pr = port;
    }

    public void run(){
        for(int i=0; i <= 100; i+=10){
            client.getProgressBar().setValue(i);
            System.out.println("ind "+i);
            try {
                sleep(1000);
            } catch (Exception e) {
            
            }
        }
        try {
            //Envoie du fichier vers serveur
            client.sendFile(server, pr, client.getPath());    
        } catch (Exception e) {
            
        }
    }
    
}
