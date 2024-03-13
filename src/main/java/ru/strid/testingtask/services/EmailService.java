package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strid.testingtask.entities.Email;
import ru.strid.testingtask.repositories.EmailRepository;

import java.util.Optional;

@Service
public class EmailService {

    @Autowired
    private EmailRepository emailRepo;

    public Email find(Integer id) {
        final Optional<Email> optEmail = emailRepo.findById(id);
        return optEmail.isPresent() ? optEmail.get() : null;
    }

    public Email find(String emailAddress) {
        return emailRepo.findFirstByEmailAddress(emailAddress);
    }

    public long count() {
        return this.emailRepo.count();
    }


    public void store(final Email email){
        this.emailRepo.saveAndFlush(email);
    }

}
