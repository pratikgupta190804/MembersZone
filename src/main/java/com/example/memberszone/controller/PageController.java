package com.example.memberszone.controller;

import jakarta.servlet.http.HttpSession;

import com.example.memberszone.dto.AdminDto;
import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.service.AdminService;
import com.example.memberszone.service.MembershipPlanService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MembershipPlanService membershipPlanService;

	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("name", "Nikhil");
		return "home";
	}

	@GetMapping("/signup")
	public String showSignupForm(Model model) {
		return "signup"; // Returns the signup.html Thymeleaf template
	}

	@PostMapping("/signup")
	public String registerAdmin(@RequestParam String username, @RequestParam String password,
			@RequestParam String confirmPassword, @RequestParam String firstName, @RequestParam String lastName,
			@RequestParam String email, @RequestParam String phoneNumber, @RequestParam String gymName,
			@RequestParam String gymAddress, Model model) {
		// Password confirmation check
		if (!password.equals(confirmPassword)) {
			model.addAttribute("error", "Passwords do not match!");
			return "signup"; // Return to the signup form with an error message
		}

		AdminDto adminDto = new AdminDto();
		adminDto.setUsername(username);
		adminDto.setPassword(password);
		adminDto.setFirstName(firstName);
		adminDto.setLastName(lastName);
		adminDto.setEmail(email);
		adminDto.setPhoneNumber(phoneNumber);
		adminDto.setGymName(gymName);
		adminDto.setGymAddress(gymAddress);

		adminService.saveAdmin(adminDto);

		model.addAttribute("message", "Registration successful!");
		return "login"; // Redirect to home or any other page after registration
	}

	@GetMapping("/login")
	public String showLoginForm(Model model) {
		return "login"; // Returns the login.html Thymeleaf template
	}

	// Handle login POST request
	@PostMapping("/login")
	public String login(@RequestParam String username, @RequestParam String password, HttpSession session,
			Model model) {

		AdminDto adminDto = adminService.validatePassword(username, password);

		if (adminDto != null) {
			// Store gymId in the session after successful login
			session.setAttribute("gymId", adminDto.getGymId()); // Ensure gymId is set in AdminDto

			return "redirect:/addplan"; // Redirect to the addplan page
		} else {
			model.addAttribute("error", "Invalid username or password!");
			return "login"; // Return to login page with an error message
		}
	}

	@GetMapping("/addplan")
	public String showAddPlanForm(Model model) {
		model.addAttribute("membershipPlan", new MembershipPlanDto());

		return "addplan"; // Thymeleaf template name
	}

	@PostMapping("/addplan")
	public String addMembershipPlan(@ModelAttribute MembershipPlanDto membershipPlanDto, HttpSession session,
			Model model) {
		System.out.println("Reached in the post mapping");
		// Retrieve gymId from session
		Long gymId = (Long) session.getAttribute("gymId");

		System.out.println(gymId);
		if (gymId != null) {
			// Set the gymId in the DTO before saving
			membershipPlanDto.setGymId(gymId);

			// Call service method to save the membership plan
			membershipPlanService.saveMembershipPlan(membershipPlanDto);

			// Add success message
			model.addAttribute("message", "Membership plan added successfully.");

		} else {
			// Handle case where gymId is not available
			model.addAttribute("error", "Unable to determine gym for the logged-in admin.");
		}

		return "addplan";
	}

	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(Model model) {
		return "forgot-password"; // Returns the forgot-password.html Thymeleaf template
	}

	
	
	@GetMapping("/admin")
	public String getAdminPage(Model model) {
		return "admin";
	}
	
	 // Fetch membership plans for the gym
    @GetMapping("/plans")
    public String getPlans(HttpSession session, Model model) {
        Long gymId = (Long) session.getAttribute("gymId");
        if (gymId == null) {
            model.addAttribute("error", "Gym not found for the current admin.");
            return "login"; // Return to plans page with error
        }
        List<MembershipPlanDto> membershipPlans = membershipPlanService.getPlansByGymId(gymId);
       System.out.println(membershipPlans);
        model.addAttribute("membershipPlans", membershipPlans);
        return "plans"; // Return to plans Thymeleaf template
    }
}
