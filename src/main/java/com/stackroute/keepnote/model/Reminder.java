package com.stackroute.keepnote.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

/*
 * The class "Reminder" will be acting as the data model for the Reminder Table in the database. 
 * Please note that this class is annotated with @Entity annotation. 
 * Hibernate will scan all package for any Java objects annotated with the @Entity annotation. 
 * If it finds any, then it will begin the process of looking through that particular 
 * Java object to recreate it as a table in your database.
 */
@Entity
public class Reminder {
	/*
	 * This class should have seven fields
	 * (reminderId,reminderName,reminderDescription,reminderType,
	 * reminderCreatedBy,reminderCreationDate,notes). Out of these seven fields, the
	 * field reminderId should be primary key and auto-generated. This class should
	 * also contain the getters and setters for the fields along with the no-arg ,
	 * parameterized constructor and toString method. The value of
	 * reminderCreationDate should not be accepted from the user but should be
	 * always initialized with the system date. annotate notes field with @OneToMany
	 * and @JsonIgnore
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int reminderId;
	private String reminderName;
	private String reminderDescription;
	private String reminderType;
	private String reminderCreatedBy;
	private Date reminderCreationDate;
	
	@JsonIgnore
	@OneToMany
	private List<Note> notes;

	public Reminder() {

	}

	public Reminder(final int reminderId, final String reminderName, final String reminderDescription, final String reminderType,
			final String reminderCreatedBy, final List<Note> notes, final Date reminderCreationDate) {
		this.reminderId = reminderId;
		this.reminderName = reminderName;
		this.reminderDescription = reminderDescription;
		this.reminderType = reminderType;
		this.reminderCreatedBy = reminderCreatedBy;
		this.reminderCreationDate = reminderCreationDate;
		this.notes = notes;
	}

	public String getReminderName() {
		return reminderName;
	}

	public String getReminderType() {
		return reminderType;
	}

	public String getReminderCreatedBy() {
		return reminderCreatedBy;
	}

	public Date getReminderCreationDate() {
		return reminderCreationDate;
	}

	public List<Note> getNotes() {
		return notes;
	}

	public int getReminderId() {
		return this.reminderId;

	}

	public void setReminderId(final int reminderId) {
		this.reminderId = reminderId;
	}

	public void setReminderName(final String reminderName) {
		this.reminderName = reminderName;
	}

	public String getReminderDescription() {
		return this.reminderDescription;
	}

	public void setReminderDescription(final String reminderDescription) {
		this.reminderDescription = reminderDescription;
	}

	public void setReminderType(final String reminderType) {
		this.reminderType = reminderType;
	}

	public void setReminderCreationDate(final Date reminderCreationDate) {
		this.reminderCreationDate = reminderCreationDate;
	}

	public void setReminderCreatedBy(final String reminderCreatedBy) {
		this.reminderCreatedBy = reminderCreatedBy;
	}

	public void setNotes(final List<Note> notes) {
		this.notes = notes;
	}
	
	@Override
	public String toString() {
		return "Reminder [reminderId=" + reminderId + ", reminderName=" + reminderName + ", reminderDescription="
				+ reminderDescription + ", reminderType=" + reminderType + ", reminderCreatedBy=" + reminderCreatedBy
				+ ", reminderCreationDate=" + reminderCreationDate + ", notes=" + notes + "]";
	}

}
