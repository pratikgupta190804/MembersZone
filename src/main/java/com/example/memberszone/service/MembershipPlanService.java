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
		return membershipPlans.stream().map(plan -> new MembershipPlanDto(plan.getId(), plan.getPlanName(),
				plan.getDurationInMonths(), plan.getPrice(), plan.getGym().getId())).toList();
	}

	// Assuming you have this method
	// Used to delete or update a membership plan
	public MembershipPlanDto getPlanById(Long id) {
		MembershipPlan membershipPlan = membershipPlanRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Plan not found"));
		return new MembershipPlanDto(membershipPlan.getId(), membershipPlan.getPlanName(),
				membershipPlan.getDurationInMonths(), membershipPlan.getPrice(), membershipPlan.getGym().getId());
	}

	// New method to delete a plan
	public void deletePlan(Long id) {
		membershipPlanRepository.deleteById(id);
	}
	
	public void updatePlan(MembershipPlanDto membershipPlanDto) {
	    MembershipPlan existingPlan = membershipPlanRepository.findById(membershipPlanDto.getId())
	        .orElseThrow(() -> new RuntimeException("Plan not found"));

	    existingPlan.setPlanName(membershipPlanDto.getPlanName());
	    existingPlan.setDurationInMonths(membershipPlanDto.getDurationInMonths());
	    existingPlan.setPrice(membershipPlanDto.getPrice());
	    membershipPlanRepository.save(existingPlan);
	}

}