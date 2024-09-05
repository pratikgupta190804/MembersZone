package com.example.memberszone.service;

import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.Member;
import com.example.memberszone.repo.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberService(com.example.memberszone.repo.MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // Method to add a new member to the database
    public Member addMember(String firstName, String lastName, String email, String phoneNumber, 
                            String address, Date joinDate, Long gymId) {
        // Create a new Member object
        Member member = new Member();
        member.setFirstName(firstName);
        member.setLastName(lastName);
        member.setEmail(email);
        member.setPhoneNumber(phoneNumber);
        member.setAddress(address);
        member.setJoinDate(joinDate);

        // Set the gym using the gymId (assuming the gym has already been fetched and assigned)
        Admin gym = new Admin();
        gym.setId(gymId);  // only the ID is required here
        member.setGym(gym);

        // Save the new member
        return memberRepository.save(member);
    }

    // Method to retrieve all members by gym ID
    public List<Member> getMembersByGym(Long gymId) {
        return memberRepository.findByGym_Id(gymId);
    }

    // Method to get a member by their email
    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email);
    }
}
