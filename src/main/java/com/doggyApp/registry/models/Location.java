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

    public int getId()     { return id; }
    public String getName() { return name; }
    public int getOrgId()  { return orgId; }

    public void setName(String name)   { this.name = name; }
    public void setOrgId(int orgId)    { this.orgId = orgId; }
}