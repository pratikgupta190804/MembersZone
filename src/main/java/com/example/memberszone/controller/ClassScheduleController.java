package com.example.memberszone.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.memberszone.dto.ClassScheduleDto;
import com.example.memberszone.dto.MemberDto;
import com.example.memberszone.dto.MembershipPlanDto;
import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.service.ClassScheduleService;

import jakarta.servlet.http.HttpSession;
import jakarta.websocket.Session;

@Controller
@RequestMapping("/")
public class ClassScheduleController {

	@Autowired
	private ClassScheduleService classScheduleService;

	@GetMapping("/classes")
	public String classes(Model model, HttpSession session) {
//Check if gym ID is present in the session
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			return "redirect:/login"; // Redirect to login or another page if gym ID is not found
		}

		List<TrainerDto> trainers = classScheduleService.getTrainerByGymId(gymId);
		model.addAttribute("trainers", trainers);

		model.addAttribute("classSchedules", new ClassScheduleDto());

		return null;
	}

	@PostMapping("/classes/add")
	public String addClass(@ModelAttribute("classSchedules") ClassScheduleDto classScheduleDto, HttpSession session,
			Model model) {
		System.out.println("Method reached s");
		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			model.addAttribute("error", "Gym ID not found in session. Please log in again.");
			return "error-page"; // Redirect to an error page or login page
		}
		List<TrainerDto> trainers = classScheduleService.getTrainerByGymId(gymId);
		model.addAttribute("trainers", trainers);

		try {
			// Add the member using the service
			System.out.println(classScheduleDto);
			classScheduleService.addClassSchedule(classScheduleDto, gymId);
			model.addAttribute("message", "Added for class successfully !");
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while adding the member: " + e.getMessage());
		}

		return "classes";

	}
}
