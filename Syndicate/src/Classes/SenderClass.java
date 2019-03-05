/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.Properties;
import javax.mail.Message.RecipientType;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author Uditha N. Bandara
 */
public class SenderClass {
    public static boolean emailSender(String from, String password, String message, String to[]){
        String host = "smtp.gmail.com";
        
        Properties props = System.getProperties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        
        Session session = Session.getDefaultInstance(props, null);
        
        MimeMessage msg = new MimeMessage(session);
        
        try {
            msg.setFrom(new InternetAddress(from));
            
            InternetAddress[] address = new InternetAddress[to.length];
            
            for(int i = 0;i<to.length;i++){
                address[i] = new InternetAddress(to[i]);
            }
            
            for(int i = 0;i<address.length;i++){
                msg.addRecipient(RecipientType.TO, address[i]);
            }
            
            msg.setSubject("The Syndicate - Password Recovery");
            msg.setText(message);
            
            Transport t = session.getTransport("smtp");
            t.connect(host, from, password);
            t.sendMessage(msg, msg.getAllRecipients());
            t.close();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return false;
    }
}
