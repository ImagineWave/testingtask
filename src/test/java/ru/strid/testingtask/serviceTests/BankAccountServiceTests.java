package ru.strid.testingtask.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.entities.Transaction;
import ru.strid.testingtask.services.BankAccountService;
import ru.strid.testingtask.services.PersonService;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class BankAccountServiceTests {

    @Autowired
    private BankAccountService bankAccountService;


    @Autowired
    private PersonService personService;

    @Test
    public void test(){
        testTransactionAbortByWrongTransactionAmount();
        testTransactionAbortByWrongSumLeft();
    }

    @Transactional
    public void testTransactionAbortByWrongTransactionAmount() { // запуск тестов ДО отмены транзакции
        BankAccount sender = createSenderAccountTest();
        BankAccount receiver = createReceiverAccountTest();

        balanceTransfer(sender, receiver);
        negativeTransferTest(sender, receiver); // Отмена транзакции, Балнас 1000 и 0
    }

    @Transactional
    public void testTransactionAbortByWrongSumLeft(){
        BankAccount sender = createSenderAccountTest();
        BankAccount receiver = createReceiverAccountTest();

        fullBalanceTransfer(sender, receiver);
        negativeBalanceLeft(sender, receiver); // Отмена транзакции
    }

    public BankAccount createSenderAccountTest() {
        Person sender = personService.createAccount(
                "sender", "sender",
                "Отправитель", "Отправителев",
                null, new Date(), "sender@mail.ru",
                "+7800sender", "1000");


        BankAccount senderBA = bankAccountService.findByPersonId((int) sender.getPersonId());
        return senderBA;
    }

    public BankAccount createReceiverAccountTest() {
        Person receiver = personService.createAccount(
                "receiver", "receiver",
                "Получатель", "Получателев",
                null, new Date(), "receiver@mail.ru",
                "+7800receiver", "0");
        BankAccount receiverBA = bankAccountService.findByPersonId((int) receiver.getPersonId());
        return receiverBA;
    }

    public Transaction balanceTransfer(BankAccount sender, BankAccount receiver){
        assertTrue(sender.getBalance() == 1000, "Sender balance is wrong!");
        assertTrue(receiver.getBalance() == 0, "receiver balance is wrong!");
        Transaction transaction = bankAccountService.createTransaction(800, receiver.getAccountNumber(), sender.getAccountNumber());
        assertTrue(sender.getBalance() == 200, "Sender new balance is wrong!");
        assertTrue(receiver.getBalance() == 800, "Receiver new balance is wrong!");
        return transaction;
    }

    public Transaction negativeTransferTest(BankAccount sender, BankAccount receiver){
        assertTrue(sender.getBalance() == 200, "Sender new balance is wrong!");
        assertTrue(receiver.getBalance() == 800, "Receiver new balance is wrong!");
        org.springframework.dao.DataIntegrityViolationException thrown = assertThrows(
                org.springframework.dao.DataIntegrityViolationException.class,
                () -> bankAccountService.createTransaction(-800, receiver.getAccountNumber(), sender.getAccountNumber()),
                "Expected createTransaction() to throw, but it didn't"
        );
        return null;
    }

    public Transaction fullBalanceTransfer(BankAccount sender, BankAccount receiver){
        assertTrue(sender.getBalance() == 1000, "Sender balance is wrong!");
        assertTrue(receiver.getBalance() == 0, "receiver balance is wrong!");
        Transaction transaction = bankAccountService.createTransaction(1000, receiver.getAccountNumber(), sender.getAccountNumber());
        assertTrue(sender.getBalance() == 0, "Sender new balance is wrong!");
        assertTrue(receiver.getBalance() == 1000, "Receiver new balance is wrong!");
        return transaction;
    }


    public Transaction negativeBalanceLeft(BankAccount sender, BankAccount receiver){
        System.out.println(sender.getBalance());
        System.out.println(receiver.getBalance());
        assertTrue(sender.getBalance() == 0, "Sender balance is wrong!");
        assertTrue(receiver.getBalance() == 1000, "receiver balance is wrong!");
        org.springframework.dao.DataIntegrityViolationException thrown = assertThrows(
                org.springframework.dao.DataIntegrityViolationException.class,
                () -> bankAccountService.createTransaction(10, receiver.getAccountNumber(), sender.getAccountNumber()),
                "Expected createTransaction() to throw, but it didn't"
        );
        return null;
    }
}
