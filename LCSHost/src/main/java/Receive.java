import javax.swing.JOptionPane;

public class Receive {
    
    public static String decrypt(String CipherText){
        String Message = "";
        int k = (Main_Host.rport % 25) +1;
        int l = 0;
        String[] Cipher = CipherText.split("~");
        for(int i=0; i<Cipher.length; i++){
            //System.out.print(Cipher[i]);
            if(Cipher[i].length() >0){
                l = ((int)Cipher[i].charAt(0) - k);
            }
            
            if( l <= 0 ){
                l = l + 127;
            }
            Message+= (char)(l);
        }
        return Message;
    }
    
}
