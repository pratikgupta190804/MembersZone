package com.example.memberszone.service;

import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.MembershipPlan;
import com.example.memberszone.repo.AdminRepository; // Import your Admin repository
import com.example.memberszone.repo.MembershipPlanRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MembershipPlanService {

    @Autowired
    private MembershipPlanRepository membershipPlanRepository;

    @Autowired
    private AdminRepository adminRepository; // Add this to fetch Admin entities

    public void saveMembershipPlan(MembershipPlanDto membershipPlanDto) {
        MembershipPlan membershipPlan = new MembershipPlan();
        membershipPlan.setPlanName(membershipPlanDto.getPlanName());
        membershipPlan.setDurationInMonths(membershipPlanDto.getDurationInMonths());
        membershipPlan.setPrice(membershipPlanDto.getPrice());

        // Fetch the Admin entity using gymId
        Admin gym = adminRepository.findById(membershipPlanDto.getGymId())
                                   .orElseThrow(() -> new RuntimeException("Gym not found"));

        membershipPlan.setGym(gym);

        membershipPlanRepository.save(membershipPlan);
    }

    public List<MembershipPlanDto> getPlansByGymId(Long gymId) {
        List<MembershipPlan> membershipPlans = membershipPlanRepository.findByGymId(gymId);
        return membershipPlans.stream().map(plan -> new MembershipPlanDto(
                plan.getId(),
                plan.getPlanName(),
                plan.getDurationInMonths(),
                plan.getPrice(),
                plan.getGym().getId()
        )).toList();
    }
}
