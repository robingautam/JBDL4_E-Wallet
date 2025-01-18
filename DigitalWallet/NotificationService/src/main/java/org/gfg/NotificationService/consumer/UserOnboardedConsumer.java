package org.gfg.NotificationService.consumer;

import jakarta.mail.MessagingException;
import org.gfg.CommonConstants;
import org.gfg.NotificationService.worker.EmailWorker;
import org.gfg.UserIdentifier;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
public class UserOnboardedConsumer {


    @Autowired
    EmailWorker emailWorker;



    @KafkaListener(topics ="USER_DETAILS_TOPIC", groupId = "email-group")
    public void listenNewlyCreatedUser(String data) throws MessagingException {
        System.out.println("Data Received: "+data);

        JSONObject userObject = new JSONObject(data);
        String name = userObject.optString(CommonConstants.USER_NAME);
        String email = userObject.optString(CommonConstants.USER_EMAIL);
        UserIdentifier userIdentifier = userObject.optEnum(UserIdentifier.class,CommonConstants.USER_IDENTIFIER);
        String userIdentifierValue = userObject.optString(CommonConstants.USER_IDENTIFIER_VALUE);

        emailWorker.sendEmailNotification(name,email,userIdentifier.name(),userIdentifierValue);

    }
}
