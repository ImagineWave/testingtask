package ru.strid.testingtask.entities;

import jakarta.persistence.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import java.util.Date;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer personId;

    @Column(name = "login", length = 128, nullable = false, unique = true)
    private String login;

    @Column(name = "pass_hash", length = 256, nullable = false)// md5 32 символа
    private String password;

    @Column(name = "first_name", length = 128, nullable = false)
    private String firstName;

    @Column(name = "last_name", length = 128, nullable = false)
    private String lastName;

    @Column(name = "patronymic", length = 128, nullable = true)
    private String patronymic;

    @Column(name = "birth_date", nullable = false)
    private Date birthDate;

    public Person(String login, String password, String firstName, String lastName, String patronymic, Date birthDate){
        this.personId = null;
        this.login = login;
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
    }

    public Person(){

    }


    public long getPersonId() {
        return personId;
    }

    public void setPersonId(int id) {
        this.personId = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public boolean isValidPassword(final String password) {
        return BCrypt.checkpw(password, this.password);
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public String getPassword(){
        return this.password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
    @Override
    public boolean equals(Object object) {
        if (object instanceof Person) {
            Person person = (Person) object;
            return (
                    (this.login.equals(person.login))
                 && (this.firstName.equals(person.firstName))
                 && (this.lastName.equals(person.lastName))
                 && (this.patronymic.equals(person.patronymic))
                 && (this.birthDate.getTime() == person.birthDate.getTime())
            );
        }
        return false;
    }


}
