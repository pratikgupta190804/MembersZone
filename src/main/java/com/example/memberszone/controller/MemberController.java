package com.example.memberszone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.memberszone.dto.MemberDto;
import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;

	// Show the form to add a new member
	@GetMapping("/add-member")
	public String showAddMemberForm(Model model, HttpSession session) {
		// Check if gym ID is present in the session
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			return "redirect:/login"; // Redirect to login or another page if gym ID is not found
		}

		// Initialize an empty MemberDto and add it to the model
		model.addAttribute("memberDto", new MemberDto());

		// Fetch membership plans for the gym and add to the model
		List<MembershipPlanDto> membershipPlans = memberService.getPlansByGymId(gymId);
		model.addAttribute("membershipPlans", membershipPlans);

		return "add-member"; // The name of the Thymeleaf template for the add member form
	}

	// Handle POST request to add a member
	@PostMapping("/add-member")
	public String addMember(@ModelAttribute("memberDto") MemberDto memberDto, HttpSession session, Model model) {
		// Retrieve gym ID from the session
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			model.addAttribute("error", "Gym ID not found in session. Please log in again.");
			return "error-page"; // Redirect to an error page or login page
		}

		// Set the gym ID in the DTO
		memberDto.setGymId(gymId);

		try {
			System.out.println(memberDto);
			// Add the member using the service
			memberService.addMember(memberDto);
			model.addAttribute("message", "Member successfully added!");
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while adding the member: " + e.getMessage());
		}

		return "redirect:/add-member"; // Redirect to the members listing page or another page
	}

	
	@GetMapping("/view-members")
	public String getAllMembers(HttpSession session, 
	                            @RequestParam(value = "searchTerm", required = false) String searchTerm,
	                            @RequestParam(value = "filterStatus", required = false) String filterStatus,
	                            Model model) {
	    // Retrieve gym ID from session
	    Long gymId = (Long) session.getAttribute("gymId");

	    if (gymId == null) {
	        // Handle case where gym ID is not available in session
	        model.addAttribute("error", "Gym ID not found in session.");
	        return "error"; // Redirect to an error page or handle as needed
	    }

	    // Fetch members based on gym ID
	    List<MemberDto> members = memberService.getAllMembersByGymId(gymId);
	    
	    // Calculate days left for each member
	    for (MemberDto member : members) {
	        long daysLeft = 0;
			try {
				daysLeft = memberService.calculateDaysLeft(member.getEndDate());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        member.setDaysLeft(daysLeft); // Assuming you have a setDaysLeft method in MemberDto
	    }

	    // Add attributes to the model
	    model.addAttribute("members", members);
	    model.addAttribute("searchTerm", searchTerm);
	    model.addAttribute("filterStatus", filterStatus);

	    // Return Thymeleaf template name
	    return "view-members"; // Assuming the Thymeleaf template is named 'view-members.html'
	}

}
