package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    @JsonIgnoreProperties({"rooms"})
    private Location location;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("room")
    private List<Event> events = new ArrayList<>();

    public int getId()             { return id; }
    public String getName()        { return name; }
    public Location getLocation()  { return location; }
    public List<Event> getEvents() { return events; }

    public void setName(String name)     { this.name = name; }
    public void setLocation(Location l)  { this.location = l; }
}