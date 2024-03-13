package ru.strid.testingtask.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.strid.testingtask.entities.Phone;
import ru.strid.testingtask.repositories.PhoneRepository;

@Service
public class PhoneService {

    @Autowired
    private PhoneRepository phoneRepo;

    public void store(final Phone phone){
        this.phoneRepo.saveAndFlush(phone);
    }

    public Phone find(String phoneNumber) {
        return phoneRepo.findFirstByPhoneNumber(phoneNumber);
    }


}
