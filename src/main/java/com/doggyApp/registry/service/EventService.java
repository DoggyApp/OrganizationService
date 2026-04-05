package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Dog;
import com.doggyApp.registry.models.Event;
import com.doggyApp.registry.models.Location;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.repo.DogRepo;
import com.doggyApp.registry.repo.EventRepo;
import com.doggyApp.registry.repo.LocationRepo;
import com.doggyApp.registry.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DogRepo dogRepo;

    @Autowired
    private LocationRepo locationRepo;

    // For employees — only their own events
    public List<Event> getByUser(int userId) {
        return eventRepo.findByUsers_Id(userId);
    }

    public List<Event> getByUserAndOrganization(int userId, int orgId) {
        return eventRepo.findByUsers_IdAndUsers_OrganizationId(userId, orgId);
    }

    public List<Event> getByDogAndOrganization(int dogId, int orgId) {
        return eventRepo.findByDogs_IdAndDogs_Organization_Id(dogId, orgId);
    }

    // For org admins — every event across the organization
    public List<Event> getByOrganization(int orgId) {
        return eventRepo.findByOrganizationId(orgId);
    }

    // Returns all events at a specific location, scoped to the requesting org
    public List<Event> getByLocation(int locationId, int orgId) {
        return eventRepo.findByLocation_IdAndLocation_OrgId(locationId, orgId);
    }

    public Event getById(int eventId) {
        return eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    // Updates event fields. Only the user who originally created the event is permitted.
    public Event update(int eventId, Event updates, int userId, int orgId) {
        Event event = eventRepo.findByIdAndCreator_Id(eventId, userId)
                .orElseThrow(() -> new RuntimeException("Event not found or you are not the creator"));

        if (updates.getEvent() != null)       event.setEvent(updates.getEvent());
        if (updates.getDescription() != null) event.setDescription(updates.getDescription());
        if (updates.getStartTime() != null)   event.setStartTime(updates.getStartTime());
        if (updates.getEndTime() != null)     event.setEndTime(updates.getEndTime());

        if (updates.getLocation() != null && updates.getLocation().getId() != 0) {
            event.setLocation(resolveLocation(updates.getLocation().getId(), updates.getOffsiteAddress(), orgId));
        }

        return eventRepo.save(event);
    }

    // User adds themselves to an existing event in their org.
    public Event assignUser(int eventId, int userId, int orgId) {
        Event event = eventRepo.findByIdAndUsers_OrganizationId(eventId, orgId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        event.setUsers(user);
        return eventRepo.save(event);
    }

    // User adds a dog to an existing event — dog must belong to the same org.
    public Event assignDog(int eventId, int dogId, int orgId) {
        Event event = eventRepo.findByIdAndUsers_OrganizationId(eventId, orgId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Dog dog = dogRepo.findByIdAndOrganizationId(dogId, orgId)
                .orElseThrow(() -> new RuntimeException("Dog not found"));

        event.setDogs(dog);
        return eventRepo.save(event);
    }

    // Only the creator may delete their event.
    public void delete(int eventId, int userId) {
        Event event = eventRepo.findByIdAndCreator_Id(eventId, userId)
                .orElseThrow(() -> new RuntimeException("Event not found or you are not the creator"));
        eventRepo.delete(event);
    }

    // Only users can create events. Dogs can be assigned afterwards via assignDog.
    public Event create(Event event, int userId, int orgId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (event.getLocation() == null || event.getLocation().getId() == 0) {
            throw new RuntimeException("A location is required");
        }
        event.setLocation(resolveLocation(event.getLocation().getId(), event.getOffsiteAddress(), orgId));

        event.setUsers(user);
        return eventRepo.save(event);
    }

    // Validates that the location belongs to this org.
    // If the location is on-site, returns it as-is (address is fixed from the org's record).
    // If the location is offsite, creates a new Location row with the user-supplied address
    // so each offsite event has its own address without overwriting shared records.
    private Location resolveLocation(int locationId, String offsiteAddress, int orgId) {
        Location location = locationRepo.findByIdAndOrgId(locationId, orgId)
                .orElseThrow(() -> new RuntimeException("Location not found in your organization"));

        if (!Boolean.TRUE.equals(location.isOffsite())) {
            return location;
        }

        if (offsiteAddress == null || offsiteAddress.isBlank()) {
            throw new RuntimeException("An address is required for offsite locations");
        }

        Location offsite = new Location();
        offsite.setName(location.getName());
        offsite.setOrgId(orgId);
        offsite.setOffsite(true);
        offsite.setAddress(offsiteAddress.trim());
        return locationRepo.save(offsite);
    }
}