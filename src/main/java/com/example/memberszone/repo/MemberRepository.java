package com.example.memberszone.repo;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    // Find members by gym ID

	 List<Member> findByGymId(Admin gymId);
    // Find members by membership status (active/inactive)
    List<Member> findByMembershipStatus(String membershipStatus);

    // Find members by plan name
    List<Member> findByPlanName(String planName);

    // Find members by fees status (paid or pending)
    List<Member> findByFeesStatus(String feesStatus);
    
    // Find members by gym ID and membership status
    List<Member> findByGymIdAndMembershipStatus(Admin gymId, String membershipStatus);
}
