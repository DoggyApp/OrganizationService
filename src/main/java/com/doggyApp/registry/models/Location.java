package com.doggyApp.registry.models;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int id;

    @Column(name = "location_name")
    private String name;

    @Column(name = "org_id")
    private int orgId;

    @Column(name = "address")
    private String address;

    @Column(name = "offsite")
    private Boolean offsite;

    public int getId()        { return id; }
    public String getName()   { return name; }
    public int getOrgId()     { return orgId; }
    public String getAddress(){ return address; }
    public Boolean isOffsite(){ return offsite; }

    public void setName(String name)      { this.name = name; }
    public void setOrgId(int orgId)       { this.orgId = orgId; }
    public void setAddress(String address){ this.address = address; }
    public void setOffsite(Boolean o)     { this.offsite = o; }
}