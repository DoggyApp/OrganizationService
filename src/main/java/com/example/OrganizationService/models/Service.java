package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "services")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties({"employees", "dogs", "services", "reviews", "password"})
    private Organization organization;

    public int getId()                   { return id; }
    public String getName()              { return name; }
    public Organization getOrganization(){ return organization; }

    public void setName(String name)               { this.name = name; }
    public void setOrganization(Organization o)    { this.organization = o; }
}