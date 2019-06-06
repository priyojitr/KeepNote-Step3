package com.stackroute.keepnote.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.ReminderDAO;
import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn�t currently 
* provide any additional behavior over the @Component annotation, but it�s a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */

@Service
public class ReminderServiceImpl implements ReminderService {

	/*
	 * Autowiring should be implemented for the ReminderDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	private final ReminderDAO reminderDAO;

	@Autowired
	public ReminderServiceImpl(final ReminderDAO reminderDAO) {
		this.reminderDAO = reminderDAO;
	}

	/*
	 * This method should be used to save a new reminder.
	 */

	public boolean createReminder(final Reminder reminder) {
		return this.reminderDAO.createReminder(reminder);

	}

	/*
	 * This method should be used to update a existing reminder.
	 */

	public Reminder updateReminder(final Reminder reminder, final int id) throws ReminderNotFoundException {
		final Reminder oldReminder = this.reminderDAO.getReminderById(id);
		if (null == oldReminder) {
			throw new ReminderNotFoundException("reminder not found exception");
		}
		// update reminder if id is found & return the same object
		this.reminderDAO.updateReminder(reminder);
		return reminder;
	}

	/* This method should be used to delete an existing reminder. */

	public boolean deleteReminder(final int reminderId) {
		return this.reminderDAO.deleteReminder(reminderId);
	}

	/*
	 * This method should be used to get a reminder by reminderId.
	 */

	public Reminder getReminderById(final int reminderId) throws ReminderNotFoundException {
		final Reminder reminder = this.reminderDAO.getReminderById(reminderId);
		if(null==reminder) {
			throw new ReminderNotFoundException("reminder not found exception");
		}
		return reminder;

	}

	/*
	 * This method should be used to get a reminder by userId.
	 */

	public List<Reminder> getAllReminderByUserId(final String userId) {
		return this.reminderDAO.getAllReminderByUserId(userId);

	}
}
