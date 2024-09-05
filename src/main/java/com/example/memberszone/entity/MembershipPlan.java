package com.example.memberszone.entity;

import java.math.BigDecimal;
import jakarta.persistence.*;

@Entity
@Table(name = "membership_plans")
public class MembershipPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "plan_name", nullable = false)
    private String planName;

    @Column(name = "duration_in_months", nullable = false)
    private Integer durationInMonths;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "gym_id", nullable = false)
    private Admin gym; // Renamed for clarity

    // Default constructor
    public MembershipPlan() {}

    // Parameterized constructor
    public MembershipPlan(String planName, Integer durationInMonths, BigDecimal price, Admin gym) {
        this.planName = planName;
        this.durationInMonths = durationInMonths;
        this.price = price;
        this.gym = gym;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getDurationInMonths() {
        return durationInMonths;
    }

    public void setDurationInMonths(Integer durationInMonths) {
        this.durationInMonths = durationInMonths;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Admin getGym() {
        return gym;
    }

    public void setGym(Admin gym) {
        this.gym = gym;
    }
}
