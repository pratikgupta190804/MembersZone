package com.example.memberszone.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.memberszone.dto.MemberDTO;
import com.example.memberszone.service.MemberService;
import com.example.memberszone.service.MembershipPlanService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
public class MemberController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private MembershipPlanService membershipPlanService;

	@GetMapping("/add-member")
	public String getAddMember(Model model, HttpSession session) {

		// Retrieve the gymId from the session
		Long gymId = (Long) session.getAttribute("gymId");

		if (gymId != null) {
			// Fetch membership plans for the gym using the gymId
			model.addAttribute("membershipPlans", membershipPlanService.getPlansByGymId(gymId));
		} else {
			// Handle case when gymId is not found (optional)
			model.addAttribute("membershipPlans", new ArrayList<>()); // Empty list or other logic
		}

		// Add a new MemberDTO to the model
		model.addAttribute("memberDTO", new MemberDTO());

		return "add-member";
	}

	@PostMapping("/add-member")
	public String addMember(@ModelAttribute MemberDTO memberDTO, HttpSession session, Model model) {
		// Retrieve the gymId from the session

		Long gymId = (Long) session.getAttribute("gymId");

		if (gymId != null) {
			// Call the service to add the member
			memberService.addMember(memberDTO, gymId);

			// Redirect to a success page or list of members
			return "redirect:/home";
		} else {
			// Handle case when gymId is not found (optional)
			model.addAttribute("error", "Gym ID is not available.");
			return "add-member";
		}
	}
	@GetMapping("/view-members")
	public String getMembers(HttpSession session, Model model) {
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			return "redirect:/login"; // Redirect to login if gymId is not in the session
		}

		List<MemberDTO> members = memberService.getAllMembersByGymId(gymId);
		System.out.println(members);
		model.addAttribute("members", members);

		return "view-members"; // The name of the Thymeleaf template to render
	}

	@GetMapping("/view-members/{memberId}")
	public String getMemberDetails(@PathVariable("memberId") Long memberId, Model model) {
		MemberDTO member = memberService.getMemberById(memberId);
		model.addAttribute("member", member);

		return "member-details"; // The name of the Thymeleaf template to render
	}
}
