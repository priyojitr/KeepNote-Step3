package com.stackroute.keepnote.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.keepnote.dao.UserDAO;
import com.stackroute.keepnote.exception.UserAlreadyExistException;
import com.stackroute.keepnote.exception.UserNotFoundException;
import com.stackroute.keepnote.model.User;

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
public class UserServiceImpl implements UserService {

	/*
	 * Autowiring should be implemented for the userDAO. (Use Constructor-based
	 * autowiring) Please note that we should not create any object using the new
	 * keyword.
	 */

	private final UserDAO userDAO;

	@Autowired
	public UserServiceImpl(final UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	/*
	 * This method should be used to save a new user.
	 */

	public boolean registerUser(final User user) throws UserAlreadyExistException {
		if(this.userDAO.registerUser(user)) {
			return Boolean.TRUE;
		}else {
			throw new UserAlreadyExistException("user already exist exception");
		}
	}

	/*
	 * This method should be used to update a existing user.
	 */

	public User updateUser(final User user, final String userId) throws Exception {
		this.userDAO.updateUser(user);
		final User updatedUser = this.getUserById(userId);
		if(null==updatedUser) {
			throw new Exception("exception updating user");
		}
		return updatedUser;

	}

	/*
	 * This method should be used to get a user by userId.
	 */

	public User getUserById(final String UserId) throws UserNotFoundException {
		final User user=this.userDAO.getUserById(UserId);
		if(null==user) {
			throw new UserNotFoundException("user not found excpetion");
		}
		
		return user;

	}

	/*
	 * This method should be used to validate a user using userId and password.
	 */

	public boolean validateUser(final String userId, final String password) throws UserNotFoundException {
		final boolean flag = Boolean.TRUE;
		if(!this.userDAO.validateUser(userId, password)) {
			throw new UserNotFoundException("user validation exception");
		}
		return flag;
	}

	/* This method should be used to delete an existing user. */
	public boolean deleteUser(final String UserId) {
		return this.userDAO.deleteUser(UserId);

	}

}
