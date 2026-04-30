package com.example.OrganizationService.service;

import com.example.OrganizationService.models.Dog;
import com.example.OrganizationService.models.Event;
import com.example.OrganizationService.models.Location;
import com.example.OrganizationService.models.User;
import com.example.OrganizationService.repo.DogRepo;
import com.example.OrganizationService.repo.EventRepo;
import com.example.OrganizationService.repo.LocationRepo;
import com.example.OrganizationService.repo.UserRepo;
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

    public List<Event> getByUserAndOrganization(int userId, int orgId) {
        return eventRepo.findByAttendees_IdAndAttendees_OrganizationId(userId, orgId);
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
}