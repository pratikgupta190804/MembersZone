package com.example.memberszone.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memberszone.dto.ClassScheduleDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.ClassSchedule;
import com.example.memberszone.repo.AdminRepository;
import com.example.memberszone.repo.ClassScheduleRepository;

import java.time.LocalDateTime;

@Service
public class ClassScheduleService {

	@Autowired
	private ClassScheduleRepository classScheduleRepository;

	@Autowired
	private AdminRepository adminRepository;

	public ClassScheduleDto addClassSchedule(ClassScheduleDto classScheduleDto, Long gymId) {
		// Fetch the Admin entity based on gymId
		Admin gymAdmin = adminRepository.findById(gymId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid gym ID"));

		// Create new ClassSchedule entity
		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setName(classScheduleDto.getName());
		classSchedule.setEmail(classScheduleDto.getEmail());
		classSchedule.setPhoneNumber(classScheduleDto.getPhoneNumber());
		classSchedule.setClassName(classScheduleDto.getClassName());
		classSchedule.setClassDateTime(classScheduleDto.getClassDateTime());
		classSchedule.setInstructorName(classScheduleDto.getInstructorName());
		classSchedule.setEnrollmentDate(LocalDateTime.now()); // Set current date and time
		classSchedule.setGymId(gymAdmin); // Set the Admin entity

		// Save and return the DTO
		ClassSchedule savedSchedule = classScheduleRepository.save(classSchedule);
		return convertToDto(savedSchedule);
	}

	private ClassScheduleDto convertToDto(ClassSchedule classSchedule) {
		ClassScheduleDto dto = new ClassScheduleDto();
		dto.setId(classSchedule.getId());
		dto.setName(classSchedule.getName());
		dto.setEmail(classSchedule.getEmail());
		dto.setPhoneNumber(classSchedule.getPhoneNumber());
		dto.setClassName(classSchedule.getClassName());
		dto.setClassDateTime(classSchedule.getClassDateTime());
		dto.setInstructorName(classSchedule.getInstructorName());
		dto.setEnrollmentDate(classSchedule.getEnrollmentDate());
		dto.setGymId(classSchedule.getGymId().getId()); // Set the gymId
		return dto;
	}
}
