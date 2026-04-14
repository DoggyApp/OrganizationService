package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dogs")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dog_id")
    private int id;

    @Column(name = "dog_name")
    private String name;

    @Column(name="breed")
    private String breed;

    @Column(name="age")
    private int age;

    @Column(name="weight")
    private int number;

    @Column(name = "image")
    private String image;




    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"dogs", "hibernateLazyInitializer"})
    private Owner owner;


    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Note> notes = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Vaccine> vaccines = new ArrayList<>();

    public int getId()                  { return id; }
    public String getName()             { return name; }
    public String getBreed()            { return breed; }
    public int getAge()                 { return age; }
    public int getWeight()              { return number; }
    public String getImage()            { return image; }
    public Owner getOwner()              { return owner; }
    public List<Alert> getAlerts()      { return alerts; }
    public List<Like> getLikes()        { return likes; }
    public List<Note> getNotes()        { return notes; }
    public List<Vaccine> getVaccines()  { return vaccines; }

    public void setName(String name)              { this.name = name; }
    public void setBreed(String breed)            { this.breed = breed; }
    public void setAge(int age)                   { this.age = age; }
    public void setWeight(int weight)             { this.number = weight; }
    public void setImage(String image)            { this.image = image; }
    public void setOrganization(Organization org) { this.organization = org; }
    public void setOwner(Owner owner)             { this.owner = owner; }
}