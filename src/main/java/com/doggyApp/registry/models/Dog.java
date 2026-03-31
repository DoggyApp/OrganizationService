package com.doggyApp.registry.models;

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



    @OneToMany(mappedBy = "dog")
    @JoinColumn(name = "alert_id")
    private List<Alert> alerts = new ArrayList<>();

    @OneToMany(mappedBy = "dog")
    @JoinColumn(name = "like_id")
    private List<Like> likes = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
    @JoinColumn(name = "vaccine_id")
    private List<Vaccine> vaccines = new ArrayList<>();

    @OneToMany(mappedBy = "dog", cascade = CascadeType.ALL)
    @JoinColumn(name = "note_id")
    private List<Note> notes = new ArrayList<>();


}