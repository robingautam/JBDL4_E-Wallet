package org.gfg.TransactionService.consumer;

import org.gfg.CommonConstants;
import org.gfg.TransactionService.model.TxnStatus;
import org.gfg.TransactionService.repository.TransactionRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionUpdateConsumer {

    @Autowired
    TransactionRepository transactionRepository;

    @KafkaListener(topics = "TRANSACTION_UPDATE_TOPIC", groupId = "transaction-update")
    public void getUpdatedTransactionDetails(String data){
        System.out.println("Updated Transaction Details: "+data);

        JSONObject jsonObject = new JSONObject(data);
        String txnId = jsonObject.optString(CommonConstants.TRANSACTION_ID);
        String status = jsonObject.optString(CommonConstants.TRANSACTION_STATUS);
        String msg = jsonObject.optString(CommonConstants.TRANSACTION_MESSAGE);
     //   System.out.println("status: "+status);
        TxnStatus txnStatus = TxnStatus.valueOf(status);
        transactionRepository.updateTransactionDetails(txnId,txnStatus ,msg);

        System.out.println("Transaction Details updated");

    }
}
