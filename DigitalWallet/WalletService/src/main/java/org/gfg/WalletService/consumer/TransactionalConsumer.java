package org.gfg.WalletService.consumer;

import org.gfg.CommonConstants;
import org.gfg.WalletService.worker.TransactionalWorker;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionalConsumer {

    @Autowired
    TransactionalWorker transactionalWorker;


    @KafkaListener(topics = "TRANSACTION_DETAILS_TOPIC", groupId = "transaction-group")
    public void listenTransactionData(String data){
        System.out.println("Transaction Data: "+data);

        JSONObject jsonObject = new JSONObject(data);
        String sender = jsonObject.optString(CommonConstants.SENDER_ID);
        String receiver = jsonObject.optString(CommonConstants.RECEIVER_ID);
        double amount = jsonObject.optDouble(CommonConstants.TRANSACTION_AMOUNT);
        String txnId = jsonObject.optString(CommonConstants.TRANSACTION_ID);

        transactionalWorker.updateWalletTransaction(sender,receiver,amount,txnId);

    }
}
