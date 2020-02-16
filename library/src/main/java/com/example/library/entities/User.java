package com.example.library.entities;

import androidx.annotation.Keep;

import com.example.library.enums.Gender;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.ServerTimestamp;

@Keep
public class User {

    @DocumentId
    private String uid;
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;
    private Gender gender;
    private String primaryPhoneNumber;
    private Address address;
    private String dateOfBirth;
    private Integer age;
    @ServerTimestamp
    private Timestamp registrationTime;

    public User() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    private void setFullName() {
        fullName = "";
        if (firstName != null && firstName.length() > 0)
            fullName += firstName;
        if (middleName != null && middleName.length() > 0)
            fullName += " " + middleName;
        if (lastName != null && lastName.length() > 0)
            fullName += " " + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setFullName();
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
        setFullName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        setFullName();
    }

    public String getPrimaryPhoneNumber() {
        return primaryPhoneNumber;
    }

    public void setPrimaryPhoneNumber(String primaryPhoneNumber) {
        this.primaryPhoneNumber = primaryPhoneNumber;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Timestamp getRegistrationTime() {
        return registrationTime;
    }

    public void setRegistrationTime(Timestamp registrationTime) {
        this.registrationTime = registrationTime;
    }

    // helper methods

    @Exclude
    public String getPrimaryPhoneNumberWithoutCode() {
        if (primaryPhoneNumber == null)
            return null;
        return primaryPhoneNumber.substring(primaryPhoneNumber.length() - 10);
    }
}
