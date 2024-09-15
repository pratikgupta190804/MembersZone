package com.example.memberszone.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.memberszone.dto.ClassScheduleDto;
import com.example.memberszone.service.ClassScheduleService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/class-schedules")
public class ClassScheduleController {

    @Autowired
    private ClassScheduleService classScheduleService;

    @PostMapping("/enroll")
    public ResponseEntity<?> enrollInClass(
            @RequestBody ClassScheduleDto classScheduleDto,
            HttpSession session) {
        Long gymId = (Long) session.getAttribute("gymId");
        if (gymId == null) {
            return new ResponseEntity<>("Gym ID not found in session", HttpStatus.FORBIDDEN);
        }

        try {
            ClassScheduleDto createdSchedule = classScheduleService.addClassSchedule(classScheduleDto, gymId);
            return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>("Invalid data provided", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            // Log the exception and provide a generic error message
            return new ResponseEntity<>("An error occurred while processing the request", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
