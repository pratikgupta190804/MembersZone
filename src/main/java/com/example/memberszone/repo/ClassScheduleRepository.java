package com.example.memberszone.repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.memberszone.entity.ClassSchedule;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long> {

    // Find all class schedules for a specific instructor
    List<ClassSchedule> findByInstructorName(String instructorName);

    // Find all class schedules for a specific class name
    List<ClassSchedule> findByClassName(String className);

    // Find all class schedules for a specific email (assuming a user might enroll in multiple classes)
    List<ClassSchedule> findByEmail(String email);

    // Find all class schedules for a specific phone number
    List<ClassSchedule> findByPhoneNumber(String phoneNumber);

    // Find all class schedules for a specific date and time
    List<ClassSchedule> findByClassDateTime(LocalDateTime classDateTime);
}
