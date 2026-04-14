package com.example.OrganizationService.service;

import com.example.OrganizationService.models.Organization;
import com.example.OrganizationService.models.User;
import com.example.OrganizationService.repo.EventRepo;
import com.example.OrganizationService.repo.OrganizationRepo;
import com.example.OrganizationService.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private OrganizationRepo organizationRepo;

    @Autowired
    private EventRepo eventRepo;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // Only the org can call this — orgId comes from the session, not the request body.
    public User create(User user, int orgId) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOrganization(org);
        return userRepo.save(user);
    }

    // Only the org can delete users. Cascades: deletes their created events and
    // nullifies their assignee reference on any events they were assigned to.
    @Transactional
    public void delete(int userId, int orgId) {
        User user = userRepo.findByIdAndOrganizationId(userId, orgId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        eventRepo.clearAssignedUser(userId);
        eventRepo.deleteByCreator_Id(userId);
        userRepo.delete(user);
    }
}
