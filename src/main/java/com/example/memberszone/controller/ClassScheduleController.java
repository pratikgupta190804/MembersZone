package com.example.memberszone.controller;

import java.io.IOException;
import java.time.LocalDateTime;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.memberszone.dto.ClassScheduleDto;
import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.service.ClassScheduleService;

import jakarta.servlet.http.HttpSession;

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

		List<ClassScheduleDto> classSchedules = classScheduleService.getClassScheduleById(gymId);
		model.addAttribute("classSchedules", classSchedules);
		model.addAttribute("classSchedule", new ClassScheduleDto());
		return null;
	}

	@PostMapping("/classes/add")
	public String addClass(@ModelAttribute("classSchedule") ClassScheduleDto classScheduleDto, HttpSession session,
			Model model) {

		Long gymId = (Long) session.getAttribute("gymId");
		if (gymId == null) {
			model.addAttribute("error", "Gym ID not found in session. Please log in again.");
			return "error-page"; // Redirect to an error page or login page
		}
		List<TrainerDto> trainers = classScheduleService.getTrainerByGymId(gymId);
		model.addAttribute("trainers", trainers);

		try {
			// Add the member using the service

			classScheduleService.addClassSchedule(classScheduleDto, gymId);
			System.out.println(classScheduleDto);
			model.addAttribute("message", "Added for class successfully !");
		} catch (Exception e) {
			model.addAttribute("error", "An error occurred while adding the member: " + e.getMessage());
		}

		return "classes";
	}

	@GetMapping("/fetch-class/{id}")
	public ResponseEntity<ClassScheduleDto> getClassSchedule(@PathVariable Long id) {
		System.out.println("method reached" + id);
		ClassScheduleDto classDto = classScheduleService.getClassById(id);
		System.out.println(classDto);
		return ResponseEntity.ok(classDto);
	}

	@DeleteMapping("/delete-class/{id}")
	public ResponseEntity<String> deleteClass(@PathVariable Long id) {
		classScheduleService.deleteClassSchedule(id);
		return ResponseEntity.ok("Class Booking  deleted successfully");
	}
	@PostMapping("/update-class")
	public ResponseEntity<String> updateClass(
	        @RequestParam(value = "id", required = true) Long classId,
	        @RequestParam("name") String name,
	        @RequestParam("email") String email,
	        @RequestParam("phoneNumber") String phoneNumber,
	        @RequestParam("classDateTime") String classDateTime,
	        @RequestParam("className") String className,
	        @RequestParam("instructorName") String instructorName,
	        @RequestParam("duration") String duration
	) throws IOException {
	    if (classId == null) {
	        return ResponseEntity.badRequest().body("Class ID is required");
	    }

	    ClassScheduleDto classScheduleDto = new ClassScheduleDto();
	    classScheduleDto.setId(classId);
	    classScheduleDto.setName(name);
	    classScheduleDto.setEmail(email);
	    classScheduleDto.setPhoneNumber(phoneNumber);
	    classScheduleDto.setClassName(className);
	    classScheduleDto.setInstructorName(instructorName);
	    classScheduleDto.setClassDateTime(LocalDateTime.parse(classDateTime));
	    classScheduleDto.setDuration(duration);

	    classScheduleService.updateClass(classScheduleDto);
	    return ResponseEntity.ok("Class updated successfully");
	}
}
