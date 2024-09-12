package com.example.memberszone.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.service.MembershipPlanService;
import com.example.memberszone.service.TrainerService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/")
public class TrainerController {
	@Autowired
	private TrainerService trainerService;
	@Autowired
	private MembershipPlanService membershipPlanService;

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
		System.out.println("Gym id in the session " + gymId);
		if (gymId != null) {
			System.out.println("Session not null");
			List<TrainerDto> trainers = trainerService.getTrainersByGymId(gymId);
			System.out.println(trainers);
			model.addAttribute("trainers", trainers);
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
