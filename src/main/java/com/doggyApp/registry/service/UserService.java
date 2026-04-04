package com.doggyApp.registry.service;

import com.doggyApp.registry.models.Organization;
import com.doggyApp.registry.models.User;
import com.doggyApp.registry.repo.EventRepo;
import com.doggyApp.registry.repo.OrganizationRepo;
import com.doggyApp.registry.repo.UserRepo;
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

    public User login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }

    // User can change their own password — must provide the correct current password first.
    public void changePassword(int userId, String oldPassword, String newPassword) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
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

    // Only the org can call this — orgId comes from the session, not the request body.
    public User create(User user, int orgId) {
        Organization org = organizationRepo.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organization not found"));

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setOrganization(org);
        return userRepo.save(user);
    }
}