package com.example.OrganizationService.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotBlank(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email required")
    @Column(name = "email", unique = true)
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotBlank
    @Column(length = 100)
    private String password;

    // Exposes the FK value directly so we can read the org ID without triggering lazy load
    @Column(name = "organization_id", insertable = false, updatable = false)
    private int organizationId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Event> createdEvents = new ArrayList<>();

    public int getId()             { return id; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public int getOrganizationId() { return organizationId; }
    public String getPassword()    { return password; }

    public void setFirstName(String firstName)       { this.firstName = firstName; }
    public void setLastName(String lastName)         { this.lastName = lastName; }
    public void setEmail(String email)               { this.email = email; }
    public void setPassword(String password)         { this.password = password; }
    public void setOrganization(Organization org)    { this.organization = org; }
}