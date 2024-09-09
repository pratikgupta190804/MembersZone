package com.example.memberszone.repo;

import com.example.memberszone.entity.MembershipPlan;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

	// Custom queries can be added here if needed
	List<MembershipPlan> findByGymId(Long gymId);


}
