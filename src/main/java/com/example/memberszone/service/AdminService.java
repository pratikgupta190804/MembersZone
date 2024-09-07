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
		Admin admin = new Admin(adminDto.getUsername(), passwordEncoder.encode(adminDto.getPassword()), // Encode
																										// password
				adminDto.getFirstName(), adminDto.getLastName(), adminDto.getEmail(), adminDto.getPhoneNumber(),
				adminDto.getGymName(), adminDto.getGymAddress());
		try {
			adminRepository.save(admin);
		} catch (Exception e) {
			// Log the exception or handle it as needed
			e.printStackTrace();
		}
	}

	// Validate password and return AdminDto if valid
	public AdminDto validatePassword(String username, String password) {
		Admin admin = adminRepository.findByUsername(username);
		if (admin != null && passwordEncoder.matches(password, admin.getPassword())) {
			// Convert Admin entity to AdminDto
			AdminDto adminDto = new AdminDto();
			adminDto.setUsername(admin.getUsername());
			adminDto.setFirstName(admin.getFirstName());
			adminDto.setLastName(admin.getLastName());
			adminDto.setEmail(admin.getEmail());
			adminDto.setPhoneNumber(admin.getPhoneNumber());
			adminDto.setGymName(admin.getGymName());
			adminDto.setGymAddress(admin.getGymAddress());
			adminDto.setGymId(admin.getId());
			return adminDto; // Return the DTO if the password matches
		}
		return null; // Return null if invalid credentials
	}
}
