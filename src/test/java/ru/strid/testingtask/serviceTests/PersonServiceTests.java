package ru.strid.testingtask.serviceTests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.strid.testingtask.entities.Person;
import ru.strid.testingtask.repositories.EmailRepository;
import ru.strid.testingtask.services.PersonService;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class PersonServiceTests {

    private final Person ADMIN_PERSON = new Person("admin","admin123","Иван","Иванов","Иванович", new Date(84,Calendar.SEPTEMBER,11));

    @Autowired
    private PersonService personService;


    @Test
    @Transactional
    public void test() { // запуск всех тестов
        personDBTest();
        createPersonTest();
        findPersonByEmailTest();
        findPersonByPhoneTest();
    }

    private void personDBTest(){
        final int testPersonId = 1;
        final Person personFromDB = this.personService.find(testPersonId);
        assertTrue(personFromDB.getPersonId() == testPersonId, "Person id is wrong!");
        assertTrue(personFromDB.getLogin().equals(ADMIN_PERSON.getLogin()), "Login is wrong");
        assertTrue(personFromDB.isValidPassword("admin123"), "Password is wrong!");
        assertTrue(personFromDB.getFirstName().equals("Иван"), "First name is wrong!");
        assertTrue(personFromDB.getLastName().equals("Иванов"), "Second name is wrong!");
        assertTrue(personFromDB.getPatronymic().equals("Иванович"), "Patronymic is wrong!");
        assertTrue(personFromDB.getBirthDate().getTime() == ADMIN_PERSON.getBirthDate().getTime(), "Birth date is wrong!");
    }

    private void createPersonTest(){
        Person testPerson = new Person("tester","tester123","Тестер","Тестеров","Тестерович", new Date());
        this.personService.store(testPerson);
        Person testPersonFromDB = this.personService.find("tester");
        assertTrue(testPerson.equals(testPersonFromDB), "Creation of new person is wrong");
        assertTrue((personService.count()==2), "Creation of new person is wrong");
    }

    private void findPersonByEmailTest(){
        Person adminPersonFromDB = personService.findByEmail("admin@mail.ru");
        assertTrue(adminPersonFromDB.equals(ADMIN_PERSON), "Wrong person by email!");
    }

    private void findPersonByPhoneTest(){
        Person adminPersonFromDB = personService.findByPhone("+78005553535");
        assertTrue(adminPersonFromDB.equals(ADMIN_PERSON), "Wrong person by phone!");
    }

}
