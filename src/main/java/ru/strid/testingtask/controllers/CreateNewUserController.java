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
import org.springframework.web.servlet.ModelAndView;
import ru.strid.testingtask.services.PersonService;

import java.util.Date;
import java.util.Map;

@Controller
public class CreateNewUserController {

    @Autowired
    private PersonService personService;


    @GetMapping("/new-user")
    public ModelAndView createNew(){
        return new ModelAndView("createUser.html");
    }
    
    @PostMapping(
            path     = "/new-user",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    /**
    / Хочу отметить, что email и phone не проходят никакую верификацию, ни на фронте, ни на беке.
     Я понимаю что она необходима, допилить ее я не успел к дедлайну
     */

    public ResponseEntity handle(@RequestBody String rawPayload) {
        JSONObject data = new JSONObject(rawPayload);
        Date birthDate = new Date(Long.parseLong(data.get("birthDate").toString()));
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

    @GetMapping("/home")
    public ModelAndView home(final Map<String, Object> model){
        return new ModelAndView("home.html");
    }

}
