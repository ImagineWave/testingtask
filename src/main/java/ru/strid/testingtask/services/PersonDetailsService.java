package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.implementations.PersonDetails;

import java.util.Optional;

@Component
public class PersonDetailsService implements UserDetailsService {

    @Autowired
    private PersonService personService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> optPerson = Optional.of(personService.find(username));
        return optPerson.map(PersonDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
