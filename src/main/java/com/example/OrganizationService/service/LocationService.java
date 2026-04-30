package com.example.OrganizationService.service;

import com.example.OrganizationService.models.Location;
import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.repo.EventRepo;
import com.example.OrganizationService.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private EventRepo eventRepo;

    public List<Location> getByOrganization(int orgId) {
        return locationRepo.findByOrgId(orgId);
    }

    public Location add(String name, String address, Organization org, Double latitude, Double longitude) {
        Location location = new Location();
        location.setName(name);
        location.setAddress(address);
        location.setOrganization(org);
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        return locationRepo.save(location);
    }

    @Transactional
    public void delete(int locationId, int orgId) {
        Location location = locationRepo.findByIdAndOrgId(locationId, orgId)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        eventRepo.clearLocation(locationId);
        locationRepo.delete(location);
    }
}