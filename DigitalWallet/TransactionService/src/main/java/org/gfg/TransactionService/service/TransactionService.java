package org.gfg.TransactionService.service;

import org.gfg.CommonConstants;
import org.gfg.TransactionService.model.Transaction;
import org.gfg.TransactionService.model.TxnStatus;
import org.gfg.TransactionService.repository.TransactionRepository;
import org.gfg.TransactionService.response.TransactionResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    KafkaTemplate<String, String> kafkaTemplate;

    public String initiateTransaction(String sender, String receiver,String purpose, String amount){

        Transaction transaction = new Transaction();
        transaction.setAmount(Double.parseDouble(amount));
        transaction.setPurpose(purpose);
        transaction.setSenderId(sender);
        transaction.setReceiverId(receiver);
        transaction.setTxnStatus(TxnStatus.INITIATED);
        transaction.setTxnStatusMessage("Transaction Initiated");

        String txnId = UUID.randomUUID().toString();
        transaction.setTxnId(txnId);

        transactionRepository.save(transaction);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.SENDER_ID,transaction.getSenderId());
        jsonObject.put(CommonConstants.RECEIVER_ID, transaction.getReceiverId());
        jsonObject.put(CommonConstants.TRANSACTION_AMOUNT,transaction.getAmount());
        jsonObject.put(CommonConstants.TRANSACTION_ID,transaction.getTxnId());

        kafkaTemplate.send(CommonConstants.TRANSACTION_DETAILS_TOPIC,jsonObject.toString());

        System.out.println("Data Sent to Kafka");

        return txnId;
    }


    public List<TransactionResponse> getTransactionHistory(String userMobile){
        List<Transaction> transaction = transactionRepository.findBySenderIdOrReceiverId(userMobile,userMobile);

        List<TransactionResponse> list = new ArrayList<>();

        for (Transaction tx: transaction){
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setAmount(tx.getAmount());
            transactionResponse.setDate(tx.getCreatedOn());
            transactionResponse.setMessage(tx.getPurpose());
            if (userMobile.equalsIgnoreCase(tx.getSenderId())){
                transactionResponse.setMobileNo(tx.getReceiverId());
                transactionResponse.setTransferType("DEBIT");
            }else {
                transactionResponse.setMobileNo(tx.getSenderId());
                transactionResponse.setTransferType("CREDIT");
            }

            list.add(transactionResponse);
        }
        return list;

    }
}
