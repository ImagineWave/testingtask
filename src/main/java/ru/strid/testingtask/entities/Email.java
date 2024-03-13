package ru.strid.testingtask.entities;

import jakarta.persistence.*;

@Entity
public class Email {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer emailId;

    @Column(name = "person_id", nullable = false)
    private Integer personId;

    @Column(name = "email_address", nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "valid")
    private Boolean isValid;

    public Email(){

    }

    public Email(String emailAddress, int personId){
        this.emailAddress = emailAddress;
        this.personId = personId;
        this.isValid = true;
    }

    public Integer getEmailId() {
        return emailId;
    }

    public void setEmailId(Integer emailId) {
        this.emailId = emailId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Email) {
            Email email = (Email) object;
            return (
                    (this.personId == email.personId)
                 && (this.emailAddress.equals(email.emailAddress))
                 && (this.isValid.equals(email.isValid))
            );
        }
        return false;
    }

}
