import java.util.*;
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import  javax.swing.DefaultListModel;
public class Main_Host {
    
    //Receiving Parameters
    static InetAddress rip;
    static int rport;
    static MulticastSocket rsocket;
    
    
    //Sending Parameters
    static InetAddress sip;
    static int sport;
    static MulticastSocket ssocket;
    
    static String Password;
    
    //Frame objects
    static HostPage hpframe = new HostPage();
    static Password pwdframe = new Password();
    static Participants pframe = new Participants();
    
    static MCQ mcqframe = new MCQ();
    static OneWord owframe = new OneWord();
    static TrueOrFalse tfframe = new TrueOrFalse();
    static FillInTheBlanks fbframe = new FillInTheBlanks();
    
    
    //Username
    static ArrayList<String> username = new ArrayList<String>();
    
    //Participants
    static DefaultListModel participantslist = new DefaultListModel();
    
    public static void main(String[] args) throws IOException { 
        
        pwdframe.setVisible(true);

        System.setProperty("java.net.preferIPv4Stack", "true");         
        
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
            
            String s = new String(b).trim();
            
            
            String[] signal = s.split(";");
            
            if(signal[1].equals("msg")){            
                HostPage.message.setText( HostPage.message.getText() + "\n " + signal[2] );                
                Send.simplesend("chat;"+signal[2]);
            }
            
            else if(signal[1].equals("username")){                
                if(username.indexOf(signal[2].strip()) == -1){                    
                    Send.simplesend( "uname;" + signal[2] + ";" + signal[3] + ";" + signal[4] + ";" + "available;" );
                    username.add(signal[2].strip());
                } 
                else{
                    Send.simplesend("uname;" + signal[2] + ";" + signal[3] + ";" + signal[4] + ";" + "unavailable;" );
                }
            }
            
            else if(signal[1].equals("getpassword")){
                Send.simplesend( "password;" + Password + ";" );
            }
            
            else if(signal[1].equals("connected")){
                
                String c = signal[2] + " " + signal[3] + " (" + signal[4] + ")";
                participantslist.addElement(c);
                Send.simplesend("plist;" + participantslist.toString());
            }
            
            else if(signal[1].equals("disconnected")){
                
                String c = signal[2] + " " + signal[3] + " (" + signal[4] + ")";
                participantslist.remove(participantslist.indexOf(c));
                
                Send.simplesend("plist;" + participantslist.toString());
            }
        }        
    }
}
