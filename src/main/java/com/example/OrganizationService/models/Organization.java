package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Column(length = 100)
    private String password;

    @Column(name = "title")
    private String title;

    @Column(name = "subscription_start")
    private LocalDate subscriptionStart;

    @Column(name = "subscription_expiration")
    private LocalDate subscriptionExpiration;

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"organization", "password"})
    private List<User> employees = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(
        name = "organization_dogs",
        joinColumns = @JoinColumn(name = "organization_id"),
        inverseJoinColumns = @JoinColumn(name = "dog_id")
    )
    @JsonIgnoreProperties({"organizations", "alerts", "likes", "notes", "vaccines", "owner"})
    private List<Dog> dogs = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("organization")
    private List<Location> locations = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Service> services = new ArrayList<>();

    @OneToMany(mappedBy = "organization", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();

    public int getId()                          { return id; }
    public String getName()                     { return name; }
    public String getEmail()                    { return email; }
    public String getPassword()                 { return password; }
    public String getTitle()                    { return title; }
    public LocalDate getSubscriptionStart()     { return subscriptionStart; }
    public LocalDate getSubscriptionExpiration(){ return subscriptionExpiration; }
    public List<User> getEmployees()            { return employees; }
    public List<Dog> getDogs()                  { return dogs; }
    public List<Location> getLocations()        { return locations; }
    public List<Service> getServices()          { return services; }
    public List<Review> getReviews()            { return reviews; }

    public void setName(String name)                        { this.name = name; }
    public void setEmail(String email)                      { this.email = email; }
    public void setPassword(String password)                { this.password = password; }
    public void setTitle(String title)                      { this.title = title; }
    public void setSubscriptionStart(LocalDate date)        { this.subscriptionStart = date; }
    public void setSubscriptionExpiration(LocalDate date)   { this.subscriptionExpiration = date; }
    public void setDogs(List<Dog> dogs)                     { this.dogs = dogs; }
}