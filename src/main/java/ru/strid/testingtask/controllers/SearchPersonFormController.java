package ru.strid.testingtask.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.strid.testingtask.services.EmailService;
import ru.strid.testingtask.services.PersonService;
import ru.strid.testingtask.services.PhoneService;

import java.util.Map;

@Controller
public class SearchPersonFormController {

    @Autowired
    private PersonService personService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PhoneService phoneService;

    @GetMapping("/find-form")
    public ModelAndView createNew(final Map<String, Object> model){
        return new ModelAndView("findForm.html", model);
    }
}
