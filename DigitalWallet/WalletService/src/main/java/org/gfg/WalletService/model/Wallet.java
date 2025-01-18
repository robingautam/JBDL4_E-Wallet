package org.gfg.WalletService.model;

import com.fasterxml.jackson.annotation.JsonTypeId;
import jakarta.persistence.*;
import org.gfg.UserIdentifier;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.Date;

@Entity(name = "wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String userId;
    String mobileNo;
    @Enumerated(EnumType.STRING)
    WalletStatus status;
    double balance;
    @Enumerated(EnumType.STRING)
    UserIdentifier userIdentifier;
    String userIdentifierValue;

    @CreationTimestamp
    Date createdOn;
    @UpdateTimestamp
    Date updatedOn;

    public Wallet() {
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public WalletStatus getStatus() {
        return status;
    }

    public void setStatus(WalletStatus status) {
        this.status = status;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public UserIdentifier getUserIdentifier() {
        return userIdentifier;
    }

    public void setUserIdentifier(UserIdentifier userIdentifier) {
        this.userIdentifier = userIdentifier;
    }

    public String getUserIdentifierValue() {
        return userIdentifierValue;
    }

    public void setUserIdentifierValue(String userIdentifierValue) {
        this.userIdentifierValue = userIdentifierValue;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public Date getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }


}
