package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;

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

    // FK projection — lets the frontend know the location ID without triggering a lazy load
    @Column(name = "location_id", insertable = false, updatable = false)
    private Integer locationId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location location;

    // Not persisted — only used during event creation/update when location is offsite.
    // The address is stored on the new Location row created for that offsite event.
    @Transient
    private String offsiteAddress;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;



    // FK projections — lets Angular know which user/dog without triggering lazy load
    @Column(name = "users_user_id", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "dogs_dog_id", insertable = false, updatable = false)
    private Integer dogId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User users;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Dog dogs;

    // Tracks who originally created the event — used for edit authorization
    @Column(name = "creator_user_id", insertable = false, updatable = false)
    private Integer creatorId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id")
    private User creator;

    public int getId()                  { return id; }
    public String getEvent()            { return event; }
    public String getDescription()      { return description; }
    public String getOffsiteAddress()    { return offsiteAddress; }
    public Integer getLocationId()       { return locationId; }
    public Location getLocation()        { return location; }
    public LocalDateTime getStartTime() { return startTime; }
    public LocalDateTime getEndTime()   { return endTime; }
    public Integer getUserId()          { return userId; }
    public Integer getCreatorId()       { return creatorId; }
    public Integer getDogId()           { return dogId; }

    public void setEvent(String event)             { this.event = event; }
    public void setDescription(String description) { this.description = description; }
    public void setOffsiteAddress(String a)        { this.offsiteAddress = a; }
    public void setLocation(Location location)     { this.location = location; }
    public void setStartTime(LocalDateTime t)      { this.startTime = t; }
    public void setEndTime(LocalDateTime t)        { this.endTime = t; }
    public void setUsers(User user)                { this.users = user; }
    public void setDogs(Dog dog)                   { this.dogs = dog; }
    public void setCreator(User creator)           { this.creator = creator; }
}