package com.example.memberszone.repo;

import com.example.memberszone.entity.Trainer;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

	List<Trainer> findByGymId(Long gymId);
	 Optional<Trainer> findByName(String name);
}
