import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
public class Main_Host {
    
    //Receiving Parameters
    static InetAddress rip;
    static int rport;
    static MulticastSocket rsocket;
    
    //Sending Parameters
    static InetAddress sip;
    static int sport;
    static MulticastSocket ssocket;
    
    public static void main(String[] args) throws IOException { 
        System.setProperty("java.net.preferIPv4Stack", "true");
        new HostPage().setVisible(true);
        
        try {
            Main_Host.sip = InetAddress.getByName("225.6.7.8");
            Main_Host.sport = 3456;
            try {
                Main_Host.ssocket = new MulticastSocket();
            } catch (IOException ex) {
                Logger.getLogger(MCQ.class.getName()).log(Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(null,ex,"Exception Occured ",JOptionPane.WARNING_MESSAGE);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(MCQ.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null,ex,"Exception Occured ",JOptionPane.WARNING_MESSAGE);
        }     
        
        rsocket = new MulticastSocket();
        
        while(true){
            byte[] b = new byte[64000];
            DatagramPacket rpacket = new DatagramPacket(b, b.length);
            
            rsocket.receive(rpacket);
            
            HostPage.message.setText( HostPage.message.getText() + "\n" + new String(b) );
        }
        
    }
}
