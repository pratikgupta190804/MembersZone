package com.example.memberszone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memberszone.dto.MemberDto;
import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.Member;
import com.example.memberszone.entity.MembershipPlan;
import com.example.memberszone.repo.AdminRepository;
import com.example.memberszone.repo.MemberRepository;
import com.example.memberszone.repo.MembershipPlanRepository;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private MembershipPlanRepository membershipPlanRepository; // Assuming you have this repository

	// Add a member by calculating the plan duration using the gymId
	public void addMember(MemberDto memberDto) {
		Member member = new Member();

		// Set the basic fields from DTO
		member.setName(memberDto.getName());
		member.setEmail(memberDto.getEmail());
		member.setPhone(memberDto.getPhone());
		member.setAddress(memberDto.getAddress());
		member.setFeesStatus(memberDto.getFeesStatus());
		member.setPaymentMethod(memberDto.getPaymentMethod());
		member.setPlanName(memberDto.getPlanName());
		member.setJoinDate(LocalDate.now());

		// Set the gymId using the provided gym ID in DTO
		Admin gym = adminRepository.findById(memberDto.getGymId())
				.orElseThrow(() -> new RuntimeException("Gym not found"));

		member.setGymId(gym);

		// Calculate plan duration based on gymId and plan name
		int planMonths = calculatePlanDuration(memberDto.getGymId(), memberDto.getPlanName());

		// Calculate endDate based on plan duration in months and update membership
		// status
		calculateEndDate(member, planMonths);

		if (member.getEndDate() != null) {
			long daysLeft = calculateDaysLeft(member.getEndDate());
			member.setDaysLeft(daysLeft);
		} else {
			member.setDaysLeft(0); // Handle missing endDate edge case
		}

		updateMembershipStatus(member);
		// Save the member to the database
		memberRepository.save(member);
	}

	// Method to calculate plan duration based on gymId and planName
	public int calculatePlanDuration(Long gymId, String planName) {
		// Fetch the relevant membership plan for the given gym and plan name
		MembershipPlan plan = membershipPlanRepository.findByGymIdAndPlanName(gymId, planName)
				.orElseThrow(() -> new RuntimeException("Plan not found for the gym"));

		// Return the plan duration in months using the `durationInMonths` field
		return plan.getDurationInMonths();
	}

	private void calculateEndDate(Member member, int planMonths) {
		// Calculate the end date based on the join date and plan duration
		member.setEndDate(member.getJoinDate().plus(planMonths, ChronoUnit.MONTHS));

		// Calculate days left and set it
		if (member.getEndDate() != null) {
			long daysLeft = calculateDaysLeft(member.getEndDate());
			member.setDaysLeft(daysLeft);
		} else {
			member.setDaysLeft(0); // If no endDate is set, daysLeft is 0
		}
		updateMembershipStatus(member);
	}

	public long calculateDaysLeft(LocalDate endDate) {
		LocalDate currentDate = LocalDate.now();
		if (endDate.isBefore(currentDate)) {
			return 0;
		}
		return ChronoUnit.DAYS.between(currentDate, endDate);
	}

	private void updateMembershipStatus(Member member) {
		// Update the membership status based on the current date and end date
		if (LocalDate.now().isBefore(member.getEndDate())) {
			member.setMembershipStatus("active");
		} else {
			member.setMembershipStatus("inactive");
		}
	}

	public List<MembershipPlanDto> getPlansByGymId(Long gymId) {
		List<MembershipPlan> plans = membershipPlanRepository.findByGymId(gymId);
		return plans.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// Convert MembershipPlan entity to MembershipPlanDto
	private MembershipPlanDto convertToDto(MembershipPlan plan) {
		MembershipPlanDto dto = new MembershipPlanDto();
		dto.setPlanName(plan.getPlanName());
		dto.setDurationInMonths(plan.getDurationInMonths());
		// set other properties if needed
		return dto;
	}
	// Method to get all members by gym ID

	// Fetch members by gym ID
	public List<MemberDto> getAllMembersByGymId(Long gymId) {
		// Fetch the Admin entity based on gymId
		Admin gymAdmin = adminRepository.findById(gymId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid gym ID"));

		// Fetch members associated with the Admin entity
		List<Member> members = memberRepository.findByGymId(gymAdmin);
		return members.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	// Convert Member entity to MemberDto
	private MemberDto convertToDto(Member member) {
		MemberDto memberDto = new MemberDto();
		memberDto.setId(member.getId());
		memberDto.setName(member.getName());
		memberDto.setEmail(member.getEmail());
		memberDto.setPhone(member.getPhone() != null ? member.getPhone() : "No phone number");
		memberDto.setAddress(member.getAddress());
		memberDto.setFeesStatus(member.getFeesStatus());
		memberDto.setPaymentMethod(member.getPaymentMethod());
		memberDto.setPlanName(member.getPlanName());
		memberDto.setJoinDate(member.getJoinDate());
		memberDto.setEndDate(member.getEndDate());
		memberDto.setMembershipStatus(member.getMembershipStatus());

		memberDto.setDaysLeft(member.getDaysLeft());

		return memberDto;
	}

}
