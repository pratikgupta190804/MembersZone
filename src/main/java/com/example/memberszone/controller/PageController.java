package com.example.memberszone.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.memberszone.dto.AdminDto;
import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.service.AdminService;
import com.example.memberszone.service.MembershipPlanService;
import com.example.memberszone.service.TrainerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class PageController {

	@Autowired
	private AdminService adminService;
	@Autowired
	private MembershipPlanService membershipPlanService;
	@Autowired
    private TrainerService trainerService;
	
	@GetMapping("/")
	public String index(Model model) {
		model.addAttribute("name", "Nikhil");
		return "home";
	}

	@GetMapping("/home")
	public String home(Model model) {
	
		return "home";
	}
	
	@GetMapping("/contact-us")
	public String contactUs(Model model) {
		
		return "contact-us";
	}
	
	@GetMapping("/features")
	public String features(Model model) {
		
		return "features";
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
	
	@GetMapping("/forgot-password")
	public String showForgotPasswordForm(Model model) {
		return "forgot-password"; // Returns the forgot-password.html Thymeleaf template
	}

	@GetMapping("/admin")
	public String getAdminPage(Model model) {
		return "admin";
	}
	
	@GetMapping("/add-member")
	public String getAddMember(Model model) {
		return "add-member";
	}

	@GetMapping("/view-members")
	public String getViewMembers(Model model) {
		return "view-members";
	}
	
	@GetMapping("/dashboard")
	public String getDashBoard(Model model) {
		return "dashboard";
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

			return "redirect:/view-trainers"; // Redirect to the addplan page
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

	

	// Fetch membership plans for the gym
	@GetMapping("/view-plans")
	public String getPlans(HttpSession session, Model model) {
		
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			model.addAttribute("error", "Gym not found for the current admin.");
			return "login"; // Return to plans page with error
		}
		List<MembershipPlanDto> membershipPlans = membershipPlanService.getPlansByGymId(gymId);
	
		model.addAttribute("membershipPlans", membershipPlans);
		return "view-plans"; // Return to plans Thymeleaf template
	}

	// Delete a membership plan (AJAX)
	@DeleteMapping("/delete-plan/{id}")
	@ResponseBody
	public ResponseEntity<String> deletePlan(@PathVariable Long id) {
		try {
			membershipPlanService.deletePlan(id);
			return new ResponseEntity<>("Plan deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error deleting plan", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/fetch-plan/{id}")
	@ResponseBody
	public MembershipPlanDto getPlan(@PathVariable Long id) {
		return membershipPlanService.getPlanById(id); // Directly return the DTO from the service
	}

	@PostMapping("/update-plan")
	@ResponseBody
	public ResponseEntity<?> updatePlan(@ModelAttribute MembershipPlanDto membershipPlanDto) {
		try {
			membershipPlanService.updatePlan(membershipPlanDto);
			return ResponseEntity.ok().body("Plan updated successfully");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Error updating plan: " + e.getMessage());
		}
	}

	
	
	 @GetMapping("/add-trainer")
	    public String showAddTrainerForm(Model model) {
	        model.addAttribute("trainerDto", new TrainerDto());
	        return "add-trainer"; // Returns the add-trainer.html Thymeleaf template
	    }

	    @PostMapping("/add-trainer")
	    public String addTrainer(@ModelAttribute TrainerDto trainerDto, Model model) {
	        try {
	            trainerService.addTrainer(trainerDto);
	            model.addAttribute("message", "Trainer added successfully.");
	            return "redirect:/trainer-list"; // Redirect to a list of trainers or another relevant page
	        } catch (IOException e) {
	            model.addAttribute("error", "Failed to add trainer. Please try again.");
	            return "add-trainer"; // Return to the add-trainer form with an error message
	        }
	    }
	
	    @GetMapping("/view-trainers")
	    public String getTrainers(Model model, HttpSession session) {
	        Long gymId = (Long) session.getAttribute("gymId");
	        System.out.println("Gym id in the session " +gymId);
	        if (gymId != null) {
	        	System.out.println("Session not null");
	            List<TrainerDto> trainers = trainerService.getTrainersByGymId(gymId);
	            System.out.println(trainers);
	            model.addAttribute("trainers",trainers);
	            return "view-trainers"; // Thymeleaf template to display trainers
	        }
	        return "redirect:/login"; // Redirect to login if gymId is not found
	    }
	    
	    
	    @GetMapping("/fetch-trainer/{id}")
		@ResponseBody
		public MembershipPlanDto getTrainer(@PathVariable Long id) {
			return membershipPlanService.getPlanById(id); // Directly return the DTO from the service
		}
}
