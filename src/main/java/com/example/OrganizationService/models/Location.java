package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "org_id")
    @JsonIgnoreProperties({"employees", "dogs", "services", "reviews", "locations", "password",
                            "subscriptionStart", "subscriptionExpiration", "hibernateLazyInitializer"})
    private Organization organization;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("location")
    private List<Room> rooms = new ArrayList<>();

    public int getId()                       { return id; }
    public String getName()                  { return name; }
    public Organization getOrganization()    { return organization; }
    public String getAddress()               { return address; }
    public Double getLatitude()              { return latitude; }
    public Double getLongitude()             { return longitude; }
    public List<Room> getRooms()             { return rooms; }

    public void setName(String name)                       { this.name = name; }
    public void setOrganization(Organization organization) { this.organization = organization; }
    public void setAddress(String address)                 { this.address = address; }
    public void setLatitude(Double latitude)               { this.latitude = latitude; }
    public void setLongitude(Double longitude)             { this.longitude = longitude; }
}