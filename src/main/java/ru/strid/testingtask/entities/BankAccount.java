package ru.strid.testingtask.entities;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bankAccountId;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "person_id", nullable = false)
    private Integer personId;

    @Column(name = "init_balance")
    private final int initBalance;

    @Column(name = "balance")
    private Integer balance;

    //TODO В зависмости от ответа, добавить или не добавлять flag лимитер вкладов PS: ответа на свой попрос я не получил

    public BankAccount(int personId, int initBalance){
        this.bankAccountId = null;
        this.accountNumber = UUID.randomUUID().toString();
        this.personId = personId;
        this.initBalance = initBalance;
        this.balance = initBalance;
    }

    public BankAccount(){
        this.initBalance = 0;
    }


    public Integer getBankAccountId() {
        return bankAccountId;
    }

    public void setBankAccountId(Integer bankAccountId) {
        bankAccountId = bankAccountId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public Integer getInitBalance() {
        return initBalance;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public boolean isGrowing(){
        return (balance<this.initBalance*2.07);
    }

    /**
     * Хотелось бы сделать какой то event который бы вызывался при создании новой транзакции, и обновлял бы поле баланс, таким образом если
     * у нас вдруг возникнет какой-то левый доступ, то в коненчом итоге (из-за того что у нас стоит блокировка транзакций read after commit)
     * баланс будет приведен в норм (в соответсвии с иситорией транзакций)
     *
     * НО, я такое не вывезу) Не хватает опыта в таких вопросах. Хотя мне кажется что подход, который я описал выше, не смог бы бещ особого ущерба
     * по производительности рещить проблему.
     */


}
