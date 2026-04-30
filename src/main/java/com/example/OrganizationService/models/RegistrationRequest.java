package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "owner_registration_requests")
public class RegistrationRequest {

    public enum Status { PENDING, ACCEPTED, REJECTED }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"dogs", "friends", "favoriteOrganizations", "password",
                            "email", "phoneNumber", "address", "birthday"})
    private Owner owner;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dog_id")
    @JsonIgnoreProperties({"owner", "vaccines", "alerts", "likes", "notes", "organizations"})
    private Dog dog;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id")
    @JsonIgnoreProperties({"employees", "dogs", "password", "email",
                            "subscriptionStart", "subscriptionExpiration", "reviews", "services"})
    private Organization organization;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public int getId()                      { return id; }
    public Owner getOwner()                 { return owner; }
    public Dog getDog()                     { return dog; }
    public Organization getOrganization()   { return organization; }
    public Status getStatus()               { return status; }
    public LocalDateTime getCreatedAt()     { return createdAt; }

    public void setStatus(Status status)    { this.status = status; }
}
