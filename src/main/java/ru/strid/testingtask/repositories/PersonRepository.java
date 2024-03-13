package ru.strid.testingtask.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.strid.testingtask.entities.Person;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {
    // возвращает всех пользователей
    public List<Person> findAll();

    // возвращает всех пользователей по заданным параметрам сортировки
    public Page<Person> findAll(final Pageable pageable);

    // возвращает пользователя по заданному логину
    public Person findFirstByLogin(final String login);

}
