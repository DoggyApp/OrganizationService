package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

    @JsonIgnore
    @NotBlank
    @Column(length = 100)
    private String password;

    public int getId()       { return id; }
    public String getName()  { return name; }
    public String getEmail() { return email; }
    public String getPassword()  { return password; }
    public void setPassword(String password) { this.password = password; }

//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
//    private Set<Dog> dogs;
//
//    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL)
//    private Set<User> users;
}
