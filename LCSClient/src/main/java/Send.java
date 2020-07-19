import java.net.*;
import javax.swing.JOptionPane;

public class Send {    
    
    public static void encrypt(String Message){
        String CipherText = "";
        char a;
        int A;
        int k = (Main_Client.sportno % 25) + 1 ;
        for(int i=0; i < Message.length() ; i++){
            a = Message.charAt(i);
            A = ((int)a + k) % 127;

            CipherText += (char)A + "~";
        }
        //return CipherText;
        
        DatagramPacket spacket = new DatagramPacket(CipherText.getBytes(), CipherText.length(), Main_Client.sipaddress, Main_Client.sportno);
        
        try {
            Main_Client.ssocket.send(spacket);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex,"Exception Occured at Encryption Phase",JOptionPane.WARNING_MESSAGE);
        }
    }
    
    public static void simplesend(String Message){
        
        DatagramPacket spacket = new DatagramPacket(Message.getBytes(), Message.length(), Main_Client.sipaddress, Main_Client.sportno);
        
        try {
            Main_Client.ssocket.send(spacket);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,ex,"Exception Occured at Encryption Phase",JOptionPane.WARNING_MESSAGE);
        }
    }
}
