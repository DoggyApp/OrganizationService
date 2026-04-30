package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private int id;

    @NotBlank(message = "Dog name must not be blank")
    @Column(name = "dog_name")
    private String name;

    @NotBlank(message = "Breed must not be blank")
    @Column(name = "breed")
    private String breed;

    @NotNull(message = "Birthday is required")
    @Past(message = "Birthday must be in the past")
    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "weight")
    private int weight;

    @Column(name = "image")
    private String image;

    @ManyToMany(mappedBy = "dogs", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Organization> organizations = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"dogs", "friends", "favoriteOrganizations", "hibernateLazyInitializer"})
    private Owner owner;

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaccine> vaccines = new ArrayList<>();

    public int getId()                           { return id; }
    public String getName()                      { return name; }
    public String getBreed()                     { return breed; }
    public LocalDate getBirthday()               { return birthday; }
    public int getWeight()                       { return weight; }
    public String getImage()                     { return image; }
    public Owner getOwner()                      { return owner; }
    public List<Organization> getOrganizations() { return organizations; }
    public List<Alert> getAlerts()               { return alerts; }
    public List<Like> getLikes()                 { return likes; }
    public List<Note> getNotes()                 { return notes; }
    public List<Vaccine> getVaccines()           { return vaccines; }

    public String getAge() {
        if (birthday == null) return "Unknown";
        Period period = Period.between(birthday, LocalDate.now());
        int years  = period.getYears();
        int months = period.getMonths();
        if (years == 0 && months == 0)
            return "Less than 1 month";
        if (years == 0)
            return months + (months == 1 ? " month" : " months");
        if (months == 0)
            return years + (years == 1 ? " year" : " years");
        return years  + (years  == 1 ? " year"  : " years") + ", "
             + months + (months == 1 ? " month" : " months");
    }

    public void setName(String name)         { this.name = name; }
    public void setBreed(String breed)       { this.breed = breed; }
    public void setBirthday(LocalDate b)     { this.birthday = b; }
    public void setWeight(int weight)        { this.weight = weight; }
    public void setImage(String image)       { this.image = image; }
    public void setOwner(Owner owner)        { this.owner = owner; }
}