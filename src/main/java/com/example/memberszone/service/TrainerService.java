package com.example.memberszone.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.entity.Admin; // Import Admin entity
import com.example.memberszone.entity.Trainer;
import com.example.memberszone.repo.AdminRepository; // Import AdminRepository
import com.example.memberszone.repo.TrainerRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class TrainerService {

	@Autowired
	private HttpSession session;

	@Autowired
	private TrainerRepository trainerRepository;

	@Autowired
	private AdminRepository adminRepository; // Add AdminRepository

	@Autowired
	private CloudinaryService cloudinaryService;

	public void addTrainer(TrainerDto trainerDto) throws IOException {
		String imageUrl = cloudinaryService.uploadImage(trainerDto.getImageFile());

		Trainer trainer = new Trainer();
		trainer.setName(trainerDto.getName());
		trainer.setEmail(trainerDto.getEmail());
		trainer.setPhoneNumber(trainerDto.getPhoneNumber());
		trainer.setSpecialization(trainerDto.getSpecialization());
		trainer.setExperience(trainerDto.getExperience());
		trainer.setCertification(trainerDto.getCertification());
		trainer.setImageUrl(imageUrl);

		Long gymId = (Long) session.getAttribute("gymId");

		// Fetch the Admin entity using the gymId
		Admin gym = adminRepository.findById(gymId).orElseThrow(() -> new RuntimeException("Gym not found"));

		trainer.setGym(gym); // Set the Admin entity

		trainerRepository.save(trainer);
	}

	// Use the repository method to get trainers by gym ID
	public List<TrainerDto> getTrainersByGymId(Long gymId) {
		List<Trainer> trainers = trainerRepository.findByGymId(gymId);

		// Convert to DTOs
		return trainers.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private TrainerDto convertToDto(Trainer trainer) {
		TrainerDto trainerDto = new TrainerDto();
		trainerDto.setTrainerId(trainer.getTrainerId());
		trainerDto.setName(trainer.getName());
		trainerDto.setEmail(trainer.getEmail());
		trainerDto.setPhoneNumber(trainer.getPhoneNumber());
		trainerDto.setSpecialization(trainer.getSpecialization());
		trainerDto.setExperience(trainer.getExperience());
		trainerDto.setCertification(trainer.getCertification());
		trainerDto.setImageUrl(trainer.getImageUrl());
		return trainerDto;
	}

	public void updateTrainer(TrainerDto trainerDto) throws IOException {
		Trainer existingTrainer = trainerRepository.findById(trainerDto.getTrainerId())
				.orElseThrow(() -> new RuntimeException("Trainer not found"));

		existingTrainer.setName(trainerDto.getName());
		existingTrainer.setEmail(trainerDto.getEmail());
		existingTrainer.setPhoneNumber(trainerDto.getPhoneNumber());
		existingTrainer.setSpecialization(trainerDto.getSpecialization());
		existingTrainer.setExperience(trainerDto.getExperience());
		existingTrainer.setCertification(trainerDto.getCertification());

		// Handle image update if a new image is uploaded
		if (trainerDto.getImageFile() != null && !trainerDto.getImageFile().isEmpty()) {
			String imageUrl = cloudinaryService.uploadImage(trainerDto.getImageFile());
			existingTrainer.setImageUrl(imageUrl);
		}

		trainerRepository.save(existingTrainer);
	}

	public void deleteTrainer(Long trainerId) {
		if (trainerRepository.existsById(trainerId)) {
			trainerRepository.deleteById(trainerId);
		} else {
			throw new RuntimeException("Trainer not found");
		}
	}

	// Method to get a trainer by ID
	public TrainerDto getTrainerById(Long trainerId) {
		Trainer trainer = trainerRepository.findById(trainerId)
				.orElseThrow(() -> new RuntimeException("Trainer not found"));

		return convertToDto(trainer);
	}

}
