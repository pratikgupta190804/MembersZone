package com.example.memberszone.dto;

import java.time.LocalDate;

public class MemberDto {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private String address;
	private String feesStatus; // Either "active" or "inactive"
	private String paymentMethod; // Either "cash" or "online"
	private String planName;
	private LocalDate joinDate; // Current date at the time of creation
	private LocalDate endDate; // Calculated using joinDate and plan duration
	private String membershipStatus; // Either "active" or "inactive"
	private Long gymId; // References the gym ID from the Admin table
	private long daysLeft;
	// Constructors, Getters, and Setters

	public MemberDto() {
	}

	public MemberDto(Long id, String name, String email, String phone, String address, String feesStatus,
			String paymentMethod, String planName, LocalDate joinDate, LocalDate endDate, String membershipStatus,
			Long gymId) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.feesStatus = feesStatus;
		this.paymentMethod = paymentMethod;
		this.planName = planName;
		this.joinDate = joinDate;
		this.endDate = endDate;
		this.membershipStatus = membershipStatus;
		this.gymId = gymId;
	}

	// Getters and Setters for all fields

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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getFeesStatus() {
		return feesStatus;
	}

	public void setFeesStatus(String feesStatus) {
		this.feesStatus = feesStatus;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getMembershipStatus() {
		return membershipStatus;
	}

	public void setMembershipStatus(String membershipStatus) {
		this.membershipStatus = membershipStatus;
	}

	public Long getGymId() {
		return gymId;
	}

	public void setGymId(Long gymId) {
		this.gymId = gymId;
	}

	public long getDaysLeft() {
		return daysLeft;
	}

	public void setDaysLeft(long daysLeft) {
		this.daysLeft = daysLeft;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + ", address="
				+ address + ", feesStatus=" + feesStatus + ", paymentMethod=" + paymentMethod + ", planName=" + planName
				+ ", joinDate=" + joinDate + ", endDate=" + endDate + ", membershipStatus=" + membershipStatus
				+ ", gymId=" + gymId + ", daysLeft=" + daysLeft + "]";
	}
}
