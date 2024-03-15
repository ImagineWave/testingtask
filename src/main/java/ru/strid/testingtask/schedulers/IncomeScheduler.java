package ru.strid.testingtask.schedulers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.services.BankAccountService;

import java.util.List;

@Component
public class IncomeScheduler {

    @Autowired
    private BankAccountService bankAccountService;

    private static final Logger log = LoggerFactory.getLogger(IncomeScheduler.class);


    @Scheduled(fixedRate = 300*1000) //Раз в 5 минут
    public void executeIncome(){
        log.info("Scheduler task has started {}");
        List<BankAccount> listOfAccounts = bankAccountService.list();

        for(BankAccount account: listOfAccounts){
            log.info("Проверяю баланс аккаунта {}", account.getAccountNumber());
            if(account.isGrowing()){
                int amount = getIncomeAmount(account.getBalance(), account.getInitBalance());
                bankAccountService.createIncomeTransaction(amount, account.getAccountNumber());
                log.info("Начислено {} на аккаунт {}. {}/{}", amount, account.getAccountNumber(),
                        account.getBalance()+amount, (int) Math.round(account.getInitBalance()*2.07));
            }
        }

    }
    private int getIncomeAmount(final int balance, final int initBalance){
        int maxBalance = (int) Math.round(initBalance*2.07);
        int plus = (int) Math.round(balance*0.05);
        if(balance+plus>maxBalance){
            return maxBalance - balance;
        }
        return plus;
    }

}
