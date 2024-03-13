package ru.strid.testingtask.entities;

import jakarta.persistence.*;

@Entity
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer phoneId;

    @Column(name = "person_id", nullable = false)
    private Integer personId;

    @Column(name = "phone_number", nullable = false, unique = true)
    private String phoneNumber;

    @Column(name = "valid")
    private Boolean isValid;

    public Phone(){

    }

    public Phone(String phoneNumber, int personId) {
        this.phoneNumber = phoneNumber;
        this.personId = personId;
        this.isValid = true;
    }

    public long getPhoneId() {
        return phoneId;
    }

    public void setPhoneId(Integer phoneId) {
        this.phoneId = phoneId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getValid() {
        return isValid;
    }

    public void setValid(Boolean valid) {
        isValid = valid;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Phone) {
            Phone phone = (Phone) object;
            return (
                    (this.personId == phone.personId)
                 && (this.phoneNumber.equals(phone.phoneNumber))
                 && (this.isValid.equals(phone.isValid))
            );
        }
        return false;
    }
    
}
