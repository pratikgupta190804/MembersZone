package com.example.memberszone.repo;

import com.example.memberszone.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    // You can define custom query methods here if needed
    // For example:
    // Optional<Trainer> findByEmail(String email);
}
