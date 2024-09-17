package com.example.memberszone.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memberszone.dto.ClassScheduleDto;
import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.entity.Admin;
import com.example.memberszone.entity.ClassSchedule;
import com.example.memberszone.entity.Trainer;
import com.example.memberszone.repo.AdminRepository;
import com.example.memberszone.repo.ClassScheduleRepository;
import com.example.memberszone.repo.TrainerRepository;

@Service
public class ClassScheduleService {

	@Autowired
	private TrainerRepository trainerRepository;
	@Autowired
	private ClassScheduleRepository classScheduleRepository;

	@Autowired
	private AdminRepository adminRepository;

	public ClassScheduleDto addClassSchedule(ClassScheduleDto classScheduleDto, Long gymId) {
		Admin gymAdmin = adminRepository.findById(gymId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid gym ID"));

		ClassSchedule classSchedule = new ClassSchedule();
		classSchedule.setName(classScheduleDto.getName());
		classSchedule.setEmail(classScheduleDto.getEmail());
		classSchedule.setPhoneNumber(classScheduleDto.getPhoneNumber());
		classSchedule.setDuration(classScheduleDto.getDuration());
		classSchedule.setClassName(classScheduleDto.getClassName());
		classSchedule.setClassDateTime(classScheduleDto.getClassDateTime());
		classSchedule.setInstructorName(classScheduleDto.getInstructorName());
		classSchedule.setEnrollmentDate(LocalDateTime.now());
		classSchedule.setGymId(gymAdmin);

		System.out.println(classSchedule);
		ClassSchedule savedSchedule = classScheduleRepository.save(classSchedule);
		return convertToDto(savedSchedule);
	}

	private ClassScheduleDto convertToDto(ClassSchedule classSchedule) {
		ClassScheduleDto dto = new ClassScheduleDto();
		dto.setId(classSchedule.getId());
		dto.setName(classSchedule.getName());
		dto.setEmail(classSchedule.getEmail());
		dto.setPhoneNumber(classSchedule.getPhoneNumber());
		dto.setDuration(classSchedule.getDuration());
		dto.setClassName(classSchedule.getClassName());
		dto.setClassDateTime(classSchedule.getClassDateTime());
		dto.setInstructorName(classSchedule.getInstructorName());
		dto.setEnrollmentDate(classSchedule.getEnrollmentDate());
		dto.setGymId(classSchedule.getGymId().getId());
		return dto;
	}

	public List<ClassScheduleDto> getClassScheduleById(Long gymId) {
		List<ClassSchedule> classes = classScheduleRepository.findByGymId_Id(gymId);
		return classes.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	public List<TrainerDto> getTrainerByGymId(Long gymId) {
		List<Trainer> plans = trainerRepository.findByGymId(gymId);
		return plans.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private TrainerDto convertToDto(Trainer trainer) {
		TrainerDto dto = new TrainerDto();
		dto.setName(trainer.getName());

		return dto;
	}

	public void deleteClassSchedule(Long gymId) {
		if (classScheduleRepository.existsById(gymId)) {
			classScheduleRepository.deleteById(gymId);
		} else {
			throw new RuntimeException("Trainer not found");
		}
	}

	public ClassScheduleDto getClassById(Long id) {
		ClassSchedule classSchedule = classScheduleRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Class not found"));
		System.out.println(classSchedule);

		return convertToDto(classSchedule);
	}

	public void updateClass(ClassScheduleDto classScheduleDto) throws IOException {
		ClassSchedule existingClass = classScheduleRepository.findById(classScheduleDto.getId())
				.orElseThrow(() -> new RuntimeException("Trainer not found"));

		existingClass.setName(classScheduleDto.getName());
		existingClass.setEmail(classScheduleDto.getEmail());
		existingClass.setPhoneNumber(classScheduleDto.getPhoneNumber());
		existingClass.setClassName(classScheduleDto.getClassName());
		existingClass.setInstructorName(classScheduleDto.getInstructorName());
		existingClass.setClassDateTime(classScheduleDto.getClassDateTime());
		existingClass.setDuration(classScheduleDto.getDuration());

		classScheduleRepository.save(existingClass);
	}
}
