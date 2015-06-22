package com.if42.tester.service.util;

import com.if42.tester.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by student on 5/26/2014.
 */
@org.springframework.stereotype.Service("mailService")
public class MailServiceImpl extends MailService {

    private final static Logger logger = LoggerFactory.getLogger(MailServiceImpl.class);

    public void send(String email, String password) {

        Properties props = new Properties();
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable", starttlsEnable);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, pass);
                    }
                }
        );
        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(email));
            message.setSubject(subject);
            message.setText(
                    "Hello, you've registered in itester system"
                            + "\n\nLogin: " + email
                            + "\nPassword: " + password
                            + "\n\nHave a nice day"
            );

            Transport.send(message);
            logger.info("Sending login and password on email: " + email);

        } catch (MessagingException e) {
            logger.error("ERROR!!!!!!" + e.getMessage());
        }
    }
}
