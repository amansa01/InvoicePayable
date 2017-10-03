package com.alacriti.invoice.util;
import java.util.Properties;    

import javax.mail.*;    
import javax.mail.internet.*;    

import org.apache.log4j.Logger;

public class MailingService{  
	public static final Logger log= Logger.getLogger(MailingService.class);

    public  void send(final String from,final String password,String to,String sub,String msg){  
    	  log.debug("in mail sending method");
    	  Properties props = new Properties();    
          props.put("mail.smtp.host", "smtp.gmail.com");    
          props.put("mail.smtp.socketFactory.port", "465");    
          props.put("mail.smtp.socketFactory.class",    
                    "javax.net.ssl.SSLSocketFactory");    
          props.put("mail.smtp.auth", "true");    
          props.put("mail.smtp.port", "465");    
          //get Session   
          Session session = Session.getInstance(props,    
           new javax.mail.Authenticator() {    
           protected PasswordAuthentication getPasswordAuthentication() {    
           return new PasswordAuthentication(from,password);  
           }    
          });    
          //compose message    
          try {    
           MimeMessage message = new MimeMessage(session);    
           message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));    
           message.setSubject(sub);    
           message.setText(msg);    
           //send message  
           Transport.send(message);    
           System.out.println("***************** message sent successfully ....!***********************");    
          } catch (MessagingException e) {
  			log.error("Exception occured ",e);
        	  throw new RuntimeException(e);}    
             
    }  
}  

