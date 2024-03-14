package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.entities.Transaction;
import ru.strid.testingtask.repositories.BankAccountRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    @Autowired
    private TransactionService transactionService;

    public BankAccount find(Integer id) {
        final Optional<BankAccount> optBankAcc = bankAccountRepo.findById(id);
        return optBankAcc.isPresent() ? optBankAcc.get() : null;
    }

    public List<BankAccount> list() {
        return this.bankAccountRepo.findAll();
    }

    public BankAccount find(String accountNumber) {
        return bankAccountRepo.findFirstByAccountNumber(accountNumber);
    }

    public BankAccount findByPersonId(Integer personId){
        return bankAccountRepo.findFirstByPersonId(personId);
    }

    public void store(final BankAccount bankAccount){
        this.bankAccountRepo.saveAndFlush(bankAccount);
    }

    @Transactional
    public Transaction createTransaction(int amount, String receiverAccountNumber, String senderAccountNumber){
        BankAccount receiver = bankAccountRepo.findFirstByAccountNumber(receiverAccountNumber);
        BankAccount sender = bankAccountRepo.findFirstByAccountNumber(senderAccountNumber);
        if(sender.getAccountNumber().equals(receiver.getAccountNumber())){
            throw new RuntimeException("Номер получателя и отправителя совпадают!");
        }

        Transaction transaction = new Transaction(amount, receiver.getAccountNumber(), sender.getAccountNumber());
        if(sender.getBalance()-amount<0){
            throw new RuntimeException("Баланс не может быть меньше нуля!");
        }
        sender.setBalance(sender.getBalance()-amount);
        receiver.setBalance(receiver.getBalance()+amount);
        transactionService.store(transaction);

        return transaction;
    }
    @Transactional
    public Transaction createIncomeTransaction(int amount, String receiverAccountNumber){
        BankAccount receiver = bankAccountRepo.findFirstByAccountNumber(receiverAccountNumber);

        Transaction transaction = new Transaction(amount, receiver.getAccountNumber());

        receiver.setBalance(receiver.getBalance()+amount);
        transactionService.store(transaction);

        return transaction;
    }
}
