package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alert_id")
    private int id;

    @Column(name = "alert")
    private String alert;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public int getId()       { return id; }
    public String getAlert() { return alert; }

    public void setAlert(String alert) { this.alert = alert; }
    public void setDog(Dog dog)        { this.dog = dog; }
}
