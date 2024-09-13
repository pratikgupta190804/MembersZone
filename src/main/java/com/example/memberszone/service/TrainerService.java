package com.example.memberszone.service;

import com.example.memberszone.dto.TrainerDto;
import com.example.memberszone.entity.Trainer;
import com.example.memberszone.entity.Admin;  // Import Admin entity
import com.example.memberszone.repo.TrainerRepository;
import com.example.memberszone.repo.AdminRepository;  // Import AdminRepository

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
    private AdminRepository adminRepository;  // Add AdminRepository

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
        Admin gym = adminRepository.findById(gymId)
                .orElseThrow(() -> new RuntimeException("Gym not found"));
        
        trainer.setGym(gym);  // Set the Admin entity

        trainerRepository.save(trainer);
    }

    public List<TrainerDto> getTrainersByGymId(Long gymId) {
        List<Trainer> trainers = trainerRepository.findByGymId(gymId);

        // Return the list of TrainerDto including the gymId
        return trainers.stream().map(trainer -> new TrainerDto(
                trainer.getTrainerId(), trainer.getName(), trainer.getEmail(), trainer.getPhoneNumber(),
                trainer.getSpecialization(), trainer.getExperience(), trainer.getCertification(),
                trainer.getGym().getId(), trainer.getImageUrl()))
                .collect(Collectors.toList());
    }

    public TrainerDto getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
        return new TrainerDto(trainer.getTrainerId(), trainer.getName(), trainer.getEmail(), trainer.getPhoneNumber(),
                trainer.getSpecialization(), trainer.getExperience(), trainer.getCertification(),
                trainer.getGym().getId(), trainer.getImageUrl());
    }

    public void deleteTrainer(Long id) {
        trainerRepository.deleteById(id);
    }

    public void updateTrainer(TrainerDto trainerDto) {
        Trainer existingTrainer = trainerRepository.findById(trainerDto.getTrainerId())
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        existingTrainer.setName(trainerDto.getName());
        existingTrainer.setEmail(trainerDto.getEmail());
        existingTrainer.setPhoneNumber(trainerDto.getPhoneNumber());
        existingTrainer.setSpecialization(trainerDto.getSpecialization());
        existingTrainer.setExperience(trainerDto.getExperience());
        existingTrainer.setCertification(trainerDto.getCertification());
        existingTrainer.setImageUrl(trainerDto.getImageUrl()); // Assuming you store an image URL

        trainerRepository.save(existingTrainer);
    }
}
