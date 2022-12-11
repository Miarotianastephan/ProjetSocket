package action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.Font;
import java.io.File;
import client.*;
    
public class MyAction implements ActionListener{

    Client client;
    public MyAction(Client cli){
        client =cli;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        try {
        if( ((JButton)(e.getSource())).getName() == "Choose" ){
            JFileChooser jfChooser = new JFileChooser();
            jfChooser.setDialogTitle("Choose your file to transfer!");

            if(jfChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION){
                File file = jfChooser.getSelectedFile();
                String path = file.getAbsolutePath();
                path = path.replace("\\", "/");
                client.setPath(path);
                client.setLabelFileSelected("Element choisis: "+path);
                client.getLabelFileSelected().setFont(new Font(("Arial"),Font.BOLD, 20));
            }

        }
        else if(((JButton)(e.getSource())).getName() == "Send"){
            if(client.getPath() == null){
                client.setLabelFileSelected("Aucun element choisis");
            }else{
                Progress pr = new Progress(client);
                pr.start();
            }

        }
            
        } catch (Exception exc) {
            exc.printStackTrace();
        }
        
    }
}