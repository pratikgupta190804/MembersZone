package com.example.memberszone.service;

import com.example.memberszone.dto.AdminDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.repo.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void saveAdmin(AdminDto adminDto) {
        Admin admin = new Admin(
            adminDto.getUsername(),
            passwordEncoder.encode(adminDto.getPassword()), // Encode password
            adminDto.getFirstName(),
            adminDto.getLastName(),
            adminDto.getEmail(),
            adminDto.getPhoneNumber(),
            adminDto.getGymName(),
            adminDto.getGymAddress()
        );
        try {
            adminRepository.save(admin);
        } catch (Exception e) {
            // Log the exception or handle it as needed
            e.printStackTrace();
        }
    }

    public boolean validatePassword(String username, String rawPassword) {
        Admin admin = adminRepository.findByUsername(username);
        if (admin != null) {
            return passwordEncoder.matches(rawPassword, admin.getPassword()); // Check password
        }
        return false;
    }
}
