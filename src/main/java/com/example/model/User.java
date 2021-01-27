package com.example.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "USER_TYPE")
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50, name = "USER_NAME")
	private String userName;

	@Column(length = 15, name = "PHONE_NUMBER")
	private String phoneNumber;

	@Column(length = 1, name = "GENDER")
	private String gender;

	@Column(name = "DATE_OF_BIRTH")
	private LocalDate dateOfBirth;

	@Column(name = "USER_TYPE", nullable = false, updatable = false, insertable = false)
	private String userType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLE_ID", nullable = false, referencedColumnName = "ID")
	private Role role;

	protected User() {
	}

	public User(String userName, String phoneNumber, String gender, LocalDate dateOfBirth) {
		this.userName = userName;
		this.phoneNumber = phoneNumber;
		this.gender = gender;
		this.dateOfBirth = dateOfBirth;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public boolean isManager() {
		return this instanceof Manager;
	}

	public boolean isCustomer() {
		return this instanceof Customer;
	}

}
