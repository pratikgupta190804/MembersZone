package com.example.memberszone.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.Member;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

	List<Member> findByNameContainingIgnoreCase(String name);

	// Find members by gymId
	List<Member> findByGymId(Admin gymId);

	// Find members by membership status (active or inactive)
	List<Member> findByMembershipStatus(Member.MembershipStatus membershipStatus);

	// Find members by fees status (paid or pending)
	List<Member> findByFeesStatus(Member.FeesStatus feesStatus);

	// Find members by plan name
	List<Member> findByPlanName(String planName);

	// Additional custom queries can be added as needed
}
