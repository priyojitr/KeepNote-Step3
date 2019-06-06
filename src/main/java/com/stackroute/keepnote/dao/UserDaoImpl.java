package com.stackroute.keepnote.dao;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserDaoImpl implements UserDAO {

	/*
	 * Autowiring should be implemented for the SessionFactory.(Use
	 * constructor-based autowiring.
	 */

	private final SessionFactory sessionFactory;

	@Autowired
	public UserDaoImpl(final SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/*
	 * Create a new user
	 */

	public boolean registerUser(final User user) {
		boolean flag = Boolean.FALSE;
		try {
			if (null == this.getUserById(user.getUserId())) {
				final Session session = this.sessionFactory.getCurrentSession();
				session.save(user);
				session.flush();
				flag = Boolean.TRUE;
			} else {
				if (user.getUserId().equals(this.getUserById(user.getUserId()).getUserId())) {
					// duplicate user
					throw new UserAlreadyExistException("user id already exists.");
				}
			}
		} catch (final Exception ex) {
			System.out.println(ex.getClass().getName() + ":" + ex.getMessage());
			ex.printStackTrace();
		}
		return flag;
	}

	/*
	 * Update an existing user
	 */

	public boolean updateUser(final User user) {
		if (null == this.getUserById(user.getUserId())) {
			return Boolean.FALSE;
		}
		final Session session = this.sessionFactory.getCurrentSession();
		session.clear();
		session.update(user);
		session.flush();
		return Boolean.TRUE;

	}

	/*
	 * Retrieve details of a specific user
	 */
	public User getUserById(final String UserId) {
		final Session session = this.sessionFactory.getCurrentSession();
		final User user = session.get(User.class, UserId);
		session.flush();
		return user;
	}

	/*
	 * validate an user
	 */

	public boolean validateUser(final String userId, final String password) throws UserNotFoundException {
		final User user = this.getUserById(userId);
		if (null == user) {
			throw new UserNotFoundException("User id provided do not exists.");
		} else {
			if (!password.equals(user.getUserPassword())) {
				return Boolean.FALSE;
			}
		}
		return Boolean.TRUE;

	}

	/*
	 * Remove an existing user
	 */
	public boolean deleteUser(final String userId) {
		final User user = this.getUserById(userId);
		if (null == user) {
			return Boolean.FALSE;
		}

		final Session session = this.sessionFactory.getCurrentSession();
		session.delete(user);
		session.flush();

		return Boolean.TRUE;

	}

}
