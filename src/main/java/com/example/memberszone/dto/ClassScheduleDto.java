package com.example.memberszone.dto;

import java.time.LocalDateTime;

public class ClassScheduleDto {

	private Long id;
	private String name;
	private String email;
	private String phoneNumber;
	private String className;
	private LocalDateTime classDateTime;
	private String instructorName;
	private LocalDateTime enrollmentDate;
	private Long gymId;
	private String duration;

	// Default constructor
	public ClassScheduleDto() {
	}

	public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

	// Parameterized constructor
	public ClassScheduleDto(Long id, String name, String email, String phoneNumber, String className,
			LocalDateTime classDateTime, String instructorName, LocalDateTime enrollmentDate) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.className = className;
		this.classDateTime = classDateTime;
		this.instructorName = instructorName;
		this.enrollmentDate = enrollmentDate;
	}

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public LocalDateTime getClassDateTime() {
		return classDateTime;
	}

	public void setClassDateTime(LocalDateTime classDateTime) {
		this.classDateTime = classDateTime;
	}

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
	}

	public LocalDateTime getEnrollmentDate() {
		return enrollmentDate;
	}

	public void setEnrollmentDate(LocalDateTime enrollmentDate) {
		this.enrollmentDate = enrollmentDate;
	}
	
	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "ClassScheduleDto [id=" + id + ", name=" + name + ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", className=" + className + ", classDateTime=" + classDateTime + ", instructorName=" + instructorName
				+ ", enrollmentDate=" + enrollmentDate + ", gymId=" + gymId + ", duration=" + duration + "]";
	}
}
