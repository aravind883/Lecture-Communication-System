import java.net.*;
import javax.swing.JOptionPane;
import java.io.*;
import java.net.DatagramPacket;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Main_Client {   
    static String cfname;
    static String clname;     
    
    //Sending Parameters
    static InetAddress sipaddress;
    static int sportno;      
    static MulticastSocket ssocket;    
    
    
    //Receiving Parameters
    static InetAddress ripaddress;
    static int rport;
    static MulticastSocket rsocket;
    
    
    public static void main(String[] args) throws IOException  {
        System.setProperty("java.net.preferIPv4Stack", "true");
        new ClientDetails().setVisible(true);           
        //JOptionPane.showMessageDialog(null,"Joined group successfully"," ",JOptionPane.WARNING_MESSAGE);   
        
        ripaddress = InetAddress.getByName("225.6.7.8");
        rsocket = new MulticastSocket(3456);
        rsocket.joinGroup(ripaddress);
        
        while(true){
            byte[] b = new byte[64000];
            DatagramPacket rpacket = new DatagramPacket(b, b.length, ripaddress, rport);
            
            rsocket.receive(rpacket);            
            String[] signal = new String(b).split(",");
            
            
            if(signal[0].equals("1") ){
                //JOptionPane.showMessageDialog(null,"Matched!","Message Received",JOptionPane.WARNING_MESSAGE);
                new MCQ().setVisible(true);
                MCQ.question.setText(signal[1]);
                MCQ.option1.setText(signal[2]);
                MCQ.option2.setText(signal[3]);
                MCQ.option3.setText(signal[4]);
                MCQ.option4.setText(signal[5]);
                
                MCQ.answer = signal[6];
            }
            else if(signal[0].equals("2") ){
                new FillInTheBlanks().setVisible(true);
                FillInTheBlanks.question.setText(signal[1]);
                
                FillInTheBlanks.ans = signal[2];
            }
            else if(signal[0].equals("3") ){
                new TrueOrFalse().setVisible(true);
                
                TrueOrFalse.question.setText(signal[1]);
                
                TrueOrFalse.answer = signal[2];
            }
            else if(signal[0].equals("4") ){
                new OneWord().setVisible(true);
                
                OneWord.question.setText(signal[1]);
                
                OneWord.ans = signal[2];
            }
        
        
        }
    }
}