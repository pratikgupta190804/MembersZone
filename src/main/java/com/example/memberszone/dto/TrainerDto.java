package com.example.memberszone.dto;

import org.springframework.web.multipart.MultipartFile;

public class TrainerDto {

    private Long gymId;
    private Long trainerId;
    private String name;
    private String email;
    private String phoneNumber;
    private String specialization;
    private Integer experience;
    private String certification;
    private String imageUrl;
    private MultipartFile imageFile;

    // Default Constructor
    public TrainerDto() {
    }

    // Constructor for creating a DTO from a Trainer entity
    public TrainerDto(Long gymId, Long trainerId, String name, String email, String phoneNumber, String specialization,
                      Integer experience, String certification, String imageUrl) {
        this.gymId = gymId;
        this.trainerId = trainerId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.experience = experience;
        this.certification = certification;
        this.imageUrl = imageUrl;
    }

    // Constructor for creating a DTO with image file
    public TrainerDto(Long gymId, Long trainerId, String name, String email, String phoneNumber, String specialization,
                      Integer experience, String certification, String imageUrl, MultipartFile imageFile) {
        this.gymId = gymId;
        this.trainerId = trainerId;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.specialization = specialization;
        this.experience = experience;
        this.certification = certification;
        this.imageUrl = imageUrl;
        this.imageFile = imageFile;
    }

    // Getters and Setters

    public TrainerDto(Long trainerId2, String name2, String email2, String phoneNumber2, String specialization2,
			Integer experience2, String certification2, Long id, String imageUrl2) {
		// TODO Auto-generated constructor stub
	}

	public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        this.certification = certification;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public MultipartFile getImageFile() {
        return imageFile;
    }

    public void setImageFile(MultipartFile imageFile) {
        this.imageFile = imageFile;
    }

    @Override
    public String toString() {
        return "TrainerDto [gymId=" + gymId + ", trainerId=" + trainerId + ", name=" + name + ", email=" + email
                + ", phoneNumber=" + phoneNumber + ", specialization=" + specialization + ", experience=" + experience
                + ", certification=" + certification + ", imageUrl=" + imageUrl + ", imageFile=" + imageFile + "]";
    }
}
