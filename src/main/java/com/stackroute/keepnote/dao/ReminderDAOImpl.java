package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.ReminderNotFoundException;
import com.stackroute.keepnote.model.Reminder;

/*
 * This class is implementing the UserDAO interface. This class has to be annotated with 
 * @Repository annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, 
 * thus clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database 
 * 					transaction. The database transaction happens inside the scope of a persistence 
 * 					context.  
 * */
@Repository
@Transactional
public class ReminderDAOImpl implements ReminderDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public ReminderDAOImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(Reminder reminder) {
		Session session = this.sessionFactory.getCurrentSession();
		session.save(reminder);
		session.flush();		
		return false;

	}

	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(Reminder reminder) {
		return false;

	}

	/*
	 * Remove an existing reminder
	 */

	public boolean deleteReminder(int reminderId) {
		Session session=this.sessionFactory.getCurrentSession();
		try {
			session.delete(this.getReminderById(reminderId));
		} catch (ReminderNotFoundException e) {
			
			e.printStackTrace();
		}
		return false;

	}

	/*
	 * Retrieve details of a specific reminder
	 */

	public Reminder getReminderById(int reminderId) throws ReminderNotFoundException {
		Session session=this.sessionFactory.getCurrentSession();
		Reminder reminder = session.get(Reminder.class, reminderId);
		session.flush();
		if (null == reminder) {
			throw new ReminderNotFoundException("Reminder with specified id is not found");
		}
		
		return reminder;

	}

	/*
	 * Retrieve details of all reminders by userId
	 */

	public List<Reminder> getAllReminderByUserId(String userId) {
		return null;

	}

}
