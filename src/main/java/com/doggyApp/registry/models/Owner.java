package com.doggyApp.registry.models;

import com.doggyApp.registry.validation.ValidPhone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ower_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    @ValidPhone(region = "US")
    private String phoneNumber;

    @PrePersist
    @PreUpdate
    public void normalizePhone() throws Exception {
        try {
            var util = PhoneNumberUtil.getInstance();
            var number = util.parse(this.phoneNumber, "US");

            this.phoneNumber =
                    util.format(number,
                            PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception ignored) {

        }
    }




    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"owner"})
    private List<Dog> dogs = new ArrayList<>();

    public int getId()              { return id; }
    public String getName()         { return name; }
    public String getEmail()        { return email; }
    public String getPhoneNumber()  { return phoneNumber; }

    public List<Dog> getDogs()                     { return dogs; }

    public void setName(String name)               { this.name = name; }
    public void setEmail(String email)             { this.email = email; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
}
