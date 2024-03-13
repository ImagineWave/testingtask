package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.entities.Email;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.entities.Phone;
import ru.strid.testingtask.repositories.EmailRepository;
import ru.strid.testingtask.repositories.PersonRepository;
import ru.strid.testingtask.repositories.PhoneRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    public static int DEFAULT_PAGE = 0;
    public static int DEFAULT_COUNT = 20;


    @Autowired
    private PersonRepository personRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PhoneService phoneService;

    @Autowired
    private BankAccountService bankAccountService;


    public long count() {
        return this.personRepo.count();
    }

    public List<Person> list(final int page, final int count) {
        Sort sort = Sort.by("login").ascending();
        return this.personRepo.findAll(PageRequest.of(page, count, sort)).getContent();
    }

    public List<Person> list() {
        return this.list(DEFAULT_PAGE, DEFAULT_COUNT);
    }

    public Person find(Integer id) {
        final Optional<Person> optPerson = personRepo.findById(id);
        return optPerson.isPresent() ? optPerson.get() : null;
    }

    public Person find(String login) {
        return personRepo.findFirstByLogin(login);
    }

    //Сохранить пользователя
    public void store(final Person person){
        this.personRepo.saveAndFlush(person);
    }

    //Удалить пользователя по ID
    public void kill(final Integer id){
        this.personRepo.deleteById(id);
    }

//    Найти пользвоателя по Email
    public Person findByEmail(String emailAddress){
        return find(emailService.find(emailAddress).getPersonId());
    }

    public Person findByPhone(String phoneNumber){
        return find(phoneService.find(phoneNumber).getPersonId());
    }

    @Transactional
    public Person createAccount(final String login, final String password, final String firstName, final String lastName, final String patronymic,
                              final Date birthDate, final String emailAddress, final String phoneNumber, final String initBalance) {

        this.store(new Person(login, password, firstName, lastName, patronymic, birthDate));
        Person personFromDB = this.find(login);
        Email email = new Email(emailAddress, (int) personFromDB.getPersonId());
        emailService.store(email);
        Phone phone = new Phone(phoneNumber, (int) personFromDB.getPersonId());
        phoneService.store(phone);
        BankAccount bankAccount = new BankAccount((int) personFromDB.getPersonId(),Integer.parseInt(initBalance));
        bankAccountService.store(bankAccount);

        return personFromDB;
    }


}
