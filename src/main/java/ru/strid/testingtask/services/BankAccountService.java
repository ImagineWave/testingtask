package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.repositories.BankAccountRepository;

import java.util.Optional;

@Service
public class BankAccountService {

    @Autowired
    private BankAccountRepository bankAccountRepo;

    public BankAccount find(Integer id) {
        final Optional<BankAccount> optBankAcc = bankAccountRepo.findById(id);
        return optBankAcc.isPresent() ? optBankAcc.get() : null;
    }

    public void store(final BankAccount bankAccount){
        this.bankAccountRepo.saveAndFlush(bankAccount);
    }

}
