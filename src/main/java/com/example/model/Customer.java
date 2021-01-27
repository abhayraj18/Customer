package com.example.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.util.DateUtil;

@Entity
@DiscriminatorValue("CUSTOMER")
@Table(name = "CUSTOMER")
public class Customer extends User {

	protected Customer() {
	}

	public Customer(String userName, String phoneNumber, String gender, String dateOfBirth) {
		super(userName, phoneNumber, gender, DateUtil.parseStringToLocalDate(dateOfBirth, DateUtil.DD_MM_YYYY_FORMAT));
	}

}
