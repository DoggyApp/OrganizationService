package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Event;
import com.doggyApp.registry.repo.EventRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRepo eventRepo;

    // For employees — only their own events
    public List<Event> getByUser(int userId) {
        return eventRepo.findByUsers_Id(userId);
    }

    // For org admins — every event across the organization
    public List<Event> getByOrganization(int orgId) {
        return eventRepo.findByOrganizationId(orgId);
    }
}