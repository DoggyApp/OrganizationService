package com.doggyApp.registry.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email required")
    @Column(name = "email")
    private String email;

    @JsonIgnore
    @NotBlank
    @Size(min = 8, max = 20)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
            message = "Password must contain upper, lower, number, and special character"
    )
    @Column
    private String password;

    // Exposes the FK value directly so we can read the org ID without triggering lazy load
    @Column(name = "organization_id", insertable = false, updatable = false)
    private int organizationId;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id")
    private Organization organization;

    public int getId()             { return id; }
    public String getFirstName()   { return firstName; }
    public String getLastName()    { return lastName; }
    public String getEmail()       { return email; }
    public int getOrganizationId() { return organizationId; }
    String getPassword()           { return password; }
}