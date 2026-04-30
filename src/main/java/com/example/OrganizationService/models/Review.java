package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;

@Entity
@Table(name = "reviews")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;

    @Column(name = "rating")
    private int rating;

    @Column(name = "comment")
    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    @JsonIgnoreProperties({"dogs", "friends", "favoriteOrganizations", "password",
                            "address", "birthday", "latitude", "longitude", "phoneNumber"})
    private Owner owner;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public int getId()                    { return id; }
    public int getRating()                { return rating; }
    public String getComment()            { return comment; }
    public Owner getOwner()               { return owner; }
    public Organization getOrganization() { return organization; }

    public void setRating(int rating)          { this.rating = rating; }
    public void setComment(String comment)     { this.comment = comment; }
    public void setOwner(Owner owner)          { this.owner = owner; }
    public void setOrganization(Organization o){ this.organization = o; }
}