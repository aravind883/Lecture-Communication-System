import java.io.IOException;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Send {
    
    public static void encrypt(String Message){
        String CipherText = "";
        char a;
        int A;
        int k = (Main_Host.rport %25 ) + 1 ;
        for(int i=0; i < Message.length() ; i++){
            a = Message.charAt(i);
            A = ((int)a + k) % 127;

            CipherText += (char)A + "~";
        }
        //return CipherText;
        DatagramPacket spacket = new DatagramPacket(CipherText.getBytes(),CipherText.length(),Main_Host.sip,Main_Host.sport);
        try {
            Main_Host.ssocket.send(spacket);            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null,ex,"Exception at Encryption Phase",JOptionPane.WARNING_MESSAGE);
        }
        
        
    }
    
    public static void simplesend(String Message){
        
        DatagramPacket spacket = new DatagramPacket(Message.getBytes(), Message.length(), Main_Host.sip,Main_Host.sport);
        
        try {
            Main_Host.ssocket.send(spacket);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex,"Exception Occured at Encryption Phase",JOptionPane.WARNING_MESSAGE);
        }
    }
    
}
