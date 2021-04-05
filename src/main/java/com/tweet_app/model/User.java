package com.tweet_app.model;

import java.util.Date;

/**
 * @author Seelam Jagadeeswara Reddy
 *
 */
public class User {
	private String id;
	private String firstName;
	private String lastName;
	private String gender;
	private String email;
	private String password;
	private Date dateOfBirth;
	private Date userCreatedTime;
	private Boolean isUserLoggedIn;

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Date getUserCreatedTime() {
		return userCreatedTime;
	}

	public void setUserCreatedTime(Date userCreatedTime) {
		this.userCreatedTime = userCreatedTime;
	}

	public Boolean getIsUserLoggedIn() {
		return isUserLoggedIn;
	}

	public void setIsUserLoggedIn(Boolean isUserLoggedIn) {
		this.isUserLoggedIn = isUserLoggedIn;
	}

	public User(String id, String firstName, String lastName, String gender, String email, String password,
			Date dateOfBirth, Date userCreatedTime, Boolean isUserLoggedIn) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.email = email;
		this.password = password;
		this.dateOfBirth = dateOfBirth;
		this.userCreatedTime = userCreatedTime;
		this.isUserLoggedIn = isUserLoggedIn;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", gender=" + gender
				+ ", email=" + email + ", password=" + password + ", dateOfBirth=" + dateOfBirth + ", userCreatedTime="
				+ userCreatedTime + ", isUserLoggedIn=" + isUserLoggedIn + "]";
	}

}
