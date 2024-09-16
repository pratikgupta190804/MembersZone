package com.example.memberszone.entity;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "class_schedule")
public class ClassSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;
    
    @Column(nullable=false,name = "duration")
    private String duration;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String className;

    @Column(nullable = false)
    private LocalDateTime classDateTime;

  
    @Column(name="instructorName")
    private String instructorName; 

    @Column(nullable = false)
    private LocalDateTime enrollmentDate;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "gym_id", nullable = false)
    private Admin gymId;

    
    public Admin getGymId() {
        return gymId;
    }

    public void setGymId(Admin gymId) {
        this.gymId = gymId;
    }

    // Constructor
    public ClassSchedule() {
        this.enrollmentDate = LocalDateTime.now(); // Set current date and time
    }

    // Getters and setters
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

    

    public LocalDateTime getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDateTime enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

	public String getInstructorName() {
		return instructorName;
	}

	public void setInstructorName(String instructorName) {
		this.instructorName = instructorName;
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

