package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private int id;

    @Column(name = "like_text")
    private String like;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public int getId()      { return id; }
    public String getLike() { return like; }

    public void setLike(String like) { this.like = like; }
    public void setDog(Dog dog)      { this.dog = dog; }
}
