package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "organizations")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "organization_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email required")
    @Column(name = "email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Column(length = 100)
    private String password;

    @Column(name = "subscription_start")
    private LocalDate subscriptionStart;

    @Column(name = "subscription_expiration")
    private LocalDate subscriptionExpiration;

    public int getId()                          { return id; }
    public String getName()                     { return name; }
    public String getEmail()                    { return email; }
    public String getPassword()                 { return password; }
    public LocalDate getSubscriptionStart()     { return subscriptionStart; }
    public LocalDate getSubscriptionExpiration(){ return subscriptionExpiration; }

    public void setName(String name)                              { this.name = name; }
    public void setEmail(String email)                            { this.email = email; }
    public void setPassword(String password)                      { this.password = password; }
    public void setSubscriptionStart(LocalDate date)              { this.subscriptionStart = date; }
    public void setSubscriptionExpiration(LocalDate date)         { this.subscriptionExpiration = date; }

//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
//    private Set<Dog> dogs;
//
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
//    private Set<User> users;
}
