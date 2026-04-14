package com.example.OrganizationService.models;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "org_id")
    private int orgId;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    public int getId()           { return id; }
    public String getName()      { return name; }
    public int getOrgId()        { return orgId; }
    public String getAddress()   { return address; }
    public Double getLatitude()  { return latitude; }
    public Double getLongitude() { return longitude; }

    public void setName(String name)           { this.name = name; }
    public void setOrgId(int orgId)            { this.orgId = orgId; }
    public void setAddress(String address)     { this.address = address; }
    public void setLatitude(Double latitude)   { this.latitude = latitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}