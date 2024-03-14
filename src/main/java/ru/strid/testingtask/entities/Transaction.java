package ru.strid.testingtask.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;


@Entity
public class Transaction {

    @Id
    private String transactionId;

    @Column(name = "receiver_account_no", nullable = false)
    private String receiverAccountNo;

    @Column(name = "sender_account_no", nullable = true)
    private String senderAccountNo;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "created_at", nullable = false)
    private long createdAt;


    public Transaction(int amount, String receiverAccountNo){
        this.transactionId = UUID.randomUUID().toString();
        this.receiverAccountNo = receiverAccountNo;
        this.amount = amount;
        this.createdAt = System.currentTimeMillis();
    }

    public Transaction(int amount, String receiverAccountNo, String senderAccountNo){
        this.transactionId = UUID.randomUUID().toString();
        this.receiverAccountNo = receiverAccountNo;
        this.senderAccountNo = senderAccountNo;
        this.amount = amount;
        this.createdAt = System.currentTimeMillis();
    }

    public Transaction(){
        this.transactionId = UUID.randomUUID().toString();
    }


    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getReceiverAccountNo() {
        return receiverAccountNo;
    }

    public void setReceiverAccountNo(String receiverAccountNo) {
        this.receiverAccountNo = receiverAccountNo;
    }

    public String getSenderAccountNo() {
        return senderAccountNo;
    }

    public void setSenderAccountNo(String senderAccountNo) {
        this.senderAccountNo = senderAccountNo;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }


}
