package ru.strid.testingtask.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.services.PersonService;

import java.util.List;
import java.util.Optional;

@RestController
public class SearchPersonRESTController {

    private static final Logger log = LoggerFactory.getLogger(SearchPersonRESTController.class);

    @Autowired
    private PersonService personService;

    @GetMapping(path = {"/find", "/find/{page}/{seek}","/find/{page}" })
    public List<Person> listAllPerson(
            @PathVariable final Optional<String> page,
            @PathVariable final Optional<String> seek
    )
    {
        int pageNum = 0;

        if(page.isPresent()){
            try {
                pageNum = Integer.parseInt(page.get());
            } catch (NumberFormatException e){
                pageNum = 0;
            }
        }

        String param = seek.isPresent() ? seek.get() : null;

        List<Person> list = personService.listWithSeek(pageNum,PersonService.DEFAULT_COUNT,param);
        return list;
    }

    @GetMapping(path = {"/find-email/{seek}" })
    public Person listFirstPersonByEmail( @PathVariable final Optional<String> seek) {

        String param = seek.isPresent() ? seek.get() : null;

        log.info("Поиск по точному соотвествию email {}", param);

        return personService.findByEmail(param);
    }
    @GetMapping(path = {"/find-phone/{seek}" })
    public Person listFirstPersonByPhone( @PathVariable final Optional<String> seek) {

        String param = seek.isPresent() ? seek.get() : null;

        return personService.findByPhone(param);
    }
}
