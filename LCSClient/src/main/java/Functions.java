
import javax.swing.JOptionPane;

public class Functions {
    
    public static void connect(){
        Main_Client.connected = true;
        Send.simplesend( "buffer;connected;" + Main_Client.cfname + ";" + Main_Client.clname + ";" + Main_Client.username + ";");  
    }
    
    public static void disconnect(){
        Send.simplesend( "buffer;disconnected;" + Main_Client.cfname + ";" + Main_Client.clname + ";" + Main_Client.username + ";"); 
        Main_Client.ssocket.close();
        Main_Client.connected = false;
        JOptionPane.showMessageDialog(null,"Disconnected Succesfully!", "Socket Closed",JOptionPane.WARNING_MESSAGE);
    }
    
    public static void username(String u){
        Main_Client.username = u;
        String s = "buffer;username;"+ u + ";" + Main_Client.cfname + ";" +Main_Client.clname + ";";
        Send.simplesend(s);
    }
    
    public static Boolean passwordauth(){
        if( Main_Client.Password.strip().equals(Main_Client.HostPassword.strip()) ){
            return true;
        }
        else{
            return false;
        }
    }
}
