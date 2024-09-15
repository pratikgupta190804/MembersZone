package com.example.memberszone.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.service.MembershipPlanService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class MemberShipPlanController {
	@Autowired
	private MembershipPlanService membershipPlanService;

	@GetMapping("/addplan")
	public String showAddPlanForm(Model model) {
		model.addAttribute("membershipPlan", new MembershipPlanDto());

		return "add-plan"; // Thymeleaf template name
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

}