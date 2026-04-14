package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

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

    public int getId()           { return id; }
    public String getName()      { return name; }
    public Location getLocation(){ return location; }

    public void setName(String name)         { this.name = name; }
    public void setLocation(Location l)      { this.location = l; }
}