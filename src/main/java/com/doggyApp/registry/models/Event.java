package com.doggyApp.registry.models;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private int id;

    @Column(name = "event")
    private String event;

    @Column(name = "description")
    private String description;

    @Column(name = "start_time")
    private Date startTime;

    @Column(name = "end_time")
    private Date endTime;



    @ManyToOne
    @JoinColumn(name = "trainer")
    private List<User> users = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "dog")
    private List<Dog> dogs = new ArrayList<>();


}