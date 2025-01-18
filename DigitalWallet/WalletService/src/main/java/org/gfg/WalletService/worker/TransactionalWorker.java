package org.gfg.WalletService.worker;

import org.apache.kafka.common.protocol.types.Field;
import org.gfg.CommonConstants;
import org.gfg.WalletService.model.Wallet;
import org.gfg.WalletService.model.WalletStatus;
import org.gfg.WalletService.repository.WalletRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TransactionalWorker {

    @Autowired
    WalletRepository walletRepository;

    String txnStatus;
    String txnMessage;

    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;


    public void updateWalletTransaction(String sender, String receiver,double amount,String txnId){

        Wallet senderWallet = walletRepository.findByMobileNo(sender);
        Wallet receiverWallet = walletRepository.findByMobileNo(receiver);

        if (senderWallet==null){
            txnStatus = "FAILED";
            txnMessage = "Sender waller doesn't exists";
        }
       else if (receiverWallet==null){
            txnStatus = "FAILED";
            txnMessage = "Receiver waller doesn't exists";
        }

        else if (senderWallet!=null && senderWallet.getStatus()!= WalletStatus.ACTIVE){
            txnStatus = "FAILED";
            txnMessage = "you are blocked to  make any transaction";
        }
       else if (receiverWallet!=null && receiverWallet.getStatus()!= WalletStatus.ACTIVE){
            txnStatus = "FAILED";
            txnMessage = "receiver is bloced to receive any transaction";
        }

        else if (senderWallet.getBalance()<amount){
            txnStatus = "FAILED";
            txnMessage = "Insufficient balance in your account";
        }

        else if (processTransaction(sender,receiver,amount)){
            txnStatus = "SUCCESS";
            txnMessage = "Transaction Success";
        }else {
            txnStatus = "PENDING";
            txnMessage = "Transaction is Pending";
        }


        // send the updated transaction information to kafka

        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CommonConstants.TRANSACTION_ID, txnId);
        jsonObject.put(CommonConstants.TRANSACTION_STATUS,txnStatus);
        jsonObject.put(CommonConstants.TRANSACTION_MESSAGE,txnMessage);

        kafkaTemplate.send(CommonConstants.TRANSACTION_UPDATE_TOPIC,jsonObject.toString());

        System.out.println("Transaction update details sent to kafka");


    }

    @Transactional
    private boolean processTransaction(String sender, String receiver, double amount){
        boolean isDone = false;
        try {
            walletRepository.updateWallet(sender,-amount);
            walletRepository.updateWallet(receiver,amount);
            isDone = true;
        }
        catch (Exception exception){
            isDone = false;
        }
        return isDone;
    }
}
