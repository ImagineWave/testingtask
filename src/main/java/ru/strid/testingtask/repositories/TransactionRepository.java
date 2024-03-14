package ru.strid.testingtask.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strid.testingtask.entities.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    public List<Transaction> findAllBySenderAccountNo(final String login);

    public List<Transaction> findAllByReceiverAccountNo(final String login);
}
