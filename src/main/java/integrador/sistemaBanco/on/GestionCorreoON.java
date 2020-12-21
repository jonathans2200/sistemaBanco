package integrador.sistemaBanco.on;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import integrador.sistemaBanco.model.Poliza;



public class GestionCorreoON {

	GestionPolizaON polizaON;;
	
	
	public void enviarCorreo(String destinatario, String asunto, String cuerpo) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");

		Session sesion = Session.getDefaultInstance(propiedad);
		String correoEnvia = "banconet123@gmail.com";
		String contrasena = "b_net123";

		MimeMessage mail = new MimeMessage(sesion);
		try {
			mail.setFrom("BANCONET <" + correoEnvia + ">");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			mail.setText(cuerpo);

			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
		} catch (AddressException ex) {
			System.out.println(ex.getMessage());
		} catch (MessagingException ex) {
			System.out.println(ex.getMessage());
		}
	}

	/**
	 * Método que permite enviar el correo electronico con los datos de las polizas aprobarPolizas.
	 * 
	 * @param destinatario El correo electronico del cliente al que se envia el correo.
	 * @param asunto El asunto del correo electronico.
	 * @param cuerpo El cuerpo del correo electronico.
	 * @param Poliza Una clase Poliza que se envia en un metodo para generar la tabla 
	 * 					de amortización en un documento pdf y guardar en un archivo.
	 *
	 */
	public void enviarCorreo2(String destinatario, String asunto, String cuerpo, Poliza Poliza) {
		Properties propiedad = new Properties();
		propiedad.setProperty("mail.smtp.host", "smtp.gmail.com");
		propiedad.setProperty("mail.smtp.starttls.enable", "true");
		propiedad.setProperty("mail.smtp.port", "587");

		Session sesion = Session.getDefaultInstance(propiedad);
		String correoEnvia = "banconet123@gmail.com";
		String contrasena = "b_net123";

		MimeMessage mail = new MimeMessage(sesion);
		Multipart multipart = new MimeMultipart();

		MimeBodyPart attachmentPart = new MimeBodyPart();

		MimeBodyPart textPart = new MimeBodyPart();

		try {
			mail.setFrom("Cooperativa JAM <" + correoEnvia + ">");
			mail.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
			mail.setSubject(asunto);
			File f = polizaON.generarTablaAmor(Poliza);
			attachmentPart.attachFile(f);
			textPart.setText(cuerpo);
			multipart.addBodyPart(attachmentPart);
			multipart.addBodyPart(textPart);
			mail.setContent(multipart);

			Transport transportar = sesion.getTransport("smtp");
			transportar.connect(correoEnvia, contrasena);
			transportar.sendMessage(mail, mail.getRecipients(Message.RecipientType.TO));
		} catch (AddressException | IOException ex) {
			System.out.println(ex.getMessage());
		} catch (MessagingException ex) {
			System.out.println(ex.getMessage());
		}
	}

}
