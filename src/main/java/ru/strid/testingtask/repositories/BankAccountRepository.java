package ru.strid.testingtask.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strid.testingtask.entities.BankAccount;

import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {

    public Integer countByPersonId(final Integer personId);

    public List<BankAccount> findAllByPersonId(final Integer ownerId, final Sort sort);

    public BankAccount findFirstByAccountNumber(final String accountNumber);

    public BankAccount findFirstByPersonId(final Integer personId);

}
