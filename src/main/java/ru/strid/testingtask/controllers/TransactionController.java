package ru.strid.testingtask.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.services.BankAccountService;
import ru.strid.testingtask.services.PersonService;

import java.security.Principal;
import java.util.Map;

@Controller
public class TransactionController {

    @Autowired
    private PersonService personService;

    @Autowired
    private BankAccountService bankAccountService;


    @GetMapping(value = "/transaction")
    public ModelAndView currentUserName(final Map<String, Object> model, Principal principal) {

        Person person = personService.find(principal.getName());

        BankAccount senderAccount = bankAccountService.findByPersonId( (int) person.getPersonId());

        model.put("balance", senderAccount.getBalance());

        return new ModelAndView("transaction.html", model);
    }

    @PostMapping(
            path     = "/transaction",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )

    public ResponseEntity handle(Principal principal, @RequestBody String rawPayload) {

        JSONObject data = new JSONObject(rawPayload);

        Person person = personService.find(principal.getName());

        BankAccount senderAccount = bankAccountService.findByPersonId( (int) person.getPersonId());
        BankAccount receiverAccount;

        int amount = 0;
        amount = data.getInt("amount");

        receiverAccount = bankAccountService.find(data.getString("receiverNumber"));
        if(receiverAccount != null){
            bankAccountService.createTransaction(amount,receiverAccount.getAccountNumber(),senderAccount.getAccountNumber());
            return new ResponseEntity("Все ок", HttpStatus.OK);
        }


        return new ResponseEntity("Аккаунт не найден", HttpStatus.NOT_FOUND);
    }

}
