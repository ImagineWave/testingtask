package ru.strid.testingtask.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.strid.testingtask.entities.Email;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.entities.Phone;
import ru.strid.testingtask.services.EmailService;
import ru.strid.testingtask.services.PersonService;
import ru.strid.testingtask.services.PhoneService;

import java.security.Principal;
import java.util.Optional;

@RestController
public class UpdatePersonDataController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PhoneService phoneService;

    private static final Logger log = LoggerFactory.getLogger(UpdatePersonDataController.class);

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/person/email/{paramEmail}")
    public ResponseEntity  createNewEmail(Principal principal, @PathVariable final Optional<String> paramEmail){
        Person person = personService.find(principal.getName());
        log.info("Попытка добавить email {} ", person.getLogin());

        String email = paramEmail.isPresent() ? paramEmail.get() : null;

        if(email == null){
            return new ResponseEntity("Пустой email", HttpStatus.BAD_REQUEST);
        }

       if(emailService.find(email) == null){
           Email emailToStore = new Email(email, (int) person.getPersonId());
           emailService.store(emailToStore);
           log.info("Аккаунту {} добавлен новый Email {}", person.getLogin(), email);
           return new ResponseEntity("Добавлен новый email", HttpStatus.OK);
       }

        return new ResponseEntity("Email уже занят", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/person/email/{paramEmail}")
    public ResponseEntity  deleteEmail(Principal principal, @PathVariable final Optional<String> paramEmail){
        Person person = personService.find(principal.getName());
        log.info("Попытка удалить email {} ", person.getLogin());

        String email = paramEmail.isPresent() ? paramEmail.get() : null;

        if(email == null){
            return new ResponseEntity("Пустой email", HttpStatus.OK);
        }

        if(emailService.find(email) == null){ //Проверка есть ли такой email В базе
            return new ResponseEntity("Такого email нет", HttpStatus.BAD_REQUEST);
        }

        Email emailToDel =  emailService.find(email);

        if(emailToDel.getPersonId() != person.getPersonId()){
            return new ResponseEntity("Этот Email не ваш!", HttpStatus.BAD_REQUEST);
        }

        if(emailService.count((int) person.getPersonId())==1){
            return new ResponseEntity("Вы не можете удалить свой последний email!", HttpStatus.BAD_REQUEST);
        }

        emailService.kill(emailToDel.getEmailId());
        log.info("У аккаунта {} удален Email {}", person.getLogin(), email);
        return new ResponseEntity("Email удален", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/person/phone/{paramPhone}")
    public ResponseEntity  createNewPhone(Principal principal, @PathVariable final Optional<String> paramPhone){
        Person person = personService.find(principal.getName());
        log.info("Попытка добавить phone {} ", person.getLogin());

        String phone = paramPhone.isPresent() ? paramPhone.get() : null;

        if(phone == null){
            return new ResponseEntity("Пустой phone", HttpStatus.BAD_REQUEST);
        }

        if(phoneService.find(phone) == null){
            Phone phoneToStore = new Phone(phone, (int) person.getPersonId());
            phoneService.store(phoneToStore);
            log.info("Аккаунту {} добавлен новый Phone {}", person.getLogin(), phone);
            return new ResponseEntity("Добавлен новый телефон", HttpStatus.OK);
        }

        return new ResponseEntity("Телефон уже занят", HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/person/phone/{paramPhone}")
    public ResponseEntity  deletePhone(Principal principal, @PathVariable final Optional<String> paramPhone){
        Person person = personService.find(principal.getName());
        log.info("Попытка удалить телефон {} ", person.getLogin());

        String phone = paramPhone.isPresent() ? paramPhone.get() : null;

        if(phone == null){
            return new ResponseEntity("Пустой phone", HttpStatus.BAD_REQUEST);
        }

        if(phoneService.find(phone) == null){ //Проверка есть ли такой email В базе
            return new ResponseEntity("Такого телефона нет", HttpStatus.BAD_REQUEST);
        }

        Phone phoneToDel =  phoneService.find(phone);

        if(phoneToDel.getPersonId() != person.getPersonId()){
            return new ResponseEntity("Этот телефон не ваш!", HttpStatus.BAD_REQUEST);
        }

        if(phoneService.count((int) person.getPersonId())==1){
            return new ResponseEntity("Вы не можете удалить свой последний телефон!", HttpStatus.BAD_REQUEST);
        }

        phoneService.kill((int) phoneToDel.getPhoneId());
        log.info("У аккаунта {} удален Phone {}", person.getLogin(), phone);
        return new ResponseEntity("Телефон удален", HttpStatus.OK);
    }

}
