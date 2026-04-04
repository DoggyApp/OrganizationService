package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "vaccines")
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vaccine_id")
    private int id;

    @Column(name = "vaccine_name")
    private String name;

    // Date the vaccine was last administered — null means not yet given
    @Column(name = "vaccinated_date")
    private LocalDate vaccinatedDate;

    // Calculated renewal due date — null means not yet given
    @Column(name = "expiration_date")
    private LocalDate expirationDate;

    // True for Bordetella and Rabies — prevents deletion
    @Column(name = "standard")
    private boolean standard;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    public int getId()                    { return id; }
    public String getName()               { return name; }
    public LocalDate getVaccinatedDate()  { return vaccinatedDate; }
    public LocalDate getExpirationDate()  { return expirationDate; }
    public boolean isStandard()           { return standard; }

    public void setName(String name)                    { this.name = name; }
    public void setVaccinatedDate(LocalDate date)       { this.vaccinatedDate = date; }
    public void setExpirationDate(LocalDate date)       { this.expirationDate = date; }
    public void setStandard(boolean standard)           { this.standard = standard; }
    public void setDog(Dog dog)                         { this.dog = dog; }
}