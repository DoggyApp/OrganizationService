package com.example.OrganizationService.models;

import com.example.OrganizationService.validation.ValidPhone;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "owners")
public class Owner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "handle", unique = true)
    private String handle;

    @Email
    @Column(name = "email")
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(length = 100)
    private String password;

    @Column(name = "phone_number")
    @ValidPhone(region = "US")
    private String phoneNumber;

    @Column(name = "address")
    private String address;

    @Column(name = "birthday")
    private LocalDate birthday;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @PrePersist
    @PreUpdate
    public void normalizePhone() {
        try {
            var util = PhoneNumberUtil.getInstance();
            var number = util.parse(this.phoneNumber, "US");
            this.phoneNumber = util.format(number, PhoneNumberUtil.PhoneNumberFormat.E164);
        } catch (Exception ignored) {
        }
    }

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"owner"})
    private List<Dog> dogs = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "owner_friends",
        joinColumns = @JoinColumn(name = "owner_id"),
        inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    @JsonIgnoreProperties({"friends", "favoriteOrganizations", "dogs"})
    private List<Owner> friends = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "owner_favorite_organizations",
        joinColumns = @JoinColumn(name = "owner_id"),
        inverseJoinColumns = @JoinColumn(name = "organization_id")
    )
    @JsonIgnoreProperties({"employees", "dogs", "services", "reviews", "password", "email", "subscriptionStart", "subscriptionExpiration"})
    private List<Organization> favoriteOrganizations = new ArrayList<>();

    public int getId()                          { return id; }
    public String getFirstName()                { return firstName; }
    public String getLastName()                 { return lastName; }
    public String getHandle()                   { return handle; }
    public String getEmail()                    { return email; }
    public String getPassword()                 { return password; }
    public String getPhoneNumber()              { return phoneNumber; }
    public String getAddress()                  { return address; }
    public LocalDate getBirthday()              { return birthday; }
    public Double getLatitude()                 { return latitude; }
    public Double getLongitude()                { return longitude; }
    public List<Dog> getDogs()                  { return dogs; }
    public List<Owner> getFriends()             { return friends; }
    public List<Organization> getFavoriteOrganizations() { return favoriteOrganizations; }

    public void setFirstName(String firstName)              { this.firstName = firstName; }
    public void setLastName(String lastName)                { this.lastName = lastName; }
    public void setHandle(String handle)                    { this.handle = handle; }
    public void setEmail(String email)                      { this.email = email; }
    public void setPassword(String password)                { this.password = password; }
    public void setPhoneNumber(String phoneNumber)          { this.phoneNumber = phoneNumber; }
    public void setAddress(String address)                  { this.address = address; }
    public void setBirthday(LocalDate birthday)   { this.birthday = birthday; }
    public void setLatitude(Double latitude)      { this.latitude = latitude; }
    public void setLongitude(Double longitude)    { this.longitude = longitude; }
}
