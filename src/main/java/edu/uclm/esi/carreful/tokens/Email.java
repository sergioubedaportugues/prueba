package edu.uclm.esi.carreful.tokens;



import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;


public class Email {
	private final Properties properties = new Properties();
	private static final String CORREO="practicadis@outlook.es";
	public void send(String destinatario, String subject, String body) {
		String smtpHost= "smtp.office365.com";
		String startTTLS="true";
		String port="587";
		String sender=CORREO;// REMITENTE
		String serverUser=CORREO;// USUARIO
		String userAutentication= "true";
		String pwd="DISENOpractica24";// PONER LA CONTRASEÑA
		String fallback="true";	
		
		properties.put("mail.smtp.host", smtpHost);  
        properties.put("mail.smtp.starttls.enable", startTTLS);  
        properties.put("mail.smtp.port", port);  
        properties.put("mail.smtp.mail.sender", sender);  
        properties.put("mail.smtp.user", serverUser);  
        properties.put("mail.smtp.auth", userAutentication);
        properties.put("mail.smtp.socketFactory.port", port);
        properties.put("mail.smtp.ssl.checkserveridentity", true);
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.socketFactory.fallback", fallback);
        Runnable r = new Runnable() {
			@Override
			public void run() {
		        Authenticator auth = new AutentificadorSMTP(sender, pwd);
		        Session session = Session.getInstance(properties, auth);

		        MimeMessage msg = new MimeMessage(session);
		        try {
			        msg.setSubject(subject);
			        msg.setText(body);
			        msg.setFrom(new InternetAddress(sender));
			        msg.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			        Transport.send(msg);
		        } catch (Exception e) {
		        	throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
				
				}
			}
		};
		new Thread(r).start();
	}
	
	private class AutentificadorSMTP extends javax.mail.Authenticator {
        private String sender;
		private String pwd;

		public AutentificadorSMTP(String sender, String pwd) {
			this.sender = sender;
			this.pwd = pwd;
		}
		@Override
		public PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(sender, pwd);
        }
    }
	
	public static void main(String[] args)  {
		Email sender=new Email();
		sender.send(CORREO, "Hola", "Caracola");
		
	}
}