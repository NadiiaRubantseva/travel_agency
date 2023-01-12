package ua.epam.travelagencyms.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import java.util.Properties;

public class EmailSender {
    private static final Logger logger = LoggerFactory.getLogger(EmailSender.class);
    private final String user;
    private final Session session;

    public EmailSender(Properties properties) {
        user = properties.getProperty("mail.user");
        session = getSession(properties, user);
    }

    public void send(String subject, String body, String sendTo) {
        MimeMessage message = new MimeMessage(session);
        try {
            sendEmail(subject, body, sendTo, message);
        } catch (MessagingException e) {
            logger.error(e.getMessage());
        }
    }

    private void sendEmail(String subject, String body, String sendTo, MimeMessage message)
            throws MessagingException {
        message.setFrom(new InternetAddress(user));
        message.setSubject(subject);
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(sendTo));
        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        Multipart multipart = new MimeMultipart();
        mimeBodyPart.setContent(body, "text/html; charset=utf-8");
        multipart.addBodyPart(mimeBodyPart);
        message.setContent(multipart);
        Transport.send(message);
    }

    private static Session getSession(Properties properties, String user) {
        return Session.getInstance(properties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(user, properties.getProperty("mail.password"));
            }
        });
    }
}