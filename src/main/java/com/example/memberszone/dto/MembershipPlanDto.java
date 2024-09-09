package com.example.memberszone.dto;

import java.math.BigDecimal;

public class MembershipPlanDto {

    private Long id;
    private String planName;
    private Integer durationInMonths;
    private BigDecimal price;
    private Long gymId;

    // Constructors
    public MembershipPlanDto() {}

    public MembershipPlanDto(Long id, String planName, Integer durationInMonths, BigDecimal price, Long gymId) {
        this.id = id;
        this.planName = planName;
        this.durationInMonths = durationInMonths;
        this.price = price;
        this.gymId = gymId;
    }

    // Getters and Setters

    public Long getId() {
        return this.id;
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

    public Long getGymId() {
        return gymId;
    }

    public void setGymId(Long gymId) {
        this.gymId = gymId;
    }

	@Override
	public String toString() {
		return "MembershipPlanDto [id=" + id + ", planName=" + planName + ", durationInMonths=" + durationInMonths
				+ ", price=" + price + ", gymId=" + gymId + "]";
	}
}
