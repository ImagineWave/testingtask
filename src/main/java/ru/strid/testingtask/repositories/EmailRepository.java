package ru.strid.testingtask.repositories;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strid.testingtask.entities.Email;

import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

    public Integer countByPersonId(final Integer personId);

    public List<Email> findAllByPersonId(final Integer ownerId, final Sort sort);

    public Email findFirstByEmailAddress(final String emailAddress);

}
