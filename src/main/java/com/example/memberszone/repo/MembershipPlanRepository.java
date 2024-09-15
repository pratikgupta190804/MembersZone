package com.example.memberszone.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.memberszone.entity.MembershipPlan;

@Repository
public interface MembershipPlanRepository extends JpaRepository<MembershipPlan, Long> {

	// Custom queries can be added here if needed
	List<MembershipPlan> findByGymId(Long gymId);

	MembershipPlan findByPlanName(String planName);

	Optional<MembershipPlan> findByGymIdAndPlanName(Long gymId, String planName);

}
