package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Location;
import com.doggyApp.registry.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepo;

    public List<Location> getByOrganization(int orgId) {
        return locationRepo.findByOrgId(orgId);
    }

    public Location add(String name, int orgId) {
        Location location = new Location();
        location.setName(name);
        location.setOrgId(orgId);
        return locationRepo.save(location);
    }

    public void delete(int locationId, int orgId) {
        Location location = locationRepo.findByIdAndOrgId(locationId, orgId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        locationRepo.delete(location);
    }
}