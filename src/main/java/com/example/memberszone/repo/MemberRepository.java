package com.example.memberszone.repo;

import com.example.memberszone.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    // Custom query to find members by gym id (Admin ID)
    List<Member> findByGym_Id(Long gymId);

    // Custom query to find members by email
    Member findByEmail(String email);
}
