package com.stackroute.keepnote.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

/*
 * The class "User" will be acting as the data model for the User Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
public class User {

	/*
	 * This class should have five fields (userId,userName,
	 * userPassword,userMobile,userAddedDate). Out of these five fields, the field
	 * userId should be the primary key. This class should also contain the getters
	 * and setters for the fields, along with the no-arg , parameterized constructor
	 * and toString method.The value of userAddedDate should not be accepted from
	 * the user but should be always initialized with the system date
	 */
	@Id
	private String userId;
	private String userName;
	private String userPassword;
	private String userMobile;
	private Date userAddedDate;

	public User() {

	}

	public User(final String userId, final String userName, final String userPassword, final String userMobile, final Date userAddedDate) {
		this.userId = userId;
		this.userName = userName;
		this.userPassword = userPassword;
		this.userMobile = userMobile;
		this.userAddedDate = userAddedDate;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(final String userId) {
		this.userId = userId;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;

	}

	public void setUserPassword(final String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserMobile() {
		return this.userMobile;

	}

	public void setUserMobile(final String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserName() {
		return userName;
	}

	public Date getUserAddedDate() {
		return userAddedDate;
	}

	public void setUserAddedDate(final Date userAddedDate) {
		this.userAddedDate = userAddedDate;
	}
	
	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", userPassword=" + userPassword + ", userMobile="
				+ userMobile + ", userAddedDate=" + userAddedDate + "]";
	}

}
