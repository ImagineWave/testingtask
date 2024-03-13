package ru.strid.testingtask.controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.strid.testingtask.entities.BankAccount;
import ru.strid.testingtask.entities.Email;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.entities.Phone;
import ru.strid.testingtask.services.BankAccountService;
import ru.strid.testingtask.services.EmailService;
import ru.strid.testingtask.services.PersonService;
import ru.strid.testingtask.services.PhoneService;

import java.util.Date;
import java.util.Map;
import java.util.Set;

@Controller
public class CreateNewUserController {

    @Autowired
    private PersonService personService;


    @GetMapping("/newuser")
    public ModelAndView createNew(final Map<String, Object> model){
        model.put("person", new Person());
        return new ModelAndView("createUser.html", model);
    }

    @PostMapping(
            path     = "/createnewuser",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity handle(@RequestBody String rawPayload) {
        JSONObject data = new JSONObject(rawPayload);
        Date birthDate = new Date(Integer.parseInt(data.get("birthDate").toString()));
        personService.createAccount(
                data.get("login").toString(),
                data.get("password").toString(),
                data.get("firstName").toString(),
                data.get("lastName").toString(),
                data.get("patronymic").toString(),
                birthDate,
                data.get("email").toString(),
                data.get("phone").toString(),
                data.get("initBalance").toString()
        );
        return new ResponseEntity("Все ок", HttpStatus.OK);
    }

}
