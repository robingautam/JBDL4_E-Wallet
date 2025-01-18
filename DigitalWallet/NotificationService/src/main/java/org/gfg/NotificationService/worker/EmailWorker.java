package org.gfg.NotificationService.worker;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.gfg.NotificationService.template.EmailTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailWorker {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmailNotification(String name, String email, String userIdentifier,
                                      String userIdentifierValue) throws MessagingException {

        String mailTemplate = EmailTemplate.getMailTemplate();
        mailTemplate = mailTemplate.replaceAll("=name=",name);
        mailTemplate = mailTemplate.replaceAll("=documentIdentifier=",userIdentifier);
        mailTemplate = mailTemplate.replaceAll("=documentNo=",userIdentifierValue);
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mail = new MimeMessageHelper(mimeMessage,true);
        mail.setTo(email);
        mail.setFrom("walletgfg@gmail.com");
        mail.setSubject("Welcome to GFG E-Wallet Application");
        mail.setText(mailTemplate,true);

        javaMailSender.send(mimeMessage);

        System.out.println("Mail sent to user");


    }
}
