package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;
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

    // Null when the event is offsite. Join room → location to get the address.
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    @JsonIgnoreProperties({"location"})
    private Room room;

    // Only populated when room is null (offsite event).
    // Service checks: room == null → use this address; otherwise derive from room.location.
    @Column(name = "address")
    private String address;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @ManyToMany
    @JoinTable(
        name = "event_attendees",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> attendees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "event_owner_attendees",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "owner_id")
    )
    private List<Owner> ownerAttendees = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "event_dogs",
        joinColumns = @JoinColumn(name = "event_id"),
        inverseJoinColumns = @JoinColumn(name = "dog_id")
    )
    private List<Dog> dogs = new ArrayList<>();

    @Column(name = "creator_user_id", insertable = false, updatable = false)
    private Integer creatorId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_user_id")
    @JsonIgnoreProperties({"organization", "password"})
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_creator_id")
    @JsonIgnoreProperties({"dogs", "friends", "favoriteOrganizations", "password"})
    private Owner ownerCreator;

    public int getId()                    { return id; }
    public String getEvent()              { return event; }
    public String getDescription()        { return description; }
    public Room getRoom()                 { return room; }
    public String getAddress()            { return address; }
    public LocalDateTime getStartTime()   { return startTime; }
    public LocalDateTime getEndTime()     { return endTime; }
    public List<User> getAttendees()      { return attendees; }
    public List<Owner> getOwnerAttendees(){ return ownerAttendees; }
    public List<Dog> getDogs()            { return dogs; }
    public Integer getCreatorId()         { return creatorId; }
    public User getCreator()              { return creator; }
    public Owner getOwnerCreator()        { return ownerCreator; }

    // Convenience: returns the resolved address regardless of onsite/offsite.
    // Offsite: address field. Onsite: room → location → address.
    public String getResolvedAddress() {
        if (room == null) return address;
        if (room.getLocation() != null) return room.getLocation().getAddress();
        return null;
    }

    public void setEvent(String event)              { this.event = event; }
    public void setDescription(String description)  { this.description = description; }
    public void setRoom(Room room)                  { this.room = room; }
    public void setAddress(String address)          { this.address = address; }
    public void setStartTime(LocalDateTime t)       { this.startTime = t; }
    public void setEndTime(LocalDateTime t)         { this.endTime = t; }
    public void setAttendees(List<User> attendees)  { this.attendees = attendees; }
    public void setOwnerAttendees(List<Owner> o)    { this.ownerAttendees = o; }
    public void setDogs(List<Dog> dogs)             { this.dogs = dogs; }
    public void setCreator(User creator)            { this.creator = creator; }
    public void setOwnerCreator(Owner o)            { this.ownerCreator = o; }
}
