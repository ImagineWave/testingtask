package ru.strid.testingtask.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strid.testingtask.entities.Phone;

import java.util.List;

@Repository
public interface PhoneRepository extends JpaRepository<Phone, Integer> {

    public long countByPersonId(final Integer personId);

    public List<Phone> findAllByPersonId(final Integer ownerId, final Sort sort);

    public Phone findFirstByPhoneNumber(final String PhoneNumber);

}
