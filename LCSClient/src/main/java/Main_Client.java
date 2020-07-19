import java.net.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.DatagramPacket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import  javax.swing.DefaultListModel;
public class Main_Client {   
    static String cfname;
    static String clname;     
    
    //Username
    static String username;
    static Boolean unset = false;
    
    //Participants
    static DefaultListModel plist = new DefaultListModel();
    
    //Toggle Parameters;
    static Boolean connected = false; 
    
    //Sending Parameters
    static InetAddress sipaddress;
    static int sportno;      
    static MulticastSocket ssocket;    
    
    
    //Receiving Parameters
    static InetAddress ripaddress;
    static int rport;
    static MulticastSocket rsocket;
    
    static String Password = "";
    static String HostPassword;
    
    //Form Parameters
    static ClientDetails cdform = new ClientDetails();
    static HostDetails hdform = new HostDetails();
    static Password pwdform = new Password();
    static SendMsg smform = new SendMsg();
    static Username unameform = new Username();
    static Participants pform = new Participants();
    
    static MCQ mcqform = new MCQ();
    static OneWord owform = new OneWord();
    static FillInTheBlanks fbform = new FillInTheBlanks();
    static TrueOrFalse tfform = new TrueOrFalse();
    
    public static void main(String[] args) throws IOException  {
        System.setProperty("java.net.preferIPv4Stack", "true");       
        
        cdform.setVisible(true);     
        //JOptionPane.showMessageDialog(null,"Joined group successfully"," ",JOptionPane.WARNING_MESSAGE);   
        
        ripaddress = InetAddress.getByName("225.6.7.8");
        rsocket = new MulticastSocket(3456);
        rsocket.joinGroup(ripaddress);
        
        while(true){
            byte[] b = new byte[64000];
            DatagramPacket rpacket = new DatagramPacket(b, b.length, ripaddress, rport);
            
            rsocket.receive(rpacket);    
            
            //String s = Receive.decrypt(new String(b).trim());
            String s = new String(b).trim();
            
            String[] signal = s.split(";");           
            
            if(signal[0].equals("1") ){
                //JOptionPane.showMessageDialog(null,"Matched!","Message Received",JOptionPane.WARNING_MESSAGE);
                mcqform.setVisible(true);
                MCQ.question.setText(signal[1]);
                MCQ.option1.setText(signal[2]);
                MCQ.option2.setText(signal[3]);
                MCQ.option3.setText(signal[4]);
                MCQ.option4.setText(signal[5]);
                
                MCQ.answer = signal[6];
            }
            else if(signal[0].equals("2") ){
                fbform.setVisible(true);
                FillInTheBlanks.question.setText(signal[1]);
                
                FillInTheBlanks.ans = signal[2];
            }
            else if(signal[0].equals("3") ){
                tfform.setVisible(true);
                
                TrueOrFalse.question.setText(signal[1]);
                
                TrueOrFalse.answer = signal[2];
            }
            else if(signal[0].equals("4") ){
                owform.setVisible(true);
                
                OneWord.question.setText(signal[1]);
                
                OneWord.ans = signal[2];
            }
            
            else if(signal[0].equals("uname")){
                
                if(unset == false){
                    if( signal[1].equals(username) && signal[2].equals(cfname) && signal[3].equals(clname) && signal[4].equals("available")){
                        unset = true;
                        JOptionPane.showMessageDialog(null,"Username available!","Success!",JOptionPane.WARNING_MESSAGE);
                        smform.setVisible(true);
                    }
                    else{
                        unset = false;
                        username = "";
                        JOptionPane.showMessageDialog(null,"Username not available! Choose another username","Sorry!",JOptionPane.WARNING_MESSAGE);
                        unameform.setVisible(true);
                    }                    
                }                
            }
            
            else if(signal[0].equals("password")){
                HostPassword = signal[1];
            }
            
            else if(signal[0].equals("plist")){
                
                
                String k = signal[1].strip().substring(1, signal[1].length() -1);
                String[] l = k.split(",");
                plist.clear();
                for(int i=0;i<l.length;i++){
                    plist.addElement(l[i].strip());
                }
            }
            
            else if (signal[0].equals("disconnect")){
                if(signal[1].strip().equals(username.strip())){
                    JOptionPane.showMessageDialog(null,"Host has removed you!","Message",JOptionPane.WARNING_MESSAGE);
                    Functions.disconnect();
                }                
            }
            
            else if (signal[0].equals("chat")){
                smform.chatbox.setText( SendMsg.chatbox.getText() + "\n " +  signal[1]);
            }
        }
    }
}