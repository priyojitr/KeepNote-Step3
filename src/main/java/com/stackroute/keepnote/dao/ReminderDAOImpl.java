package com.stackroute.keepnote.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
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
	public ReminderDAOImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new reminder
	 */

	public boolean createReminder(final Reminder reminder) {
		final Session session = this.sessionFactory.getCurrentSession();
		session.save(reminder);
		session.flush();
		return false;

	}

	/*
	 * Update an existing reminder
	 */

	public boolean updateReminder(final Reminder reminder) {
		boolean flag = Boolean.TRUE;
		final Session session = this.sessionFactory.getCurrentSession();
		try {
			// check whether reminder id exists or else exception will be thrown
			this.getReminderById(reminder.getReminderId());
			session.clear();
			session.update(reminder);
			session.flush();
		} catch (final ReminderNotFoundException ex) {
			flag = Boolean.FALSE;
			ex.printStackTrace();
		}
		return flag;
	}

	/*
	 * Remove an existing reminder
	 */

	public boolean deleteReminder(final int reminderId) {
		boolean flag = Boolean.TRUE;
		final Session session = this.sessionFactory.getCurrentSession();
		try {
			session.delete(this.getReminderById(reminderId));
			session.flush();
		} catch (final ReminderNotFoundException ex) {
			flag = Boolean.FALSE;
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}
		return flag;
	}

	/*
	 * Retrieve details of a specific reminder
	 */

	public Reminder getReminderById(final int reminderId) throws ReminderNotFoundException {
		final Session session = this.sessionFactory.getCurrentSession();
		final Reminder reminder = session.get(Reminder.class, reminderId);
		session.flush();
		if (null == reminder) {
			throw new ReminderNotFoundException("Reminder with specified id is not found");
		}
		return reminder;
	}

	/*
	 * Retrieve details of all reminders by userId
	 */

	public List<Reminder> getAllReminderByUserId(final String userId) {
		final Session session = this.sessionFactory.getCurrentSession();
		final String hql = "FROM Reminder reminder where reminderCreatedBy = :userId";
		final Query<Reminder> qry = session.createQuery(hql).setParameter("userId", userId);
		return qry.getResultList();
	}

}
