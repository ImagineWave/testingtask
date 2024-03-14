package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strid.testingtask.entities.Transaction;
import ru.strid.testingtask.repositories.TransactionRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepo;

    public void store(Transaction transaction){
        transactionRepo.saveAndFlush(transaction);
    }

    public List<Transaction> getAllIncomingTransactions(String accountNumber){
        return transactionRepo.findAllByReceiverAccountNo(accountNumber);
    }

    public List<Transaction> getAllOutgoingTransactions(String accountNumber){
        return transactionRepo.findAllBySenderAccountNo(accountNumber);
    }

    public Optional<Transaction> findFirstByTransactionUUID (String transactionId){
        return transactionRepo.findById(transactionId);
    }
}
