package com.example.memberszone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.memberszone.dto.AdminDto;
import com.example.memberszone.service.AdminService;

import jakarta.servlet.http.HttpSession;


@Controller
@RequestMapping("/")
public class LoginController {
	@Autowired
	private AdminService adminService;
	
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

			return "redirect:/dashboard"; // Redirect to the addplan page
		} else {
			model.addAttribute("error", "Invalid username or password!");
			return "login"; // Return to login page with an error message
		}
	}

}
