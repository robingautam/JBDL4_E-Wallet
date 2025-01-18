package org.gfg.TransactionService.response;


import java.util.Date;

public class TransactionResponse {

    String mobileNo;
    double amount;
    Date date;
    String message;
    String transferType;

    public TransactionResponse(){}

    public TransactionResponse(String mobileNo, double amount, Date date, String message, String transferType) {
        this.mobileNo = mobileNo;
        this.amount = amount;
        this.date = date;
        this.message = message;
        this.transferType = transferType;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }
}
