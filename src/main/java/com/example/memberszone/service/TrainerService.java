package com.example.memberszone.service;

import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.entity.Trainer;
import com.example.memberszone.repo.TrainerRepository;

import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainerService {

	@Autowired
	private HttpSession session;
	@Autowired
	private TrainerRepository trainerRepository;

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
		trainer.setGymId(gymId);

		trainerRepository.save(trainer);
	}

	public List<TrainerDto> getTrainersByGymId(Long gymId) {
		List<Trainer> trainers = trainerRepository.findByGymId(gymId);
		return trainers.stream()
				.map(trainer -> new TrainerDto(trainer.getTrainerId(), trainer.getName(), trainer.getEmail(),
						trainer.getPhoneNumber(), trainer.getSpecialization(), trainer.getExperience(),
						trainer.getCertification(), trainer.getImageUrl()))
				.collect(Collectors.toList());
	}


	
}
