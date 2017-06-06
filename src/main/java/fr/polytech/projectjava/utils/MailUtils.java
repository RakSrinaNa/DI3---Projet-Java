package fr.polytech.projectjava.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

/**
 * Created by Thomas Couchoud (MrCraftCod - zerderr@gmail.com) on 06/06/2017.
 *
 * @author Thomas Couchoud
 * @since 2017-06-06
 */
public class MailUtils
{
	/**
	 * Send a email through a session build by the values in the configuration file.
	 *
	 * @param emailFrom The email of the sender.
	 * @param fromName  The name of the sender.
	 * @param to        The mail of the recipient.
	 * @param object    The object of the mail.
	 * @param body      The body of the mail.
	 *
	 * @throws MessagingException           From javax mail.
	 * @throws UnsupportedEncodingException From javax mail.
	 */
	public static void sendMail(String emailFrom, String fromName, String to, String object, String body) throws UnsupportedEncodingException, MessagingException
	{
		Properties properties = System.getProperties();
		properties.put("mail.smtp.starttls.enable", Configuration.getString("smtpStartTls"));
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.host", Configuration.getString("smtpHost"));
		properties.put("mail.smtp.port", Configuration.getInt("smtpPort"));
		Session session = Session.getInstance(properties, new Authenticator()
		{
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication(Configuration.getString("smtpUser"), Configuration.getString("smtpPassword"));
			}
		});
		sendMail(session, emailFrom, fromName, to, object, body);
	}
	
	/**
	 * Send a email through a session.
	 *
	 * @param session   The session to use.
	 * @param emailFrom The email of the sender.
	 * @param fromName  The name of the sender.
	 * @param to        The mail of the recipient.
	 * @param object    The object of the mail.
	 * @param body      The body of the mail.
	 *
	 * @throws MessagingException           From javax mail.
	 * @throws UnsupportedEncodingException From javax mail.
	 */
	public static void sendMail(Session session, String emailFrom, String fromName, String to, String object, String body) throws UnsupportedEncodingException, javax.mail.MessagingException
	{
		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(emailFrom, fromName));
		message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(object);
		message.setText(body);
		Transport.send(message);
	}
}
