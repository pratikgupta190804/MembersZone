package com.example.memberszone.repo;

import com.example.memberszone.entity.Admin; // Adjust import if the package name is different
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    Admin findByUsername(String username);
}
