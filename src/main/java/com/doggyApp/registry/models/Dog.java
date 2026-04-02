package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

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

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public int getId()       { return id; }
    public String getName()  { return name; }
    public String getBreed() { return breed; }
    public int getAge()      { return age; }
    public int getWeight()   { return number; }
    public String getImage() { return image; }

//
//    @OneToMany(mappedBy = "dog")
//    private Set<Alert> alerts;
//
//    @OneToMany(mappedBy = "dog")
//    private Set<Like> likes;
//
//    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
//    private Set<Vaccine> vaccines;
//
//    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
//    private Set<Note> notes;


}