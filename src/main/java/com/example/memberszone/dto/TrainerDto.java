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

	// Getters and Setters

	public TrainerDto(Long trainerId, String name, String email, String phoneNumber, String specialization,
			Integer experience, String certification, String imageUrl) {
		// TODO Auto-generated constructor stub
	}

	public TrainerDto() {
		// TODO Auto-generated constructor stub
	}

	public MultipartFile getImageFile() { // Add this method
		return imageFile;
	}

	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
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

	public Long getGymId() {
		return gymId;
	}

	public void setGymId(Long gymId) {
		this.gymId = gymId;
	}



	@Override
	public String toString() {
		return "TrainerDto [gymId=" + gymId + ", trainerId=" + trainerId + ", name=" + name + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", specialization=" + specialization + ", experience=" + experience
				+ ", certification=" + certification + ", imageUrl=" + imageUrl + ", imageFile=" + imageFile + "]";
	}
}
