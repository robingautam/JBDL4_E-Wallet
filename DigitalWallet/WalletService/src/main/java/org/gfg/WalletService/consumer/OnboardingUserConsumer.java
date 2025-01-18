package org.gfg.WalletService.consumer;

import org.gfg.CommonConstants;
import org.gfg.UserIdentifier;
import org.gfg.WalletService.model.Wallet;
import org.gfg.WalletService.model.WalletStatus;
import org.gfg.WalletService.repository.WalletRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.KafkaListeners;

@Configuration
public class OnboardingUserConsumer {

    @Value("${wallet.initial.amount}")
    public String amount;

    @Autowired
    WalletRepository walletRepository;


    @KafkaListener(topics = "USER_DETAILS_TOPIC", groupId = "wallet-group")
    public void listenNewlyCreatedUser(String data){
        System.out.println("Data Received: "+data);

        JSONObject jsonObject = new JSONObject(data);
        String userId = jsonObject.optString(CommonConstants.USER_ID);
        String mobile = jsonObject.optString(CommonConstants.USER_MOBILE);
        UserIdentifier userIdentifier = jsonObject.optEnum(UserIdentifier.class,CommonConstants.USER_IDENTIFIER);
        String userIdentifierValue = jsonObject.optString(CommonConstants.USER_IDENTIFIER_VALUE);

        Wallet wallet = new Wallet();
        wallet.setUserId(userId);
        wallet.setMobileNo(mobile);
        wallet.setUserIdentifier(userIdentifier);
        wallet.setUserIdentifierValue(userIdentifierValue);
        wallet.setBalance(Double.parseDouble(amount));
        wallet.setStatus(WalletStatus.ACTIVE);

        walletRepository.save(wallet);

        System.out.println("Wallet account has been created successfully");
    }
}
