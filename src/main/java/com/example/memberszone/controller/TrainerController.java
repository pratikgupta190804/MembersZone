package com.example.memberszone.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.service.TrainerService;

import jakarta.servlet.http.HttpSession;

@Controller
public class TrainerController {
	@Autowired
	private TrainerService trainerService;

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
	public String viewTrainers(HttpSession session, Model model) {
		// Retrieve the gym ID from the session
		Long gymId = (Long) session.getAttribute("gymId");

		if (gymId == null) {
			throw new RuntimeException("Gym ID not found in session!");
		}

		// Fetch trainers for the gym
		List<TrainerDto> trainers = trainerService.getTrainersByGymId(gymId);

		// Add trainers to the model
		model.addAttribute("trainers", trainers);

		// Return the Thymeleaf view name (trainers.html)
		return "view-trainers";
	}

	@GetMapping("/fetch-trainer/{id}")
	public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) {
		TrainerDto trainerDto = trainerService.getTrainerById(id);
		return ResponseEntity.ok(trainerDto);
	}

	@PostMapping("/update-trainer")
	public ResponseEntity<String> updateTrainer(@RequestParam("trainerId") Long trainerId,
			@RequestParam("name") String name, @RequestParam("email") String email,
			@RequestParam("phoneNumber") String phoneNumber, @RequestParam("specialization") String specialization,
			@RequestParam("experience") Integer experience, @RequestParam("certification") String certification,
			@RequestParam(value = "imageFile", required = false) MultipartFile imageFile) throws IOException {
		TrainerDto trainerDto = new TrainerDto();
		trainerDto.setTrainerId(trainerId);
		trainerDto.setName(name);
		trainerDto.setEmail(email);
		trainerDto.setPhoneNumber(phoneNumber);
		trainerDto.setSpecialization(specialization);
		trainerDto.setExperience(experience);
		trainerDto.setCertification(certification);
		trainerDto.setImageFile(imageFile);

		trainerService.updateTrainer(trainerDto);
		return ResponseEntity.ok("Trainer updated successfully");
	}

	@DeleteMapping("/delete-trainer/{id}")
	public ResponseEntity<String> deleteTrainer(@PathVariable Long id) {
		trainerService.deleteTrainer(id);
		return ResponseEntity.ok("Trainer deleted successfully");
	}

}
