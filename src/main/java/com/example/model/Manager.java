package com.example.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.example.util.DateUtil;


@Entity
@DiscriminatorValue("MANAGER")
@Table(name = "MANAGER")
public class Manager extends User {

	protected Manager() {
	}

	public Manager(String userName, String phoneNumber, String gender, String dateOfBirth) {
		super(userName, phoneNumber, gender, DateUtil.parseStringToLocalDate(dateOfBirth, DateUtil.DD_MM_YYYY_FORMAT));
	}

}
