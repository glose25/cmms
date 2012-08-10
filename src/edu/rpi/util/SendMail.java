package edu.rpi.util;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class SendMail {

    public static final String MAIL_SERVER = "mail.rpi.edu";
    public static final String USERNAME = "";//"vcmr.rpi.edu";
    public static final String PASSWORD = "";//"password";

    public static void sendMessage(String fromAddress, InternetAddress[] toAddresses, String subject, String message) {
        try {
//            String fromAddress  = "appworx@rpi.edu";
//            String toAddress = "glosej@rpi.edu";
//            String subject = "This is a test Message";
//            String message = "Hello Hows u?";

            Properties properties = System.getProperties();
            properties.put("mail.smtps.host", MAIL_SERVER);
            properties.put("mail.smtps.auth", "true");

            Session session = Session.getInstance(properties);
            MimeMessage msg = new MimeMessage(session);
      
            msg.setFrom(new InternetAddress(fromAddress));
            msg.addRecipients(Message.RecipientType.TO, toAddresses);
//            msg.addRecipients(Message.RecipientType.TO, toAddress);
            msg.setSubject(subject);
            msg.setText(message);

            Transport tr = session.getTransport("smtp");
            tr.connect(MAIL_SERVER, USERNAME, PASSWORD);
            tr.sendMessage(msg, msg.getAllRecipients());
            tr.close();
        } catch (AddressException ex) {
            System.out.println(ex.getMessage());
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
}