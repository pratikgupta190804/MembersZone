package com.example.memberszone.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.memberszone.dto.MemberDTO;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.Member;
import com.example.memberszone.entity.Member.MembershipStatus;
import com.example.memberszone.repo.AdminRepository;
import com.example.memberszone.repo.MemberRepository;
import com.example.memberszone.repo.MembershipPlanRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private MembershipPlanRepository membershipPlanRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Transactional
	public void addMember(MemberDTO memberDTO, Long gymId) {
		// Convert MemberDTO to Member entity
		Member member = new Member();
		member.setName(memberDTO.getName());
		member.setEmail(memberDTO.getEmail());
		member.setPhoneNumber(memberDTO.getPhoneNumber());
		member.setAddress(memberDTO.getAddress());
		member.setFeesStatus(Member.FeesStatus.valueOf(memberDTO.getFeesStatus()));
		member.setPaymentMethod(Member.PaymentMethod.valueOf(memberDTO.getPaymentMethod()));
		member.setPlanName(memberDTO.getPlanName());

		// Handle null joinDate
		LocalDate joinDate = convertToLocalDate(memberDTO.getJoinDate());
		if (joinDate == null) {
			joinDate = LocalDate.now(); // Use current date as a default if joinDate is null
		}
		member.setJoinDate(joinDate);

		// Calculate the end date using the non-null joinDate
		member.setEndDate(calculateEndDate(joinDate, memberDTO.getPlanName()));

		LocalDate today = LocalDate.now();
		if (member.getEndDate().isBefore(today)) {
			member.setMembershipStatus(MembershipStatus.INACTIVE);
		} else {
			member.setMembershipStatus(MembershipStatus.ACTIVE);
		}

		Admin admin = adminRepository.findById(gymId)
				.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + gymId));
		member.setGymId(admin);

		// Save the member
		memberRepository.save(member);
	}

	private LocalDate convertToLocalDate(String date) {
		if (date == null || date.isEmpty()) {
			return null; // Return null if date is empty or null
		}

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(date, formatter);
	}

	private LocalDate calculateEndDate(LocalDate joinDate, String planName) {
		int months = getPlanDuration(planName);
		return joinDate.plusMonths(months);
	}

	private int getPlanDuration(String planName) {
		// Fetch the plan duration based on planName from MembershipPlanRepository
		// For simplicity, returning a fixed duration
		// Example: return membershipPlanRepository.findByName(planName).getDuration();
		return 12; // Assuming a default duration of 12 months
	}

	public List<MemberDTO> getAllMembersByGymId(Long gymId) {
		Admin gymAdmin = adminRepository.findById(gymId)
				.orElseThrow(() -> new RuntimeException("Admin not found with ID: " + gymId));
		List<Member> members = memberRepository.findByGymId(gymAdmin);

		// Directly create MemberDTO objects from Member entities
		return members.stream()
				.map(member -> new MemberDTO(member.getId(), member.getName(), member.getEmail(),
						member.getPhoneNumber(), member.getAddress(), member.getJoinDate(), member.getEndDate(),
						member.getMembershipStatus().name(), // Convert enum to String
						member.getFeesStatus().name(), // Convert enum to String
						member.getPaymentMethod(), member.getPlanName()))
				.collect(Collectors.toList());
	}

	public MemberDTO getMemberById(Long memberId) {
		Member member = memberRepository.findById(memberId)
				.orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

		// Directly create a MemberDTO object from a Member entity
		return new MemberDTO(member.getId(), member.getName(), member.getEmail(), member.getPhoneNumber(),
				member.getAddress(), member.getJoinDate(), member.getEndDate(), member.getMembershipStatus().name(), // Convert
																														// enum
																														// to
																														// String
				member.getFeesStatus().name(), // Convert enum to String
				member.getPaymentMethod(), member.getPlanName());
	}
}
